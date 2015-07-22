package es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeMaintenanceSurveyTaskRequest extends PostponeRequest {

    private MaintenanceSurveyTask task;


    public PostponeMaintenanceSurveyTaskRequest() {
    }

    public PostponeMaintenanceSurveyTaskRequest(MaintenanceSurveyTask task) {
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
