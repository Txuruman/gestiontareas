package es.securitasdirect.tareas.model;

import java.util.List;

/**
 * Información de las Tareas de tipo Excel
 */
public abstract class TareaExcel extends Tarea {

    /**
     * Closing reason - Motivo de Cierre, lista de valores
     */
    protected List<String> closingReason;
    /**
     * Compensation - Compensación.
     */
    protected String compensation;

    public List<String> getClosingReason() {
        return closingReason;
    }

    public void setClosingReason(List<String> closingReason) {
        this.closingReason = closingReason;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }
}
