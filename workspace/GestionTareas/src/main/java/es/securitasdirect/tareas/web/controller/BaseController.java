package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.InstallationDataResponse;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Date;

@Controller
@RequestMapping("/installation")
public abstract class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Inject
    protected MessageUtil messageUtil;
    @Inject
    protected TareaService tareaService;

    protected TareaResponse toTareaResponse(Tarea tarea, InstallationData installationData) {
        TareaResponse tareaResponse = new TareaResponse();
        LOGGER.info("Process task response: {}", tarea);
        if (tarea != null) {
            tareaResponse.setTarea(tarea);
            tareaResponse.success(messageUtil.getProperty("task.success"));
        } else {
            tareaResponse.danger(messageUtil.getProperty("task.notFound"));
        }
        if(installationData!=null){
            tareaResponse.setInstallationData(installationData);
            tareaResponse.success(messageUtil.getProperty("installationData.success"));
        }else{
            tareaResponse.danger(messageUtil.getProperty("installationData.notFound"));
        }
        LOGGER.info("Task Response: {}", tareaResponse);
        return tareaResponse;
    }


    protected TareaResponse toTareaResponse(Tarea tarea) {
        TareaResponse tareaResponse = new TareaResponse();
        LOGGER.info("Process task response: {}", tarea);
        if (tarea != null) {
            tareaResponse.setTarea(tarea);
            tareaResponse.success(messageUtil.getProperty("task.success"));
        } else {
            tareaResponse.danger(messageUtil.getProperty("task.notFound"));
        }
        LOGGER.info("Task Response: {}", tareaResponse);
        return tareaResponse;
    }


    /**
     * Procesamiento generico para aplazar una tarea
     * @param task
     * @param recallType
     * @param delayDate
     * @return
     */
    public BaseResponse delayTask(Tarea task, String recallType, Date delayDate) {
        LOGGER.debug("Aplazando tarea {} TODO ", task ,delayDate, recallType);

        BaseResponse response = new BaseResponse();

        //Llamada al servicio para aplazar
        try {
            //Llamada al servicio para aplazar
            boolean ok = tareaService.aplazar(9);
            if(ok){
                response.success(messageUtil.getProperty("notificationTask.postpone.success"));
            }else{
                response.danger(messageUtil.getProperty("notificationTask.postpone.error"));
            }
        } catch (Exception e) {
            response = processException(e);
        }

        LOGGER.debug("Aplazamiento de tarea\nResponse:{}", response);
        return response;
    }




    protected BaseResponse processException(Exception exception) {
        LOGGER.error("Error sent in BaseResponse {}" , exception.getMessage());
        BaseResponse response = new BaseResponse();
        response.danger(exception.getMessage()); //TODO MENSAGE GENERICO CON
        return response;
    }
}
