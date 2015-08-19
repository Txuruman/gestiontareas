package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.KeyboxTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaLimpiezaCuota;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.AgentController;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.keyboxtask.DiscardKeyboxTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.keyboxtask.FinalizeKeyboxTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.exceltask.keyboxtask.PostponeKeyboxTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.maintenancetask.MaintenanceTaskCreateRequest;
import es.securitasdirect.tareas.web.controller.dto.request.maintenancetask.MaintenanceTaskFinalizeRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.dto.support.DummyResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author jel
 */

@Controller
@RequestMapping("/keyboxtask")
public class KeyboxTaskController extends TaskController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Inject
    private InstallationService installationDataService;
    @Autowired
    private AgentController agentController;


    private static final Logger LOGGER = LoggerFactory.getLogger(KeyboxTaskController.class);

    /*
    @RequestMapping(value = "/gettarea", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getKeyboxTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        String SERVICE_MESSAGE = "keyboxtask.gettask";
        TareaResponse response;
        LOGGER.debug("Get keybox task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}", ccUserId, callingList, tareaId);
        try{
            KeyboxTask tarea = (KeyboxTask) queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(tarea, SERVICE_MESSAGE);
            LOGGER.debug("Keybox task obtained from service: \n{}", tarea);
        }catch(Exception e){
            response = processException(e, SERVICE_MESSAGE);
        }
        return response;
    }
    */

    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse postpone(@RequestBody PostponeKeyboxTaskRequest request) {
        return super.delayTask(request.getTask(), request.getRecallType(), request.getDelayDate());
    }

    @RequestMapping(value = "/descartar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody DiscardKeyboxTaskRequest request) {
        LOGGER.debug("Descartar de keybox:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Descartada tarea de keybox:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BaseResponse finalizar(@RequestBody FinalizeKeyboxTaskRequest request) {
        LOGGER.debug("Finalizando tarea de keybox:\nRequest: {}", request);
        BaseResponse response = dummyResponseGenerator.dummyFinalizeSuccess();
        LOGGER.debug("Finalizada tarea de keybox:\nResponse: {}",response);
        return response;
    }

    @RequestMapping(value = "/getInstallationAndTask", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody TareaResponse getInstallationAndTask(
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {

        LOGGER.debug("Get Notification task for params: \ncallingList:{}\ntareaId:{}", callingList, tareaId);
        TareaResponse response = new TareaResponse();
        if (agentController.isLogged()) {
            try {
                //Buscar Tarea
                KeyboxTask task = (KeyboxTask)queryTareaService.queryTarea(
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
                    response.danger("getTask.notFound");
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
}
