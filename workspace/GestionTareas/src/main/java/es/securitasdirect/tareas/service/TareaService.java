package es.securitasdirect.tareas.service;


import com.webservice.CCLIntegration;
import com.webservice.IclResponse;
import com.webservice.WsResponse;
import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.exceptions.FrameworkException;
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
    public void delayTask(Agent agent, Tarea tarea, Date schedTime, String recordType) throws Exception {
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
            wsFilanizeTask(agent,tarea );
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
     * TODO 6.	En Tarea de tipo Mantenimiento, al finalizar, ejecutar WS de grabar commlogs  de IBS con los datos de la pantalla.
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
            wsFilanizeTask(agent, tarea );
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
     *
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
            boolean modificado = avisoService.updateTicket(agent, tarea, installationData);
            if (!modificado) {
                LOGGER.error("Can't finalize NotificationTask because can't update Ticket");
                return false;
            }
        }

        //2. Finalizar el Aviso
        boolean finalizadoAviso = avisoService.closeTicket(tarea.getIdAviso(), agent.getAgentIBS(), tarea.getClosing(), tarea.getDatosAdicionalesCierre() == null ? null : Integer.valueOf(tarea.getDatosAdicionalesCierre()), finalizadoDesdeMantenimiento, idMantenimiento);
        if (!finalizadoAviso) {
            LOGGER.error("Can't finalize NotificationTask because can't close Ticket");
            return false;
        }

        //3. Finalizar la Tarea
        if (!isTareaInMemory(tareaRefrescada)) {
            wsFilanizeTask(agent , tarea );
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
     *
     * Si no modifica campos y Aplaza:
     * 1 Si cambia la fecha aplazar aviso
     * 2 aplazar tarea
     * 2.1 si está en memoria aplazar tarea ws ccl nuevo y cerrar interacción con javascript
     * 2.2 si no está en  memoria aplazar tarea ws ccl antiguo y volver a la ventana de buscar
     * Si modifica campos y Aplaza:
     * 1 modificar aviso
     * 2 si cambia la fecha aplazar aviso
     * 3 aplazar tarea
     * 3.1 si está en memoria aplazar tarea ws ccl nuevo y cerrar interacción con javascript
     * 3.2 si no está en  memoria aplazar tarea ws ccl antiguo y volver a la ventana de buscar
     *
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
        TareaAviso tareaRefrescada = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());
        if (isTaskRequiresSaveModifications(tareaRefrescada, tarea)) {
            boolean modificado = avisoService.updateTicket(agent, tarea, installationData);
            if (!modificado) {
                LOGGER.error("Can't finalize NotificationTask because can't update Ticket");
                return false;
            }
        }

        //2. Aplazar Tarea Comprobamos que la tarea no esté en memoria, para ello volvemos a buscar
        if (!isTareaInMemory(tareaRefrescada)) {
            wsDelayTask(agent, tarea, schedTime, recordType);
        } else {
            //Aplazar tarea en memoria
            wsDelayInMemoryTask(agent,tarea,schedTime,recordType);
            //Finalizar tarea en memoria
            wsFinalizeInMemoryTask(agent, tarea);
        }

        //3. Aplazar el Aviso, Si es de tipo Aviso hay que retrasar el aviso también
        boolean delayed = avisoService.delayTicket(tarea.getIdAviso(), agent.getIdAgent(), "", schedTime);
        return delayed;
    }


    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * <p/>
     * o	Record_status = 1 (ready)
     * o	Dial_sched_time = dd/mm/aaaa hh:mm:ss
     * o	Recort_type = 5 (personal callback) / 6 (campaing callback)
     */
    public void delayOtherTask(Agent agent,
                               Tarea tarea, Date schedTime, String recordType) throws Exception {
        LOGGER.info("Delaying Task : {} {} {}",   schedTime,recordType,tarea);

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
    public boolean createTask(Agent agent, Tarea tarea, InstallationData installationData) {
        LOGGER.debug("Creating task: {}", tarea);
        boolean creado = false;
        if (tarea instanceof TareaAviso) {
            creado = avisoService.createTicket(agent, (TareaAviso) tarea, installationData);
        }

        //TODO: Crear Tarea
        return creado;
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

     * @param schedTime
     * @param recordType
     * @return
     */
    private void wsDelayTask(Agent agent, Tarea tarea , Date schedTime, String recordType) {

        String ccUserId = agent.getIdAgent();
        String country = agent.getAgentCountryJob();
        String desktopDepartment = agent.getDesktopDepartment();
        String campaign = tarea.getCampana();
        String contactInfo = tarea.getTelefono();
        String callingList = tarea.getCallingList();
        Integer idTarea= tarea.getId();

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
    private void wsFilanizeTask(Agent agent, Tarea tarea) {

        String ccUserId = agent.getIdAgent();
        String country = agent.getAgentCountryJob();
        String desktopDepartment = agent.getDesktopDepartment();
        String campaign = tarea.getCampana();
        String contactInfo = tarea.getTelefono();
        String callingList = tarea.getCallingList();
        Integer idTarea= tarea.getId();

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
     */
    private void wsFinalizeInMemoryTask(Agent agent, Tarea tarea) {
        if (validateInMemoryParameters(agent, tarea)) {
            WsResponse wsResponse = cclIntegration.finalizeRecord(tarea.getOutAgentPlace(), tarea.getOutCampaignName(), tarea.getOutClName(), Integer.valueOf(tarea.getOutRecordHandle()));
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
            WsResponse wsResponse = cclIntegration.rejectRecord(tarea.getOutAgentPlace(), tarea.getOutCampaignName(), tarea.getOutClName(), Integer.valueOf(tarea.getOutRecordHandle()));
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
                wsResponse = cclIntegration.rescheduleRecord(tarea.getOutAgentPlace(), tarea.getOutCampaignName(), Integer.valueOf(tarea.getOutRecordHandle()), recordType, date2);
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
     *
     *
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


        TareaAviso tareaOriginal = (TareaAviso) queryTareaService.queryTarea(agent, tarea.getCallingList(), tarea.getId().toString());

        boolean ok = false;
        if (isTaskRequiresSaveModifications(tareaOriginal, tarea)) {
            ok = avisoService.updateTicket(agent, (TareaAviso) tarea, installationData);

            if (!ok) {
                LOGGER.error("Error calling avisoService.updateTicket to save {}-{}-{}", agent, (TareaAviso) tarea, installationData);
            }
        }

        // si ha cambiado Tipo1 o Motivo1
        if (isChangedTipoOrMotivo(tareaOriginal, tarea)) {
            if (isTareaInMemory(tareaOriginal)) {
                // Finalizar Tarea no en memoria
                wsFilanizeTask(agent , tarea );
            } else {
                //Cancelar cuando está en memoria
                wsRejectInMemoryTask(agent, tarea);
            }

            // desmarcar Aviso de la Tarea
            avisoService.unmarkTicket(tarea.getIdAviso());

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
        return   tarea.getOutAgentPlace()!=null &&
                        tarea.getOutCampaignName()!=null &&
                        tarea.getOutClName()!=null &&
                tarea.getOutRecordHandle() != null;
    }

}
