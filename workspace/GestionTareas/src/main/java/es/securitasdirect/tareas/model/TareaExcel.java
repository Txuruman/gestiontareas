package es.securitasdirect.tareas.model;

import java.util.List;

/**
 * Información de las Tareas de tipo Excel
 */
public class TareaExcel extends Tarea {

    /**
     * Motivo de Cierre, lista de valores
     */
    protected List<String> motivosCierre;
    /**
     * Compensación.
     */
    protected String compensacion;

    public List<String> getMotivosCierre() {
        return motivosCierre;
    }

    public void setMotivosCierre(List<String> motivosCierre) {
        this.motivosCierre = motivosCierre;
    }
}
