package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.TareaLimpiezaCuota;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.feecleaning.DiscardFeeCleaningTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.feecleaning.FinalizeFeeCleaningTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.feecleaning.PostponeFeeCleaningTaskRequest;
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
@RequestMapping("/feecleaningtask")
public class FeeCleaningTaskController extends BaseController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(FeeCleaningTaskController.class);

    @RequestMapping(value = "/getTarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getFeeCleaningTask(
        @RequestParam(value = "ccUserId", required = true) String ccUserId,
        @RequestParam(value = "callingList", required = true) String callingList,
        @RequestParam(value = "tareaId", required = true) String tareaId
    ) throws DataServiceFault {
        LOGGER.debug("Get fee cleaning task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        TareaLimpiezaCuota tarea = (TareaLimpiezaCuota) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Fee cleaning task obtained from service: \n{}", tarea);
        return toTareaResponse(tarea);
    }


    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody PostponeFeeCleaningTaskRequest request) {
        LOGGER.debug("Finalizando tarea de limpieza de cuotas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizando tarea de limpieza de cuotas:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody DiscardFeeCleaningTaskRequest request) {
        LOGGER.debug("Finalizando tarea de limpieza de cuotas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizando tarea de limpieza de cuotas:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/finalize", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody FinalizeFeeCleaningTaskRequest request) {
        LOGGER.debug("Finalizando tarea de limpieza de cuotas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizando tarea de limpieza de cuotas:\nResponse: {}",response);
        return response;
    }


}