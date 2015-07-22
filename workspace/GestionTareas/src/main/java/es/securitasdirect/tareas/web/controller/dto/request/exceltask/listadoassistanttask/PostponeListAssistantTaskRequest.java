package es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeListAssistantTaskRequest extends PostponeRequest {

    private TareaListadoAssistant task;

    public PostponeListAssistantTaskRequest() {
    }

    public PostponeListAssistantTaskRequest(TareaListadoAssistant task) {
        this.task = task;
    }

    public TareaListadoAssistant getTask() {
        return task;
    }

    public void setTask(TareaListadoAssistant task) {
        this.task = task;
    }


    @Override
    public String toString() {
        return "PostponeNotificationTaskRequest{" +
                "task=" + task +
                '}';
    }
}
