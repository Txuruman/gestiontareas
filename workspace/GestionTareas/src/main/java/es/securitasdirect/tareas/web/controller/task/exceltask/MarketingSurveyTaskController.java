package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.tareaexcel.MarketingSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.marketingsurvey.DiscardMarketingSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.marketingsurvey.FinalizeMarketingSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.marketingsurvey.PostponeMarketingSurveyTaskRequest;
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
@RequestMapping("/marketingsurveytask")
public class MarketingSurveyTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private InstallationService installationDataService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketingSurveyTaskController.class);

    @RequestMapping(value = "/gettarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getMarketingSurveyTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        LOGGER.debug("Get maintenance survey task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        TareaResponse response;
        try{
            MarketingSurveyTask tarea = (MarketingSurveyTask) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(tarea, "marketingSurveyTask.getTask");
            LOGGER.debug("Marketing survey task obtained from service: \n{}", tarea);
        }catch(Exception e){
            response = processException(e, "marketingSurveyTask.getTask.error");
        }
        return response;
    }

    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody PostponeMarketingSurveyTaskRequest request) {
        LOGGER.debug("Aplazar tarea de encuesta de marketing:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Aplazando tarea de encuesta de marketing:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody DiscardMarketingSurveyTaskRequest request) {
        LOGGER.debug("Descartar tarea de encuesta de marketing:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Descartando tarea de encuesta de marketing:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/finalize", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody FinalizeMarketingSurveyTaskRequest request) {
        LOGGER.debug("Finalizando tarea de encuesta de marketing:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizando tarea de encuesta de marketing:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/getInstallationAndTask", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getInstallationAndTask(
            @RequestParam(value = "installationId", required = true) String installationId,
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ){
        LOGGER.debug("Get marketing survey task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        TareaResponse response = new TareaResponse();
        try{
            MarketingSurveyTask task = (MarketingSurveyTask) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            TareaResponse tareaResponse = processSuccessTask(task,"marketingSurveyTask.getTask");
            response.addMessages(tareaResponse.getMessages());
            response.setTarea(tareaResponse.getTarea());
            LOGGER.debug("Marketing survey task obtained from service: \n{}", task);
        }catch(Exception e) {
            TareaResponse tareaResponse = processException(e, "marketingSurveyTask.getTask.error");
            response.addMessages(tareaResponse.getMessages());
        }
        LOGGER.debug("Get installation data for params: \ninstallationId: {}", installationId);
        try{
            InstallationData installationData = installationDataService.getInstallationData(installationId);
            TareaResponse installationResponse = processSuccessInstallation(installationData);
            LOGGER.debug("Installation data obtained from service: \n{}", installationData);
            response.addMessages(installationResponse.getMessages());
            response.setInstallationData(installationResponse.getInstallationData());
        }catch(Exception e){
            TareaResponse installationResponse = processException(e, "installationData.error");
            response.addMessages(installationResponse.getMessages());
        }
        return response;
    }
}
