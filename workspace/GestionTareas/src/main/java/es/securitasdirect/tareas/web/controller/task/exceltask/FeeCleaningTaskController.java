package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.KeyboxTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaLimpiezaCuota;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.TaskController;
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
public class FeeCleaningTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Inject
    private InstallationService installationDataService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FeeCleaningTaskController.class);

    @RequestMapping(value = "/gettarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getFeeCleaningTask(
        @RequestParam(value = "ccUserId", required = true) String ccUserId,
        @RequestParam(value = "callingList", required = true) String callingList,
        @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        String SERVICE_MESSAGE = "feeCleaningTask.getTask";
        LOGGER.debug("Get fee cleaning task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        TareaResponse response;
        try{
            TareaLimpiezaCuota tarea = (TareaLimpiezaCuota) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            LOGGER.debug("Fee cleaning task obtained from service: \n{}", tarea);
            response = processSuccessTask(tarea, SERVICE_MESSAGE);
        }catch(Exception e){
            response = processException(e, SERVICE_MESSAGE);
        }
        return response;
    }


    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody PostponeFeeCleaningTaskRequest request) {
        LOGGER.debug("Aplazando tarea de limpieza de cuotas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Aplazada tarea de limpieza de cuotas:\nResponse: {}",response);
        return response;
    }


    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody DiscardFeeCleaningTaskRequest request) {
        LOGGER.debug("Descartando tarea de limpieza de cuotas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Descartada tarea de limpieza de cuotas:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody FinalizeFeeCleaningTaskRequest request) {
        LOGGER.debug("Finalizando tarea de limpieza de cuotas:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizada tarea de limpieza de cuotas:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/getInstallationAndTask", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getInstallationAndTask(
            @RequestParam(value = "installationId", required = true) String installationId, //TODO QUITAR ESTE PARAMETRO
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {

        LOGGER.debug("Get Notification task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaResponse response = new TareaResponse();
        try {
            //Buscar Tarea
            TareaLimpiezaCuota task = (TareaLimpiezaCuota)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
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
        String TASK_SERVICE_MESSAGE = "feeCleaningTask.getTask";
        TareaResponse response = new TareaResponse();
        LOGGER.debug("Get fee cleaning task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        try{
            LOGGER.debug("Get installation data for params: \ninstallationId: {}", installationId);
            TareaLimpiezaCuota task = (TareaLimpiezaCuota) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            TareaResponse tareaResponse = processSuccessTask(task, TASK_SERVICE_MESSAGE);
            response.addMessages(tareaResponse.getMessages());
            response.setTarea(tareaResponse.getTarea());
            LOGGER.debug("Fee cleaning task obtained from service: \n{}", task);
        }catch(Exception e){
            TareaResponse tareaResponse = processException(e, TASK_SERVICE_MESSAGE);
            response.addMessages(tareaResponse.getMessages());
        }
        String INSTALLATION_SERVICE_MESSAGE = "installationData";
        try{
            InstallationData installationData = installationDataService.getInstallationData(installationId);
            TareaResponse installationResponse = processSuccessInstallation(installationData);
            response.addMessages(installationResponse.getMessages());
            response.setInstallationData(installationResponse.getInstallationData());
            LOGGER.debug("Installation data obtained from service: \n{}", installationData);
        }catch(Exception e){
            TareaResponse installationResponse = processException(e, INSTALLATION_SERVICE_MESSAGE);
            response.addMessages(installationResponse.getMessages());
        }
        return response;
        */
    }
}
