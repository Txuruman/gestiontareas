package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;

/**
 * Tarea Keybox
 *
 * @author Team Vision
 */
public class TareaKeybox extends TareaExcel {

    /**
     * Número de Instalación
     */
    String numeroInstalacion;
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
    Double importeLinea;

    /**
     * Identificador de ítem
     */
    String identificadorItem;
    /**
     * Tipo de Panel.
     */
    String tipoPanel;

    public String getNumeroInstalacion() {
        return numeroInstalacion;
    }

    public void setNumeroInstalacion(String numeroInstalacion) {
        this.numeroInstalacion = numeroInstalacion;
    }

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

    public Double getImporteLinea() {
        return importeLinea;
    }

    public void setImporteLinea(Double importeLinea) {
        this.importeLinea = importeLinea;
    }

    public String getIdentificadorItem() {
        return identificadorItem;
    }

    public void setIdentificadorItem(String identificadorItem) {
        this.identificadorItem = identificadorItem;
    }

    public String getTipoPanel() {
        return tipoPanel;
    }

    public void setTipoPanel(String tipoPanel) {
        this.tipoPanel = tipoPanel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaKeybox{");
        sb.append("numeroInstalacion='").append(numeroInstalacion).append('\'');
        sb.append(", numeroFactura='").append(numeroFactura).append('\'');
        sb.append(", fechaFactura=").append(fechaFactura);
        sb.append(", importeLinea=").append(importeLinea);
        sb.append(", identificadorItem='").append(identificadorItem).append('\'');
        sb.append(", tipoPanel='").append(tipoPanel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
