package es.securitasdirect.senales.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.webservice.CCLIntegration;
import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.model.SignalMetadata;
import es.securitasdirect.senales.support.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.GetInstallationDataResult;
import org.wso2.ws.dataservice.SPAIOTAREAS2PortType;
import org.wso2.ws.dataservice.SPInstallationMonDataPortType;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Servicio con la lógica principal para la gestión de señales.
 */
/*
Este sería un ejemplo de la señal en XML que se recoge de la cola:

<PARAMS>
<CIBB>
	<EVENTS MMV="020401" Modelo="Modelo Desconocido" Counter="00B"
Ack="1" InsNumber="00000000" InsNumber_e="1555667" DataTime="1502091042" TypeProtocol="E">
		<EVENT id="UCB">
			<type>2</type>
			<ArmType value='Panel disarm'>D</ArmType>
			<ArmForced value='Panel is not forced armed'>N</ArmForced>
			<Zone value='There is not  any  zone open'>N</Zone>

<CancelPending value='There is not  any  cancel situation
in panel'>N</CancelPending>
			<PanelInFault value='Panel is in Fault'>S</PanelInFault>
			<EventType value='Informative events'>I</EventType>
			<DevIdentification value='Central:Central '>
CE</DevIdentification>
			<EventStatus value='Event status does not apply for this
event.'>N</EventStatus>
			<ArmTime>067511</ArmTime>
			<DevManufacturer>0KYMGB</DevManufacturer>
		</EVENT>
	</EVENTS>  <!-- TimeArrived Mon Feb 09 10:42:35 CET 2015 -->
	<PROPS tfno="0000000000000"
	texto="SDES04702040100B1000000000001502091042E1555667#XDN067511NNSICE0KYMGBNUCB2!C517"
		pais="ESP" host="es1recveri04v" op="MOVS" centro=""
Numero="comfort"
		tipo="SDI2" user="1555667" err=""
		transId="es1recveri04v_rx_GPRS_es_mov7587_20150209104235556"
TimeIn="1423474955000"
		RecepName="rx_GPRS_es_mov7587" Medio="GPRS"
TansmisionType="EVENT"
		SeviceType="comfort" ProtocolType="POSESA" InOrOut="INPUT"
		DestinoType="MACHINE" OrigenType="PANEL" ModeloId=""
origen="panel"
		Ok="true" ServiceType="COM" />
</CIBB>
</PARAMS>

Una vez recogida la señal, el escuchador procesa el  mensaje siguiendo las directrices que se citan a continuación.

Control señales permitidas

La forma en la que se implementa el control de señales permitidas mediante parámetros en el fichero de configuración de CCL (CCLconfig.properties) es la siguiente:

1)	Un parámetro ALLOWED_QSIGNALS que indicará una lista de los códigos de señal permitidos, separados por “;”.
2)	Para cada señal permitida:
2.1)  Un parámetro con el identificador para luego invocar el método de obtención de
calling list y campaña (XXX_SIGNAL_CL_ID).
2.2)  Otro parámetro para indicar el status permitido para la señal
(XXX_SIGNAL_STATUS).Donde XXX es el código de señal.

Ejemplo:

ALLOWED_QSIGNALS=ILS;UCB
ILS_SIGNAL_CL_ID=DIY_TAREAS_BTN
ILS_SIGNAL_STATUS=1
UCB_SIGNAL_CL_ID=DIY_TAREAS_BTN
UCB_SIGNAL_STATUS=2

Tratamiento señales

•	El campo a utilizar para obtener el resto de información antes de insertar el registro en Tareas será InsNumber_e.
•	Se invocará a un DataService con este dato de la instalación para obtener el resto de campos (Req030): http://10.2.144.63:9763/services/SP_AIO_TAREAS?wsdl
•	Una vez que se dispone de todos los datos, se pueden invocar los métodos de la capa CCL Obtener Calling List Tareas e Insertar contacto en calling list.
Todas las operaciones necesarias para el tratamiento de una señal quedarán delimitadas dentro de una transacción. Si algún paso falla, la transacción se deshará.

 */
