package es.securitasdirect.tareas.model;

/**
 * Tarea de Tipo Mantenimiento.
 * @author Team Vision
 */

import java.util.Date;

public class TareaMantenimiento extends Tarea {
    /**
     * Identificativo Instalación: número de instalación/cliente
     */
    String numeroInstalacion;
    /**
     * Contrato
     */
    String contrato;
    /**
     * Telefónos de contacto: los dos primeros del Plan de Acción y el de la instalación
     */
    String telefonoContactoPlanAccion1;
    String telefonoContactoPlanAccion2;
    /** Telefono de la instalación */
    String telefonoContactoInstalacion;
    /**
     * Dirección
     */
    String direccion;
    /**
     * Ciudad
     */
    String ciudad;
    /**
     * Panel: tipo de panel de la instalación
     */
    String tipoPanel;
    /**
     * Versión: versión del panel
     */
    String versionPanel;
    /**
     * Fecha y hora del evento
     */
    Date fechaEvento;
    /**
     * Tipificación
     */
    String tipificacion;
    /**
     * Agente asignado
     */
    String agenteAsignado;
    /**
     * Agente de cierre
     */
    String agenteCierre;
    /**
     * Tipo de Mantenimiento
     */
    String tipoMantenimiento;
}
