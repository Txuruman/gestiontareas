package es.securitasdirect.tareas.service;


import com.webservice.CCLIntegration;
import com.webservice.WsResponse;
import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.exceptions.FrameworkException;
import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.web.controller.params.TaskServiceParams;
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


    //Dial_sched_time = dd/mm/aaaa HH:mm:ss
    private SimpleDateFormat sdfSchedTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * Comprueba si la tarea que llega es de tipo Aviso
     */
    public void delayTask(Agent agent, Tarea tarea, Date schedTime, String recordType) throws Exception {
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
     * Descartar: descartar las tareas
     * Comprueba si la tarea que llega es de tipo Aviso
     */
    public void discardTask(Agent agent, Tarea tarea, InstallationData installation) throws Exception {
        //Llamada a los discard
        if (tarea instanceof TareaAviso) {
            this.discardNotificationTask(agent, (TareaAviso) tarea, installation);
        } else {
            this.discardOtherTask(agent, tarea, installation);
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

        //2. TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria

    }

    /**
     * Finalizar tarea de tipo Mantenimiento,    es distinto porque tiene que cancelar la señal IWS
     * <p/>
     * Cancelar la señal de IWS al finalizar una Tarea de tipo Mantenimiento y en la gestión de señales, cuando está fuera de horario.
     * <p/>
     * En Tarea de tipo Mantenimiento, al finalizar, ejecutar WS de grabar commlogs  de IBS con los datos de la pantalla.
     * <p/>
     * //TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria
     *
     * @param agent
     * @param tarea
     */
    public void finalizeMaintenanceTask(Agent agent, TareaMantenimiento tarea) throws Exception {
        //Consultar la tarea de nuevo
        Tarea tareaRefrescada = (TareaMantenimiento) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
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
        registerCommlog(tarea);

        //TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria

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
        return !t1.equalsSinDatosCierre(t2);
    }


    private boolean isChangedTipoOrMotivo(TareaAviso t1, TareaAviso t2) {
        return !t1.equalsTipo1Motivo1(t2);
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
    public boolean delayNotificationTask(Agent agent, TareaAviso tarea, Date schedTime, String recordType) throws Exception {
        //1. Buscamos los datos de la instalación de la tarea
        InstallationData installationData = installationService.getInstallationData(tarea.getNumeroInstalacion());

        //2. Modificar Aviso si hace falta por haber cambiado los datos. Comprobamos si la tarea que nos pasa el front difiere con la de la BBDD, si es asi modificamos
        TareaAviso tareaRefrescada = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (isTaskRequiresSaveModifications(tareaRefrescada, tarea)) {
            try {
                avisoService.updateTicket(agent, tarea, installationData);
            } catch (Throwable e) {
                //TODO Repasar la exceptión
                LOGGER.error("ELIMINAR ESTA CAPTURA DE EXCEPCIÓN");
            }
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
        boolean delayed = avisoService.delayTicket(tarea.getIdAviso(), agent.getIdAgent(), "", schedTime);
        return delayed;
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
                               Tarea tarea, Date schedTime, String recordType) throws Exception {
        LOGGER.info("Delaying Task : {} {} {}", schedTime, recordType, tarea);

        //Consultar la tarea de nuevo
        Tarea tareaRefrescada = queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId());

        if (!isTareaInMemory(tareaRefrescada)) {
            wsDelayTask(agent, tarea, schedTime, recordType);
            //TODO Pendiente, cuando esté funcionando el Reporting de BI el dato Motivo de Cierre y Compensación deben de registrarse en la auditoria
        } else {
            //Aplazar tarea en memoria
            wsDelayInMemoryTask(agent, tarea, schedTime, recordType);
            //Finalizar tarea en memoria
            wsFinalizeInMemoryTask(agent, tarea);
        }
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
        saTime.getItem().add(TaskServiceParams.TAREA_COMMONS_FECHA_REPROGRAMACION);
        saTime.getItem().add(sdfSchedTime.format(schedTime));
        // Recort_type = 5 (personal callback) / 6 (campaing callback)
        saType.getItem().add("record_type");
        saType.getItem().add(recordType);

        modifyValues.add(saRecordStatus);
        modifyValues.add(saTime);
        modifyValues.add(saType);

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
        //Al aplazar cuando no está en memoria, en el ws hay que rellenar también el campo agent_id con 101@place (por ejemplo: 101@P17001)
        String agentId = "101@" + agent.getAgentPlace();

        String filter = "chain_id=" + idTarea;

        List<java.lang.String> callingLists = Arrays.asList(callingList);
        List<java.lang.String> campaingns = Arrays.asList(campaign);

        List<net.java.dev.jaxb.array.StringArray> modifyValues = new ArrayList<net.java.dev.jaxb.array.StringArray>();
        net.java.dev.jaxb.array.StringArray saStatus = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        net.java.dev.jaxb.array.StringArray saResult = new net.java.dev.jaxb.array.StringArray();// RECORD_STATUS, DIAL_SHCED_TIME, RECORDTYPE
        net.java.dev.jaxb.array.StringArray saAgentId = new net.java.dev.jaxb.array.StringArray();
        modifyValues.add(saStatus);
        modifyValues.add(saResult);
        modifyValues.add(saAgentId);
        //   o	Record_status = 3 (updated)
        saStatus.getItem().add("record_status");
        saStatus.getItem().add("3");
        // o	Call_result = 0 (ok)
        saResult.getItem().add("call_result");
        saResult.getItem().add("0");

        //Al aplazar cuando no está en memoria, en el ws hay que rellenar también el campo agent_id con 101@place (por ejemplo: 101@P17001)
        saAgentId.getItem().add("agent_id");
        saAgentId.getItem().add(agentId);


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
     */
    private void wsFinalizeInMemoryTask(Agent agent, Tarea tarea) {
        if (validateInMemoryParameters(agent, tarea)) {
            WsResponse wsResponse = cclIntegration.finalizeRecord(getAgentExtensionFromAgentPlace(tarea.getOutAgentPlace()), tarea.getOutCampaignName(), tarea.getOutClName(), Integer.valueOf(tarea.getOutRecordHandle()));
            if (wsResponse.getResultCode() == 200) {
                LOGGER.debug("Finalized successfully in memory task {}", tarea);
            } else {
                LOGGER.error("Error finalizing in memory task {} with error {} {} ", tarea, wsResponse.getResultCode(), wsResponse.getResultMessage());
                throw new BusinessException(BusinessException.ErrorCode.ERROR_FINALIZE_TASK_INMEMORY, wsResponse.getResultCode() + "", wsResponse.getResultMessage());
            }
        } else {
            LOGGER.error("Can't finalize in memory task from a user that is not the owner.");
            throw new BusinessException(BusinessException.ErrorCode.ERROR_NOT_OWNER_TASK_INMEMORY);
        }
    }


    /**
     * Llamada al WS de Cancelar una Tarea cuando está en memoria
     */
    private void wsRejectInMemoryTask(Agent agent, Tarea tarea) {
        if (validateInMemoryParameters(agent, tarea)) {
            WsResponse wsResponse = cclIntegration.rejectRecord(getAgentExtensionFromAgentPlace(tarea.getOutAgentPlace()), tarea.getOutCampaignName(), tarea.getOutClName(), Integer.valueOf(tarea.getOutRecordHandle()));
            if (wsResponse.getResultCode() == 200) {
                LOGGER.debug("Canceled successfully in memory task {}", tarea);
            } else {
                LOGGER.error("Error cancelling in memory task {} with error {} {} ", tarea, wsResponse.getResultCode(), wsResponse.getResultMessage());
                throw new BusinessException(BusinessException.ErrorCode.ERROR_FINALIZE_TASK_INMEMORY, wsResponse.getResultCode() + "", wsResponse.getResultMessage());
            }
        } else {
            LOGGER.error("Can't Cancel in memory task from a user that is not the owner.");
            throw new BusinessException(BusinessException.ErrorCode.ERROR_NOT_OWNER_TASK_INMEMORY);
        }
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
     * Descartar sin modificar campos:
     * 1.1 si está en memoria descartarla con el ws ccl nuevo y cerrar interacción con javascript
     * 1.2 si no está en memoria volver a la ventana de buscar
     *
     * @param agent
     * @param tarea
     * @param installationData
     * @return
     * @throws Exception
     */
    public void discardNotificationTask(Agent agent, TareaAviso tarea, InstallationData installationData) throws Exception {


        TareaAviso tareaRefrescada = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());

        //Existe un caso en el que no viene instalacion porque no se ha encontrado, en este caso de error el descartar no actualiza la Tarea
        if (installationData != null) {
            if (isTaskRequiresSaveModifications(tareaRefrescada, tarea)) {
                avisoService.updateTicket(agent, (TareaAviso) tarea, installationData);
            } 
        }else {
        	//Si no hay instalacion por error de datos
            LOGGER.warn("Can't update task because there was no installation found, the task will be finalized");
            if (isTareaInMemory(tareaRefrescada)) {
                // Finalizar Tarea en memoria
                wsFinalizeInMemoryTask(agent, tarea);
            } else {
                //Cancelar cuando está en memoria
                wsFinalizeTask(agent, tarea);
            }
        } //Fin sin instalacion

        // si ha cambiado Tipo1 o Motivo1
        if (isChangedTipoOrMotivo(tareaRefrescada, tarea)) {
            if (isTareaInMemory(tareaRefrescada)) {
                // Finalizar Tarea en memoria
                wsFinalizeInMemoryTask(agent, tarea);
            } else {
                //Cancelar cuando está en memoria
                wsFinalizeTask(agent, tarea);
            }

            // desmarcar Aviso de la Tarea
            avisoService.unmarkTicket(tarea.getIdAviso());

        } else {
            //Si no se ha cambiado ni Tipo1 o Motivo1 rechazamos la tarea si está en memoria
            if (isTareaInMemory(tareaRefrescada)) {
                // Rechazar Tarea en memoria
                wsRejectInMemoryTask(agent, tarea);
            }
        }


    }
    
    /**
     * Descartar para las tareas de tipo excell, finalizan la tarea
     * @param agent
     * @param tarea
     * @param installationData
     * @throws Exception
     */
    public void discardOtherTask(Agent agent, Tarea tarea, InstallationData installationData) throws Exception {
    	Tarea tareaRefrescada = queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString()); 
    	if (isTareaInMemory(tareaRefrescada)) {
             // Finalizar Tarea en memoria
             wsFinalizeInMemoryTask(agent, tarea);
         } else {
             //Cancelar cuando está en memoria
             wsFinalizeTask(agent, tarea);
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


}






