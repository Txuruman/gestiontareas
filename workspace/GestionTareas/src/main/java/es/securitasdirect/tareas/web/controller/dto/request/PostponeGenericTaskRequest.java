package es.securitasdirect.tareas.web.controller.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeGenericTaskRequest extends PostponeRequest {

    private Tarea task;


    public PostponeGenericTaskRequest() {
    }

    public PostponeGenericTaskRequest(Tarea task) {
        this.task = task;
    }

    public Tarea getTask() {
        return task;
    }

    public void setTask(Tarea task) {
        this.task = task;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PostponeNotificationTaskRequest{");
        sb.append("delayDate=").append(delayDate);
        sb.append(", recallType=").append(recallType);
        sb.append(", task=").append(task);
        sb.append('}');
        return sb.toString();
    }
}
