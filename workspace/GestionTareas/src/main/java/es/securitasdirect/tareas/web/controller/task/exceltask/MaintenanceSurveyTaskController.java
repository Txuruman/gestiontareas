package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.AgentController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey.DiscardMaintenanceSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey.FinalizeMaintenanceSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey.PostponeMaintenanceSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.FinalizeNotificationTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.dto.support.DummyResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author
 */

@Controller
@RequestMapping("/maintenancesurveytask")
public class MaintenanceSurveyTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private InstallationService installationDataService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Autowired
    private AgentController agentController;


    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceSurveyTaskController.class);

    /*
    @RequestMapping(value = "/gettarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getMaintenanceSurveyTask(
        @RequestParam(value = "ccUserId", required = true) String ccUserId,
        @RequestParam(value = "callingList", required = true) String callingList,
        @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        String TASK_SERVICE_MESSAGE = "maintenanceSurveyTask.getTask";
        TareaResponse response;
        LOGGER.debug("Get maintenance survey task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        try{
            MaintenanceSurveyTask tarea = (MaintenanceSurveyTask) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            LOGGER.debug("Maintenance survey task obtained from service: \n{}", tarea);
            response = processSuccessTask(tarea,TASK_SERVICE_MESSAGE );
        }catch(Exception e){
            response = processException(e,TASK_SERVICE_MESSAGE);
        }
        return response;
    }
    */


    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse getInstallationAndTask(
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        return super.getInstallationAndTask(callingList, tareaId);
    }


    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse postpone(@RequestBody PostponeMaintenanceSurveyTaskRequest request) {
        return super.delayTask(request.getTask(), request.getRecallType(), request.getDelayDate());
    }



    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse descartar(@RequestBody DiscardMaintenanceSurveyTaskRequest request) {
        LOGGER.debug("Descartar tarea de encuesta de mantenimiento:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyCustomSuccess("commonexcel.discard.success");
        LOGGER.debug("Descartada tarea de encuesta de mantenimiento:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse finalizeTask(@RequestBody FinalizeMaintenanceSurveyTaskRequest request) {
        return super.finalizeTask(request.getTask());
    }



}
