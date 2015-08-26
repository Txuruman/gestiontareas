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
    @Inject
    protected InstallationService installationService;
    @Resource(name = "applicationUser")
    private String applicationUser;

    //Dial_sched_time = dd/mm/aaaa hh:mm:ss
    private SimpleDateFormat sdfSchedTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");


    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     */
    public boolean delayTask(Agent agent, Tarea tarea, Date schedTime, String recordType) throws Exception {
        return this.delayTask(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCallingList(), tarea.getId().toString(), schedTime, recordType);
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
            } else {
                LOGGER.warn("Can't finalize task because is in Retrieved state {}", tarea);
            }
        }
        return finalized;
    }

    /**
     * Finalizar tarea de tipo Mantenimiento,    es distinto porque tiene que cancelar la señal IWS
     *
     * Cancelar la señal de IWS al finalizar una Tarea de tipo Mantenimiento y en la gestión de señales, cuando está fuera de horario.
     *
     * TODO 6.	En Tarea de tipo Mantenimiento, al finalizar, ejecutar WS de grabar comlog de IBS con los datos de la pantalla.
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
            } else {
                LOGGER.warn("Can't finalize task because is in Retrieved state {}", tarea);
            }
        }
        return finalized;
    }

    /**
     * Finalizar la tarea de tipo Aviso, es distinta al resto de tareas porque hay que llamar a cancelar
     * @param agent
     * @param tarea
     * @return
     */
    public boolean finalizeNotificationTask(Agent agent, TareaAviso tarea) throws Exception{
        LOGGER.debug("Finalizando tarea Aviso {}", tarea);
        //1. Finalizar la Tarea
        boolean finalized = true;//wsFilanizeTask(agent.getAgentIBS(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), tarea.getCampana(), tarea.getTelefono(), tarea.getCallingList(), tarea.getId());
        if (finalized) {
        	 //2. Modificar
            //Comprobamos si la tarea que nos pasa el front difiere con la de la BBDD, si es asi modificamos
            TareaAviso tareaOriginal= (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
            LOGGER.debug("Comparamos con la tarea de la BBDD {}", tareaOriginal);
            if(!compareTask(tareaOriginal, tarea)){
            	//Buscamos los datos de la instalación de la tarea
            	InstallationData installationData= installationService.getInstallationData(tarea.getNumeroInstalacion());
            	//Modificar
            	boolean modificado=avisoService.updateTicket(agent, tarea, installationData);
            	LOGGER.debug("Tarea modificada {}", modificado);
            }
            //3. Finalizar el aviso
            //TODO finalizadoDesdeMantenimiento
            boolean finalizadoDesdeMantenimiento = false;
            try {
                Integer adicional = 0;
                if (((TareaAviso) tarea).getDatosAdicionalesCierre() != null) {
                    adicional = Integer.valueOf(((TareaAviso) tarea).getDatosAdicionalesCierre());
                }
    ////TODO SOLO DEBUG            finalized = avisoService.closeTicket(tarea.getIdAviso(), agent.getAgentIBS(), tarea.getClosing(), adicional, finalizadoDesdeMantenimiento);
                finalized=true;//TODO SOLO DEBUG
            } catch (Exception e) {
                LOGGER.error("Error Closing Ticket.", e);
                finalized = false;
            }
        }
        return finalized;
    }
    
    /**
     * compareTask: Compara dos tareas
     * @param TareaAviso t1
     * @param TareaAviso t2
     * @return
     */
    private boolean compareTask(TareaAviso t1, TareaAviso t2){
    	return t1.equals(t2);
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
                if (delayed && tarea instanceof TareaAviso) {
                    //Si es de tipo Aviso hay que retrasar el aviso también

                    //TODO PENDIENTE
//                    delayed = avisoService.delayTicket(
//                            ((TareaAviso) tarea).getIdAviso(),
//                            ccUserId,
//
//                    );
                }
            } else {
                LOGGER.warn("Can't delay task because is in Retrieved state {}", tarea);
            }
        } else {
            LOGGER.error("Can't find task to delay");
            //TODO CREAR EXCEPCIONES DE NEGOCIO
        }
        return delayed;
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
     * @return
     */
    private boolean closeIncidence(String installationNumber) {
        CloseIncBTNDIY closeIncInput = new CloseIncBTNDIY();
        closeIncInput.setInsNo(installationNumber);
        closeIncInput.setComment(""); //TODO COMENTARIO?????
        try {
            String closeIncBTNDIYResult = spAioTareas2.closeIncBTNDIY(closeIncInput);
            LOGGER.debug("Closed Incidences for Installation {} with result {}",closeIncInput.getInsNo(),closeIncBTNDIYResult);
        } catch (DataServiceFault dataServiceFault) {
            LOGGER.error("Error closing Incidence",dataServiceFault);
        }
        return true;
    }

}
