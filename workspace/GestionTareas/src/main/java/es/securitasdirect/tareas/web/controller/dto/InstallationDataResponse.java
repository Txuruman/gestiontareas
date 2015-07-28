package es.securitasdirect.tareas.web.controller.dto;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

import java.util.List;

/**
 * Created by Roberto on 13/07/2015.
 */
public class InstallationDataResponse extends BaseResponse {
    private InstallationData installationData;

    public InstallationDataResponse(){}

    public InstallationDataResponse(BaseResponse baseResponse){
        super.setMessages(baseResponse.getMessages());
    }

    public InstallationData getInstallationData() {
        return installationData;
    }

    public void setInstallationData(InstallationData installationData) {
        this.installationData = installationData;
    }

    @Override
    public String toString() {
        return "InstallationDataResponse{" +
                "installationData=" + installationData +
                '}';
    }
}
