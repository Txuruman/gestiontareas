package es.securitasdirect.tareas.web.controller.task;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.service.AvisoService;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.service.model.DiscardNotificationTaskResult;
import es.securitasdirect.tareas.web.controller.AgentController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.DiscardNotificationTaskResponse;
import es.securitasdirect.tareas.web.controller.dto.request.GetInstallationAndTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.*;
import es.securitasdirect.tareas.web.controller.dto.response.NotificationTaskResponse;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author jel
 */

@Controller
@RequestMapping("/notificationtask")
public class NotificationTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private ExternalDataService externalDataService;
    @Inject
    private InstallationService installationDataService;
    @Inject
    private AvisoService avisoService;
    @Autowired
    private AgentController agentController;


    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse getInstallationAndTask(@RequestBody GetInstallationAndTaskRequest request) {
        return super.getInstallationAndTask(request.getCallingList(),request.getTaskId(), request.getParams());
    }


    @RequestMapping(value = "/getClosing", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getClosing() {
        String SERVICE_MESSAGE = "notificationtask.getclosing";
        PairListResponse response;
        List<Pair> closingList = null;
        try {

            response = new PairListResponse();
            response.setPairList(closingList);
            //processresponse
        } catch (Exception e) {
            response = new PairListResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }


    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTaskController.class);

    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse postpone(@RequestBody PostponeNotificationTaskRequest request) {
        return super.delayTask(request.getTask(), request.getRecallType(), request.getDelayDate());
    }

    /**
     * Finalización de Tarea tipo Aviso
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse finalizeTask(@RequestBody FinalizeNotificationTaskRequest request) {
        LOGGER.debug("Finalizando tarea aviso {}  ", request);
        BaseResponse response = new BaseResponse();

        //Validación de los datos de respuesta si se finaliza por Crear Mantenimiento
        if (request.isFinalizedByCreateMaintenance()) {
            if (request.getStatus() == null) {
                response.danger(messageUtil.getProperty("createMaintenance.response.unknown"));
                return response;
            } else if (request.getStatus() != 0) { //Detectamos error en la respuesta , TODO Hay que ver que datos vienen en varios casos
                response.danger(messageUtil.getProperty("createMaintenance.response.error", request.getStatus(), request.getMessage()));
                return response;
            }
        }


        //Llamada al servicio para finalizar
        try {
            Agent agent = agentController.getAgent();
            boolean ok = tareaService.finalizeNotificationTask(agent, request.getTask(), request.isFinalizedByCreateMaintenance(), request.getAppointmentNumber());

            if (ok) {
                response.info(messageUtil.getProperty("finalize.success"));
            } else {
                response.danger(messageUtil.getProperty("finalize.error"));
            }
        } catch (Exception e) {
            response = processException(e);
        }
        LOGGER.debug("Finalizado de tarea\nResponse:{}", response);
        return response;
    }


    @RequestMapping(value = "/descartaraviso", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse discardNotificationTask(@RequestBody DiscardNotificationTaskRequest request) {
        LOGGER.debug("Descartar tareaAviso\nRequest: {}", request);
        DiscardNotificationTaskResponse response = new DiscardNotificationTaskResponse();
        Agent agent = agentController.getAgent();
        try {
            DiscardNotificationTaskResult discardNotificationTaskResult = tareaService.discardNotificationTask(agent, request.getTask(), request.getInstallation(), true, request.isCallDone());
            response.setResult(discardNotificationTaskResult);
            response.success(messageUtil.getProperty("notificationTask.modify.success"));
        } catch (Exception e) {
            return processException(e);
        }

        LOGGER.debug("Descartar tareaAviso\nResponse:{}", response);
        return response;
    }



    @RequestMapping(value = "/descartaravisosinsalvartarea", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse discardNotificationWithoutSaveTask(@RequestBody DiscardNotificationTaskRequest request) {
        LOGGER.debug("Descartar tareaAviso\nRequest: {}", request);
        BaseResponse response = new BaseResponse();
        Agent agent = agentController.getAgent();
        try {
            tareaService.discardNotificationTask(agent, request.getTask(), request.getInstallation(), false, false);
            response.success(messageUtil.getProperty("notificationTask.modify.success"));
        } catch (Exception e) {
            response = processException(e);
        }


        LOGGER.debug("Descartar tareaAviso\nResponse:{}", response);
        return response;
    }




}
