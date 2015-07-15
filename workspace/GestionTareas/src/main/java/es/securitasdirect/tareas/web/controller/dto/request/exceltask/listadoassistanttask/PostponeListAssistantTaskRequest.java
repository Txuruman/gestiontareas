package es.securitasdirect.tareas.web.controller.dto.request.exceltask.listadoassistanttask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeListAssistantTaskRequest extends BaseRequest {

    private TareaListadoAssistant task;

    private String prueba;

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

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    @Override
    public String toString() {
        return "PostponeNotificationTaskRequest{" +
                "task=" + task +
                ", prueba='" + prueba + '\'' +
                '}';
    }
}
