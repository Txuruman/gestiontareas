package es.securitasdirect.tareas.web.controller.dto.request.notificationtask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeNotificationTaskRequest extends PostponeRequest {
	
	
    private TareaAviso task;
    

    public PostponeNotificationTaskRequest() {
    }

    public PostponeNotificationTaskRequest(TareaAviso task) {
        this.task = task;
    }

    public TareaAviso getTask() {
        return task;
    }

    public void setTask(TareaAviso task) {
        this.task = task;
    }

	
	@Override
	public String toString() {
		return "PostponeNotificationTaskRequest [task=" + task + "]";
	}

   
}
