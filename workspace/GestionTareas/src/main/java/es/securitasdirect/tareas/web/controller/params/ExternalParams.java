package es.securitasdirect.tareas.web.controller.params;

/**
 * Constantes de los parametros que nos pasan desde la llamada, http.
 */

/*
Os paso los parámetros de cada uno de los nuevos métodos que llegan por post:
•         cancel_record:
o   phoneNumber - bp_out_contact_info
o   campaignName - bp_out_GSW_CAMPAIGN_NAME
•         finalize_record
o   placeId - bp_agentPlace (hay que eliminar la P y confirmar que los places de los agentes siempre son P+extensión)
o   campaignName - bp_out_GSW_CAMPAIGN_NAME
o   callingList - bp_out_clname
o   recordId – Tengo que investigar
•         reschedule_record
o   placeId - bp_agentPlace (hay que eliminar la P y confirmar que los places de los agentes siempre son P+extensión)
o   campaignName - bp_out_GSW_CAMPAIGN_NAME
o   recordId – Tengo que investigar
o   callbackType – Puede ser Campaign o Personal (por ahora será Campaign).
o   dateTime – Fecha de replanificación, el formato es el siguiente:
  AAAA-MM-DDThh:mm:ss

 */
public interface ExternalParams {

    /*  	Identificador del agente  */
    public static final String identificadorAgente = "bp_agent";
    /*  	Calling List  */
    public static String CALLING_LIST = "bp_out_clname";

    public static String ID_TAREA = "bp_out_GSW_CHAIN_ID_CUSTOM";

    public static String NUMERO_INSTALACION = "bp_out_instalacion";

    public static String NUMERO_CONTRATO = "bp_out_ctr_no";


    public static String ID_AVISO = "idaviso";
    

    public static String CONN_ID = "bp_connid";

    public static String AGENT_IBS = "bp_agentIBS";

    public static String AGENT_USER_SD = "bp_agentUserSD";
    
    public static String AGENT_COUTRY_JOB = "bp_agentCountryJob";

    public static String CURRENT_LANGUAGE = "bp_currentLanguage";

    public static String AGENT_PLACE = "bp_agentPlace";
    
    public static String AGENT_GROUP_SD = "bp_agentGroupSD";

    public static String AGENT_GROUP_OUT_SERVICE = "bp_agentGroupOutService";

    public static String DESKTOP_DEPARTMENT = "bp_desktopDepartment";
    
    public static String CALLING_LIST_MANAGED_DESK= "bp_callingListManagedDesktop";
    
    public static String AUTH_REQUEST_DATE= "bp_auth_requestDate";

    public static String AUTH_CONNID = "bp_auth_connid";

    public static String AUTH_IPADDRESS= "bp_auth_ipAddress";
    
    public static String AUTH_SIGNATURE= "bp_auth_signature";

    public static String INTERACTION_DIRECTION = "bp_interactionDirection";

    public static String INTERACTION_TYPE= "bp_interactionType";

    public static String CONTACT_INFO= "bp_out_contact_info";

    public static String CAMPAIGN_NAME= "bp_out_GSW_CAMPAIGN_NAME";

    public static String RECORD_HANDLE= "bp_out_GSW_RECORD_HANDLE";

}
