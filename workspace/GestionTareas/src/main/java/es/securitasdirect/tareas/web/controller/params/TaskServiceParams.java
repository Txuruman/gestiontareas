package es.securitasdirect.tareas.web.controller.params;

/**
 * Constantes de los parametros que nos pasan desde el IWS.
 * Al documento Integracion_IWS_Web_DiseñoTecnico_v2.5 contiene un listado y explicaciones.
 */
public interface TaskServiceParams {

    public static String NUMERO_INSTALACION = "ins_no";
    public static String COMPENSACION = "compensation";


    /**
     * TAREA COMUNES
     */
    public static String TAREA_COMMONS_PERSONA_CONTACTO = "NOMBRE";
    public static String TAREA_COMMONS_INSTALACION = "INSTALACION";
    public static String TAREA_COMMONS_TELEFONO = "CONTACT_INFO";

    /**
     * Generales
     */
    //Ejemplo DATE_FORMAT                    = "2015-02-18T03:05:20.000+01:00";
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";



    /*
     * Tarea de tipo Listado Assistant
     */
    //INSTALLATION DATA
    public static String LIST_ASSISTANT_TASK_INS_NUMERO_INSTALACION = "INSTALACION";
    public static String LIST_ASSISTANT_TASK_INS_PERSONA_CONTACTO = "NOMBRE";
    public static String LIST_ASSISTANT_TASK_INS_TELEFONO = "CONTACT_INFO";
    public static String LIST_ASSISTANT_TASK_CALLING_LIST = "CALLING_LIST";
    public static String LIST_ASSISTANT_TASK_N_CONTRATO = "CTR_NO";

    public static String LIST_ASSISTANT_TASK_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String LIST_ASSISTANT_TASK_TITULAR = "";
    public static String LIST_ASSISTANT_TASK_PANEL = "";
    public static String LIST_ASSISTANT_TASK_VERSION = "";
    public static String LIST_ASSISTANT_TASK_MOTIVO_CIERRE = "";//EN PANTALLA
    public static String LIST_ASSISTANT_TASK_COMPENSACION = "";//EN PANTALLA
    public static String LIST_ASSISTANT_TASK_N_MANTENIMIENTO = "MANTENIMIENTO";
    public static String LIST_ASSISTANT_TASK_TECNICO = "TECNICO";
    public static String LIST_ASSISTANT_TASK_DEPARTAMENTO = "DEPARTAMENTO";
    public static String LIST_ASSISTANT_TASK_TOTAL_SIN_IVA = "TOTAL_SINIVA";
    public static String LIST_ASSISTANT_TASK_TOTAL_CON_IVA = "TOTAL";
    public static String LIST_ASSISTANT_TASK_GRUPO_PANEL = "GRUPOPANEL";
    public static String LIST_ASSISTANT_TASK_TIPO_INCIDENCIA = "TIPOINCIDENCIA";
    public static String LIST_ASSISTANT_TASK_SUBTIPO_INCIDENCIA = "SUBTIPO_INCIDENCIA";
    public static String LIST_ASSISTANT_TASK_PARTE_N = "NUM_PARTE";
    public static String LIST_ASSISTANT_TASK_FECHA_INCIDENCIA = "F_INCIDENCIA";
    public static String LIST_ASSISTANT_TASK_FECHA_PAGO = "F_PAGO";
    public static String LIST_ASSISTANT_TASK_OPERADOR = "OPERADOR";
    public static String LIST_ASSISTANT_TASK_FECHA_CIERRE = "F_CIERRE";
    public static String LIST_ASSISTANT_TASK_FECHA_ARCHIVO = "F_ARCHIVO";
    public static String LIST_ASSISTANT_TASK_BO_FECHA_RECEPCION = "BO_FRECEPCION";
    public static String LIST_ASSISTANT_TASK_BO_MATRICULA = "BO_MATRICULA";
    public static String LIST_ASSISTANT_TASK_BO_DATOS = "BO_DATOS";
    public static String LIST_ASSISTANT_TASK_BO_FECHA_GESTION = "BO_FGESTION";
    public static String LIST_ASSISTANT_TASK_BO_TIPO = "BO_TIPO";
    public static String LIST_ASSISTANT_TASK_BO_COMENTARIOS = "BO_COMENTARIOS";
    public static String LIST_ASSISTANT_TASK_SOLICITUD = "SOLICITUD";
    public static String LIST_ASSISTANT_TASK_CAMBIOS = "CAMBIOS";