@Named(value = "gestionSenales")
@Singleton
public class GestionSenalesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestionSenalesService.class);

    @Autowired
    protected SPAIOTAREAS2PortType spAioTareas2;
    @Autowired
    protected CCLIntegration cclIntegration;

    //Working Hours
    @Resource
    protected Integer startWorkHour;
    @Autowired
    protected Integer startWorkMinute;
    @Autowired
    protected Integer endWorkHour;
    @Autowired
    protected Integer endWorkMinute;

    @Resource
    protected Integer daysToDiscardOldMessages;

//    @Inject
//    protected SPInstallationMonDataPortType spInstallationMonData;

    /**
     * List of processed Messajes to avoid repetition.
     */
    protected Cache<Integer, Integer> processed = CacheBuilder.newBuilder()
            .maximumSize(50)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    /**
     * Map of allowed signals and metadata for each of them
     */
    @Resource(name = "allowedQSignals")
    private Map<String, SignalMetadata> allowedQSignals;

    @Autowired
    private FileService fileService;

    @PostConstruct
    private void init() {
        LOGGER.info("Initializating...");
        //TODO CHECK Y LOG DE LA CONFIGURACON
    }

    public void onMessage(Message message) {
        //TODO ThreadPool o Async
        processMessage(message);
    }

    /**
     * Main proces of the message.
     *
     * @param message
     * @throws Exception
     */
    private void processMessage(Message message) {
        boolean processedOk = false;
        if (isDuplicated(message)) {
            LOGGER.debug("Discarting duplicated message {}", message);
        } else {
            LOGGER.debug("Processing Message {}", message);
            try {


                if (isExpired(message)) {
                    discardExpiredMessage(message);
                } else {
                    if (!isWorkingHours()) {
                        processMessageOutOfWorkingHours(message);
                    } else {
                        //Si la señal ha llegado fuera del horario de atención establecido
                        processMessageInWorkingHours(message);
                    }
                }

                processedOk = true;
            } catch (Exception e) {
                LOGGER.error("Error processing message {}", message);//TODO Añadir excepcion
                onError(message);
                processedOk = false;
            }

            if (processedOk) {
                markProcessed(message);
            } //TODO Marcamos los errores como procesados? creo que no mejor que se controle por el scheduler cuando entran
        }

    }

    /**
     * Process the message IN Working Hours.
     * Cuando una señal debe generar una Tarea, se accederá a los repositorios correspondientes para obtener el resto de información necesaria.
     *
     * @param message
     */
    private void processMessageInWorkingHours(Message message) throws Exception {
        // El campo a utilizar para obtener el resto de información antes de insertar el registro en Tareas será InsNumber_e.
        Integer insNumberE = message.getParamsType().getCIBB().getEVENTS().getInsNumberE();
        GetInstallationDataResult installationData = getInstallationData(insNumberE);

        //Una vez que se dispone de todos los datos, se pueden invocar los métodos de la capa CCL Obtener Calling List Tareas
        //TODO
        // Insertar contacto en calling list.
        //TODO

        //TODO Procesarlo
        if (new Random().nextBoolean() || true) {
            throw new Exception("Error Procesando Mensaje " + message.toString());
        }
        LOGGER.debug("Processed sucesfully Message {}", message);
    }

    /**
     * Process the message OUT of Working Hours.
     * Si la señal ha llegado fuera del horario de atención establecido, se enviará un SMS al cliente, en el idioma correspondiente, y se cancelará la incidencia.
     *
     * @param message
     */
    private void processMessageOutOfWorkingHours(Message message) throws DataServiceFault {
        //TODO Enviar SMS y Calcelar

        // Procesar el mensaje
        // El campo a utilizar para obtener el resto de información antes de insertar el registro en Tareas será InsNumber_e.
        Integer insNumberE = message.getParamsType().getCIBB().getEVENTS().getInsNumberE();
        GetInstallationDataResult installationData = getInstallationData(insNumberE);
    }

    private void discardExpiredMessage(Message message) {
        LOGGER.info("Discarting message because of expired date. {}" , message);
        //TODO Hay que cancelar cosas, mirar el documento
    }

    /**
     * Check if a message has been processed
     *
     * @param message
     * @return
     */
    private boolean isDuplicated(Message message) {
        assert message != null && message.getId() != null : "The message Id must not be null";
        return processed.getIfPresent(message.getId()) != null;
    }

    private void markProcessed(Message message) {
        processed.put(message.getId(), message.getId());//TODO Guardar algo más interesante
    }

    /**
     * Store the message to be processed later, used when there are errors
     */
    private void onError(Message message) {
        LOGGER.error("Storing Message on error to try later {}", message);
        fileService.writeMessage(message);
    }

    private GetInstallationDataResult getInstallationData(Integer insNumberE) throws DataServiceFault {
        List<GetInstallationDataResult> installationData = spAioTareas2.getInstallationData(insNumberE, 1);//TODO Consultar que es el segundo parametro
        if (installationData != null && !installationData.isEmpty()) {
            return installationData.get(0);
        } else {
            LOGGER.error("Can't find installation data for insNumber {}", insNumberE);
            return null;
        }
    }

    /**
     * Detallo a continuación el criterio de obtención de los datos que posteriormente se insertarán en los campos de la calling list y que se obtendrán de la señal y el webservice de tareas, tal y como SD transmitió a INDRA.
     * <p/>
     * No habrá dos chain_n sino un único registro y los teléfonos irán en campos de la calling list. Se han incluido los campos que son obligatorios para el IWS (ctr_no, clname, SEC_comment, notCallId). Al estar el ctr_no ya se utiliza un campo de CONTRATO.
     * <p/>
     * <p/>
     * Columna de BD OCS	Origen del dato	Dato obtenido
     * INSTALACION	WS Tareas	GetInstallationDataResults / ins_no
     * CTR_NO	WS Tareas	GetInstallationDataResults / GetContractNumberResponse / ctr_no
     * NOMBRE	WS Tareas	GetInstallationDataResults / fname + name
     * DIRECCION	WS Tareas	GetInstallationDataResults / street1no2+street1+street1no1+street2
     * CIUDAD	WS Tareas	GetInstallationDataResults / city
     * PANEL	WS Tareas	GetInstallationDataResults / panel
     * VERSION	WS Tareas	GetInstallationDataResults / versión
     * TIPO_MANTENIMIENTO	N/A	Valor “DIY”
     * TELEFONO1	WS Tareas	GetInstallationDataResults / phone1
     * TELEFONO2	WS Tareas	GetInstallationDataResults / phone2
     * TELEFONO3	WS Tareas	GetInstallationDataResults / phone3
     * IDIOMA	WS Tareas	GetInstallationDataResults / skill  quedándose con los tres primeros caracteres que son los del idioma (A alemán, I inglés, E español)
     * CLNAME	Config. CCL	Obtenido del getCallingListANDCampaing
     * SEC_COMMENT	N/A	Vacío
     * NOTCALLID	N/A	Concatenación de instalación_DIY_telefono1
     * F_CREACION_TAREA	Señal	Fecha / Hora del evento en formato dd/mm/aaaa hh:mm:ss
     * Atributo “DataTime” de la etiqueta EVENTS
     */
    private void insertInCallingList() {
        //TODO cclIntegration.
    }

    protected boolean isWorkingHours() {
        return isWorkingHours(new Date());
    }

    /**
     * Checks if the actual time is in the working hours
     */
    protected boolean isWorkingHours(Date date) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(new Date());   // assigns calendar to given date

        calendar.set(Calendar.HOUR_OF_DAY, startWorkHour);
        calendar.set(Calendar.MINUTE, startWorkMinute);
        calendar.set(Calendar.SECOND, 0);
        Date startWork = calendar.getTime();
        if (startWork.after(date)) {
            return false;
        }

        calendar.set(Calendar.HOUR_OF_DAY, endWorkHour);
        calendar.set(Calendar.MINUTE, endWorkMinute);
        calendar.set(Calendar.SECOND, 0);
        Date endWork = calendar.getTime();
        if (endWork.before(date)) {
            return false;
        }

        return true;
    }

    /**
     * Check if the message is old enought to be discarted
     */
    protected boolean isExpired(final Message message) {
        if (daysToDiscardOldMessages!=null && daysToDiscardOldMessages!=0) {
            Calendar calendar = Calendar.getInstance(); // this would default to now
            calendar.add(Calendar.DAY_OF_MONTH, -daysToDiscardOldMessages);
            Date discardDate = calendar.getTime();
            if (message.getEntryDate().before(discardDate)) {
                return true;
            } else {
                return false;
            }
        } else {
            //Is not configured
            return false;
        }
    }


}
