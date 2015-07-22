package es.securitasdirect.tareas.web.controller.dto.request.exceltask.anothercampaigns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeAnotherCampaignsTaskRequest extends PostponeRequest {

    private MaintenanceSurveyTask task;

    public PostponeAnotherCampaignsTaskRequest() {
    }

    public PostponeAnotherCampaignsTaskRequest(MaintenanceSurveyTask task) {
        this.task = task;
    }

    public MaintenanceSurveyTask getTask() {
        return task;
    }

    public void setTask(MaintenanceSurveyTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PostponeAnotherCampaignsTaskRequest{");
        sb.append("task=").append(task);
        sb.append('}');
        return sb.toString();
    }
}
