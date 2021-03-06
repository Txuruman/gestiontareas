package es.securitasdirect.tareas.web.controller.dto.request.exceltask.maintenancesurvey;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FinalizeMaintenanceSurveyTaskRequest extends BaseRequest {

    private MaintenanceSurveyTask task;

    /** Ultimo telefono al que se ha llamado pulsando en la pantalla al botón del telefono, o vacio si no se ha pulsado ninguno */
    private String lastPhoneCalled;

    public String getLastPhoneCalled() {
        return lastPhoneCalled;
    }

    public void setLastPhoneCalled(String lastPhoneCalled) {
        this.lastPhoneCalled = lastPhoneCalled;
    }

    public FinalizeMaintenanceSurveyTaskRequest() {
    }

    public FinalizeMaintenanceSurveyTaskRequest(MaintenanceSurveyTask task) {
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
        return "FinalizeMaintenanceSurveyTaskRequest{" +
                "task=" + task +
                '}';
    }
}
