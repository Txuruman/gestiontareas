package es.securitasdirect.tareas.web.controller.dto.request.notificationtask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ModifyNotificationTaskRequest extends BaseRequest {

    private TareaAviso task;
    private InstallationData installation;


    private String prueba;

    public ModifyNotificationTaskRequest() {
    }

    public ModifyNotificationTaskRequest(TareaAviso task, InstallationData installation) {
        this.task = task;
        this.installation=installation;
    }

    public TareaAviso getTask() {
        return task;
    }

    public void setTask(TareaAviso task) {
        this.task = task;
    }

    public InstallationData getInstallation() {
        return installation;
    }

    public void setInstallation(InstallationData installation) {
        this.installation = installation;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    @Override
    public String toString() {
        return "ModifyNotificationTaskRequest{" +
                "task=" + task + ", installation=" + installation +
        ", prueba='" + prueba + '\'' +
                '}';
    }
}
