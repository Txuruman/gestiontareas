package es.securitasdirect.tareas.web.controller.task;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.external.DescriptionPair;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.service.model.FinalizeMaintenanceTaskResult;
import es.securitasdirect.tareas.web.controller.AgentController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.GetInstallationAndTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.maintenancetask.MaintenanceTaskCreateRequest;
import es.securitasdirect.tareas.web.controller.dto.request.maintenancetask.MaintenanceTaskFinalizeRequest;
import es.securitasdirect.tareas.web.controller.dto.response.FinalizeMaintenanceTaskResponse;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;
import java.util.List;

/**
 * @author jel
 */

@Controller
@RequestMapping("/maintenancetask")
public class MaintenanceTaskController extends TaskController {

    @Inject
    private ExternalDataService externalDataService;
    @Autowired
    private AgentController agentController;


    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceTaskController.class);


    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse getInstallationAndTask(@RequestBody GetInstallationAndTaskRequest request) {
        return super.getInstallationAndTask(request.getCallingList(),request.getTaskId(), request.getParams());
    }



    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse finalizeTask(@RequestBody MaintenanceTaskFinalizeRequest request) {
        assert request.getTask() != null : "Es necesario el parametro de la tarea";
        LOGGER.debug("Finalizando tarea {}  ", request.getTask());
        FinalizeMaintenanceTaskResponse response = new FinalizeMaintenanceTaskResponse();
        //Llamada al servicio para finalizar
        try {
            FinalizeMaintenanceTaskResult result = tareaService.finalizeMaintenanceTask(agentController.getAgent(), request.getTask(), request.getLastCalledPhone());
            response.setResult(result);

            response.info(messageUtil.getProperty("finalize.success"));
        } catch (Exception e) {
            return  processException(e);
        }
        LOGGER.debug("Finalizado de tarea Response:{}", response);
        return response;
    }

    @RequestMapping(value = "/getDesplegableKey1", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getDesplegableKey1() {
        PairListResponse response;
        try {
            List<Pair> desplegableKey1 = externalDataService.getDesplegableKey1();
            response = new PairListResponse(desplegableKey1);
        } catch (Exception e) {
            response = new PairListResponse(processException(e));
        }
        return response;
    }

    @RequestMapping(value = "/getDesplegableKey2", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getDesplegableKey2(@RequestParam(value = "key1", required = true) Integer key1) {
        PairListResponse response;
        try {
            List<Pair> desplegableKey2 = externalDataService.getDesplegableKey2(key1);
            response = new PairListResponse(desplegableKey2);
        } catch (Exception e) {
            response = new PairListResponse(processException(e));
        }
        return response;
    }

    /**
     * Consulta datos combo cierre específicos para Tarea Mantenimiento.
     *
     * @return
     */
    @RequestMapping(value = "/getCancelationType", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getCancelationType() {
        PairListResponse response;
        try {
            List<DescriptionPair> cancelationTypeList = externalDataService.getCancelationTypeMaintenanceTask();
            response = new PairListResponse();
            response.setPairList(cancelationTypeList);
        } catch (Exception e) {
            response = new PairListResponse(processException(e));
        }
        return response;
    }


}
