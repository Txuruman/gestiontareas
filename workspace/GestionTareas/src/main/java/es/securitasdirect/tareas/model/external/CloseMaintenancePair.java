package es.securitasdirect.tareas.model.external;


/**
 * Clase para almacenar los tipos de cierre de tarea de tipo Mantenimiento que incluye
 * un boolean que indica si se debe de abir la url de mantenimiento
 */
public class CloseMaintenancePair extends DescriptionPair {

    private boolean openMaintenanceWindow = false;

    public boolean isOpenMaintenanceWindow() {
        return openMaintenanceWindow;
    }

    public void setOpenMaintenanceWindow(boolean openMaintenanceWindow) {
        this.openMaintenanceWindow = openMaintenanceWindow;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CloseMaintenancePair{");
        sb.append("openMaintenanceWindow=").append(openMaintenanceWindow);
        sb.append('}');
        return sb.toString();
    }
}
