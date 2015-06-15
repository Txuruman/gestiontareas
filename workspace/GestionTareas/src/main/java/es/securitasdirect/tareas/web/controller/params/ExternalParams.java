package es.securitasdirect.tareas.web.controller.params;

/**
 * Constantes de los parametros que nos pasan desde el IWS.
 * Al documento Integracion_IWS_Web_DiseñoTecnico_v2.5 contiene un listado y explicaciones.
 */
public interface ExternalParams {

    /*  bp_agent	Identificador del agente  */
    public static String identificadorAgente = "bp_agent";
    /*  bp_agentIBS	Código IBS definido en el objeto Person  */
    public static String codigoIBS = "bp_agentIBS";
    /*  bp_agentUserSD	Código UserSD definido en el objeto Person  */
    public static String codigoUserSD = "bp_agentUserSD";
    /*  bp_auth_requestDate	Fecha en la que se realiza la petición en formato YYYYMMDDHHMM  */
    public static String fechaPeticion = "bp_auth_requestDate";
    /*  bp_auth_connid	Identificador de la interacción. Si no hay interacción este valor será 0  */
    public static String identificadorInteraccion = "bp_auth_connid";
    /*  bp_auth_ipAddress	Dirección IP desde la que se ejecuta IWS  */
    public static String direccionIPIWS = "bp_auth_ipAddress";
    /*  bp_auth_signature	Firma de la conexión  */
    public static String firmaConexion = "bp_auth_signature";


    public static String NUMERO_INSTALACION = "ins_no";
    public static String TIPO_TAREA = "tipotarea";

    public static String ID_AVISO = "idaviso";

}
