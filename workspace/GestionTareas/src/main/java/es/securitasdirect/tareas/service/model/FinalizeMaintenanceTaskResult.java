package es.securitasdirect.tareas.service.model;

import es.securitasdirect.tareas.model.Agent;

/**
 * Created by Javier Naval on 21/09/2015.
 */
public class FinalizeMaintenanceTaskResult {

    /** Indica si se debe de abrir la ventana de mantenimiento al terminar */
    private boolean openMaintenanceWindow = false;

    /** Agente, por si se le ha actualizado la session de infopoint */
    private Agent agent;

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
}
