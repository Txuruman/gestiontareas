package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.IclResponse;
import com.webservice.WsResponse;
import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.web.controller.params.TaskServiceParams;
import net.java.dev.jaxb.array.StringArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
@Named(value = "tareaService")
@Singleton
public class TareaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TareaService.class);

    //Web Services para hacer pruebas directamente
    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;
    @Inject
    protected SPIBSCommlogDataPortType wsIBSCommlog;
    @Inject
    protected QueryTareaService queryTareaService;
    @Inject
    protected AvisoService avisoService;
    @Inject
    protected CCLIntegration cclIntegration;
    @Inject
    protected InstallationService installationService;
    @Resource(name = "applicationUser")
    private String applicationUser;

    //Dial_sched_time = dd/mm/aaaa hh:mm:ss
    private SimpleDateFormat sdfSchedTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");


    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * Comprueba si la tarea que llega es de tipo Aviso
     */
    public boolean delayTask(Agent agent, Tarea tarea, Date schedTime, String recordType) throws Exception {
        if(tarea instanceof TareaAviso){
        	return this.delayNotificationTask(agent, (TareaAviso)tarea, schedTime, recordType);
        }else{
        	return this.delayTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCallingList(), tarea.getId().toString(), schedTime, recordType);
        }
    }

    /**
     * Finalizar la tarea, cualquier tipo menos TareaAviso y Tarea Mantenimiento
     *
     * @param agent
     * @param tarea
     * @return
     */
    public boolean finalizeTask(Agent agent, Tarea tarea) throws Exception {
        boolean finalized = false;

        //Consultar la tarea de nuevo
        tarea = queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (tarea != null) {
            //Si no está en memoria se puede ejecutar
            if (!tarea.isRetrieved()) {
                //1. Finalizar la Tarea
                finalized = wsFilanizeTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId());
                //2. TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria
            } else {
                LOGGER.warn("Can't finalize task because is in Retrieved state {}", tarea);
            }
        }
        return finalized;
    }

    /**
     * Finalizar tarea de tipo Mantenimiento,    es distinto porque tiene que cancelar la señal IWS
     * <p/>
     * Cancelar la señal de IWS al finalizar una Tarea de tipo Mantenimiento y en la gestión de señales, cuando está fuera de horario.
     * <p/>
     * TODO 6.	En Tarea de tipo Mantenimiento, al finalizar, ejecutar WS de grabar commlogs  de IBS con los datos de la pantalla.
     * <p/>
     * //TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria
     *
     * @param agent
     * @param tarea
     * @return
     */
    public boolean finalizeMaintenanceTask(Agent agent, TareaMantenimiento tarea) throws Exception {
        boolean finalized = false;

        //Consultar la tarea de nuevo
        tarea = (TareaMantenimiento) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (tarea != null) {
            //Si no está en memoria se puede ejecutar
            if (!tarea.isRetrieved()) {
                //1. Finalizar la Tarea
                finalized = wsFilanizeTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId());
                //2. Cancelar la señal
                closeIncidence(tarea.getNumeroInstalacion());

                //3. En Tarea de tipo Mantenimiento, al finalizar, ejecutar WS de grabar commlogs  de IBS con los datos de la pantalla.
                registerCommlog(tarea);

                //TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria
            } else {
                LOGGER.warn("Can't finalize task because is in Retrieved state {}", tarea);
            }
        }
        return finalized;
    }

    /**
     * Finalizar la tarea de tipo Aviso, es distinta al resto de tareas porque hay que llamar a cancelar
     *
     * @param agent
     * @param tarea
     * @return
     */
    public boolean finalizeNotificationTask(Agent agent, TareaAviso tarea) throws Exception {
        LOGGER.debug("Finalizando tarea Aviso {}", tarea);
        //Buscamos los datos de la instalación de la tarea
        InstallationData installationData = installationService.getInstallationData(tarea.getNumeroInstalacion());


        //1. Modificar Aviso si hace falta por haber cambiado los datos. Comprobamos si la tarea que nos pasa el front difiere con la de la BBDD, si es asi modificamos
        TareaAviso tareaOriginal = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (!isTaskRequiresSaveModifications(tareaOriginal, tarea)) {
            boolean modificado = avisoService.updateTicket(agent, tarea, installationData);
            if (!modificado) {
                LOGGER.error("Can't finalize NotificationTask because can't update Ticket");
                return false;
            }
        }

        //2. Finalizar el Aviso
        boolean finalizadoDesdeMantenimiento = false;
        try {
            boolean finalizadoAviso = avisoService.closeTicket(tarea.getIdAviso(), agent.getAgentIBS(), tarea.getClosing(), Integer.valueOf(tarea.getDatosAdicionalesCierre()), finalizadoDesdeMantenimiento);
            if (!finalizadoAviso) {
                LOGGER.error("Can't finalize NotificationTask because can't close Ticket");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error Closing Ticket.", e);
            return false;
        }

        //3. Finalizar la Tarea
        boolean finalizadaTarea = true;//wsFilanizeTask(agent.getAgentIBS(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId());
        if (!finalizadaTarea) {
            LOGGER.error("Error finalizing Notification Task because can't finalize Task");
            return false;
        }

        //TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria

        return true;

    }

    /**
     * isTaskRequiresSaveModifications: Compara dos tareas
     *
     * @param t1
     * @param t2
     * @return
     */

    private boolean isTaskRequiresSaveModifications(TareaAviso t1, TareaAviso t2) {
        return t1.equalsSinDatosCierre(t2);
    }

    private boolean isTaskRequiresSaveModifications2(TareaAviso t1, TareaAviso t2) {
        return t1.equalsConDatosCierre(t2);
    }
    
    /**
     * Función Aplazar específica para tareas de tipo aviso 
     * @param agent
     * @param tarea
     * @param schedTime
     * @param recordType
     * @return
     * @throws Exception
     */
    public boolean delayNotificationTask(Agent agent, TareaAviso tarea, Date schedTime, String recordType) throws Exception {
    	 //Buscamos los datos de la instalación de la tarea
        InstallationData installationData = installationService.getInstallationData(tarea.getNumeroInstalacion());

        //1. Modificar Aviso si hace falta por haber cambiado los datos. Comprobamos si la tarea que nos pasa el front difiere con la de la BBDD, si es asi modificamos
        TareaAviso tareaOriginal = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (!isTaskRequiresSaveModifications(tareaOriginal, tarea)) {
            boolean modificado = avisoService.updateTicket(agent, tarea, installationData);
            if (!modificado) {
                LOGGER.error("Can't finalize NotificationTask because can't update Ticket");
                return false;
            }
        }
        boolean delayed=false;

        //2. Retrasar Tarea
        //Comprobamos que la tarea no esté en memoria, para ello volvemos a buscar
        TareaAviso tarea2=(TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (!tarea2.isRetrieved()) {
        	delayed = ccdDelayTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea2.getCampana(), tarea2.getTelefono(), tarea2.getCallingList(), tarea2.getId(), schedTime, recordType);
	    	 if (delayed) {
	             //Si es de tipo Aviso hay que retrasar el aviso también
	             delayed = false;
	             Date fecha = schedTime;
	             SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
	             String fHasta = format.format(fecha);
	
	             delayed = avisoService.delayTicket(tarea.getIdAviso(),agent.getIdAgent(),"", fHasta);
	    	 }
	     } else {
	    	 LOGGER.warn("Can't delay task because is in Retrieved state {}", tarea2);
	     }
    	return delayed;
    }
    
    
    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * <p/>
     * 1.Comenzamos consultando de nuevo la tarea
     * 2.Si está en memoria...
     * <p/>
     * o	Record_status = 1 (ready)
     * o	Dial_sched_time = dd/mm/aaaa hh:mm:ss
     * o	Recort_type = 5 (personal callback) / 6 (campaing callback)
     */
    public boolean delayTask(String ccUserId, String country, String desktopDepartment,
                             String callingList,
                             String idTarea, Date schedTime, String recordType) throws Exception {
        LOGGER.info("Delaying Task : {} {}", callingList, idTarea);

        boolean delayed = false;
        //Consultar la tarea de nuevo
        Tarea tarea = queryTareaService.queryTarea(ccUserId, country, desktopDepartment, callingList, idTarea);
        if (tarea != null) {
            //Si no está en memoria se puede ejecutar
            if (!tarea.isRetrieved()) {
                delayed = ccdDelayTask(ccUserId, country, desktopDepartment, tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId(), schedTime, recordType);
//                if (delayed && tarea instanceof TareaAviso) {
//                    //Si es de tipo Aviso hay que retrasar el aviso también
//                    delayed = false;
//                    Date fecha = schedTime;
//                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
//                    String fHasta = format.format(fecha);
//
//                    delayed = avisoService.delayTicket(
//                            ((TareaAviso) tarea).getIdAviso(),
//                            ccUserId, "", fHasta
//                    );
//                }

                //TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria

            } else {
                LOGGER.warn("Can't delay task because is in Retrieved state {}", tarea);
            }
        } else {
            LOGGER.error("Can't find task to delay");
            //TODO CREAR EXCEPCIONES DE NEGOCIO
        }
        return delayed;
    }
    public boolean createTask(Agent agent, Tarea tarea) {
    	LOGGER.debug("Creating task: {}", tarea);
    	//TODO: Crear Tarea
    	return true;
    }
    public boolean createTask(Agent agent, TareaMantenimiento tareaMantenimiento) {
        LOGGER.debug("Creating task: {}", tareaMantenimiento);
        boolean result;
        try {
            // TODO Tarea mantenimiento la crea un batch, el usuario desde pantalla crea tarea de aviso
            // TODO rellenar las variables
            String ccIdentifier = agent.getAgentGroupSD();
            String ccUserId = agent.getIdAgent();

            List<StringArray> insertValues = new ArrayList<StringArray>();
            StringArray stringArray = new StringArray();
            stringArray.getItem().add("instalacion");
            stringArray.getItem().add(tareaMantenimiento.getNumeroInstalacion());
            stringArray.getItem().add("contrato");
            stringArray.getItem().add(tareaMantenimiento.getNumeroContrato());
            stringArray.getItem().add("nombre");
            stringArray.getItem().add(tareaMantenimiento.getPersonaContacto());
            stringArray.getItem().add("telefono");
            stringArray.getItem().add(tareaMantenimiento.getTelefono());
            stringArray.getItem().add("direccion");
            stringArray.getItem().add(tareaMantenimiento.getDireccion());
            stringArray.getItem().add("ciudad");
            stringArray.getItem().add(tareaMantenimiento.getCiudad());
            stringArray.getItem().add("panel");
            stringArray.getItem().add("valor"); // TODO
            stringArray.getItem().add("version");
            stringArray.getItem().add("valor"); // TODO
            stringArray.getItem().add("fechaevento");
            stringArray.getItem().add("valor"); // TODO
            stringArray.getItem().add("horaevento");
            stringArray.getItem().add("valor"); // TODO
            stringArray.getItem().add("telefono1");
            stringArray.getItem().add(tareaMantenimiento.getTelefono1());
            stringArray.getItem().add("telefono2");
            stringArray.getItem().add(tareaMantenimiento.getTelefono2());
            stringArray.getItem().add("telefono3");
            stringArray.getItem().add(tareaMantenimiento.getTelefono3());
            insertValues.add(stringArray);

            String date = "21/08/2015"; // TODO
            String hour = "08:34:00"; // TODO
            String dialRule = ""; // constante
            String timeFrom = ""; // constante
            String timeUntil = ""; // constante
            String callingList = Constants.TAREA_MANTENIMIENTO; // constante
            String campaing = Constants.TAREA_MANTENIMIENTO; // constante

            List<StringArray> numbers = new ArrayList<StringArray>();
            StringArray stringArray2 = new StringArray();
            stringArray2.getItem().add(tareaMantenimiento.getTelefono1());
            numbers.add(stringArray2);

            String country = agent.getAgentCountryJob();
            String ctrNo = tareaMantenimiento.getNumeroInstalacion();
            String isEquals = "true"; // constante

            // Llamada WS crear tarea
            IclResponse iclResponse = cclIntegration.insertCallingListContact(ccIdentifier, applicationUser, ccUserId, insertValues,
                    date, hour, dialRule, timeFrom, timeUntil, callingList, campaing, numbers, country, ctrNo, isEquals);

            if (iclResponse != null && iclResponse.getOperationResult().getResultCode() == 200) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("Error creating task: {}", e);
            result = false;
        }
        return result;
    }


    public boolean createMaintenance(Tarea task) {
        LOGGER.debug("Creating maintenance: {}", task);
        boolean result;
        try {
            //TODO Llamada WS crear tarea
            //TODO establecer criterio de OK y KO
            if (true) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("Error creating maintenance: {}", e);
            result = false;
        }
        return result;
    }

    /**
     * WS 27: Guardar commlogs en IBS.
     *
     * @return
     */
    protected boolean registerCommlog(TareaMantenimiento tarea) {
        LOGGER.debug("Registering Commlog for task {}", tarea);
        String insNo = tarea.getNumeroInstalacion();
        String dealId = "0";//deal_id = 0  fijo
        String source = "CT";  //CT  fijo
        String key1 = tarea.getKey1() == null ? "" : tarea.getKey1().toString();
        String key2 = tarea.getKey2() == null ? "" : tarea.getKey2().toString();
        String key3 = ""; //	key3 =  vacío
        String key4 = ""; //	key3 =  vacío
        String media = "TEL"; //	media = TEL  fijo
        String dir = "O"; //	dir = O  fijo
        String resCode = "CERR"; //	res_code = CERR  fijo
        String longtext = tarea.getTextoCancelacion();  //	texto = texto de la cancelación
        String contactName = tarea.getPersonaContacto();  //	contact_name = persona de contacto
        String contactPhone = tarea.getTelefono1();//TODO PENDIENTE	contact_phone = número de teléfono, del último llamado (entre los 3 que hay en la ventana). Si no ha pulsado ninguno, dejarlo vacío.
        String userid = "ATOMIC"; //	userid = “ATOMIC”  fijo
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
            LOGGER.error("Can't register CommLog {}", tarea, e);
            return false;
        }

        if (fullCommLog == null || fullCommLog.isEmpty()) {
            LOGGER.error("Error on CreateFullComLog the response is empty");
            return false;
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
     * Llamada al WS de delay
     *
     * @param ccUserId
     * @param country           del agente
     * @param desktopDepartment alias ccIdentifier
     * @param callingList       calling list
     * @param campaign          de la tarea
     * @param contactInfo       contactInfo
     * @param idTarea           chain_id
     * @param schedTime
     * @param recordType
     * @return
     */
    private boolean ccdDelayTask(String ccUserId, String country, String desktopDepartment,
                                 String campaign, String contactInfo, String callingList,
                                 Integer idTarea, Date schedTime, String recordType) {

        String filter = "chain_id=" + idTarea;

        List<java.lang.String> callingLists = Arrays.asList(callingList);
        List<java.lang.String> campaingns = Arrays.asList(campaign);

        List<net.java.dev.jaxb.array.StringArray> modifyValues = new ArrayList<net.java.dev.jaxb.array.StringArray>();
        net.java.dev.jaxb.array.StringArray saRecordStatus = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        net.java.dev.jaxb.array.StringArray saTime = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        net.java.dev.jaxb.array.StringArray saType = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE

        //   Record_status = 1 (ready)
        saRecordStatus.getItem().add("record_status");
        saRecordStatus.getItem().add("1");
        //  Dial_sched_time = dd/mm/aaaa hh:mm:ss
        saTime.getItem().add( TaskServiceParams.TAREA_COMMONS_FECHA_REPROGRAMACION);
        saTime.getItem().add(sdfSchedTime.format(schedTime));
        // Recort_type = 5 (personal callback) / 6 (campaing callback)
        saType.getItem().add("record_type");
        saType.getItem().add(recordType);

        modifyValues.add(saRecordStatus);
        modifyValues.add(saTime);
        modifyValues.add(saType);

        WsResponse wsResponse = cclIntegration.updateCallingListContact(desktopDepartment, applicationUser, ccUserId, filter, modifyValues, callingLists, campaingns, contactInfo, country);

        if (wsResponse.getResultCode() == 200) {
            return true;
        } else {
            LOGGER.error("Error calling updateCallingListContact to delay {}-{}", wsResponse.getResultCode(), wsResponse.getResultMessage());
            return false;
        }

    }

    /**
     * Llamada al WS de finalizar
     *
     * @return
     */
    private boolean wsFilanizeTask(String ccUserId, String country, String desktopDepartment,
                                   String campaign, String contactInfo, String callingList,
                                   Integer idTarea) {
        String filter = "chain_id=" + idTarea;

        List<java.lang.String> callingLists = Arrays.asList(callingList);
        List<java.lang.String> campaingns = Arrays.asList(campaign);

        List<net.java.dev.jaxb.array.StringArray> modifyValues = new ArrayList<net.java.dev.jaxb.array.StringArray>();
        net.java.dev.jaxb.array.StringArray saStatus = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        net.java.dev.jaxb.array.StringArray saResult = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        modifyValues.add(saStatus);
        modifyValues.add(saResult);
        //   o	Record_status = 3 (updated)
        saStatus.getItem().add("record_status");
        saStatus.getItem().add("3");
        // o	Call_result = 0 (ok)
        saResult.getItem().add("call_result");
        saResult.getItem().add("0");


        WsResponse wsResponse = cclIntegration.updateCallingListContact(desktopDepartment, applicationUser, ccUserId, filter, modifyValues, callingLists, campaingns, contactInfo, country);

        if (wsResponse.getResultCode() == 200) {
            return true;
        } else {
            LOGGER.error("Error calling updateCallingListContact to finalize {}-{}", wsResponse.getResultCode(), wsResponse.getResultMessage());
            return false;
        }
    }


    /**
     * Closes the incidence in IBS with the SpAioTareas2 WS
     *
     * @return
     */
    private boolean closeIncidence(String installationNumber) {
        CloseIncBTNDIY closeIncInput = new CloseIncBTNDIY();
        closeIncInput.setInsNo(installationNumber);
        closeIncInput.setComment("");
        try {
            String closeIncBTNDIYResult = spAioTareas2.closeIncBTNDIY(closeIncInput);
            LOGGER.debug("Closed Incidences for Installation {} with result {}", closeIncInput.getInsNo(), closeIncBTNDIYResult);
        } catch (DataServiceFault dataServiceFault) {
            LOGGER.error("Error closing Incidence", dataServiceFault);
        }
        return true;
    }

    public boolean saveTask(Agent agent, TareaAviso tarea, InstallationData installationData) throws Exception {

        boolean saved = false;

        if (tarea instanceof TareaAviso) {

            TareaAviso tareaOriginal = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());

            boolean ok = false;
            if(!isTaskRequiresSaveModifications2(tareaOriginal, tarea)) {
                ok = avisoService.updateTicket(agent, (TareaAviso) tarea, installationData);
                saved = ok;
            }

            boolean finalized=false;
            if (isTaskRequiresFinalizeModifications(tarea)) {
                // Finalizar Tarea
                finalized = wsFilanizeTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId());
                saved = finalized;
                // TODO desmarcar Aviso de la Tarea (otro ws)
            }


        }

        return saved;
    }

    private boolean isTaskRequiresFinalizeModifications(TareaAviso tarea) {

        boolean result = false;

        if( tarea.getClosing() != null && !tarea.getClosing().isEmpty()
         && tarea.getDatosAdicionalesCierre() != null && !tarea.getDatosAdicionalesCierre().isEmpty() ) {
          return true;
        }

        return result;

    }

}
