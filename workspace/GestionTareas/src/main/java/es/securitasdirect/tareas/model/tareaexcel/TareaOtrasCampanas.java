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
}
