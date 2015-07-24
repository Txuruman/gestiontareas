package es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DiscardMaintenanceSurveyTaskRequest extends BaseRequest {

    private MaintenanceSurveyTask task;

    private String prueba;

    public DiscardMaintenanceSurveyTaskRequest() {
    }

    public DiscardMaintenanceSurveyTaskRequest(MaintenanceSurveyTask task) {
        this.task = task;
    }

    public MaintenanceSurveyTask getTask() {
        return task;
    }

    public void setTask(MaintenanceSurveyTask task) {
        this.task = task;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    @Override
    public String toString() {
        return "DiscardMaintenanceSurveyTaskRequest{" +
                "task=" + task +
                ", prueba='" + prueba + '\'' +
                '}';
    }
}
