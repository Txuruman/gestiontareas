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

    @Inject
    private DummyResponseGenerator dummyResponseGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTaskController.class);

    @RequestMapping(value = "/createtask", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse createTask(@RequestBody CreateTaskRequest request) {
        LOGGER.debug("Creating task");
        BaseResponse baseResponse = dummyResponseGenerator.dummyCustomSuccess("createtask.create.success");
        return baseResponse;
    }


    @RequestMapping(value = "/createmaintenance", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse createMaintenance(@RequestBody CreateMaintenanceRequest request) {
        LOGGER.debug("Creating maintenance");
        BaseResponse baseResponse = dummyResponseGenerator.dummyCustomSuccess("createtask.createmaintenance.success");
        return baseResponse;
    }

    @RequestMapping(value = "/gettypelist", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PairListResponse getTypeList(){
        LOGGER.debug("Getting task types");
        PairListResponse response = null;
        try{
            //TODO llamada a servicio;
            response = dummyResponseGenerator.dummyPairCustomSuccess("createtask.gettypelist.success");
            if(response!=null){
                //
            }else{

            }
        }catch(Exception e){
            //TODO captura de error de llamada al servicio
            response = (PairListResponse)super.processException(e);
        }
        return response;
    }

    //getreasonlist


/*
    try{
        tipoAvisoList = externalDataService.getNotificationType();
    }catch (DataServiceFault dsf){
        LOGGER.error("Error obteniendo los tipos de aviso: \nFaultInfo:{}\nMessage:{}\n{}", dsf.getFaultInfo(), dsf.getMessage(),dsf.toString());
        tipoAvisoList = null;
    }

    try{
        motivoAvisoList = externalDataService.getNotificationTypeReason();
    }catch (DataServiceFault dsf){
        LOGGER.error("Error obteniendo los motivos de tipos de aviso: \nFaultInfo:{}\nMessage:{}\n{}", dsf.getFaultInfo(), dsf.getMessage(),dsf.toString());
        motivoAvisoList = null;
    }
*/


}
