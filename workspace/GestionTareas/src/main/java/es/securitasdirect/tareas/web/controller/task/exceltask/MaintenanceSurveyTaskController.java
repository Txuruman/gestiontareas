package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey.DiscardMaintenanceSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey.FinalizeMaintenanceSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey.PostponeMaintenanceSurveyTaskRequest;
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
@RequestMapping("/maintenancesurveytask")
public class MaintenanceSurveyTaskController extends BaseController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private InstallationService installationDataService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceSurveyTaskController.class);

    @RequestMapping(value = "/gettarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
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


    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getInstallationAndTask(
            @RequestParam(value = "installationId", required = true) String installationId,
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) throws DataServiceFault {
        LOGGER.debug("Get maintenance survey task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        MaintenanceSurveyTask tarea = (MaintenanceSurveyTask) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Maintenance survey task obtained from service: \n{}", tarea);
        LOGGER.debug("Get installation data for params: \ninstallationId: {}", installationId);
        InstallationData installationData = installationDataService.getInstallationData(installationId);
        LOGGER.debug("Installation data obtained from service: \n{}", installationData);
        return toTareaResponse(tarea, installationData);
    }


    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse aplazar(@RequestBody PostponeMaintenanceSurveyTaskRequest request) {
        LOGGER.debug("Aplazar tarea de encuesta de mantenimiento:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Aplazada tarea de encuesta de mantenimiento:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse descartar(@RequestBody DiscardMaintenanceSurveyTaskRequest request) {
        LOGGER.debug("Descartar tarea de encuesta de mantenimiento:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyCustomSuccess("commonexcel.discard.success");
        LOGGER.debug("Descartada tarea de encuesta de mantenimiento:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody FinalizeMaintenanceSurveyTaskRequest request) {
        LOGGER.debug("Finalizar tarea de encuesta de mantenimiento:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyCustomSuccess("commonexcel.finalize.success");
        LOGGER.debug("Finalizada tarea de encuesta de mantenimiento:\nResponse: {}",response);
        return response;
    }


}
