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
public class DiscardNotificationTaskRequest extends BaseRequest {

    private TareaAviso task;
    private InstallationData installation;
    private boolean isCallDone;
    private boolean withInteaction;

    public DiscardNotificationTaskRequest() {
    }

    public DiscardNotificationTaskRequest(TareaAviso task) {
        this.task = task;
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

	public boolean isCallDone() {
		return isCallDone;
	}

	public void setCallDone(boolean isCallDone) {
		this.isCallDone = isCallDone;
	}
	
	
	public boolean isWithInteaction() {
		return withInteaction;
	}

	public void setWithInteaction(boolean withInteaction) {
		this.withInteaction = withInteaction;
	}

	@Override
	public String toString() {
		return "DiscardNotificationTaskRequest [task=" + task + ", installation=" + installation + ", isCallDone="
				+ isCallDone + ", withInteaction=" + withInteaction + "]";
	}

}
