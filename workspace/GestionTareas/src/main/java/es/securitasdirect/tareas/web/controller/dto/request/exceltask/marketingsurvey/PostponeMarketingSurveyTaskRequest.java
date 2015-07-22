package es.securitasdirect.tareas.web.controller.dto.request.exceltask.marketingsurvey;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeMarketingSurveyTaskRequest extends PostponeRequest {

    private MaintenanceSurveyTask task;

    public PostponeMarketingSurveyTaskRequest() {
    }

    public PostponeMarketingSurveyTaskRequest(MaintenanceSurveyTask task) {
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
        return "PostponeMaintenanceSurveyTaskRequest{" +
                "task=" + task +
                '}';
    }
}
