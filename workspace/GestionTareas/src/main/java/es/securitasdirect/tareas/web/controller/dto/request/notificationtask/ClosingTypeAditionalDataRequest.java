package es.securitasdirect.tareas.web.controller.dto.request.notificationtask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ClosingTypeAditionalDataRequest extends BaseRequest {

    private String closingTypeId;

    public String getClosingTypeId() {
        return closingTypeId;
    }

    public void setClosingTypeId(String closingTypeId) {
        this.closingTypeId = closingTypeId;
    }

    @Override
    public String toString() {
        return "ClosingTypeAditionalDataRequest{" +
                "closingTypeId=" + closingTypeId +
                '}';
    }
}
