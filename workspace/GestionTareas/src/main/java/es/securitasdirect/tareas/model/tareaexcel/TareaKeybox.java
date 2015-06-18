package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;
import java.util.List;

/**
 * Tarea Keybox
 *
 * @author Team Vision
 */
public class TareaKeybox extends TareaExcel {

    /**
     * Número de Factura
     */
    String numeroFactura;
    /**
     * Fecha de la Factura
     */
    Date fechaFactura;
    /**
     * Importe de la Línea
     */
    Integer importeLinea;

    /**
     * Identificador de ítem
     */
    String identificadorItem;
    /**
     * Contrato.
     */
    String contrato;


    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Integer getImporteLinea() {
        return importeLinea;
    }

    public void setImporteLinea(Integer importeLinea) {
        this.importeLinea = importeLinea;
    }

    public String getIdentificadorItem() {
        return identificadorItem;
    }

    public void setIdentificadorItem(String identificadorItem) {
        this.identificadorItem = identificadorItem;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaKeybox{");
        sb.append("numeroFactura='").append(numeroFactura).append('\'');
        sb.append(", fechaFactura=").append(fechaFactura);
        sb.append(", importeLinea=").append(importeLinea);
        sb.append(", identificadorItem='").append(identificadorItem).append('\'');
        sb.append(", contrato='").append(contrato).append('\'');
        sb.append(", motivosCierre=").append(motivosCierre);
        sb.append(", compensacion='").append(compensacion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
