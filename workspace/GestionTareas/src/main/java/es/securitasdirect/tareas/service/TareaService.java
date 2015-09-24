package es.securitasdirect.tareas.service;


import com.webservice.CCLIntegration;
import com.webservice.WsResponse;
import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.exceptions.FrameworkException;
import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.model.external.CloseMaintenancePair;
import es.securitasdirect.tareas.model.external.DescriptionPair;
import es.securitasdirect.tareas.service.model.DiscardNotificationTaskResult;
import es.securitasdirect.tareas.service.model.FinalizeMaintenanceTaskResult;
import es.securitasdirect.tareas.web.controller.params.TaskServiceParams;
import es.securitasdirect.ws.ReportingTareas;
import es.securitasdirect.ws.ReportingTareasDetalle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Los tres métodos del CCL a utilizar cuando la tarea está en memoria son:
 * -cancelRecord
 * -finalizeRecord
 * -rescheduleRecord
 */
@Named(value = "tareaService")
@Singleton
public class TareaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TareaService.class);

    //Tiempo mínimo de retraso de una tarea en milisegundos
    private static final long MIN_DELAY_TIME = 5 * 60 * 1000;

    //Si se quiere simular que las tareas están en memoria cambiar a true, Atención!! No dejarlo a true nunca, solo para pruebas
    private boolean SIMULATE_TASKS_IN_MEMORY = false;

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
    @Inject
    protected ExternalDataService externalDataService;
    @Inject
    protected InfopointService infopointService;
    @Inject
    protected ReportingTareas reportingTareas;


    @Resource(name = "applicationUser")
    private String applicationUser;
    @Resource(name = "externalCreateMaintenanceURLFinalizeMaintenanceTask")
    private String externalCreateMaintenanceURLFinalizeMaintenanceTask;

    /**
     * Datos cierre tarea mantenimiento configurados en spring
     */
    @Resource(name = "datosCierreTareaMantenimiento")
    protected List<CloseMaintenancePair> datosCierreTareaMantenimiento;


    //Dial_sched_time = dd/mm/aaaa HH:mm:ss
    private SimpleDateFormat sdfSchedTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * Comprueba si la tarea que llega es de tipo Aviso
     */
    public void delayTask(Agent agent, Tarea tarea, Date schedTime, String recordType) {
        //Validar las horas
        if (schedTime == null || schedTime.getTime() - System.currentTimeMillis() < MIN_DELAY_TIME) {
            throw new BusinessException(BusinessException.ErrorCode.ERROR_DELAY_INCOMPATIBLE_DATE);
        }

        //Llamada a los Delay
        if (tarea instanceof TareaAviso) {
            this.delayNotificationTask(agent, (TareaAviso) tarea, schedTime, recordType);
        } else {
            this.delayOtherTask(agent, tarea, schedTime, recordType);
        }
    }


    /**
     * Finalizar la tarea, cualquier tipo menos TareaAviso y Tarea Mantenimiento
     *
     * @param agent
     * @param tarea
     * @return
     */
    public void finalizeExcelTask(Agent agent, Tarea tarea) throws Exception {

        //1. Consultar la tarea de nuevo
        Tarea tareaRefrescada = queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());

        //Si no está en memoria se puede ejecutar
        if (!isTareaInMemory(tareaRefrescada)) {
            //1. Finalizar la Tarea
            wsFinalizeTask(agent, tarea);
        } else {
            //1. Finalizar un registro cuando está en memoria
            wsFinalizeInMemoryTask(agent, tarea);
        }

        wsReportingTareas(tarea, agent, "GESTIONAR");


    }

    /**
     * Finalizar tarea de tipo Mantenimiento,    es distinto porque tiene que cancelar la señal IWS
     * <p/>
     * Cancelar la señal de IWS al finalizar una Tarea de tipo Mantenimiento y en la gestión de señales, cuando está fuera de horario.
     * <p/>
     * En Tarea de tipo Mantenimiento, al finalizar, ejecutar WS de grabar commlogs  de IBS con los datos de la pantalla.
     * <p/>
     *
     * @param agent
     * @param tarea
     */
    public FinalizeMaintenanceTaskResult finalizeMaintenanceTask(Agent agent, TareaMantenimiento tarea, Integer lastCalledPhone) throws Exception {
        FinalizeMaintenanceTaskResult result = new FinalizeMaintenanceTaskResult();
        result.setAgent(agent); //Lo asignamos siempre para que se pueda refrescar en NG sin problea

        //Consultar la tarea de nuevo
        TareaMantenimiento tareaRefrescada = (TareaMantenimiento) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        //Si no está en memoria se puede ejecutar
        if (!isTareaInMemory(tareaRefrescada)) {
            //1. Finalizar la Tarea
            wsFinalizeTask(agent, tarea);
        } else {
            //1. Finalizar un registro cuando está en memoria
            wsFinalizeInMemoryTask(agent, tarea);
        }
        //2. Cancelar la señal
        closeIncidence(tarea.getNumeroInstalacion());

        //3. En Tarea de tipo Mantenimiento, al finalizar, ejecutar WS de grabar commlogs  de IBS con los datos de la pantalla.
        registerCommlog(tarea, lastCalledPhone);

        //4. Si lo que se ha seleccionado como tipo de cancelación requiere que se abra ventana de mantenimiento lo indicamos en la respuesta
        result.setOpenMaintenanceWindow(isRequireOpenWindowOnFinalizeMaintenanceTask(tarea, agent));


        //5. Si hay que abrir ventana de mantenimiento en la respuesta debe de ir la session de infopoint y la URL
        if (result.isOpenMaintenanceWindow()) {
            //Si el agente no tiene session la creamos
            if ( agent.getInfopointSession() == null) {
                infopointService.createSession(agent);
            }
            result.setOpenMaintenanceWindowURL(prepareExternalCreateMaintenanceURLFinalizeMaintenanceTask(tarea, agent));
        }

        wsReportingTareas(tarea, agent, "GESTIONAR");


        return result;
    }

    public boolean finalizeNotificationTask(Agent agent, TareaAviso tarea) throws Exception {
        //Finalizar tarea como si no viene de matenimiento
        return finalizeNotificationTask(agent, tarea, false, null);
    }

    /**
     * Finalizar la tarea de tipo Aviso, es distinta al resto de tareas porque hay que llamar a cancelar.
     * <p/>
     * Finalizar:
     * 1 si modifica campos modificar aviso
     * 2 finalizar aviso
     * 3 finaliza tarea
     * 3.1 si está en memoria finalizar tarea ws ccl nuevo y cerrar interacción con javascript
     * 3.2 si no está en  memoria finalizar tarea ws ccl antiguo y volver a la ventana de buscar
     *
     * @param agent
     * @param tarea
     * @return
     */
    public boolean finalizeNotificationTask(Agent agent, TareaAviso tarea, boolean finalizadoDesdeMantenimiento, Integer idMantenimiento) throws Exception {
        LOGGER.debug("Finalizando tarea Aviso {}", tarea);
        //Buscamos los datos de la instalación de la tarea
        InstallationData installationData = installationService.getInstallationData(tarea.getNumeroInstalacion());


        //1. Modificar Aviso si hace falta por haber cambiado los datos. Comprobamos si la tarea que nos pasa el front difiere con la de la BBDD, si es asi modificamos
        TareaAviso tareaRefrescada = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (isTaskRequiresSaveModifications(tareaRefrescada, tarea)) {
            avisoService.updateTicket(agent, tarea, installationData);
        }

        //2. Finalizar el Aviso
        boolean finalizadoAviso = avisoService.closeTicket(tarea.getIdAviso(), agent.getAgentIBS(), tarea.getClosing(), tarea.getDatosAdicionalesCierre() == null ? null : Integer.valueOf(tarea.getDatosAdicionalesCierre()), finalizadoDesdeMantenimiento, idMantenimiento);
        if (!finalizadoAviso) {
            LOGGER.error("Can't finalize NotificationTask because can't close Ticket");
            return false;
        }

        //3. Finalizar la Tarea
        if (!isTareaInMemory(tareaRefrescada)) {
            wsFinalizeTask(agent, tarea);
        } else {
            wsFinalizeInMemoryTask(agent, tarea);
        }


        wsReportingTareas(tarea, agent, "GESTIONAR");


        return true;

    }


    /**
     * Función Aplazar específica para tareas de tipo aviso
     * <p/>
     * 1 consulta la tarea de nuevo
     * 2 si modifica campos modificar aviso
     * 3 aplazar tarea
     * 3.1 si no está en memoria aplazar tarea con cclIntegration.updateCallingListContact y volver a la ventana de buscar
     * 3.2 si está en  memoria
     * 3.2.1 aplazar tarea con cclIntegration.rescheduleRecord
     * 3.2.2 finalizar la tarea con con cclIntegration.updateCallingListContact
     * 3.2.3 cerrar interacción con javascript
     * 4 aplazar aviso
     *
     * @param agent
     * @param tarea
     * @param schedTime
     * @param recordType
     * @return
     * @throws Exception
     */
    public void delayNotificationTask(Agent agent, TareaAviso tarea, Date schedTime, String recordType) {
        //1. Buscamos los datos de la instalación de la tarea
        InstallationData installationData = installationService.getInstallationData(tarea.getNumeroInstalacion());

        //2. Modificar Aviso si hace falta por haber cambiado los datos. Comprobamos si la tarea que nos pasa el front difiere con la de la BBDD, si es asi modificamos
        TareaAviso tareaRefrescada = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (isTaskRequiresSaveModifications(tareaRefrescada, tarea)) {
            avisoService.updateTicket(agent, tarea, installationData);
        }

        //3. Aplazar Tarea Comprobamos que la tarea no esté en memoria, para ello volvemos a buscar
        if (!isTareaInMemory(tareaRefrescada)) {
            wsDelayTask(agent, tarea, schedTime, recordType);
        } else {
            //Aplazar tarea en memoria
            wsDelayInMemoryTask(agent, tarea, schedTime, recordType);
            //Finalizar tarea en memoria
            wsFinalizeInMemoryTask(agent, tarea);
        }

        //4. Aplazar el Aviso, Si es de tipo Aviso hay que retrasar el aviso también
        avisoService.delayTicket(tarea.getIdAviso(), agent.getIdAgent(), schedTime);

        wsReportingTareas(tarea, agent, "APLAZAR");

    }


    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * <p/>
     * <p/>
     * 1 consulta la tarea de nuevo
     * 2.1 si no está en  memoria
     * 2.1.1 aplazar la tarea con cclIntegration.updateCallingListContact
     * 2.1.2 Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria
     * 2.2 si está en memoria
     * 2.2.1 aplazar la tarea con cclIntegration.rescheduleRecord
     * 2.2.2 finalizar la tarea con cclIntegration.updateCallingListContact
     * <p/>
     * o	Record_status = 1 (ready)
     * o	Dial_sched_time = dd/mm/aaaa hh:mm:ss
     * o	Recort_type = 5 (personal callback) / 6 (campaing callback)
     */
    public void delayOtherTask(Agent agent,
                               Tarea tarea, Date schedTime, String recordType) {
        LOGGER.info("Delaying Task : {} {} {}", schedTime, recordType, tarea);

        //Consultar la tarea de nuevo
        Tarea tareaRefrescada = queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId());

        if (!isTareaInMemory(tareaRefrescada)) {
            wsDelayTask(agent, tarea, schedTime, recordType);

        } else {
            //Aplazar tarea en memoria
            wsDelayInMemoryTask(agent, tarea, schedTime, recordType);
            //Finalizar tarea en memoria
            wsFinalizeInMemoryTask(agent, tarea);
        }

        wsReportingTareas(tarea, agent, "APLAZAR");

    }

    /**
     * Crear el aviso desde la pantalla de crear tarea
     */
    public void createTask(Agent agent, TareaAviso tarea, InstallationData installationData) {
        LOGGER.debug("Creating task: {}", tarea);

        avisoService.createTicket(agent, (TareaAviso) tarea, installationData);

        //No se crea la tarea, se crean automáticamente en el reparto
    }


    /**
     * WS 27: Guardar commlogs en IBS.
     *
     * @return
     */
    protected boolean registerCommlog(TareaMantenimiento tarea, Integer lastCalledPhone) throws Exception {
        LOGGER.debug("Registering Commlog for task {}", tarea);
        String insNo = tarea.getNumeroInstalacion();
        String dealId = "0";//deal_id = 0  fijo
        String source = "DIY";  // Al grabar el commlog de finalizar la Tarea, en el campo "source", en lugar de poner "CT" hay que poner "DIY".
        //Nuevos valores key1 y key2, por correo de Jesus Buera
        String key1 = externalDataService.getKey1KeyId(tarea.getKey1());
        String key2 = externalDataService.getKey2KeyId(tarea.getKey1(), tarea.getKey2());
        String key3 = ""; //	key3 =  vacío
        String key4 = ""; //	key3 =  vacío
        String media = "TEL"; //	media = TEL  fijo
        String dir = "O"; //	dir = O  fijo
        String resCode = "CERR"; //	res_code = CERR  fijo
        String longtext = tarea.getTextoCancelacion();  //	texto = texto de la cancelación
        String contactName = tarea.getPersonaContacto();  //	contact_name = persona de contacto
        //String contactPhone = tarea.getTelefono1();
        // contact_phone = número de teléfono, del último llamado (entre los 3 que hay en la ventana). Si no ha pulsado ninguno, dejarlo vacío.
        String contactPhone = (lastCalledPhone != null) ? lastCalledPhone.toString() : "";
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
     * Llamada al WS de delay
     *
     * @param schedTime
     * @param recordType
     * @return
     */
    private void wsDelayTask(Agent agent, Tarea tarea, Date schedTime, String recordType) {

        String ccUserId = agent.getIdAgent();
        String country = agent.getAgentCountryJob();
        String desktopDepartment = agent.getDesktopDepartment();
        String campaign = tarea.getCampana();
        String contactInfo = tarea.getTelefono();
        String callingList = tarea.getCallingList();
        Integer idTarea = tarea.getId();
        //Al aplazar cuando no está en memoria, en el ws hay que rellenar también el campo agent_id con 101@place (por ejemplo: 101@P17001)
        //String agentId = "101@" + agent.getAgentPlace();
        // han cambiado de opinion
        String agentId = agent.getIdAgent();

        String filter = "chain_id=" + idTarea;

        List<java.lang.String> callingLists = Arrays.asList(callingList);
        List<java.lang.String> campaingns = Arrays.asList(campaign);

        List<net.java.dev.jaxb.array.StringArray> modifyValues = new ArrayList<net.java.dev.jaxb.array.StringArray>();
        net.java.dev.jaxb.array.StringArray saRecordStatus = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        net.java.dev.jaxb.array.StringArray saTime = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        net.java.dev.jaxb.array.StringArray saType = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        net.java.dev.jaxb.array.StringArray saAgentId = new net.java.dev.jaxb.array.StringArray();

        //   Record_status = 1 (ready)
        saRecordStatus.getItem().add("record_status");
        saRecordStatus.getItem().add("1");
        //  Dial_sched_time = dd/mm/aaaa hh:mm:ss
        saTime.getItem().add(TaskServiceParams.TAREA_COMMONS_FECHA_REPROGRAMACION);
        saTime.getItem().add(sdfSchedTime.format(schedTime));
        // Recort_type = 5 (personal callback) / 6 (campaing callback)
        saType.getItem().add("record_type");
        saType.getItem().add(recordType);
        //Al aplazar cuando no está en memoria, en el ws hay que rellenar también el campo agent_id con 101@place (por ejemplo: 101@P17001)
        // han cambiado de opnion
        saAgentId.getItem().add("agent_id");
        saAgentId.getItem().add(agentId);


        modifyValues.add(saRecordStatus);
        modifyValues.add(saTime);
        modifyValues.add(saType);
        modifyValues.add(saAgentId);

        WsResponse wsResponse = cclIntegration.updateCallingListContact(desktopDepartment, applicationUser, ccUserId, filter, modifyValues, callingLists, campaingns, contactInfo, country);

        if (wsResponse.getResultCode() == 200) {
            LOGGER.debug("Sucessfully delayed task {} - {}", callingList, idTarea);
        } else {
            LOGGER.error("Error calling updateCallingListContact to delay {}-{}", wsResponse.getResultCode(), wsResponse.getResultMessage());
            throw new BusinessException(BusinessException.ErrorCode.ERROR_DELAY_TASK, wsResponse.getResultCode() + "", wsResponse.getResultMessage());
        }

    }

    /**
     * Llamada al WS de finalizar una Tarea cuando no está en memoria
     *
     * @return
     */
    private void wsFinalizeTask(Agent agent, Tarea tarea) {

        String ccUserId = agent.getIdAgent();
        String country = agent.getAgentCountryJob();
        String desktopDepartment = agent.getDesktopDepartment();
        String campaign = tarea.getCampana();
        String contactInfo = tarea.getTelefono();
        String callingList = tarea.getCallingList();
        Integer idTarea = tarea.getId();

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
            LOGGER.debug("Successfully finalized Task {}", idTarea);
        } else {
            LOGGER.error("Error calling updateCallingListContact to finalize {}-{}", wsResponse.getResultCode(), wsResponse.getResultMessage());
            throw new BusinessException(BusinessException.ErrorCode.ERROR_FINALIZE_TASK, wsResponse.getResultCode() + "", wsResponse.getResultMessage());
        }
    }


    /**
     * Llamada al WS de finalizar una Tarea cuando está en memoria
     * Función comentada porque entra en conflicto con la de javascript de cerrar interaccion
     */
    private void wsFinalizeInMemoryTask(Agent agent, Tarea tarea) {
//        if (validateInMemoryParameters(agent, tarea)) {
//            WsResponse wsResponse = cclIntegration.finalizeRecord(getAgentExtensionFromAgentPlace(tarea.getOutAgentPlace()), tarea.getOutCampaignName(), tarea.getOutClName(), Integer.valueOf(tarea.getOutRecordHandle()));
//            if (wsResponse.getResultCode() == 200) {
//                LOGGER.debug("Finalized successfully in memory task {}", tarea);
//            } else {
//                LOGGER.error("Error finalizing in memory task {} with error {} {} ", tarea, wsResponse.getResultCode(), wsResponse.getResultMessage());
//                throw new BusinessException(BusinessException.ErrorCode.ERROR_FINALIZE_TASK_INMEMORY, wsResponse.getResultCode() + "", wsResponse.getResultMessage());
//            }
//        } else {
//            LOGGER.error("Can't finalize in memory task from a user that is not the owner.");
//            throw new BusinessException(BusinessException.ErrorCode.ERROR_NOT_OWNER_TASK_INMEMORY);
//        }
    }


    /**
     * Llamada al WS de Cancelar una Tarea cuando está en memoria
     */
    private void wsRejectInMemoryTask(Agent agent, Tarea tarea) {
//        if (validateInMemoryParameters(agent, tarea)) {
//            WsResponse wsResponse = cclIntegration.rejectRecord(getAgentExtensionFromAgentPlace(tarea.getOutAgentPlace()), tarea.getOutCampaignName(), tarea.getOutClName(), Integer.valueOf(tarea.getOutRecordHandle()));
//            if (wsResponse.getResultCode() == 200) {
//                LOGGER.debug("Canceled successfully in memory task {}", tarea);
//            } else {
//                LOGGER.error("Error cancelling in memory task {} with error {} {} ", tarea, wsResponse.getResultCode(), wsResponse.getResultMessage());
//                throw new BusinessException(BusinessException.ErrorCode.ERROR_FINALIZE_TASK_INMEMORY, wsResponse.getResultCode() + "", wsResponse.getResultMessage());
//            }
//        } else {
//            LOGGER.error("Can't Cancel in memory task from a user that is not the owner.");
//            throw new BusinessException(BusinessException.ErrorCode.ERROR_NOT_OWNER_TASK_INMEMORY);
//        }
    }


    /**
     * Llamada al WS de Aplazar una Tarea cuando está en memoria
     */
    private void wsDelayInMemoryTask(Agent agent, Tarea tarea, Date schedTime, String recordType) {
        if (validateInMemoryParameters(agent, tarea)) {
            WsResponse wsResponse = null;
            try {
                //Fecha
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(schedTime);
                XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                //Esto es necesario porque el formato es <dateTime>2015-09-10T19:19:19</dateTime>
                date2.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
                date2.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

                String recordTypeText;
                if (recordType == null || recordType.isEmpty() || recordType.equalsIgnoreCase("6")) {
                    recordTypeText = "Campaign";
                } else {
                    recordTypeText = "Personal";
                }

                wsResponse = cclIntegration.rescheduleRecord(getAgentExtensionFromAgentPlace(tarea.getOutAgentPlace()), tarea.getOutCampaignName(), Integer.valueOf(tarea.getOutRecordHandle()), recordTypeText, date2);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new FrameworkException(e);
            }

            //Parseo de la respuesta
            if (wsResponse.getResultCode() == 200) {
                LOGGER.debug("Delayed successfully in memory task {}", tarea);
            } else {
                LOGGER.error("Error delaying in memory task {} with error {} {} ", tarea, wsResponse.getResultCode(), wsResponse.getResultMessage());
                throw new BusinessException(BusinessException.ErrorCode.ERROR_FINALIZE_TASK_INMEMORY, wsResponse.getResultCode() + "", wsResponse.getResultMessage());
            }
        } else {
            LOGGER.error("Can't delay in memory task from a user that is not the owner.");
            throw new BusinessException(BusinessException.ErrorCode.ERROR_NOT_OWNER_TASK_INMEMORY);
        }
    }

    /**
     * isTaskRequiresSaveModifications: Compara dos tareas
     *
     * @param t1
     * @param t2
     * @return
     */

    private boolean isTaskRequiresSaveModifications(TareaAviso t1, TareaAviso t2) {
        return !t1.equalsSinDatosCierre(t2);
    }


    private boolean isChangedTipoOrMotivo(TareaAviso t1, TareaAviso t2) {
        return !t1.equalsTipo1Motivo1(t2);
    }

    private XMLGregorianCalendar convertStringToXmlGregorian(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = sdf.parse(dateString);
            GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
            gc.setTime(date);

            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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


    /**
     * Descarta Tareas de tipo excel.
     */
    public void discardExcelTask(Agent agent, Tarea tarea, InstallationData installation) throws Exception { //TODO QUITAR ESTA EXCEPCION
        this.discardOtherTask(agent, tarea, installation);
    }

    /**
     * Descarta tareas de tipo mantenimiento
     *
     * @param agent
     * @param tarea
     * @param installation
     */
    public void discardMaintenanceTask(Agent agent, Tarea tarea, InstallationData installation) throws Exception { //TODO QUITAR ESTA EXCEPCION
        this.discardOtherTask(agent, tarea, installation);
    }


    /**
     * Aqui se llega cuando alguien pulsa descartar.
     * Solo cuando es tarea de tipo aviso, para los demás no se hace nada más que volver a otra pantalla
     * <p/>
     * <p/>
     * Descartar modificando campos:
     * 1 mensaje de confirmación de las modificaciones
     * 2 modificar aviso
     * 3 si ha cambiado Tipo1/Motivo1
     * 3.1 Desmarcar aviso de la tarea
     * 3.2 Finalizar tarea
     * 3.2.1 si está en memoria finalizar tarea ws ccl nuevo y cerrar interacción con javascript
     * 3.2.2 si no está en  memoria finalizar tarea ws ccl antiguo y volver a la ventana de buscar
     * 4 Descartar sin modificar campos:
     * 1.1 si está en memoria descartarla con el ws ccl nuevo y cerrar interacción con javascript
     * 1.2 si no está en memoria volver a la ventana de buscar
     *
     * @param agent
     * @param tarea
     * @param installationData
     * @param saveTicketIfRequired, indica si se quiere salvar el Ticket, solo cuando realmente se deba de salvar
     * @param isCallDone            resultado de la llamada al JavaScript isCallDone
     * @param withInteraction       indica si hay una interacción, en caso contrario viene a decir que hemos cargado la tarea desde la búsqueda
     * @return
     * @throws Exception
     */
    public DiscardNotificationTaskResult discardNotificationTask(Agent agent, TareaAviso tarea, InstallationData installationData, boolean saveTicketIfRequired, boolean isCallDone, boolean withInteraction) throws Exception {

        DiscardNotificationTaskResult infoResult = new DiscardNotificationTaskResult();

        TareaAviso tareaRefrescada = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());

        //Existe un caso en el que no viene instalacion porque no se ha encontrado, en este caso de error el descartar no actualiza la Tarea
        if (installationData != null) {
            LOGGER.debug("There is no installation in the task {}", tarea);
            //Modificar aviso si es necesario
            if (saveTicketIfRequired && isTaskRequiresSaveModifications(tareaRefrescada, tarea)) {
                avisoService.updateTicket(agent, tarea, installationData);
                infoResult.setTicketWasSaved(true);
            }
        } else {
            //Si no hay instalacion por error de datos
            LOGGER.warn("Can't update task because there was no installation found, the task will be finalized");
            infoResult.setTaskWasFinalized(true);
            if (isTareaInMemory(tareaRefrescada)) {
                infoResult.setWasInMemory(true);
                // Finalizar Tarea en memoria
                wsFinalizeInMemoryTask(agent, tarea);
            } else {
                infoResult.setWasInMemory(false);
                //Cancelar cuando no está en memoria
                wsFinalizeTask(agent, tarea);
            }
        } //Fin sin instalacion

        // si ha cambiado Tipo1 o Motivo1
        if (isChangedTipoOrMotivo(tareaRefrescada, tarea)) {
            infoResult.setTaskWasFinalized(true);

            if (isTareaInMemory(tareaRefrescada)) {
                infoResult.setWasInMemory(true);
                // Finalizar Tarea en memoria
                wsFinalizeInMemoryTask(agent, tarea);
                wsReportingTareas(tarea, agent, "GESTIONAR");
            } else {
                infoResult.setWasInMemory(false);
                //Cancelar cuando no está en memoria
                wsFinalizeTask(agent, tarea);
                wsReportingTareas(tarea, agent, "GESTIONAR");
            }

            // desmarcar Aviso de la Tarea
            avisoService.unmarkTicket(tarea.getIdAviso());

        } else {
            //Si no se ha cambiado ni Tipo1 o Motivo1 rechazamos la tarea si está en memoria
            if (isTareaInMemory(tareaRefrescada)) {
                infoResult.setWasInMemory(true);
                //Se han añadido estas condiciones Por cambios en correo de Andres: si no se ha modificado tipo y motivo, tenemos interaccion y no se ha reaizado una llamada entonces no llamamos a Descartar
                if (!isChangedTipoOrMotivo(tareaRefrescada, tarea) &&  withInteraction && !isCallDone) {
                    LOGGER.debug("Ommiting calling reject task because of current condition of the interaction and task will make the javascript RejectInteractioinPushPreview to be called");
                    infoResult.setOmmitedCallToDiscard(true);
                    wsReportingTareas(tarea, agent, "DESCARTAR");
                } else {
                	//Desmarcar el aviso
                	avisoService.unmarkTicket(tarea.getIdAviso());
                    //No se rechazará mediante javascript
                    infoResult.setOmmitedCallToDiscard(false);
                    wsReportingTareas(tarea, agent, "GESTIONAR");
                }
            } else { //La tarea no está en memoria y no se ha cambiado ni tipo ni motivo
                //Parece ser que no hay que hacer nada
                LOGGER.debug("Not doing anything with the task beacase it wasn't changed and wasn't in memory, task {}", tarea);
                infoResult.setWasInMemory(false);
            }
        }

        return infoResult;
    }

    /**
     * Descartar para las tareas de tipo excell, finalizan la tarea
     *
     * @param agent
     * @param tarea
     * @param installationData
     * @throws Exception
     */
    public void discardOtherTask(Agent agent, Tarea tarea, InstallationData installationData) {
        Tarea tareaRefrescada = queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (isTareaInMemory(tareaRefrescada)) {
            // Finalizar Tarea en memoria
            wsFinalizeInMemoryTask(agent, tarea);

            wsReportingTareas(tarea, agent, "DESCARTAR");

        } else {
            //Cancelar cuando no está en memoria
            wsFinalizeTask(agent, tarea);
        }
    }
    
    /**
     * Obtener tipo de aplazamientos
     * @param tarea
     * GprAplazamiento
     * @return
     */
    public List<GrpAplazamiento> getTipoAplaza(){
    	try {
			List<GrpAplazamiento> listaGrpAplazamiento=spAioTareas2.getTipoAplazamientos();
			return listaGrpAplazamiento;
		} catch (DataServiceFault e) {
			LOGGER.error("Error calling getTipoAplaza");
            throw new BusinessException(BusinessException.ErrorCode.ERROR_GET_DELAY_TYPE);
		}
    }
    
    
    
    
    private boolean isTaskRequiresFinalizeModifications(TareaAviso tarea) {

        boolean result = false;

        if (tarea.getTipoAviso1() != null && !tarea.getTipoAviso1().isEmpty()
                || tarea.getMotivo1() != null && !tarea.getDatosAdicionalesCierre().isEmpty()) {
            return true;
        }

        return result;

    }

    /**
     * Valida si la tarea está en memoria, llamar con una Tarea recien consultada a la base de datos
     *
     * @param tarea
     * @return
     */
    private boolean isTareaInMemory(Tarea tarea) {
        if (SIMULATE_TASKS_IN_MEMORY) {
            LOGGER.error("ATTENTION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nThe Tasks are been simulated in memory\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111");
            return true;
        }
        return tarea.isRetrieved();
    }


    /**
     * Comprueba que los parametros de la tarea incluyen aquellos que permiten operar con la tarea en memoria.
     *
     * @param agent
     * @param tarea
     * @return
     */
    private boolean validateInMemoryParameters(Agent agent, Tarea tarea) {
        return tarea.getOutAgentPlace() != null &&
                tarea.getOutCampaignName() != null &&
                tarea.getOutClName() != null &&
                tarea.getOutRecordHandle() != null;
    }


    /**
     * Eliminar la P del Place y tienes la Extension
     * Ejemplo Place: P17023
     *
     * @param placeID
     * @return
     */
    protected static String getAgentExtensionFromAgentPlace(String placeID) {
        if (placeID != null && (placeID.startsWith("P") || placeID.startsWith("p"))) {
            return placeID.substring(1);
        } else {
            return placeID;
        }
    }

    /**
     * Indica si al finalizar una tarea de tipo mantenimiento se debe de abrir una ventana
     *
     * @param tarea
     * @param agent
     * @return
     */
    protected boolean isRequireOpenWindowOnFinalizeMaintenanceTask(TareaMantenimiento tarea, Agent agent) {
        if (tarea.getTipoCancelacion() != null && this.datosCierreTareaMantenimiento != null) {
            for (CloseMaintenancePair descriptionPair : datosCierreTareaMantenimiento) {
                if (tarea.getTipoCancelacion().equalsIgnoreCase(descriptionPair.getId())) {
                    return (descriptionPair.isOpenMaintenanceWindow());
                }
            }
        }
        return false;
    }

    /**
     * Prepara la url de abrir mantenimiento.
     * Imagino que al abrir sesión en infopoint el token de seguridad de infopoint habrá que pasarlo como parámetro.
     * Por eso yo le decía a Isabel que eso no podía funcionar, pero ella insistió que a ella le dijeron que sí, y que lo probáramos. Que si infopoint comprueba que tu ip está logada, se lo traga.
     */
    protected String prepareExternalCreateMaintenanceURLFinalizeMaintenanceTask(TareaMantenimiento tareaMantenimiento, Agent agent) {
        return externalCreateMaintenanceURLFinalizeMaintenanceTask;
    }


    protected void wsReportingTareas(Tarea tarea, Agent agent, String accionUsuario)
    {

        try {

            InstallationData installationData = installationService.getInstallationData(tarea.getNumeroInstalacion());

            ReportingTareasDetalle reportingTareasDetalle = new ReportingTareasDetalle();

            // fechaActual
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(new Date(System.currentTimeMillis()));
            XMLGregorianCalendar fechaActual = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            //Esto es necesario porque el formato es <dateTime>2015-09-10T19:19:19</dateTime>
            fechaActual.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
            fechaActual.setTimezone(DatatypeConstants.FIELD_UNDEFINED);


            // DATOS DE LA TAREA

            reportingTareasDetalle.setTimestampTarea(fechaActual); // TODO F_CREACION_TAREA recuperarla de la callinglist
            reportingTareasDetalle.setIdTarea(tarea.getId());
            reportingTareasDetalle.setUsuarioCreacion(agent.getAgentIBS());
            reportingTareasDetalle.setCallingList(tarea.getCallingList());
            if (tarea instanceof TareaAviso) {
                reportingTareasDetalle.setTipoTarea("AVISO");
            } else if (tarea instanceof TareaMantenimiento) {
                reportingTareasDetalle.setTipoTarea("MANTENIMIENTO");
            }
            if (tarea instanceof TareaExcel) {
                reportingTareasDetalle.setTipoTarea("EXCEL");
            }
            reportingTareasDetalle.setInsNo(tarea.getNumeroInstalacion());
            if (tarea instanceof TareaAviso) {
                reportingTareasDetalle.setIdAviso(((TareaAviso) tarea).getIdAviso()); // AVISO
                reportingTareasDetalle.setTipo(((TareaAviso) tarea).getTipoAviso1()); // AVISO
                reportingTareasDetalle.setMotivo(((TareaAviso) tarea).getMotivo1());  // AVISO
            }
            reportingTareasDetalle.setPanel(installationData.getPanel());
            reportingTareasDetalle.setVersion(installationData.getVersion());
            if (tarea instanceof TareaMantenimiento) {

                // fechaEvento
                //GregorianCalendar c = new GregorianCalendar();
                c.setTime(((TareaMantenimiento)tarea).getFechaEvento());
                XMLGregorianCalendar fechaEvento = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                //Esto es necesario porque el formato es <dateTime>2015-09-10T19:19:19</dateTime>
                fechaEvento.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
                fechaEvento.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

                reportingTareasDetalle.setTimestampSobre(fechaEvento);
            }
            reportingTareasDetalle.setSkill(""); // constante




            // DATOS DE LA ACCION

            reportingTareasDetalle.setTimestampAccion(fechaActual);
            reportingTareasDetalle.setAccion(accionUsuario); // APLAZAR/DESCARTAR/GESTIONAR/LLAMAR
            reportingTareasDetalle.setAgenteAccion(agent.getAgentIBS());
            if (tarea instanceof TareaExcel) {
                reportingTareasDetalle.setTipificacionCierre(((TareaExcel) tarea).getTypeName()); // EXCEL
                reportingTareasDetalle.setCompensacion(((TareaExcel) tarea).getCompensation());   // EXCEL
            }
            reportingTareasDetalle.setConnid(agent.getConnid());
            reportingTareasDetalle.setInteractionId(""); // TODO recuperarlo  BP PLUGIN
            reportingTareasDetalle.setServicio(agent.getAgentIBS());
            reportingTareasDetalle.setServicio(agent.getInteractionType());
            reportingTareasDetalle.setInteractionDirection(agent.getInteractionDirection());
            if ("APLAZAR".equals(accionUsuario)) {

                // fechaReprogramacion
                //GregorianCalendar c = new GregorianCalendar();
                c.setTime(tarea.getFechaReprogramacion());
                XMLGregorianCalendar fechaReprogramacion = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                //Esto es necesario porque el formato es <dateTime>2015-09-10T19:19:19</dateTime>
                fechaReprogramacion.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
                fechaReprogramacion.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

                reportingTareasDetalle.setFechaReprogramacion(fechaReprogramacion);
            }

            //reportingTareas.storeTareasReportingData(reportingTareasDetalle); TODO NO INVOCAMOS DE MOMENTO


            LOGGER.info("reportingTareasDetalle", reportingTareasDetalle);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FrameworkException(e);
        }

    }


}






