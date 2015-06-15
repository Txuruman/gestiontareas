package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

/**
 * Tarea Otras Campañas.
 * En este caso, en función del grupo origen (Tipo de Campaña)  se asigna una Calling List determinada.
 *
 * @author Team Vision
 */
public class TareaOtrasCampanas extends TareaExcel {

    /**
     * Número de Cliente
     */
    String numeroCliente;
    /**
     * Persona de Contacto
     */
    String personaContacto;
    /**
     * Teléfono de Contacto
     */
    String telefonoContacto;
    /**
     * Tipo de Campaña (origen y motivo)
     * varchar (10)
     */
    String tipoCampana;
    /**
     * Comentarios.
     * varchar (1000)
     */
    String comentario;
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

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getTipoCampana() {
        return tipoCampana;
    }

    public void setTipoCampana(String tipoCampana) {
        this.tipoCampana = tipoCampana;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
        final StringBuilder sb = new StringBuilder("TareaOtrasCampanas{");
        sb.append("numeroCliente='").append(numeroCliente).append('\'');
        sb.append(", personaContacto='").append(personaContacto).append('\'');
        sb.append(", telefonoContacto='").append(telefonoContacto).append('\'');
        sb.append(", tipoCampana='").append(tipoCampana).append('\'');
        sb.append(", comentario='").append(comentario).append('\'');
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
