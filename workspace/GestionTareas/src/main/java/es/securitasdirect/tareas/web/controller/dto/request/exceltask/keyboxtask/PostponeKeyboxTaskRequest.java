package es.securitasdirect.tareas.web.controller.dto.request.exceltask.keyboxtask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.KeyboxTask;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeKeyboxTaskRequest extends PostponeRequest {

    private KeyboxTask task;


    public PostponeKeyboxTaskRequest() {
    }

    public PostponeKeyboxTaskRequest(KeyboxTask task) {
        this.task = task;
    }

    public KeyboxTask getTask() {
        return task;
    }

    public void setTask(KeyboxTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "PostponeKeyboxTaskRequest{" +
                "task=" + task +
                '}';
    }
}
