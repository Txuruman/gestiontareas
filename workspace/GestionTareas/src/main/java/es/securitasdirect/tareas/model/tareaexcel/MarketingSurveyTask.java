package es.securitasdirect.tareas.model.tareaexcel;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaExcel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Encuestas Marketing
 * @author Team Vision.
 */
public class MarketingSurveyTask extends TareaExcel {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date fecha;
    private String motivo;

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
        return "MarketingSurveyTask{" +
                "fecha=" + fecha +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