    /**
     * Tarea de tipo Encuesta mantenimientos
     */
    public static String MAINTENANCE_SURVEY_TASK_NUMERO_MANTENIMIENTO = "MANTENIMIENTO";
    public static String MAINTENANCE_SURVEY_TASK_TECNICO = "TECNICO";
    public static String MAINTENANCE_SURVEY_TASK_RESPONSABLE = "JE";
    public static String MAINTENANCE_SURVEY_TASK_CENTROCOSTE = "CC";
    public static String MAINTENANCE_SURVEY_TASK_RAZON = "RAZON";
    public static String MAINTENANCE_SURVEY_TASK_SOLUCION = "SOLUCION";
    public static String MAINTENANCE_SURVEY_TASK_COMPROMISO = "COMPROMISO";
    public static String MAINTENANCE_SURVEY_TASK_DPTO_DESTINO = "DPTO_DESTINO";


    public static String MAINTENANCE_SURVEY_TASK_INSTALACION = "INSTALACION";
    public static String MAINTENANCE_SURVEY_TASK_TITULAR = ""; // WS GET INST
    public static String MAINTENANCE_SURVEY_TASK_PERSONA_CONTACTO = "NOMBRE";
    public static String MAINTENANCE_SURVEY_TASK_PANEL = ""; // WS GET INST
    public static String MAINTENANCE_SURVEY_TASK_TELEFONO = "CONTACT_INFO";
    public static String MAINTENANCE_SURVEY_TASK_VERSION = ""; //WS GET INST
    public static String MAINTENANCE_SURVEY_TASK_MOTIVO_CIERRE = ""; //SE RELLENA EN PANTALLA
    public static String MAINTENANCE_SURVEY_TASK_COMPENSACION = ""; //SE RELLENA EN PANTALLA
    public static String MAINTENANCE_SURVEY_TASK = "F_CREACION_TAREA"; //CONFIRMAR


    /**
     * Tarea de tipo Encuesta mercados
     */
    public static String MARKETING_SURVEY_TASK_FECHA = "F_CREACION_TAREA";//CONFIRMAR
    public static String MARKETING_SURVEY_TASK_MOTIVO = "MOTIVO";
    public static String MARKETING_SURVEY_TASK_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String KEYBOX_TASK_NUMERO_FACTURA = "FACTURA";
    public static String KEYBOX_TASK_FECHA_FACTURA = "F_FACTURA";
    public static String KEYBOX_TASK_IMPORTE_LINEA = "IMPORTE_LINEA";
    public static String KEYBOX_TASK_ID_ITEM = "ITEM";
    public static String KEYBOX_TASK_CONTRATO = "CTR_NO";
    public static String KEYBOX_TASK_PANEL = "PANEL";
    public static String KEYBOX_TASK_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";




    public static String OTRASCAMPANAS_CAMPO1 = "CUSTOM01";
    public static String OTRASCAMPANAS_CAMPO2 = "CUSTOM02";
    public static String OTRASCAMPANAS_CAMPO3 = "CUSTOM03";
    public static String OTRASCAMPANAS_COMENTARIO = "COMENTARIOS";
    public static String OTRASCAMPANAS_TIPO_CAMPANA = "TIPO_CAMPAÑA";

    public static String LIMPIEZA_CUOTA_CONTRATO = "CTR_NO";
    public static String LIMPIEZA_CUOTA_DPT_ASIGNADO = "DEPARTAMENTO";
    public static String LIMPIEZA_CUOTA_INCIDENCIA = "INCIDENCIA";

