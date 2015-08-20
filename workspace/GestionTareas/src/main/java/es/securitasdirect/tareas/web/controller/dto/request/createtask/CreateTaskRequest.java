package es.securitasdirect.tareas.web.controller.dto.request.createtask;

import org.wso2.ws.dataservice.Installation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.tareaexcel.TareaOtrasCampanas;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CreateTaskRequest extends BaseRequest {

    private TareaAviso task;
    private InstallationData installationData;

    public CreateTaskRequest() {
    }

    public CreateTaskRequest(TareaAviso task, InstallationData installationData) {
        this.task = task;
        this.installationData=installationData;
    }

    public TareaAviso getTask() {
        return task;
    }

    public void setTask(TareaAviso task) {
        this.task = task;
    }
    
    
    public InstallationData getInstallationData() {
		return installationData;
	}

	public void setInstallationData(InstallationData installationData) {
		this.installationData = installationData;
	}

	@Override
	public String toString() {
		return "CreateTaskRequest [task=" + task + ", installationData=" + installationData + "]";
	}

	
}
