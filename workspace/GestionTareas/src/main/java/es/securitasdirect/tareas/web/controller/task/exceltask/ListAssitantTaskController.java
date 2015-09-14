package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.AgentController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.DiscardExcelTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.GetInstallationAndTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.keyboxtask.PostponeKeyboxTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask.DiscardListAssistantTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask.FinalizeListAssistantTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask.PostponeListAssistantTaskRequest;
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
 * @author jel
 */

@Controller
@RequestMapping("/listassistanttask")
public class ListAssitantTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Inject
    private InstallationService installationDataService;
    @Autowired
    private AgentController agentController;


    private static final Logger LOGGER = LoggerFactory.getLogger(ListAssitantTaskController.class);


    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse postpone(@RequestBody PostponeListAssistantTaskRequest request) {
        return super.delayTask(request.getTask(), request.getRecallType(), request.getDelayDate());
    }

    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse descartar(@RequestBody DiscardExcelTaskRequest request) {
    	return super.discardTask(request.getTask(), request.getInstallation());
    }


    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse finalizeTask(@RequestBody FinalizeListAssistantTaskRequest request) {
        return super.finalizeTask(request.getTask());
    }


    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse getInstallationAndTask(@RequestBody GetInstallationAndTaskRequest request) {
        return super.getInstallationAndTask(request.getCallingList(),request.getTaskId(), request.getParams());
    }
}
