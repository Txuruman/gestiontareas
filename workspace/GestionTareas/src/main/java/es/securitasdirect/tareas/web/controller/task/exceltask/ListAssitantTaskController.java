package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask.DiscardListAssistantTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask.FinalizeListAssistantTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask.PostponeListAssistantTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.dto.support.DummyResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author jel
 */

@Controller
@RequestMapping("/listassistanttask")
public class ListAssitantTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Inject
    private InstallationService installationDataService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListAssitantTaskController.class);

    @RequestMapping(value = "/gettarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getListAssistantTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        TareaResponse response;
        String SERVICE_MESSAGE = "listAssistantTask.getTask";
        LOGGER.debug("Get list assistant task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        try{
            TareaListadoAssistant tarea = (TareaListadoAssistant) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(tarea, SERVICE_MESSAGE);
            LOGGER.debug("List assistant task obtained from service: \n{}", tarea);
        }catch(Exception e){
            response = processException(e, SERVICE_MESSAGE);
        }
        return response;
    }


    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse aplazar(@RequestBody PostponeListAssistantTaskRequest request) {
        LOGGER.debug("Aplazando tarea de ListAssistant:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyCustomSuccess("commonexcel.postpone.success");
        LOGGER.debug("Finalizando tarea de ListAssistant:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse descartar(@RequestBody DiscardListAssistantTaskRequest request) {
        LOGGER.debug("Descartar tarea de ListAssistant:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyCustomSuccess("commonexcel.discard.success");
        LOGGER.debug("Descartando tarea de ListAssistant:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody FinalizeListAssistantTaskRequest request) {
        LOGGER.debug("Finalizando tarea de ListAssistant:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyCustomSuccess("excelcommonfields.finalizes.success");
        LOGGER.debug("Finalizando tarea de ListAssistant:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/getInstallationAndTask", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getInstallationAndTask(
            @RequestParam(value = "installationId", required = true) String installationId,
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        TareaResponse response = new TareaResponse();
        String TASK_SERVICE_MESSAGE = "listAssistantTask.getTask";
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        try{
            TareaListadoAssistant listAssistantTask = (TareaListadoAssistant) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            TareaResponse tareaResponse = processSuccessTask(listAssistantTask, TASK_SERVICE_MESSAGE);
            response.addMessages(tareaResponse.getMessages());
            response.setTarea(tareaResponse.getTarea());
            LOGGER.debug("List assistant task obtained from service: \n{}", listAssistantTask);
        }catch(Exception e){
            TareaResponse tareaResponse = processException(e, TASK_SERVICE_MESSAGE);
            response.addMessages(tareaResponse.getMessages());
        }
        LOGGER.debug("Get installation data for params: \ninstallationId: {}", installationId);
        String INSTALLATION_SERVICE_MESSAGE = "installationData";
        try{
            InstallationData installationData = installationDataService.getInstallationData(installationId);
            TareaResponse installationResponse = processSuccessInstallation(installationData);
            LOGGER.debug("Installation data obtained from service: \n{}", installationData);
            response.addMessages(installationResponse.getMessages());
            response.setInstallationData(installationResponse.getInstallationData());
        }catch(Exception e){
            TareaResponse tareaResponse = processException(e,INSTALLATION_SERVICE_MESSAGE);
            response.addMessages(tareaResponse.getMessages());
        }
        return response;
    }
}
