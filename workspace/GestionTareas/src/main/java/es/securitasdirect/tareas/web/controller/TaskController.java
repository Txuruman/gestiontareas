package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;
import java.util.Date;

public abstract class TaskController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Inject
    protected MessageUtil messageUtil;
    @Inject
    protected TareaService tareaService;

    /**
     * Procesamiento generico para aplazar una tarea
     * @param task
     * @param recallType
     * @param delayDate
     * @return
     */
    public BaseResponse delayTask(Tarea task, String recallType, Date delayDate, String message) {
        LOGGER.debug("Aplazando tarea {} TODO ", task ,delayDate, recallType);

        BaseResponse response = new BaseResponse();

        //Llamada al servicio para aplazar
        try {
            //Llamada al servicio para aplazar
            //TODO - idAViso = task.getIdAviso();
            boolean ok = tareaService.aplazar(9);
            if(ok){
                response.success(messageUtil.getProperty(message + ".success"));
            }else{
                response.danger(messageUtil.getProperty(message + ".notFound"));
            }
        } catch (Exception e) {
            response = processException(e, message + ".error");
        }
        LOGGER.debug("Aplazamiento de tarea\nResponse:{}", response);
        return response;
    }

    /**
     * Se informa con la tarea obtenida y la cadena correspondiente al mensaje de respuesta, salvo la última sección que se define aquí dependiendo del resultado.
     * @param task
     * @param s
     * @return
     */
    public TareaResponse processSuccessTask(Tarea task, String s) {
        TareaResponse tareaResponse = new TareaResponse();
        LOGGER.info("Process task response: {}", task);
        if(task!=null) {
            tareaResponse.setTarea(task);
            tareaResponse.success(messageUtil.getProperty(s+".success"));
        }else{
            tareaResponse.danger(messageUtil.getProperty(s + "notFound"));
        }
        LOGGER.info("Task Response: {}", tareaResponse);
        return tareaResponse;
    }

    /**
     * Se informa con la instalacion obtenida y se informa la respuesta con dicha instalación y el mensaje correspondiente.
     * @param installationData
     * @return
     */
    public TareaResponse processSuccessInstallation(InstallationData installationData) {
        TareaResponse tareaResponse = new TareaResponse();
        LOGGER.info("Process installationData response: {}", installationData);
        if(installationData!=null) {
            tareaResponse.setInstallationData(installationData);
            tareaResponse.success(messageUtil.getProperty("installationData.success"));
        }else{
            tareaResponse.danger(messageUtil.getProperty("installationData.notFound"));
        }
        LOGGER.info("Installation Data Response: {}", tareaResponse);
        return tareaResponse;
    }

    public TareaResponse processException(Exception e, String msg){
        return new TareaResponse(super.processException(e, msg));
    }
}