    /*Tarea Mantenimiento */
    /**
     *
     1.1.1.1.2.	Mapeo tarea mantenimiento
     Campo pantalla	Datos WS
     ESTADO                 NOMBRE_CAMPO            NOMBRE_CAMPO_WS     COMENTARIOS
     --installationData--   numeroInstalacion	    INSTALACION
     --installationdata--   titular	                                    (se lee del WS de getInstalacion [ref])
     --installationData--   Persona de contacto     NOMBRE              (no existe en el modelo)
     --installationData--   panel 	                                    WS de getInstalacion[ref]   (no viene en el modelo)
     --installationdata--   telefono	            CONTACT_INFO
     --installationData--   version                                     (no viene en el modelo)	WS de getInstalacion[ref]
     --CONFIRMAR--          numeroContrato	        CTR_NO              (confirmar)
     --NO_MOSTRADO--        tipoMantenimiento       TIPO_MANTENIMIENTO  (no viene en el modelo)
     --X--                  direccion	            DIRECCION
     --CONFIRMAR--          fechaEvento	            F_CREACION_TAREA?
     --X--                  ciudad	                CIUDAD
     --CONFIRMAR--          agenteAsignado	        AGENT_ID            (CONFIRMAR, SI ES EL LOGIN_ID HAY QUE LLAMAR A UN WS PARA TRADUCIRLO)
     --CONFIRMAR--                                  Tipo cancelacion    (no viene en el modelo, o es tipificacion?)
     --MODIFICACION--       tipoCancelacion         Tipo cancelacion    (no viene en el modelo, o es tipificacion?)
     --CONFIRMAR--                                  Texto cancelacion   (no viene en el modelo, o es tipificacion?)
     --MODIFICACION--       textCancelacion         Texto cancelacion   (no viene en el modelo, o es tipificacion?)
     --X--                  telefono1               TELEFONO1           (no viene en el modelo)
     --X--                  telefono2               TELEFONO2           (no viene en el modelo)
     --X--                  telefono3               TELEFONO3           (no viene en el modelo)
     --DESCONOCIDO--        Key1
     --DESCONOCIDO--        Key2
     --NO_INDICADO--        agenteCierre
     --NO_INDICADO--        opcionTipificacion
     --NO_INDICADO--        textoCancelacion
     */
    //INSTALLATION DATA
    public static String TAREA_MANTENIMIENTO_INS_NUMERO_INSTALACION = "INSTALACION";
    public static String TAREA_MANTENIMIENTO_INS_PERSONA_CONTACTO = "NOMBRE";
    public static String TAREA_MANTENIMIENTO_INS_TELEFONO = "CONTACT_INFO";
    //TAREA MANTENIMIENTO
    public static String TAREA_MANTENIMIENTO_NUMERO_CONTRATO = "CTR_NO";
    public static String TAREA_MANTENIMIENTO_TIPO_MANTENIMIENTO = "TIPO_MANTENIMIENTO";
    public static String TAREA_MANTENIMIENTO_DIRECCION = "DIRECCION";
    public static String TAREA_MANTENIMIENTO_CIUDAD = "CIUDAD";
    public static String TAREA_MANTENIMIENTO_FECHAEVENTO_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static String TAREA_MANTENIMIENTO_FECHAEVENTO= "F_CREACION_TAREA";
    public static String TAREA_MANTENIMIENTO_AGENTEASIGNADO= "AGENT_ID";
    public static String TAREA_MANTENIMIENTO_TIPO_CANCELACION = "Tipo cancelacion";
    public static String TAREA_MANTENIMIENTO_TELEFONO_1 = "TELEFONO1";
    public static String TAREA_MANTENIMIENTO_TELEFONO_2 = "TELEFONO2";
    public static String TAREA_MANTENIMIENTO_TELEFONO_3 = "TELEFONO3";
    public static String TAREA_MANTENIMIENTO_KEY1= "TAREA_MANTENIMIENTO_KEY1";
    public static String TAREA_MANTENIMIENTO_KEY2= "TAREA_MANTENIMIENTO_KEY2";
    //No se utiliza en pantalla ni tenemos equivalencia.
    public static String TAREA_MANTENIMIENTO_AGENTECIERRE= "TAREA_MANTENIMIENTO_AGENTECIERRE";
    public static String TAREA_MANTENIMIENTO_OPCIONTIPIFICACION= "TAREA_MANTENIMIENTO_OPCIONTIPIFICACION";
    public static String TAREA_MANTENIMIENTO_TEXTO_CANCELACION = "Tipo cancelacion";


    /**
     * NOTIFICATION TASK
     */



    /**
     * TAREA_AVISO
     */
    //Servicio previo de donde se obtiene el ID_AVISO
    public static String TAREA_AVISO_CALLING_LIST_RESPONSE_ID_AVISO = "ID_AVISO";
    //Servicio de Consulta de Aviso



}
