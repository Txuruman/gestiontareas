package es.securitasdirect.tareas.web.controller.dto.request.exceltask.feecleaning;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaLimpiezaCuota;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeFeeCleaningTaskRequest extends PostponeRequest {

    private TareaLimpiezaCuota task;


    public PostponeFeeCleaningTaskRequest() {
    }

    public PostponeFeeCleaningTaskRequest(TareaLimpiezaCuota task) {
        this.task = task;
    }

    public TareaLimpiezaCuota getTask() {
        return task;
    }

    public void setTask(TareaLimpiezaCuota task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "PostponeFeeCleaningTaskRequest{" +
                "task=" + task +
                '}';
    }
}
