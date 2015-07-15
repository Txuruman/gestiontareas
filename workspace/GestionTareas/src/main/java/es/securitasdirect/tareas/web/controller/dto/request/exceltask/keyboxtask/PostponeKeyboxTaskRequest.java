package es.securitasdirect.tareas.web.controller.dto.request.exceltask.keyboxtask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.KeyboxTask;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PostponeKeyboxTaskRequest extends BaseRequest {

    private KeyboxTask task;

    private String prueba;

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

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    @Override
    public String toString() {
        return "PostponeKeyboxTaskRequest{" +
                "task=" + task +
                ", prueba='" + prueba + '\'' +
                '}';
    }
}
