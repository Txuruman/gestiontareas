package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;

/**
 * Encuestas Marketing
 * @author Team Vision.
 */
public class TareaEncuestaMarketing extends TareaExcel {


    /**
     * Fecha
     */
    Date date;
    /**
     * Motivo.
     * varchar (500)
     */
    String motivo;



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaEncuestaMarketing{");
        sb.append("date=").append(date);
        sb.append(", motivo='").append(motivo).append('\'');
        sb.append(", closingReason=").append(closingReason);
        sb.append(", compensation='").append(compensation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
