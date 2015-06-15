package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;

/**
 * Encuestas Marketing
 * @author Team Vision.
 */
public class TareaEncuestaMarketing extends TareaExcel {

    /**
     * Número de Instalación
     */
    String numeroInstalacion;
    /**
     * Cliente.
     * varchar (12)
     */
    String cliente;
    /**
     * Fecha
     */
    Date fecha;
    /**
     * Motivo.
     * varchar (500)
     */
    String motivo;
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



    public String getNumeroInstalacion() {
        return numeroInstalacion;
    }

    public void setNumeroInstalacion(String numeroInstalacion) {
        this.numeroInstalacion = numeroInstalacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

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
        final StringBuilder sb = new StringBuilder("TareaEncuestaMarketing{");
        sb.append("numeroInstalacion='").append(numeroInstalacion).append('\'');
        sb.append(", cliente='").append(cliente).append('\'');
        sb.append(", fecha=").append(fecha);
        sb.append(", motivo='").append(motivo).append('\'');
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
