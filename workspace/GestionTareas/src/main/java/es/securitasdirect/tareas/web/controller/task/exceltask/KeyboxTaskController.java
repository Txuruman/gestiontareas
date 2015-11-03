package es.securitasdirect.tareas.web.controller.task.exceltask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.request.DiscardExcelTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.GetInstallationAndTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.keyboxtask.FinalizeKeyboxTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.keyboxtask.PostponeKeyboxTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * @author jel
 */

@Controller
@RequestMapping("/keyboxtask")
public class KeyboxTaskController extends TaskController {



    private static final Logger LOGGER = LoggerFactory.getLogger(KeyboxTaskController.class);



    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse postpone(@RequestBody PostponeKeyboxTaskRequest request) {
        return super.delayTask(request.getTask(), request.getRecallType(), request.getDelayDate(), request.getMotive(), request.getFromSearch());
    }

    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse descartar(@RequestBody DiscardExcelTaskRequest request) {
    	return super.discardExcelTask(request.getTask(), request.getInstallation(), request.getFromSearch());
    }


    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse finalizeTask(@RequestBody FinalizeKeyboxTaskRequest request) {
        return super.finalizeTask(request.getTask(), request.getFromSearch());
    }


    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse getInstallationAndTask(@RequestBody GetInstallationAndTaskRequest request) {
        return super.getInstallationAndTask(request.getCallingList(),request.getTaskId(), request.getParams());
    }
}
