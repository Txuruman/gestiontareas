package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

/**
 * Limpieza de Cuota
 *
 * @author Team Vision
 */
public class TareaLimpiezaCuota extends TareaExcel {

    /**
     * Instalacion.
     * varchar(12)
     */
    String instalacion;
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
     * Nombre.
     * varchar (120)
     */
    String nombre;
    /**
     * Telefono.
     * varchar(15)
     */
    String telefono;

    public String getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(String instalacion) {
        this.instalacion = instalacion;
    }

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaLimpiezaCuota{");
        sb.append("instalacion='").append(instalacion).append('\'');
        sb.append(", departamentoAsignado='").append(departamentoAsignado).append('\'');
        sb.append(", descripcionIncidencia='").append(descripcionIncidencia).append('\'');
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
