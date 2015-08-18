package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public abstract class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Inject
    protected MessageUtil messageUtil;


    /**
     * Procesa una excepción y añade el texto de error como respuesta del mensaje
     * @param exception
     * @return
     */
    protected BaseResponse processException(Exception exception) {
        LOGGER.error("Server error {}" , exception.getMessage());
        BaseResponse response = new BaseResponse();
        response.danger(exception.getMessage());
        return response;
    }

    /**
     * Procesa una excepción , añade el mensaje y los datos de la excepción.
     * @param exception
     * @param funcMsgParam
     * @return
     */
    protected BaseResponse processException(Exception exception, String funcMsgParam){
        BaseResponse response = new BaseResponse();
        if(funcMsgParam!=null && !funcMsgParam.isEmpty()){
            try {
                String localizedMessage = messageUtil.getProperty(funcMsgParam + ".error");
                response.danger(localizedMessage);
            } catch (Exception e) {
                response.danger(e.getMessage());
            }
        }

        return response;
    }

    protected BaseResponse processSuccessMessages(Object obj, String msg) {
        BaseResponse response = new BaseResponse();
        if (obj != null) {
            if (obj instanceof List) {
                if (!((List) obj).isEmpty()) {
                    response.success(messageUtil.getProperty(msg + ".success"));
                    LOGGER.debug("Service {} - Response {}", msg, "Success");
                } else {
                    response.warning(messageUtil.getProperty(msg + ".notFound"));
                    LOGGER.debug("Service {} - Response {}", msg, "Not Found");
                }
            } else {
                response.success(messageUtil.getProperty(msg + ".success"));
                LOGGER.debug("Service {} - Response {}", msg, "Success");
            }
        } else {
            response.warning(messageUtil.getProperty(msg + ".notFound"));
            LOGGER.debug("Service {} - Response {}", msg, "Not Found");
        }
        return response;
    }

    protected BaseResponse processParamsError(String msg){
        BaseResponse response = new BaseResponse();
        response.warning(msg + ".paramsError");
        return response;
    }



    protected BaseResponse processSuccessMessages(boolean res, String msg){
        Object object = null;
        if(res){
            object=new Object();
        }
        return processSuccessMessages(object, msg);
    }
}
