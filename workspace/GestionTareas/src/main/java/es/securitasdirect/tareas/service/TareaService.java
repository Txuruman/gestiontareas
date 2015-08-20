package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CCLIntegrationService;
import com.webservice.IclResponse;
import com.webservice.WsResponse;
import es.securitasdirect.tareas.model.*;
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
    protected QueryTareaService queryTareaService;
    @Inject
    protected AvisoService avisoService;
    @Inject
    protected CCLIntegration cclIntegration;
    @Resource(name = "applicationUser")
    private String applicationUser;

    //Dial_sched_time = dd/mm/aaaa hh:mm:ss
    private SimpleDateFormat sdfSchedTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    @Resource(name = "datosCierreTareaAviso")
    protected Map<String, Map<Integer, String>> datosCierreTareaAviso;



    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     */
    public boolean delayTask(Agent agent, Tarea tarea, Date schedTime, String recordType) throws Exception {
        return this.delayTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCallingList(), tarea.getId().toString(), schedTime, recordType);
    }

    /**
     * Finalizar la tarea
     * @param agent
     * @param tarea
     * @return
     */
    public boolean finalizeTask(Agent agent,Tarea tarea) {
        //1. Finalizar la Tarea
        boolean finalized = wsFilanizeTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId());
        return finalized;
    }

    /**
     * Finalizar la tarea de tipo Aviso, es distinto porque tiene que cancelar el aviso también
     * @param agent
     * @param tarea
     * @return
     */
    public boolean finalizeNotificationTask(Agent agent,TareaAviso tarea) {
        LOGGER.debug("Finalizando tarea Aviso {}" , tarea);
        //1. Finalizar la Tarea
        boolean finalized = wsFilanizeTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId());
        if (finalized) {
            //2. Finalizar el aviso
            //TODO finalizadoDesdeMantenimiento
            boolean finalizadoDesdeMantenimiento=false;
            //finalized = avisoService.closeTicket(tarea.getIdAviso(),agent.getIdAgent(),tarea.getClosing(),tarea.getDatosAdicionalesCierre(),finalizadoDesdeMantenimiento);
        }
        return finalized;
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

        //Consultar la tarea de nuevo
        Tarea tarea = queryTareaService.queryTarea(ccUserId, country, desktopDepartment, callingList, idTarea);
        if (tarea != null) {
            //Si no está en memoria se puede ejecutar
            if (!tarea.isRetrieved()) {
                boolean delayed = ccdDelayTask(ccUserId, country, desktopDepartment, tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId(), schedTime, recordType);
                if (!delayed) {
                    return false;
                } else {
                    //TODO Si es de tipo Aviso aplazar el aviso
                    // Invocará un web service para actualizar el Aviso.
                    return true;
                }
            } else {
                LOGGER.warn("Can't delay task because is in Retrieved {}", tarea);
                return false;
            }

        } else {
            LOGGER.error("Can't find task to delay");
            //TODO CREAR EXCEPCIONES DE NEGOCIO
            return false;
        }

    }

    public boolean createTask(Agent agent,TareaMantenimiento tarea) {
        LOGGER.debug("Creating task: {}", tarea);
    public boolean createTask(Agent agent,TareaAviso tarea) {
        LOGGER.debug("Creating task: {}", tarea);        boolean result;
        try {
            // TODO rellenar las variables
            String ccIdentifier= agent.getAgentGroupSD();
            String ccUserId = agent.getIdAgent();
            List<StringArray> insertValues = new ArrayList<StringArray>();
            String date = "";
            String hour = "";
            String dialRule = ""; // constante
            String timeFrom = ""; // constante
            String timeUntil = ""; // constante
            String callingList = Constants.TAREA_MANTENIMIENTO; // constante
            String campaing = Constants.TAREA_MANTENIMIENTO; // constante
            List<StringArray> numbers = new ArrayList<StringArray>();
            String country = agent.getAgentCountryJob();
            String ctrNo = tareaMantenimiento.getNumeroInstalacion();
            String isEquals = "true"; // constante
            // Llamada WS crear tarea
            IclResponse iclResponse = cclIntegration.insertCallingListContact(ccIdentifier, applicationUser, ccUserId, insertValues,
                    date, hour, dialRule, timeFrom, timeUntil, callingList, campaing, numbers, country, ctrNo, isEquals);

            if (iclResponse!= null && iclResponse.getOperationResult().getResultCode() ==  200) {
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
        saTime.getItem().add("dial_sched_time");
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
}
