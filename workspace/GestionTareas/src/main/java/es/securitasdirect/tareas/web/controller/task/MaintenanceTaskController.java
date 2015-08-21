package es.securitasdirect.tareas.web.controller.task;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.external.DescriptionPair;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.AgentController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.maintenancetask.MaintenanceTaskCreateRequest;
import es.securitasdirect.tareas.web.controller.dto.request.maintenancetask.MaintenanceTaskFinalizeRequest;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
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
import java.util.List;

/**
 * @author jel
 */

@Controller
@RequestMapping("/maintenancetask")
public class MaintenanceTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Inject
    private ExternalDataService externalDataService;
    @Inject
    private InstallationService installationDataService;
    @Autowired
    private AgentController agentController;



    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceTaskController.class);

    /*
    @RequestMapping(value = "/gettarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getMaintenanceTask(
        @RequestParam(value = "ccUserId", required = true) String ccUserId,
        @RequestParam(value = "callingList", required = true) String callingList,
        @RequestParam(value = "tareaId", required = true) String tareaId
    ) throws DataServiceFault {
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        TareaResponse response;
        String SERVICE_MESSAGE = "maintenancetask.gettarea";
        try{
            TareaMantenimiento tareaMantenimiento = (TareaMantenimiento) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(tareaMantenimiento,SERVICE_MESSAGE);
            LOGGER.debug("Maintenance task obtained from service: \n{}", tareaMantenimiento);
        }catch(Exception e){
            response = new TareaResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }
    */

    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getInstallationAndTask(
        @RequestParam(value = "callingList", required = true) String callingList,
        @RequestParam(value = "tareaId", required = true) String tareaId
    ) {


        LOGGER.debug("Get Notification task for params: \ncallingList:{}\ntareaId:{}", callingList, tareaId);
        TareaResponse response = new TareaResponse();
        if (agentController.isLogged()) {
            try {
                //Buscar Tarea
                TareaMantenimiento task = (TareaMantenimiento)queryTareaService.queryTarea(
                        agentController.getAgent().getIdAgent(),
                        agentController.getAgent().getAgentCountryJob(),
                        agentController.getAgent().getDesktopDepartment()
                        , callingList, tareaId);
                if (task!=null) {
                    response.setTarea(task);
                    //Buscamos la instalación
                    if (task.getNumeroInstalacion()!=null) {
                        InstallationData installationData = installationDataService.getInstallationData(task.getNumeroInstalacion());
                        if (installationData!=null) {
                            response.setInstallationData(installationData);
                        } else {
                            response.danger(messageUtil.getProperty("getTask.noInstallation"));
                        }
                    } else {
                        response.danger(messageUtil.getProperty("getTask.noInstallation"));
                    }
                } else {
                    response.danger(messageUtil.getProperty("getTask.notFound"));
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
                processException(e);
            }
        } else {
            response.danger("agent.notLoggedIn");
        }

        return response;

    }




    @RequestMapping(value = "/create", method = {RequestMethod.PUT}, consumes  = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse interactionCreateMaintenance(@RequestBody MaintenanceTaskCreateRequest peticion) {
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


    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse finalizeTask(@RequestBody MaintenanceTaskFinalizeRequest request) {
        return super.finalizeTask(request.getTask());
    }

    @RequestMapping(value = "/getDesplegableKey1", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getDesplegableKey1() {
        String SERVICE_MESSAGE = "maintenanceTask.getDesplegableKey1";
        PairListResponse response;
        try{
            List<Pair> desplegableKey1 = externalDataService.getDesplegableKey1();
            response = new PairListResponse(processSuccessMessages(desplegableKey1,SERVICE_MESSAGE ));
            response.setPairList(desplegableKey1);
        }catch(Exception e){
            response = new PairListResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }

    @RequestMapping(value = "/getDesplegableKey2", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getDesplegableKey2(@RequestParam(value = "key1", required = true) Integer key1)  {
        String SERVICE_MESSAGE =  "maintenanceTask.getDesplegableKey2";
        PairListResponse response;
        try{
            List<Pair> desplegableKey2 = externalDataService.getDesplegableKey2(key1);
            response = new PairListResponse(processSuccessMessages(desplegableKey2,SERVICE_MESSAGE));
            response.setPairList(desplegableKey2);
        }catch(Exception e){
            response = new PairListResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }

    /**
     * Consulta datos combo cierre específicos para Tarea Mantenimiento.
     * @return
     */
    @RequestMapping(value = "/getCancelationType", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getCancelationType(){
        PairListResponse response;
        try{
            List<DescriptionPair> cancelationTypeList = externalDataService.getCancelationTypeMaintenanceTask();
            //Internacinalización de los mensajes
            for (DescriptionPair descriptionPair : cancelationTypeList) {
                descriptionPair.setValue(messageUtil.getProperty(descriptionPair.getValue()));
                descriptionPair.setDescription(messageUtil.getProperty(descriptionPair.getDescription()));
            }
            response = new PairListResponse();
            response.setPairList(cancelationTypeList);
        }catch(Exception e){
            response = new PairListResponse(processException(e));
        }
        return response;
    }


}
