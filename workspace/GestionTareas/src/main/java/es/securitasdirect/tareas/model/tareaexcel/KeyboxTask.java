package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;

/**
 * Tarea Keybox
 *
 * @author Team Vision
 */
public class KeyboxTask extends TareaExcel {

    /**
     * Invoce number - Número de Factura
     */
    String invoiceNumber;
    /**
     * Invoice date - Fecha de la Factura
     */
    Date invoiceDate;
    /**
     * Importe de la Línea
     */
    Integer lineValue;

    /**
     * Identificador de ítem
     */
    String identificadorItem;
    /**
     * Contrato.
     */
    String contrato;

    /**
     * Panel
     */
    String panel;

    public String getPanel() {
        return panel;
    }

    public void setPanel(String panel) {
        this.panel = panel;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getLineValue() {
        return lineValue;
    }

    public void setLineValue(Integer lineValue) {
        this.lineValue = lineValue;
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
        sb.append("invoiceNumber='").append(invoiceNumber).append('\'');
        sb.append(", invoiceDate=").append(invoiceDate);
        sb.append(", lineValue=").append(lineValue);
        sb.append(", identificadorItem='").append(identificadorItem).append('\'');
        sb.append(", contrato='").append(contrato).append('\'');
        sb.append(", closingReason=").append(closingReason);
        sb.append(", compensation='").append(compensation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
