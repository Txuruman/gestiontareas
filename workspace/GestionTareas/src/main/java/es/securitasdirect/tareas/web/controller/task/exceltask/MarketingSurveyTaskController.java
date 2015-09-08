package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.tareaexcel.MarketingSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.AgentController;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.GetInstallationAndTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.marketingsurvey.DiscardMarketingSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.marketingsurvey.FinalizeMarketingSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.marketingsurvey.PostponeMarketingSurveyTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.dto.support.DummyResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AgentController agentController;


    private static final Logger LOGGER = LoggerFactory.getLogger(MarketingSurveyTaskController.class);



    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse postpone(@RequestBody PostponeMarketingSurveyTaskRequest request) {
        return super.delayTask(request.getTask(), request.getRecallType(), request.getDelayDate());
    }


    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse descartar(@RequestBody DiscardMarketingSurveyTaskRequest request) {
        LOGGER.debug("Descartar tarea de encuesta de marketing:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Descartando tarea de encuesta de marketing:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse finalizeTask(@RequestBody FinalizeMarketingSurveyTaskRequest request) {
        return super.finalizeTask(request.getTask());
    }


    /**
     * http://localhost:8080/gestiontareas/marketingsurveytask/getInstallationAndTask?installationId=111111&ccUserId=12187&callingList=CL_CCT_XLS_ENCUESTAS_MKT&tareaId=1
     * @param callingList
     * @param tareaId
     * @return
     */
    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse getInstallationAndTask(@RequestBody GetInstallationAndTaskRequest request) {
        return super.getInstallationAndTask(request.getCallingList(),request.getTaskId(), request.getParams());
    }
}
