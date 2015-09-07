package es.securitasdirect.tareas.web.controller.dto.request.notificationtask;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Request para consultar el tipo de aplicación de crear mantenimiento que hay que abrir.
 */
public class CreateMaintenanceAppRequest extends BaseRequest {

    private InstallationData installationData;
    private TareaAviso tareaAviso;

    public InstallationData getInstallationData() {
        return installationData;
    }

    public void setInstallationData(InstallationData installationData) {
        this.installationData = installationData;
    }

    public TareaAviso getTareaAviso() {
        return tareaAviso;
    }

    public void setTareaAviso(TareaAviso tareaAviso) {
        this.tareaAviso = tareaAviso;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CreateMaintenanceAppRequest{");
        sb.append("installationData=").append(installationData);
        sb.append(", tareaAviso=").append(tareaAviso);
        sb.append('}');
        return sb.toString();
    }
}
