package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

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

    public String getTypeName(){
    	return "TASK_TYPE_LIMPIEZA_CUOTA";
    }
    
    public void setTypeName(String type){
    	
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaLimpiezaCuota{");
        sb.append("contrato='").append(contrato).append('\'');
        sb.append(", departamentoAsignado='").append(departamentoAsignado).append('\'');
        sb.append(", descripcionIncidencia='").append(descripcionIncidencia).append('\'');
        sb.append(", closingReason=").append(closingReason);
        sb.append(", compensation='").append(compensation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
