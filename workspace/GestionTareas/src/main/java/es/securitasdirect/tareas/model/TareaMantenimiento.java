package es.securitasdirect.tareas.model;

/**
 * Tarea de Tipo Mantenimiento.
 * @author Team Vision
 */

import java.util.Date;

public class TareaMantenimiento extends Tarea {

    /**
     * Contrato
     */
    String contrato;
    /**
     * Dirección
     */
    String direccion;
    /**
     * Ciudad
     */
    String ciudad;
    /**
     * Fecha y hora del Evento
     */
    Date fechaEvento;
    /**
     * Tipificacion
     */
    String tipificacion;
    /**
     * Agente Asignado
     */
    String agenteAsignado;
    /**
     * Agente de Cierre
     */
    String agenteCierre;
    /**
     * Opcion de tipificación
     */
    Integer opcionTipificacion;
    /**
     * Key1
     */
    Integer key1;
    /**
     * Key2
     */
    Integer key2;

    
}
