package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.List;

/**
 * Limpieza de Cuota
 *
 * @author Team Vision
 */
public class TareaLimpiezaCuota extends TareaExcel {

    /**
     * Contrato.
     */
    String contrato;
    /**
     * Departamento asignado.
     * varchar (30)
     */
    String departamentoAsignado;
    /**
     *  Descripción de la Incidencia.
     *  varchar (50)
     */
    String descripcionIncidencia;
    /**
     * Motivo de Cierre, lista de valores
     */
    List<String> motivosCierre;
    /**
     * Compensación.
     */
    String compensacion;

    public String getDepartamentoAsignado() {
        return departamentoAsignado;
    }

    public void setDepartamentoAsignado(String departamentoAsignado) {
        this.departamentoAsignado = departamentoAsignado;
    }

    public String getDescripcionIncidencia() {
        return descripcionIncidencia;
    }

    public void setDescripcionIncidencia(String descripcionIncidencia) {
        this.descripcionIncidencia = descripcionIncidencia;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public List<String> getMotivosCierre() {
        return motivosCierre;
    }

    public void setMotivosCierre(List<String> motivosCierre) {
        this.motivosCierre = motivosCierre;
    }

    public String getCompensacion() {
        return compensacion;
    }

    public void setCompensacion(String compensacion) {
        this.compensacion = compensacion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaLimpiezaCuota{");
        sb.append("contrato='").append(contrato).append('\'');
        sb.append(", departamentoAsignado='").append(departamentoAsignado).append('\'');
        sb.append(", descripcionIncidencia='").append(descripcionIncidencia).append('\'');
        sb.append(", motivosCierre=").append(motivosCierre);
        sb.append(", compensacion='").append(compensacion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
