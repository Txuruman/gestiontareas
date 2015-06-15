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
}
