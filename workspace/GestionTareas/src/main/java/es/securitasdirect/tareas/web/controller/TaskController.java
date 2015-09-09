package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

public abstract class TaskController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Inject
    protected MessageUtil messageUtil;
    @Inject
    protected TareaService tareaService;
    @Inject
    protected AgentController agentController;
    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private InstallationService installationDataService;


    /**
     * Procesamiento generico para aplazar una tarea
     *
     * @param task
     * @param recallType
     * @param delayDate
     * @return
     */
    public BaseResponse delayTask(Tarea task, String recallType, Date delayDate) {
        LOGGER.debug("Aplazando tarea {} {} {}  ", task, delayDate, recallType);
        BaseResponse response = new BaseResponse();
        //Llamada al servicio para aplazar
        try {
            Agent agent = agentController.getAgent();
            tareaService.delayTask(agent, task, delayDate, recallType);
            response.info(messageUtil.getProperty("postpone.success"));
        } catch (Exception e) {
            response = processException(e);
        }
        LOGGER.debug("Aplazamiento de tarea\nResponse:{}", response);
        return response;
    }


    /**
     * Procesamiento generico para finalizar una tarea. No usar para Aviso ni mantenimiento.
     *
     * @param task
     * @return
     */
    public BaseResponse finalizeTask(Tarea task) {
        assert task != null : "Es necesario el parametro de la tarea";
        LOGGER.debug("Finalizando tarea {}  ", task);
        BaseResponse response = new BaseResponse();
        //Llamada al servicio para finalizar
        try {
            tareaService.finalizeExcelTask(agentController.getAgent(), task);
            response.info(messageUtil.getProperty("finalize.success"));
        } catch (Exception e) {
            response = processException(e);
        }
        LOGGER.debug("Finalizado de tarea\nResponse:{}", response);
        return response;
    }


    public TareaResponse processException(Exception e, String msg) {
        return new TareaResponse(super.processException(e, msg));
    }


    /**
     * Consulta de la Tarea y su instalación, punto básico de entrada a la aplicación cuando se recive una tarea
     * @param callingList
     * @param tareaId
     * @return
     */
    public BaseResponse getInstallationAndTask(String callingList, String tareaId, Map<String, String> parameters) {
        LOGGER.debug("Get Notification task for params: \ncallingList:{}\ntareaId:{}", callingList, tareaId);
        TareaResponse response = new TareaResponse();
        if (agentController.isLogged()) {
            try {
                //Buscar Tarea
                Tarea task = queryTareaService.queryTarea(agentController.getAgent()  , callingList, tareaId);

                response.setTarea(task);
                //Buscamos la instalación
                if (task.getNumeroInstalacion() != null) {
                    InstallationData installationData = installationDataService.getInstallationData(task.getNumeroInstalacion());
                    if (installationData != null) {
                        response.setInstallationData(installationData);
                    } else {
                        response.danger(messageUtil.getProperty("getTask.noInstallation"));
                    }
                } else {
                    response.danger(messageUtil.getProperty("getTask.noInstallation"));
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return processException(e);
            }
        } else {
            response.danger("agent.notLoggedIn");
        }

        return response;


    }

}
