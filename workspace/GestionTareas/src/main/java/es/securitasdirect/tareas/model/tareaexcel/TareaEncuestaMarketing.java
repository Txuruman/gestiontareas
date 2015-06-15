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

}
