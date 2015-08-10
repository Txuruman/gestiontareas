package es.securitasdirect.tareas.web.controller;

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
            boolean result = tareaService.createTask(request.getTask());
            response = processSuccessMessages(result, SERVICE_MESSAGE);
        }catch(Exception e){
            response = processException(e,SERVICE_MESSAGE);
        }
        return response;
    }


    @RequestMapping(value = "/createmaintenance", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse createMaintenance(@RequestBody CreateMaintenanceRequest request) {
        String SERVICE_MESSAGE = "createtask.createmaintenance";
        LOGGER.debug("Creating maintenance");
        BaseResponse response;
        try{
            //TODO LLAMADA A SERVICE
            response = super.processSuccessMessages(tareaService.createMaintenance(request.getTask()), SERVICE_MESSAGE);
        }catch(Exception e){
            response = super.processException(e, SERVICE_MESSAGE);
        }
        //TODO LLAMADA A SERVICE ?
        //BaseResponse baseResponse = dummyResponseGenerator.dummyCustomSuccess("createtask.createmaintenance.success");
        return response;
    }

    @RequestMapping(value = "/gettypelist", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PairListResponse getTypeList(){
        LOGGER.debug("Getting task types");
        PairListResponse response = null;
        try{
            //TODO llamada a SERVICE;
            //response = dummyResponseGenerator.dummyPairCustomSuccess("createtask.gettypelist.success");
            if(response!=null){
                //
            }else{

            }
        }catch(Exception e){
            //TODO captura de error de llamada al servicio
            response = new PairListResponse(super.processException(e));
        }
        return response;
    }
}
