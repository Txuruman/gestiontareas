package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.request.createtask.CreateMaintenanceRequest;
import es.securitasdirect.tareas.web.controller.dto.request.createtask.CreateTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
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
@RequestMapping("/createtask")
public class CreateTaskController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTaskController.class);

    @Inject
    protected TareaService tareaService;
    @Autowired
    private AgentController agentController;


    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/createtask", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse createTask(@RequestBody CreateTaskRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            Agent agent = agentController.getAgent();
            tareaService.createTask(agent, request.getTask(), request.getInstallationData());
            response.info(messageUtil.getProperty("createtask.create.success"));
        } catch (Exception e) {
            LOGGER.error("Error creating task.", e);
            response = processException(e);
        }
        return response;
    }


}
