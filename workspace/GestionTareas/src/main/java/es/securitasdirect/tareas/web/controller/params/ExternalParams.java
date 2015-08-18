package es.securitasdirect.tareas.web.controller.params;

/**
 * Constantes de los parametros que nos pasan desde la llamada, http.
 */
public interface ExternalParams {

    /*  	Identificador del agente  */
    public static final String identificadorAgente = "bp_agent";
    /*  	Calling List  */
    public static String CALLING_LIST = "bp_out_clname";

    public static String ID_TAREA = "bp_out_GSW_CHAIN_ID_CUSTOM";

    public static String NUMERO_INSTALACION = "bp_out_INSTALACION";

    public static String ID_AVISO = "idaviso";
    

    public static String CONN_ID = "bp_connid";

    public static String AGENT_IBS = "bp_agentIBS";

    public static String AGENT_USER_SD = "bp_agentUserSD";
    
    public static String AGENT_COUTRY_JOB = "bp_agentCountryJob";

    public static String CURRENT_LANGUAGE = "bp_currentLanguage";

    public static String AGENT_PLACE = "bp_agentPlace";
    
    public static String AGENT_GROUP_SD = "bp_agentGroupSD";

    public static String AGENT_GROUP_OUT_SERVICE = "bp_agentGroupOutService";

    public static String DESKTOP_DEP = "bp_desktopDepartment";
    
    public static String CALLING_LIST_MANAGED_DESK= "bp_callingListManagedDesktop";
    
    public static String AUTH_REQUEST_DATE= "bp_auth_requestDate";

    public static String AUTH_CONNID = "bp_auth_connid";

    public static String AUTH_IPADDRESS= "bp_auth_ipAddress";
    
    public static String AUTH_SIGNATURE= "bp_auth_signature";

    public static String INTERACTION_DIRECTION = "bp_interactionDirection";

    public static String INTERACTION_TYPE= "bp_interactionType";
    
    public static String OUT_GSW_CHAIN_ID= "bp_out_GSW_CHAIN_ID";

    public static String OUT_CTR_NO= "bp_out_ctr_no";
    
//    public static String OUT_ClNAME= "bp_out_clname";
//
//    public static String OUT_GSW_CHAIN_ID_CUSTOM= "bp_out_GSW_CHAIN_ID_CUSTOM";

    

}
