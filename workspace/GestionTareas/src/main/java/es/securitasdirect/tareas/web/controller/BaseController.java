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
    @Inject
    protected TareaService tareaService;

    protected BaseResponse processException(Exception exception) {
        LOGGER.error("Error sent in BaseResponse {}" , exception.getMessage());
        BaseResponse response = new BaseResponse();
        response.danger(exception.getMessage());
        return response;
    }

    protected BaseResponse processException(Exception exception, String funcMsgParam){
        String funcMsg = funcMsgParam + ".error";
        BaseResponse response = new BaseResponse();
        if(funcMsg!=null && !funcMsg.isEmpty()){
            String localizedMessage = messageUtil.getProperty(funcMsg);
            if(localizedMessage!=null && !localizedMessage.isEmpty()){
                response.danger(localizedMessage);
            }else{
                response.warning("Localized message not found");
                response.danger(funcMsg);
            }
        }
        LOGGER.error(funcMsg);
        if(exception instanceof DataServiceFault){
            DataServiceFault dataServiceFault = (DataServiceFault) exception;
            StringBuilder sb = new StringBuilder();
            sb.append("Error in data service: ")
                    .append("FaultInfo:").append(dataServiceFault.getFaultInfo())
                    .append("Message:").append(dataServiceFault.getMessage());
            response.danger(sb.toString());
            sb.append("\n").append(dataServiceFault.toString());
            LOGGER.error(sb.toString());
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append("Error sent in response: ").append(exception.getMessage()).append(" - ").append(exception.toString()).append(" - ").append(exception);
            response.danger(sb.toString());
            LOGGER.error(sb.toString().replace(" - ", "\n"));
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
