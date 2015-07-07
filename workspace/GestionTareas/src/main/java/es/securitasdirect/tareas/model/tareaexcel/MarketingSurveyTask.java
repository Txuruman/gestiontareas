package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaExcel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Encuestas Marketing
 * @author Team Vision.
 */
public class MarketingSurveyTask extends TareaExcel {


    /**
     * Date - Fecha
     */
    Date date;
    /**
     * Reason - Motivo.
     * varchar (500)
     */
    String reason;



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }




    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaEncuestaMarketing{");
        sb.append("date=").append(date);
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", closingReason=").append(closingReason);
        sb.append(", compensation='").append(compensation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
