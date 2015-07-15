package es.securitasdirect.tareas.web.controller.dto.request.exceltask.feecleaning;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaLimpiezaCuota;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FinalizeFeeCleaningTaskRequest extends BaseRequest {

    private TareaLimpiezaCuota task;

    private String prueba;

    public FinalizeFeeCleaningTaskRequest() {
    }

    public FinalizeFeeCleaningTaskRequest(TareaLimpiezaCuota task) {
        this.task = task;
    }

    public TareaLimpiezaCuota getTask() {
        return task;
    }

    public void setTask(TareaLimpiezaCuota task) {
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
        return "FinalizeMaintenanceSurveyTaskRequest{" +
                "task=" + task +
                ", prueba='" + prueba + '\'' +
                '}';
    }
}
