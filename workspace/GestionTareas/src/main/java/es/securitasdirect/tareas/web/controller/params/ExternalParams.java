package es.securitasdirect.tareas.web.controller.params;

/**
 * Constantes de los parametros que nos pasan desde el IWS.
 * Al documento Integracion_IWS_Web_DiseñoTecnico_v2.5 contiene un listado y explicaciones.
 */
public interface ExternalParams {

    /*  	Identificador del agente  */
    public static String identificadorAgente = "bp_agent";
    /*  	Código IBS definido en el objeto Person  */
    public static String codigoIBS = "bp_agentIBS";
    /*  	Código UserSD definido en el objeto Person  */
    public static String codigoUserSD = "bp_agentUserSD";
    /*  	Fecha en la que se realiza la petición en formato YYYYMMDDHHMM  */
    public static String fechaPeticion = "bp_auth_requestDate";
    /*  	Identificador de la interacción. Si no hay interacción este valor será 0  */
    public static String identificadorInteraccion = "bp_auth_connid";
    /*  	Dirección IP desde la que se ejecuta IWS  */
    public static String direccionIPIWS = "bp_auth_ipAddress";
    /*  	Firma de la conexión  */
    public static String firmaConexion = "bp_auth_signature";


    public static String NUMERO_INSTALACION = "ins_no";
    public static String TIPO_TAREA = "tipotarea";

    public static String ID_AVISO = "idaviso";
    public static String MOTIVO_CIERRE= "motivocierre";


    public static String COMPENSACION = "compensacion";

    public static String ENCUESTAMARKETING_MOTIVO = "motivo";
    public static String ENCUESTAMARKETING_FECHA = "fecha";

    public static String ASSISTANT_INSTALACION = "instalacion";
    public static String ASSISTANT_MANTENIMIENTO = "";
    public static String ASSISTANT_TECNICO = "instalacion";
    public static String ASSISTANT_DEPARTAMENTO = "instalacion";
    public static String ASSISTANT_GRUPOPANEL = "instalacion";
    public static String ASSISTANT_TOTALSINIVA = "instalacion";
    public static String ASSISTANT_TOTALCONIVA = "instalacion";
    public static String ASSISTANT_NPARTE = "instalacion";
    public static String ASSISTANT_CIERRE_FECHA = "instalacion";
    public static String ASSISTANT_ARCHIVO_FECHA = "instalacion";
    public static String ASSISTANT_SUBIDA_INC_FECHA = "instalacion";
    public static String ASSISTANT_PAGO_FECHA = "instalacion";
    public static String ASSISTANT_INCIDENCIA = "instalacion";
    public static String ASSISTANT_SUBINCIDENCIA = "instalacion";
    public static String ASSISTANT_SOLICITUD = "instalacion";
    public static String ASSISTANT_CAMBIOS = "instalacion";
    public static String ASSISTANT_BO_GESTION_FECHA = "instalacion";
    public static String ASSISTANT_BO_MATRICULA = "instalacion";
    public static String ASSISTANT_BO_RECEPCION_FECHA = "instalacion";
    public static String ASSISTANT_BO_EMPRESA_PARTICULAR = "instalacion";
    public static String ASSISTANT_BO_COMENTARIOS = "instalacion";

    public static String ASSISTANT_CONTACTO_TELEFONO = "instalacion";


    public static String ENCUESTAMNTOS_MANTENIMIENTO = "mantenimiento";
    public static String ENCUESTAMNTOS_TECNICO = "mantenimiento";
    public static String ENCUESTAMNTOS_RESPONSABLE = "mantenimiento";
    public static String ENCUESTAMNTOS_CENTROCOSTE = "mantenimiento";
    public static String ENCUESTAMNTOS_RAZON = "mantenimiento";
    public static String ENCUESTAMNTOS_SOLUCION = "mantenimiento";
    public static String ENCUESTAMNTOS_COMPROMISO = "mantenimiento";
    public static String ENCUESTAMNTOS_DPTO_DESTINO = "mantenimiento";



    public static String ENCUESTASMKT_FECHA = "mantenimiento";
    public static String ENCUESTASMKT_MOTIVO = "mantenimiento";

    public static String KEYBOX_CONTRATO = "KEYBOX_CONTRATO";
    public static String KEYBOX_FECHA_FACTURA = "KEYBOX_FECHA_FACTURA";
    public static String KEYBOX_NUMERO_FACTURA = "KEYBOX_NUMERO_FACTURA";
    public static String KEYBOX_IMPORTE_LINEA = "KEYBOX_IMPORTE_LINEA";
    public static String KEYBOX_ID_ITEM = "KEYBOX_ID_ITEM";

    public  static String OTRASCAMPANAS_CAMPO1 = "campo";
    public  static String OTRASCAMPANAS_CAMPO2 = "campo";
    public  static String OTRASCAMPANAS_CAMPO3 = "campo";
    public  static String OTRASCAMPANAS_COMENTARIO = "campo";
    public  static String OTRASCAMPANAS_TIPOTAREA = "campo";

    public  static String LIMPIEZA_CUOTA_CONTRATO = "campo";
    public static  String LIMPIEZA_CUOTA_DPT_ASIGNADO = "campo";
    public static  String LIMPIEZA_CUOTA_DESCRIPTCION = "campo";






}
