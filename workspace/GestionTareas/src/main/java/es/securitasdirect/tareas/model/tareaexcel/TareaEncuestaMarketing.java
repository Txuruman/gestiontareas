package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;
import java.util.List;

/**
 * Encuestas Marketing
 * @author Team Vision.
 */
public class TareaEncuestaMarketing extends TareaExcel {


    /**
     * Fecha
     */
    Date fecha;
    /**
     * Motivo.
     * varchar (500)
     */
    String motivo;



    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        sb.append("fecha=").append(fecha);
        sb.append(", motivo='").append(motivo).append('\'');
        sb.append(", motivosCierre=").append(motivosCierre);
        sb.append(", compensacion='").append(compensacion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
