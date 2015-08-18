package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.tareaexcel.TareaOtrasCampanas;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.anothercampaigns.DiscardAnotherCampaignsTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.anothercampaigns.FinalizeAnotherCampaignsTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.anothercampaigns.PostponeAnotherCampaignsTaskRequest;
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
@RequestMapping("/anothercampaignstask")
public class AnotherCampaignsTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Inject
    private InstallationService installationDataService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnotherCampaignsTaskController.class);

    @RequestMapping(value = "/gettarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getTarea(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        String SERVICE_MESSAGE = "anothercampaigntask.gettarea";
        TareaResponse response;
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        try{
            TareaOtrasCampanas tarea = (TareaOtrasCampanas) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(tarea, SERVICE_MESSAGE);
            LOGGER.debug("Another campaigns task obtained from service: \n{}", tarea);
        }catch(Exception e){
            response = processException(e, SERVICE_MESSAGE);
        }
        return response;
    }

    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody PostponeAnotherCampaignsTaskRequest request) {
        LOGGER.debug("Finalizando tarea de otras campañas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizando tarea de otras campañas:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody DiscardAnotherCampaignsTaskRequest request) {
        LOGGER.debug("Finalizando tarea de otras campañas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizando tarea de otras camapañas:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/finalize", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody FinalizeAnotherCampaignsTaskRequest request) {
        LOGGER.debug("Finalizando tarea de otras campañas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizando tarea de otras campañas:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/getInstallationAndTask", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getInstallationAndTask(
            @RequestParam(value = "installationId", required = true) String installationId, //TODO QUITAR ESTE PARAMETRO
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {


        LOGGER.debug("Get Notification task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaResponse response = new TareaResponse();
        try {
            //Buscar Tarea
            TareaOtrasCampanas task = (TareaOtrasCampanas)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            if (task!=null) {
                response.setTarea(task);
                //Buscamos la instalación
                if (task.getNumeroInstalacion()!=null) {
                    InstallationData installationData = installationDataService.getInstallationData(task.getNumeroInstalacion());
                    if (installationData!=null) {
                        response.setInstallationData(installationData);
                    } else {
                        response.danger("getTask.noInstallation");
                    }
                } else {
                    response.danger("getTask.noInstallation");
                }
            } else {
                response.danger("getTask.notFound");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            processException(e);
        }
        return response;


        /*
        String TASK_SERVICE_MESSAGE = "anotherCampaignsTask.getTask";
        TareaResponse response;
        LOGGER.debug("Get another campaigns task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        try{
            TareaOtrasCampanas task = (TareaOtrasCampanas) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(task, TASK_SERVICE_MESSAGE);
            LOGGER.debug("Another campaigns task obtained from service: \n{}", task);
        }catch(Exception e){
            response = processException(e, TASK_SERVICE_MESSAGE);
        }
        String INSTALLATION_SERVICE_MESSAGE = "installationData";
        LOGGER.debug("Get installation data for params: \ninstallationId: {}", installationId);
        try{
            InstallationData installationData = installationDataService.getInstallationData(installationId);
            TareaResponse installationResponse = super.processSuccessInstallation(installationData);
            response.addMessages(installationResponse.getMessages());
            response.setInstallationData(installationResponse.getInstallationData());
            LOGGER.debug("Installation data obtained from service: \n{}", installationData);

        } catch (Exception e) {
            TareaResponse installationResponse = processException(e, INSTALLATION_SERVICE_MESSAGE);
            response.addMessages(installationResponse.getMessages());
        }
        return response;
        */
    }
}
