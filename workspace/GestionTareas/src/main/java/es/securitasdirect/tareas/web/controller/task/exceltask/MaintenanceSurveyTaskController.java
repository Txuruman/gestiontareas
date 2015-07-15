package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.maintenancetask.MaintenanceTaskCreateRequest;
import es.securitasdirect.tareas.web.controller.dto.request.maintenancetask.MaintenanceTaskFinalizeRequest;
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
 * @author jel
 */

@Controller
@RequestMapping("/maintenancesurveytask")
public class MaintenanceSurveyTaskController extends BaseController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceSurveyTaskController.class);

    @RequestMapping(value = "/getTarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getMaintenanceSurveyTask(
        @RequestParam(value = "ccUserId", required = true) String ccUserId,
        @RequestParam(value = "callingList", required = true) String callingList,
        @RequestParam(value = "tareaId", required = true) String tareaId
    ) throws DataServiceFault {
        LOGGER.debug("Get maintenance survey task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        MaintenanceSurveyTask tarea = (MaintenanceSurveyTask) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Maintenance survey task obtained from service: \n{}", tarea);
        return toTareaResponse(tarea);
    }

    @RequestMapping(value = "/create", method = {RequestMethod.PUT}, consumes  = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse interactionCreateMaintenanceSurveyTask(@RequestBody MaintenanceTaskCreateRequest peticion) {
        LOGGER.debug("Creando tarea de mantenimiento:\nRequest: {}",peticion);
        BaseResponse response = new BaseResponse();
        if (true) {
            response.success(messageUtil.getProperty("tareamantenimiento.create.success"));
        } else {
            response.danger(messageUtil.getProperty("tareamantenimiento.create.error"));
        }
        LOGGER.debug("Creación de tarea de mantenimiento:\nResponse {}", response);
        return response;
    }

    @RequestMapping(value = "/finalize", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody MaintenanceTaskFinalizeRequest request) {
        LOGGER.debug("Finalizando tarea de mantenimiento:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizando tarea de mantenimiento:\nResponse: {}",response);
        return response;
    }

}