package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;

/**
 * @author Team Vision
 */
public class TareaListadoAssistant extends TareaExcel {

    /**
     * Número de Instalación.
     * varchar (12)
     */
    String numeroInstalacion;
    /**
     * Número de Mantenimiento
     */
    Integer numeroMantenimiento;
    /**
     * Nombre
     * varchar(120)
     */
    String nombre;
    /**
     * Telefono.
     * varchar(15)
     */
    String telefono;
    /**
     * Técnico.
     * varchar (10)
     */
    String tecnico;
    /**
     * Departamento.
     * varchar (50)
     */
    String departamento;
    /**
     * Grupo del Panel.
     * varchar (20)
     */
    String grupoPanel;
    /**
     * Total sin IVA
     */
    Float totalSinIVA;
    /**
     * Total con IVA
     */
    Float totalConIVA;
    /**
     * Número de Parte.
     * varchar(10)
     */
    String numeroParte;
    /**
     * Fecha de Cierre
     */
    Date fechaCierre;
    /**
     * Fecha de Archivo
     */
    Date fechaArchivo;
    /**
     * Fecha de Incidencia
     */
    Date fechaIncidencia;
    /**
     * Fecha de Pago
     */
    Date fechaPago;
    /**
     * Operador.
     * varchar (10)
     */
    String operador;
    /**
     * Tipo de Incidencia.
     * varchar (50)
     */
    String tipoIncicencia;
    /**
     * Subtipo de Incidencia.
     * varchar (50)
     */
    String subtipoIncidencia;
    /**
     * Solicitud al cliente.
     * varchar (200)
     */
    String solicitudCliente;
    /**
     * Incidencia.
     * Varchar(120)
     */
    String incidencia;
    /**
     * Cambios en la incidencia.
     * varchar (40)
     */
    String cambiosIncidencia;

    /*  Datos de Back Office (fecha de gestion, matrícula, datos recuperados, fecha de recepción, tipo y comentarios). */

    /**
     * Fecha de Gestión
     */
    Date boFechaGestion;
    /**
     * Matrícula.
     * varchar (10)
     */
    String boMatricula;
    /**
     * Datos recuperados.
     * varchar (120)
     */
    String boDatosRecuperados;
    /**
     * Fecha de recepción
     */
    Date boFechaRecepcion;
    /**
     * Tipo.
     * varchar (20)
     */
    String boTipo;
    /**
     * Comentarios.
     * varchar (500)
     */
    String boComentarios;


}
