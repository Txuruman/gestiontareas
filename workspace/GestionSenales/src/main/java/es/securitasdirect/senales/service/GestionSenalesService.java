package es.securitasdirect.senales.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.webservice.*;
import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.model.SignalMetadata;
import es.securitasdirect.senales.model.SmsMessageLocation;
import es.securitasdirect.senales.support.FileService;
import es.securitasdirect.ws.ReportingTareas;
import es.securitasdirect.ws.ReportingTareasDetalle;
import net.java.dev.jaxb.array.StringArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.wso2.ws.dataservice.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.jws.WebParam;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static final String EMPTY = "";

    @Autowired
    protected SPAIOTAREAS2PortType wsSpAioTareas2;
    @Autowired
    protected SPInstallationMonDataPortType wsSPInstallationMonData;
    @Autowired
    protected CCLIntegration cclIntegration;
    @Autowired
    protected SPIBSCommlogDataPortType wsIBSCommlog;
    @Autowired
    protected ReportingTareas reportingTareas;


    @Resource
    protected Integer daysToDiscardOldMessages;
    @Resource
    protected String ccIdentifier;
    @Resource
    protected String nameGroup;
    @Resource
    protected String applicationUser;
    @Resource
    protected String ccUserId;
    @Resource
    protected String callingList;
    @Resource
    protected String campaign;
    @Resource
    protected String country;
    @Resource
    protected String smsAccount;
    @Resource
    protected String smsTestPhone;

    protected Date upTime = new Date();
    protected AtomicInteger successfulMessages = new AtomicInteger();
    protected AtomicInteger errorMessages = new AtomicInteger();
    protected AtomicInteger inWorkingHoursMessages = new AtomicInteger();
    protected AtomicInteger outWorkingHousMessages = new AtomicInteger();

    protected class MixedInstallationData {
        public GetInstallationDataResult installationDataResultTareas;
        public Mainstallationdataresult installationDataResultInstallation;
    }

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

    /**
     * Prefijo telefonico de los distintos paises para utiliza anteponiéndolo al número de telefono
     */
    @Resource(name = "countryPhoneCodes")
    private Map<String, String> countryPhoneCodes;

    /**
     * Map of sms messages locations
     */
    @Resource(name = "smsLocation")
    private Map<String, SmsMessageLocation> smsMessageLocationMap;

    @Autowired
    private FileService fileService;



    @PreDestroy
    public void shutdown() {

    }


    @Async
    public void onMessage(Message message) {
        processMessage(message);
    }

    /**
     * Para los test unitarios, procesamiento síncrono para que el test no termine antes que el proceso.
     *
     * @param messsage
     */
    protected void onMessageSynchonous(Message messsage) {
        processMessage(messsage);
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
            LOGGER.debug("Discarting duplicated  {}", message);
        } else if (!isAllowedType(message)) {
            LOGGER.error("Not allowed type  {}", message);
        } else if (isExpired(message)) {
            LOGGER.warn("Expired  {}", message);
        } else {

            try {
                if (!isWorkingHours(message)) {
                    //Si la señal ha llegado fuera del horario de atención establecido
                    LOGGER.debug("Processing Out of working hours {}", message);
                    processMessageOutOfWorkingHours(message);
                } else {
                    //Si la señal ha llegado dentro del horario de atencion
                    LOGGER.debug("Processing  {}", message);
                    processMessageInWorkingHours(message);
                }
                processedOk = true;
            } catch (Exception e) {
                LOGGER.error("Error processing message {}", message, e);
                onError(message);
                processedOk = false;
            }
            if (processedOk) {
                markProcessed(message);
            } //Los mensajes con error no se marcan como procesados.
        }

    }

    /**
     * Process the message IN Working Hours.
     * Cuando una señal debe generar una Tarea, se accederá a los repositorios correspondientes para obtener el resto de información necesaria.
     *
     * @param message
     */
    private void processMessageInWorkingHours(Message message) throws Exception {
        inWorkingHoursMessages.incrementAndGet();

        // El campo a utilizar para obtener el resto de información antes de insertar el registro en Tareas será InsNumber_e.
        //Obtener instalacion
        Integer insNumberE = message.getCibb().getEVENTS().getInsNumberE();
        MixedInstallationData installationData = getInstallationData(insNumberE);

        if (installationData == null) {
            LOGGER.error("Discarding message because can't find installation {} of message {}", insNumberE, message);
        } else {

            //Nota, ya no hace falta, estos datoa van a ir constantes. Obtener Calling List ,Una vez que se dispone de todos los datos, se pueden invocar los métodos de la capa CCL Obtener Calling List Tareas
            // // ClResponse callingListANDCampaign = getCallingListANDCampaign(message);


            try {
                // Insertar contacto en calling list. (	Una vez que se dispone de todos los datos, se pueden invocar los métodos de la capa CCL Obtener Calling List Tareas e Insertar contacto en calling list)
                insertCallingListContact(message, installationData);
                LOGGER.debug("Processed sucesfully Message {}", message);
                successfulMessages.incrementAndGet();
            } catch (Exception e) {
                errorMessages.incrementAndGet();
                LOGGER.error("Error insertingCallingListContact {}", e.getMessage(), e);
                throw e;
            }

        }
    }

    /**
     * Process the message OUT of Working Hours.
     * <p/>
     * Si la hora actual está fuera del rango horario establecido (de 9 a 21 horas), se enviará un SMS al cliente, con el texto en el idioma adecuado, invocando un web service.
     * A continuación debe cancelar la incidencia en IBS, invocando un web service.
     * El texto del SMS es el siguiente:
     * 	Español: “Securitas Direct le informa que para darles asistencia en el cambio de pilas, deberan pulsar el boton de 9 a 21 h, de L a S, lo activamos de nuevo. Gracias.”
     * 	Resto de idiomas: “Securitas Direct informs, to change the batteries, you must press the envelope bottom from 9 to 21, Mon thru Sat, We´ll activate the bottom again. Thank you.”
     *
     * @param message
     */
    private void processMessageOutOfWorkingHours(Message message) throws DataServiceFault {
        outWorkingHousMessages.incrementAndGet();

        // El campo a utilizar para obtener el resto de información antes de insertar el registro en Tareas será InsNumber_e.
        Integer insNumberE = message.getCibb().getEVENTS().getInsNumberE();

        //Buscar Instalacion
        MixedInstallationData installationData = getInstallationData(insNumberE);

        //Envio del SMS al cliente
        sendSMSOutOfWorkingHours(message, installationData);
        //Después de enviar el SMS, hay que enviar un comlog a IBS. Los datos son:
        addComlogAfterSMS(message, installationData);

        //A continuación debe cancelar la incidencia en IBS, invocando un web service.
        boolean closeIncidence = closeIncidence(installationData);

        if (closeIncidence) {
            successfulMessages.incrementAndGet();
        } else {
            errorMessages.incrementAndGet();
        }
    }

    /**
     * Closes the incidence in IBS with the SpAioTareas2 WS
     *
     * @return
     */
    private boolean closeIncidence(MixedInstallationData installationData) {
        CloseIncBTNDIY closeIncInput = new CloseIncBTNDIY();
        closeIncInput.setInsNo(installationData.installationDataResultTareas.getInsNo());
        closeIncInput.setComment("");
        try {
            String closeIncBTNDIYResult = wsSpAioTareas2.closeIncBTNDIY(closeIncInput);
            LOGGER.debug("Closed Incidences for Installation {} with result {}", closeIncInput.getInsNo(), closeIncBTNDIYResult);
        } catch (DataServiceFault dataServiceFault) {
            LOGGER.error("Error closing Incidence", dataServiceFault);
            return false;
        }
        return true;
    }

    /**
     * Sends the SMS throw the cclIntegration WS
     *
     * @param message
     * @param mixedInstallationData
     * @return
     */
    private boolean sendSMSOutOfWorkingHours(Message message, MixedInstallationData mixedInstallationData) {
        ////Envio de SMS

        //Seleccionar el texto a enviar en el mensaje
        String outOfWorkingHoursText = getSmsMessageText(message);

        //TODO Repasar Parametros
        String ccIdentifier = this.ccIdentifier; //Configurado estatico en app
        String applicationUser = this.applicationUser; //Configurado estatico en app
        String ccUserId = this.ccUserId; //Configurado estatico en app
        String destination = null;
        if (smsTestPhone != null && !smsTestPhone.isEmpty()) {
            destination = smsTestPhone;
        } else {
            destination = mixedInstallationData.installationDataResultInstallation.getTelefonoServicio();
            if (destination == null || destination.isEmpty()) {
                LOGGER.error("Can't send SMS to installation {} without phone", mixedInstallationData.installationDataResultInstallation.getInsNo());
                return false;
            }
        }

        //Segun correo de Jesus:Otra cosa. Me ha dicho que en XML que llega de la señal está el país, para el envío del SMS.
        // Eso incluye el prefijo del teléfono.
        String phoneCountry = message.getLanguageLocationKey();
        if (destination != null && !destination.startsWith("+")) {
            String contryPrefix = countryPhoneCodes.get(phoneCountry);
            if (contryPrefix == null) {
                LOGGER.warn("Not configured phone prefix for country ", phoneCountry);
            } else {
                destination = contryPrefix + destination;
            }
        }
        String text = outOfWorkingHoursText;
        String account = smsAccount; //Configurado estatico en app
        String country = this.country;  //Configurado estatico en app

        LOGGER.debug("SMS sending  '{}'", destination, text);

        WsResponse wsResponse = cclIntegration.sendSMS(ccIdentifier,
                applicationUser,
                ccUserId,
                destination,
                text,
                account,
                country);
        ////Envio SMS End

        if (wsResponse.getResultCode() == 200) {
            return true;
        } else {
            LOGGER.error("Error sending SMS {}-{}", wsResponse.getResultCode(), wsResponse.getResultMessage());
            return false;
        }
    }


    /**
     * Después de enviar el SMS, hay que enviar un comlog a IBS. Los datos son:
     * ins_no = instalación
     * deal_id = 0
     * source = CT
     * key1 = COM_PR
     * key2, key3, key4 = vacíos
     * media = BOT
     * dir = O
     * res_code = CERR
     * texto = "Envío automático SMS DIY por pulsación del botón fuera de horario"
     * contact_name = vacío
     * contact_phone = teléfono de servicio
     * userid = ATOMIC
     */

    private boolean addComlogAfterSMS(Message message, MixedInstallationData mixedInstallationData) {
        LOGGER.debug("Registering Commlog after SMS {}", message);
        String insNo = mixedInstallationData.installationDataResultTareas.getInsNo(); //ins_no = instalación
        String dealId = "0";//deal_id = 0  fijo
        String source = "CT"; //source = CT
        String key1 = "COM_PR"; //key1 = COM_PR
        String key2 = ""; //key2, key3, key4 = vacíos
        String key3 = ""; //key2, key3, key4 = vacíos
        String key4 = ""; //key2, key3, key4 = vacíos
        String media = "BOT"; // media = BOT
        String dir = "O"; //	dir = O  fijo
        String resCode = "CERR"; //	res_code = CERR  fijo
        String longtext = "Envío automático SMS DIY por pulsación del botón fuera de horario"; //texto = "Envío automático SMS DIY por pulsación del botón fuera de horario"
        String contactName = ""; // contact_name = vacío
        String contactPhone = mixedInstallationData.installationDataResultInstallation.getTelefonoServicio();
        String userid = "ATOMIC";


        List<CreateFullCommLogResult> fullCommLog;
        try {
            fullCommLog = wsIBSCommlog.createFullCommLog(insNo,
                    dealId,
                    source,
                    key1,
                    key2,
                    key3,
                    key4,
                    media,
                    dir,
                    resCode,
                    longtext,
                    contactName,
                    contactPhone,
                    userid);
        } catch (DataServiceFault e) {
            LOGGER.error("Can't register CommLog {}", message, e);
            return false;
        }

        if (fullCommLog == null || fullCommLog.isEmpty()) {
            //Entendemos que la respuesta vacia es correcta
            //            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
            //            <soapenv:Body>
            //            <CreateFullCommLogResults xmlns="http://ws.wso2.org/dataservice"/>
            //            </soapenv:Body>
            //            </soapenv:Envelope>
            return true;
        } else {
            if (fullCommLog.get(0).getX().equals("0")) {
                return true;
            } else {
                LOGGER.error("Error CrateFullComLog {}-{}", fullCommLog.get(0).getX(), fullCommLog.get(0).getComlogSComm());
                return false;
            }
        }
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

    private boolean isAllowedType(Message message) {
        boolean isAllowed = false;
        String messageType = message.getType();
        if (messageType != null) {
            SignalMetadata signalMetadata = allowedQSignals.get(message.getType());
            if (signalMetadata == null) {
                LOGGER.warn("Not allowed signal type {}", messageType);
            } else {
                Integer messageEventType = message.getEventType();
                if (messageEventType == null) {
                    LOGGER.warn("Cannot get the message event type of {}", message);
                } else {
                    if (signalMetadata.getAllowedStatus() == null || !signalMetadata.getAllowedStatus().contains(messageEventType)) {
                        LOGGER.warn("Not allowed signal event type {} {}", messageType, messageEventType);
                    } else {
                        isAllowed = true;
                    }
                }
            }
        } else {
            LOGGER.warn("Cannot get the message type of {}", message);
        }

        return isAllowed;
    }

    /**
     * Marks a message has been processed
     *
     * @param message
     * @return
     */

    private void markProcessed(Message message) {
        processed.put(message.getId(), message.getId());
        //Aqui podemos incluir lo que se quiera como información relevante.
    }

    /**
     * Store the message to be processed later, used when there are errors
     */
    private void onError(Message message) {
        LOGGER.error("Storing Message on error to try later {}", message);
        fileService.writeMessage(message);
    }


    /**
     * Para enviar el SMS, ya no se comprueba el horario. Se debe comprobar si hay agentes logados en el grupo.
     * Hay que ejecutar el ws checkLoginAgentGroup de la CCL.
     * Pero en el ejemplo que me han pasado da error. Cuando lo aclaren, habrá que ponerlo.
     *
     * @return
     */
    protected boolean isWorkingHours(Message message) throws Exception {
        String ccIdentifier = this.ccIdentifier;
        String applicationUser = this.applicationUser;
        String ccUserId = this.ccUserId;
        String nameGroup = this.nameGroup; // El nombre del grupo es TAREAS_DIY
        String country = this.country;

        ChgResponse chgResponse = cclIntegration.checkLoginAgentGroup(ccIdentifier,
                applicationUser,
                ccUserId,
                nameGroup,
                country);

        if (chgResponse.getNumLogin() > 0) {
            return true;//Hay agentes trabajando
        } else if (chgResponse.getOperationResult()!=null && chgResponse.getOperationResult().getResultCode()!=200) {
            LOGGER.error("Error checking login agent {} {}", chgResponse.getOperationResult().getResultCode(),chgResponse.getOperationResult().getResultMessage());
            throw new Exception("Error checking login agent " + chgResponse.getOperationResult().getResultCode() + " " + chgResponse.getOperationResult().getResultMessage());
        } else {
            LOGGER.debug("There are no agents logged in, will send SMS");
            return false; //No hay nadie trabajando
        }

    }


    /**
     * Check if the message is old enought to be discarted
     */
    protected boolean isExpired(final Message message) {
        if (daysToDiscardOldMessages != null && daysToDiscardOldMessages != 0) {
            Calendar calendar = Calendar.getInstance(); // this would default to now
            calendar.add(Calendar.DAY_OF_MONTH, -daysToDiscardOldMessages);
            Date discardDate = calendar.getTime();
            if (message.getEntryDate().before(discardDate)) {
                return true;
            } else {
                return false;
            }
        } else {
            //Is not configured the expiration info
            return false;
        }
    }


    /**
     * Obtiene los datos de instalacion, consultamos los dos web services que hay de instalación porque algunos datos van por uno y otros por el otro
     * - wsSpAioTareas2.getInstallationData
     * -
     *
     * @param insNumberE
     * @return
     * @throws DataServiceFault
     */
    protected MixedInstallationData getInstallationData(Integer insNumberE) throws DataServiceFault {
        MixedInstallationData mixedInstallation = new MixedInstallationData();


        //1.Consulta Instalacion InstallationData
        List<Mainstallationdataresult> wsSPInstallationMonDataInstallationData = wsSPInstallationMonData.getInstallationData(insNumberE.toString());
        if (wsSPInstallationMonDataInstallationData != null && !wsSPInstallationMonDataInstallationData.isEmpty()) {
            mixedInstallation.installationDataResultInstallation = wsSPInstallationMonDataInstallationData.get(0);
        } else {
            LOGGER.warn("Can't find installation data for insNumber {} in wsSPInstallationMonData", insNumberE);
            return null;
        }

        //2. Consulta Instalacion Tareas2
        //Como segundo parametro metemos también el número de instalación , parece que con eso devuelve datos de número de contrato
        GetInstallationDataInput queryInput = new GetInstallationDataInput();
        if (mixedInstallation.installationDataResultInstallation.getSIns() != null) {
            queryInput.setSIns(mixedInstallation.installationDataResultInstallation.getSIns().intValue());
            queryInput.setSCtr(mixedInstallation.installationDataResultInstallation.getSIns().intValue());
            GetInstallationDataResults installationDataTareaResult = wsSpAioTareas2.getInstallationData(queryInput);
            if (installationDataTareaResult != null && installationDataTareaResult != null && !installationDataTareaResult.getGetInstallationDataResult().isEmpty()) {
                mixedInstallation.installationDataResultTareas = installationDataTareaResult.getGetInstallationDataResult().get(0);
            } else {
                LOGGER.error("Can't find installation data for insNumber {} in wsSpAioTareas2", insNumberE);
            }
        } else {
            LOGGER.error("Can't find installation number from InstallationMonData");
        }

        return mixedInstallation;
    }


    /**
     * Insertar contacto en calling list. CCLIntegration.insertCallingListContact
     * Documentación WS30 Diseño Técnico Tareas
     */
    protected void insertCallingListContact(Message message, MixedInstallationData mixedInstallationData) throws Exception {
        LOGGER.debug("Inserting calling list contact for {}", message);
        /*

        Columna de BD OCS	Origen del dato	Dato obtenido
        INSTALACION	WS Tareas	GetInstallationDataResults / ins_no
        CTR_NO	WS Tareas	GetInstallationDataResults / GetContractNumberResponse / ctr_no
        NOMBRE	WS Tareas	GetInstallationDataResults / fname + name
        DIRECCION	WS Tareas	GetInstallationDataResults / street1no2+street1+street1no1+street2
        CIUDAD	WS Tareas	GetInstallationDataResults / city
        PANEL	WS Tareas	GetInstallationDataResults / panel
        VERSION	WS Tareas	GetInstallationDataResults / versión
        TIPO_MANTENIMIENTO	N/A	Valor “DIY”
        TELEFONO1	WS Tareas	GetInstallationDataResults / phone1
        TELEFONO2	WS Tareas	GetInstallationDataResults / phone2
        TELEFONO3	WS Tareas	GetInstallationDataResults / phone3
        IDIOMA	WS Tareas	GetInstallationDataResults / skill  quedándose con los tres primeros caracteres que son los del idioma (A alemán, I inglés, E español)
        CLNAME	Config. CCL	Obtenido del getCallingListANDCampaing
        SEC_COMMENT	N/A	Vacío
        NOTCALLID	N/A	Concatenación de instalación_DIY_telefono1
        F_CREACION_TAREA	Señal	Fecha / Hora del evento en formato dd/mm/aaaa hh:mm:ss
        Atributo “DataTime” de la etiqueta EVENTS
         */

        //Datos básicos
        String ccIdentifier = this.ccIdentifier; //Configurado estatico en app
        String applicationUser = this.applicationUser; //Configurado estatico en app
        String ccUserId = this.ccUserId; //Configurado estatico en app
        String date = null;  // date = vacío  para contactar en el momento
        String hour = null;   // 	hour = vacío  para contactar en el momento
        String dialRule = null;  //	dial rule = vacío
        String timeFrom = null; //	timeFrom = vacío  tomará el de la campaña
        String timeUntil = null;    // 	timeUntil = vacío  tomará el de la campaña
        String campaign = this.campaign; //Configurado estatico en app
        String country = this.country; //Configurado estatico en app
        String ctrNo = null; //Numero de contrato de los datos de instalación
        if (!(mixedInstallationData.installationDataResultTareas.getGetContractNumberResponse() == null || mixedInstallationData.installationDataResultTareas.getGetContractNumberResponse().getGetContractNumberResponses() == null || mixedInstallationData.installationDataResultTareas.getGetContractNumberResponse().getGetContractNumberResponses().isEmpty() || mixedInstallationData.installationDataResultTareas.getGetContractNumberResponse().getGetContractNumberResponses().get(0).getCtrNo() == null)) {
            ctrNo = mixedInstallationData.installationDataResultTareas.getGetContractNumberResponse().getGetContractNumberResponses().get(0).getCtrNo();
        }

        if (ctrNo == null) {
            LOGGER.warn("Can't find contract in installation data, will use the installation number for the contract");
            ctrNo = mixedInstallationData.installationDataResultInstallation.getInsNo();
        }

        String isEquals = "true";  // isEqual = true

        //Mapa con los datos a insertar de Tarea
        //        	insertValues= listas de campos y valores a insertar, por parejas:
        //              <item>campo</item>
        //              <item>valor</item>
        //        Los valores son:
        //              	Instalación = número de instalación (ins_no)
        //              	Contrato = número de instalación (ctr_no)
        //              	Nombre = Persona de contacto
        //              	Teléfono = Phone 1 de la instalación
        //              	Dirección = dirección de la instalación
        //              	Ciudad = ciudad de la instalación
        //              	Panel = panel de la instalación
        //              	Versión = versión de la instalación
        //              	Fecha evento = fecha actual (recepción de la señal)
        //              	Hora evento = hora actual (recepción de la señal)
        //              	Teléfono 1= Phone 1 del contacto 1 del plan de acción de la instalación
        //              	Teléfono 2= Phone 1 del contacto 2 del plan de acción de la instalación
        //              	Teléfono 3= Phone 2 de la instalación

        List<net.java.dev.jaxb.array.StringArray> insertValues = new ArrayList<StringArray>();

        //INSTALACION	WS Tareas	GetInstallationDataResults / ins_no
        addStringArray(insertValues, "INSTALACION", mixedInstallationData.installationDataResultTareas.getInsNo());

        //NOMBRE	WS Tareas	GetInstallationDataResults / fname + name
        addStringArray(insertValues, "NOMBRE", mixedInstallationData.installationDataResultTareas.getFname() + mixedInstallationData.installationDataResultTareas.getName());

        //DIRECCION	WS Tareas	GetInstallationDataResults / street1no2+street1+street1no1+street2
        addStringArray(insertValues, "DIRECCION", mixedInstallationData.installationDataResultTareas.getStreet1() + mixedInstallationData.installationDataResultTareas.getStreet1() + mixedInstallationData.installationDataResultTareas.getStreet1No1() + mixedInstallationData.installationDataResultTareas.getStreet2());

        //CIUDAD	WS Tareas	GetInstallationDataResults / city
        addStringArray(insertValues, "CIUDAD", mixedInstallationData.installationDataResultTareas.getCity());

        //PANEL	WS Tareas	GetInstallationDataResults / panel
        addStringArray(insertValues, "PANEL", mixedInstallationData.installationDataResultTareas.getPanel());

        //VERSION	WS Tareas	GetInstallationDataResults / versión
        addStringArray(insertValues, "VERSION", mixedInstallationData.installationDataResultTareas.getVersion());

        // TIPO_MANTENIMIENTO	N/A	Valor “DIY” //TODO QUE ES ESTO DA ERROR
//        StringArray saTipoMantenimiento = new StringArray();
//        insertValues.add(saTipoMantenimiento);
//        saTipoMantenimiento.getItem().add("TIPO_MANTENIMIENTO");
//        saTipoMantenimiento.getItem().add("DIY");

        // TELEFONO1	WS Tareas	GetInstallationDataResults / phone1 TODO PENDIENTE REPASAR
        if (mixedInstallationData.installationDataResultInstallation != null && mixedInstallationData.installationDataResultInstallation.getInstallationcontactsresults().getInstallationcontactsresult().size() > 0 && mixedInstallationData.installationDataResultInstallation.getInstallationcontactsresults().getInstallationcontactsresult().get(0) != null) {
            addStringArray(insertValues, "TELEFONO1", mixedInstallationData.installationDataResultInstallation.getInstallationcontactsresults().getInstallationcontactsresult().get(0).getPH1());
        }

        // TELEFONO2	WS Tareas	GetInstallationDataResults / phone2 TODO PENDIENTE REPASAR
        if (mixedInstallationData.installationDataResultInstallation != null && mixedInstallationData.installationDataResultInstallation.getInstallationcontactsresults().getInstallationcontactsresult().size() > 1 && mixedInstallationData.installationDataResultInstallation.getInstallationcontactsresults().getInstallationcontactsresult().get(1) != null) {
            addStringArray(insertValues, "TELEFONO2", mixedInstallationData.installationDataResultInstallation.getInstallationcontactsresults().getInstallationcontactsresult().get(1).getPH1());
        }

        // TELEFONO3	WS Tareas	GetInstallationDataResults / phone3  TODO PENDIENTE REPASAR
        if (mixedInstallationData.installationDataResultInstallation != null) {
            addStringArray(insertValues, "TELEFONO3", mixedInstallationData.installationDataResultInstallation.getPhone());
        }

        // IDIOMA	WS Tareas	GetInstallationDataResults / skill  quedándose con los tres primeros caracteres que son los del idioma (A alemán, I inglés, E español)
        addStringArray(insertValues, "IDIOMA", mixedInstallationData.installationDataResultTareas.getSkill().substring(2, 3));

        // CLNAME	Config. CCL	Obtenido del getCallingListANDCampaing
        addStringArray(insertValues, "CLNAME", this.callingList);  //Configurado estatico en app

        //Rellenar el campo "CUSTOM01" con la fecha y hora que viene en la señal TODO FORMATO???
        addStringArray(insertValues, "CUSTOM01", new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(message.getEntryDate()));


        // SEC_COMMENT	N/A	Vacío
        // NOTCALLID	N/A	Concatenación de instalación_DIY_telefono1
        // F_CREACION_TAREA	Señal	Fecha / Hora del evento en formato dd/mm/aaaa hh:mm:ss //TODO Atributo “DataTime” de la etiqueta EVENTS que formato tiene este valor??? si queremos utilizarlo
        addStringArray(insertValues, "F_CREACION_TAREA", new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(message.getEntryDate()));


        //Numeros de contacto
        //        	numbers = teléfono de contacto (Phone 1 de la instalación), en el formato indicado: 3 <ítem> con los valores:
        //        	Orden del teléfono: 0
        //        	Tipo de contacto: 0: No Contact Type, 1: Home Phone, 2: Direct Bussiness Phone, 3: Bussiness whit ext, 4: Mobile, 5: Vacation Phone, 6: Pager, 7: Modem, 8: Voice Mail, 9: Pin Pager, 10: E-mail Address
        //        	Valor del contacto: número de teléfono

        List<net.java.dev.jaxb.array.StringArray> numbers = new ArrayList<StringArray>();
        StringArray saNumber0 = new StringArray();
        numbers.add(saNumber0);
        saNumber0.getItem().add("0"); //Orden del teléfono: 0
        // De donde sale este tipo de contacto. Lo dejamos estático como mobile
        saNumber0.getItem().add("4"); //Tipo de contacto: 0: No Contact Type, 1: Home Phone, 2: Direct Bussiness Phone, 3: Bussiness whit ext, 4: Mobile, 5: Vacation Phone, 6: Pager, 7: Modem, 8: Voice Mail, 9: Pin Pager, 10: E-mail Address
        saNumber0.getItem().add(mixedInstallationData.installationDataResultTareas.getPhone1()); //Valor del contacto: número de teléfono


        IclResponse iclResponse = cclIntegration.insertCallingListContact(
                ccIdentifier,
                applicationUser,
                ccUserId,
                insertValues,
                date,
                hour,
                dialRule,
                timeFrom,
                timeUntil,
                callingList, //Configurado estatico en app
                campaign,
                numbers,
                country,
                ctrNo, //Requerido
                isEquals
        );


        if (iclResponse.getOperationResult().getResultCode() != 200) {
            throw new Exception("Error in insertCallingList " + iclResponse.getOperationResult().getResultCode() + "-" + iclResponse.getOperationResult().getResultMessage());
        } else {
            if (iclResponse.getReturnData() != null && !iclResponse.getReturnData().isEmpty()) {
                for (ReturnData returnData : iclResponse.getReturnData()) {
                    if (returnData != null && returnData.getOperationResult() != null && returnData.getOperationResult().getResultCode() != 200) {
                        throw new Exception("Error in insertCallingList " + returnData.getOperationResult().getResultCode() + "-" + returnData.getOperationResult().getResultMessage());
                    }
                }
                wsReportingTareas(
                        message.getEntryDate(), // fechaCreacionTarea
                        iclResponse.getReturnData().get(0).getChainId(), //idTarea
                        ccUserId, //usuarioCreacion
                        callingList, //callingList
                        ctrNo,//numInstalacion
                        mixedInstallationData.installationDataResultTareas.getPanel(),  // panel
                        mixedInstallationData.installationDataResultTareas.getVersion(), // version
                        message.getEntryDate(), // fechaEvento
                        ccUserId, //agentAccion
                        "0", //agentConnid
                        "NO_INTERACTION", //agentInteractionType
                        null //agentInteractionDirection

                );
            }
        }
    }

    /**
     * Obtiene el mensaje de texto a enviar al usuario
     *
     * @return
     */
    protected String getSmsMessageText(final Message message) {
        String messageLanguageLocationKey = message.getLanguageLocationKey();
        SmsMessageLocation smsMessageLocation = null;
        if (messageLanguageLocationKey != null && !messageLanguageLocationKey.isEmpty()) {
            smsMessageLocation = smsMessageLocationMap.get(messageLanguageLocationKey);
        }
        //If smsMessageLocation is null, for not found message language location key in message location map or for not
        if (smsMessageLocation == null) {
            smsMessageLocation = smsMessageLocationMap.get(SmsMessageLocation.DEFAULT);
        }
        String outOfWorkingHours = smsMessageLocation.getOutOfWorkingHours();
        return outOfWorkingHours;
    }

    private List<net.java.dev.jaxb.array.StringArray> addStringArray(List<net.java.dev.jaxb.array.StringArray> list, String propertyName, Object propertyValue) {
        StringArray sa = null;
        if (propertyValue != null) {
            String value = propertyValue.toString();
            if (value != null && !value.isEmpty()) {
                sa = new StringArray();
                sa.getItem().add(propertyName);
                sa.getItem().add(value);
                list.add(sa);
            }
        }
        return list;
    }


    public int getSuccessfulMessages() {
        return successfulMessages.get();
    }

    public int getErrorMessages() {
        return errorMessages.get();
    }

    public int getInWorkingHoursMessages() {
        return inWorkingHoursMessages.get();
    }

    public int getOutWorkingHousMessages() {
        return outWorkingHousMessages.get();
    }

    public Date getUpTime() {
        return upTime;
    }


    /**
     * Fecha desde String cuando es un valor EPOC.
     * Le restamos dos horas porque lo que envian esta en UTC+2 Paris
     *
     * @param value
     * @return
     */
    protected Date toDateEpocFromMap(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            try {
                //Restamos dos horas
                return new Date((Long.valueOf(value) - (2 * 60 * 60)) * 1000);
            } catch (Exception e) {
                LOGGER.error("Can't parse EPOC date from value {}", value, e);
                return null;
            }
        }
    }

    private void wsReportingTareas(Date fechaCreacionTarea, int idTarea, String usuarioCreacion,  String callingList, String numInstalacion, String panel, String version, Date fechaEvento,
                                   String agentAccion, String agentConnid, String agentInteractionType, String agentInteractionDirection)
    {

        ReportingTareasDetalle reportingTareasDetalle = new ReportingTareasDetalle();

        try {

            // DATOS DE LA TAREA

            // fechaCreacionTarea
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(fechaCreacionTarea);
            XMLGregorianCalendar fechCreacionTarea = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            //Esto es necesario porque el formato es <dateTime>2015-09-10T19:19:19</dateTime>
            fechCreacionTarea.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
            fechCreacionTarea.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            reportingTareasDetalle.setTimestampTarea(fechCreacionTarea);
            reportingTareasDetalle.setIdTarea(idTarea);
            reportingTareasDetalle.setUsuarioCreacion(usuarioCreacion);
            reportingTareasDetalle.setCallingList(callingList);
            reportingTareasDetalle.setTipoTarea("MANTENIMIENTO");
            reportingTareasDetalle.setInsNo(numInstalacion);
            reportingTareasDetalle.setPanel(panel);
            reportingTareasDetalle.setVersion(version);
            // fechaEvento
            //GregorianCalendar c = new GregorianCalendar();
            c.setTime(fechaEvento);
            XMLGregorianCalendar fechEvento = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            //Esto es necesario porque el formato es <dateTime>2015-09-10T19:19:19</dateTime>
            fechEvento.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
            fechEvento.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            reportingTareasDetalle.setTimestampSobre(fechEvento);
            reportingTareasDetalle.setSkill(""); // constante


            // DATOS DE LA ACCION

            // fechaActual
            c.setTime(new Date(System.currentTimeMillis()));
            XMLGregorianCalendar fechaActual = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            //Esto es necesario porque el formato es <dateTime>2015-09-10T19:19:19</dateTime>
            fechaActual.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
            fechaActual.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            reportingTareasDetalle.setTimestampAccion(fechaActual);
            reportingTareasDetalle.setAccion("CREAR");
            reportingTareasDetalle.setAgenteAccion(agentAccion);
            reportingTareasDetalle.setConnid(agentConnid);
            reportingTareasDetalle.setInteractionId("0");
            //reportingTareasDetalle.setServicio(agentAccion);
            reportingTareasDetalle.setServicio(agentInteractionType);
            reportingTareasDetalle.setInteractionDirection(agentInteractionDirection);

            //reportingTareas.storeTareasReportingData(reportingTareasDetalle); // TODO NO INVOCAMOS DE MOMENTO

            LOGGER.info("reportingTareasDetalle", reportingTareasDetalle);

        }
        catch (Exception e)
        {
            LOGGER.error("error en reportingTareas", reportingTareasDetalle);
        }
    }
}
