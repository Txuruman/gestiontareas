package es.securitasdirect.tareas.model;

import java.util.Date;

/**
 * Tareas de tipo Aviso
 */
public class TareaAviso extends Tarea {

    /**
     * Tipos de Aviso: el primero es el utilizado, el segundo y el tercero son sólo informativos.
     */
    String tipoAviso1;
    String tipoAviso2;
    String tipoAviso3;
    /**
     * Motivos: el primero es el utilizado, el segundo y el tercero son sólo informativos.
     */
    String motivo1;
    String motivo2;
    String motivo3;
    /*
    Observaciones: información adicional
    */
    String observaciones;
    /**
     * Identificativo Aviso-Tarea: conexión entre el Aviso y la Tarea (para poder seguir la trazabilidad entre ambos aplicativos)
     */
    String identificativoAvisoTarea;
    /**
     * Identificativo Instalación: número de instalación/cliente
     */
    String numeroInstalacion;
    /*	Titular: nombre del cliente, a modo informativo  */
    String titular;
    /*	Panel: tipo de panel de la instalación  */
    String tipoPanel;
    /*	Versión: versión del panel  */
    String versionPanel;
    /*	Requerido por: importante para el reporting y para algunos departamentos  */
    String requeridoPor;
    /*	Datos de Contacto: cliente que inició la incidencia, forma de contacto, horarios de contacto  */
    String datosContacto;
    /*	Fecha de creación: información para trazabilidad y para la gestión de días de vencimiento  */
    Date fechaCreacion;
    /*	Estado  */
    String estado;
    /*	Datos del cierre: motivo, fecha, responsable y datos adicionales  */
    String datosCierre;


}
