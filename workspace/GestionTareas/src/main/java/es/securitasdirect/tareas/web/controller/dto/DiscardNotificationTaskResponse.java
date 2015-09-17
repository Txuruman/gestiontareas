package es.securitasdirect.tareas.web.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.model.DiscardNotificationTaskResult;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DiscardNotificationTaskResponse extends BaseResponse {

   protected DiscardNotificationTaskResult result;

    public DiscardNotificationTaskResult getResult() {
        return result;
    }

    public void setResult(DiscardNotificationTaskResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DiscardNotificationTaskResponse{");
        sb.append("result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
