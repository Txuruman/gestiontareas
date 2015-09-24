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
    private boolean callDone;
	private boolean withInteaction;

    public DiscardNotificationTaskRequest() {
    }

   public DiscardNotificationTaskRequest(TareaAviso task, InstallationData installation, boolean callDone,
			boolean withInteaction) {
		super();
		this.task = task;
		this.installation = installation;
		this.callDone = callDone;
		this.withInteaction = withInteaction;
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
	
	public boolean isWithInteaction() {
		return withInteaction;
	}

	public void setWithInteaction(boolean withInteaction) {
		this.withInteaction = withInteaction;
	}
	

	public boolean isCallDone() {
		return callDone;
	}

	public void setCallDone(boolean callDone) {
		this.callDone = callDone;
	}

	@Override
	public String toString() {
		return "DiscardNotificationTaskRequest [task=" + task + ", installation=" + installation + ", callDone="
				+ callDone + ", withInteaction=" + withInteaction + "]";
	}

}
