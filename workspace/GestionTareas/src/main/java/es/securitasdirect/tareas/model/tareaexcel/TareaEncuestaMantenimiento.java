package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.List;

/**
 * Encuestas Mantenimientos.
 *
 *  MANTENIMIENTO		int	POST		SI
 *  TECNICO		varchar (10)	POST		SI
 *  JE		varchar (10)	POST		SI
 *  CC		varchar (5)	POST		SI
 *  RAZON		varchar (500)	POST		SI
 *  SOLUCION		varchar (500)	POST		SI
 *  COMPROMISO		varchar(500)	POST		SI
 *  DPTO_DESTINO		varchar (15)	POST		SI
 *  Motivo de cierre	?			Lista de valores		SI
 *  Conpensación	?					SI
 *
 *
 *
 * @author Team Vision
 */
public class TareaEncuestaMantenimiento extends TareaExcel {


    /**
     * Número de Mantenimiento
     */
    Integer numeroMantenimiento;
    /**
     * Técnico.
     * varchar (10)
     */
    String tecnico;
    /**
     * Responsable, alias JE.
     * varchar(10)
     */
    String responsable;
    /**
     * Centro de Coste.
     * varchar(5)
     */
    String centroCoste;
    /**
     * Razón clave de la valoración.
     * varchar (500)
     */
    String razonClaveValoracion;
    /**
     * Solución.
     * varchar (500)
     */
    String solucion;
    /**
     * Compromiso.
     * varchar(500)
     */
    String compromiso;
    /**
     * Departamento destino.
     * varchar (15)
     */
    String departamentoDestino;


    public Integer getNumeroMantenimiento() {
        return numeroMantenimiento;
    }

    public void setNumeroMantenimiento(Integer numeroMantenimiento) {
        this.numeroMantenimiento = numeroMantenimiento;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getCentroCoste() {
        return centroCoste;
    }

    public void setCentroCoste(String centroCoste) {
        this.centroCoste = centroCoste;
    }

    public String getRazonClaveValoracion() {
        return razonClaveValoracion;
    }

    public void setRazonClaveValoracion(String razonClaveValoracion) {
        this.razonClaveValoracion = razonClaveValoracion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getCompromiso() {
        return compromiso;
    }

    public void setCompromiso(String compromiso) {
        this.compromiso = compromiso;
    }

    public String getDepartamentoDestino() {
        return departamentoDestino;
    }

    public void setDepartamentoDestino(String departamentoDestino) {
        this.departamentoDestino = departamentoDestino;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaEncuestaMantenimiento{");
        sb.append("numeroMantenimiento=").append(numeroMantenimiento);
        sb.append(", tecnico='").append(tecnico).append('\'');
        sb.append(", responsable='").append(responsable).append('\'');
        sb.append(", centroCoste='").append(centroCoste).append('\'');
        sb.append(", razonClaveValoracion='").append(razonClaveValoracion).append('\'');
        sb.append(", solucion='").append(solucion).append('\'');
        sb.append(", compromiso='").append(compromiso).append('\'');
        sb.append(", departamentoDestino='").append(departamentoDestino).append('\'');
        sb.append(", motivosCierre=").append(motivosCierre);
        sb.append(", compensacion='").append(compensacion).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
