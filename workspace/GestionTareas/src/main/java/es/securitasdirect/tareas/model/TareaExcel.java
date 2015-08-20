package es.securitasdirect.tareas.model;

import es.securitasdirect.tareas.model.external.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Información de las Tareas de tipo Excel
 */
public abstract class TareaExcel extends Tarea {

    /**
     * Closing reason - Motivo de Cierre, lista de valores
     */
    protected Integer closingReason;
    /**
     * Compensation - Compensación.
     */
    protected String compensation;

    public Integer getClosingReason() {
        return closingReason;
    }

    public void setClosingReason(Integer closingReason) {
        this.closingReason = closingReason;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

}
