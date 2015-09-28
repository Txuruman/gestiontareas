package es.securitasdirect.tareas.service.model;

import es.securitasdirect.tareas.model.Agent;

/**
 * Created by Javier Naval on 21/09/2015.
 */
public class FinalizeMaintenanceTaskResult {

    /** Indica si se debe de abrir la ventana de mantenimiento al terminar */
    private boolean openMaintenanceWindow = false;

    /** Url a abrir con los parametros preparados */
    private  String openMaintenanceWindowURL;

    /** Agente, por si se le ha actualizado la session de infopoint */
    private Agent agent;
    private boolean createdInfopointSession = false;

    public boolean isOpenMaintenanceWindow() {
        return openMaintenanceWindow;
    }

    public void setOpenMaintenanceWindow(boolean openMaintenanceWindow) {
        this.openMaintenanceWindow = openMaintenanceWindow;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getOpenMaintenanceWindowURL() {
        return openMaintenanceWindowURL;
    }

    public void setOpenMaintenanceWindowURL(String openMaintenanceWindowURL) {
        this.openMaintenanceWindowURL = openMaintenanceWindowURL;
    }

    public void setCreatedInfopointSession(boolean createdInfopointSession) {
        this.createdInfopointSession = createdInfopointSession;
    }

    public boolean isCreatedInfopointSession() {
        return createdInfopointSession;
    }
}
