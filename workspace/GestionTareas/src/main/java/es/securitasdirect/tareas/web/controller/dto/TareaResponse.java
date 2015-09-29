package es.securitasdirect.tareas.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TareaResponse extends BaseResponse {

    private InstallationData installationData;

    private Tarea tarea;
    
    private boolean noInstallation;
    private boolean noTicked;
    
    /*Usamos también cuando el ticked está gestionado*/
    private String noInstallationMsg;
    
    public TareaResponse() {
    }

    public TareaResponse(BaseResponse baseResponse){
        super.setMessages(baseResponse.getMessages());
    }

    public TareaResponse(Tarea tarea) {
        this.tarea = tarea;
    }

    public TareaResponse(BaseResponse baseResponse, Tarea tarea){
        super.setMessages(baseResponse.getMessages());
        this.tarea = tarea;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }


    public InstallationData getInstallationData() {
        return installationData;
    }

    public void setInstallationData(InstallationData installationData) {
        this.installationData = installationData;
    }

	public boolean isNoInstallation() {
		return noInstallation;
	}

	public void setNoInstallation(boolean noInstallation) {
		this.noInstallation = noInstallation;
	}
	
	public boolean isNoTicked() {
		return noTicked;
	}

	public void setNoTicked(boolean noTicked) {
		this.noTicked = noTicked;
	}

	public String getNoInstallationMsg() {
		return noInstallationMsg;
	}

	public void setNoInstallationMsg(String noInstallationMsg) {
		this.noInstallationMsg = noInstallationMsg;
	}

	@Override
	public String toString() {
		return "TareaResponse [installationData=" + installationData + ", tarea=" + tarea + ", noInstallation="
				+ noInstallation + ", noTicked=" + noTicked + ", noInstallationMsg=" + noInstallationMsg + "]";
	}

    
}
