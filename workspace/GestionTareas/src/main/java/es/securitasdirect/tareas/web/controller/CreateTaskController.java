package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.DummyGenerator;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.request.createtask.CreateMaintenanceRequest;
import es.securitasdirect.tareas.web.controller.dto.request.createtask.CreateTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.dto.support.DummyResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.wso2.ws.dataservice.DataServiceFault;

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

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/createtask", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse createTask(@RequestBody CreateTaskRequest request) {
        String SERVICE_MESSAGE = "createtask.create";
        LOGGER.debug("Creating task");
        BaseResponse response;
        try{
            LOGGER.debug("Create task request: {}", request);
            Agent agent = DummyGenerator.getAgent();
            boolean result = tareaService.createTask( (TareaMantenimiento)request.getTask());
            LOGGER.debug("Created task result: {}", result);
            response = processSuccessMessages(result, SERVICE_MESSAGE);
        }catch(Exception e){
            LOGGER.error("Error creating task:");
            response = processException(e,SERVICE_MESSAGE);
        }
        return response;
    }


    @RequestMapping(value = "/createmaintenance", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse createMaintenance(@RequestBody CreateMaintenanceRequest request) {
        String SERVICE_MESSAGE = "createtask.createmaintenance";
        LOGGER.debug("Creating maintenance for request: {}", request);
        BaseResponse response;
        try{
            boolean createdMaintenance = tareaService.createMaintenance(request.getTask());
            LOGGER.debug("Created maintenance result: {}", createdMaintenance);
            response = super.processSuccessMessages( createdMaintenance, SERVICE_MESSAGE);
        }catch(Exception e){
            LOGGER.debug("Error creating maintenance:");
            response = super.processException(e, SERVICE_MESSAGE);
        }
        return response;
    }
}
