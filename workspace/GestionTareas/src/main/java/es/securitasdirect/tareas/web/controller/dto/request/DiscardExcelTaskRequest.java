package es.securitasdirect.tareas.web.controller.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by JAS on 14/09/2015.
 * Request generico para descartar de tareas de tipo excel
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DiscardExcelTaskRequest extends BaseRequest {

    private Tarea task;

    private InstallationData installation;

    public DiscardExcelTaskRequest() {
    }

    public DiscardExcelTaskRequest(Tarea task) {
        this.task = task;
    }

    public Tarea getTask() {
        return task;
    }

    public void setTask(Tarea task) {
        this.task = task;
    }

	public InstallationData getInstallation() {
		return installation;
	}

	public void setInstallation(InstallationData installation) {
		this.installation = installation;
	}

	@Override
	public String toString() {
		return "DiscardExcellTaskRequest [task=" + task + ", installation=" + installation + "]";
	}

   
}
