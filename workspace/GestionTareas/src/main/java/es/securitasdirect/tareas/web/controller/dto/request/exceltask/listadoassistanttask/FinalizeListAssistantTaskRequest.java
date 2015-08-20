package es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FinalizeListAssistantTaskRequest extends BaseRequest {

    private TareaListadoAssistant task;

    public FinalizeListAssistantTaskRequest() {
    }

    public FinalizeListAssistantTaskRequest(TareaListadoAssistant task) {
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
        return "FinalizeNotificationTaskRequest{" +
                "task=" + task +
                '}';
    }
}
