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

}
