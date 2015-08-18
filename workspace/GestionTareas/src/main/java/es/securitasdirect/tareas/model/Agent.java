package es.securitasdirect.tareas.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

//Esto funciona para meter un componente en la sesion
//@Component
//@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Agent {

    private String idAgent;
    private String connid;
    private String agentIBS;
    private String agentUserSD;
    private String agentCountryJob;
    private String currentLanguage;
    private String agentPlace;
    private String agentGroupSD;
    private String agentGroupOutService;
    private String desktopDepartment;
    private String callingListManagedDesktop;
    private String auth_requestDate;
    private String auth_connid;
    private String auth_ipAddress;
    private String auth_signature;
    private String interactionDirection;
    private String interactionType;
    private String out_GSW_CHAIN_ID;
    private String out_ctr_no;
    
    //Calling List
    private String out_clname; 
    
    //ID_TAREA
    private String out_GSW_CHAIN_ID_CUSTOM;
    
    
    
    public String getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

	public String getConnid() {
		return connid;
	}

	public void setConnid(String connid) {
		this.connid = connid;
	}

	public String getAgentIBS() {
		return agentIBS;
	}

	public void setAgentIBS(String agentIBS) {
		this.agentIBS = agentIBS;
	}

	public String getAgentUserSD() {
		return agentUserSD;
	}

	public void setAgentUserSD(String agentUserSD) {
		this.agentUserSD = agentUserSD;
	}

	public String getAgentCountryJob() {
		return agentCountryJob;
	}

	public void setAgentCountryJob(String agentCountryJob) {
		this.agentCountryJob = agentCountryJob;
	}

	public String getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	public String getAgentPlace() {
		return agentPlace;
	}

	public void setAgentPlace(String agentPlace) {
		this.agentPlace = agentPlace;
	}

	public String getAgentGroupSD() {
		return agentGroupSD;
	}

	public void setAgentGroupSD(String agentGroupSD) {
		this.agentGroupSD = agentGroupSD;
	}

	public String getAgentGroupOutService() {
		return agentGroupOutService;
	}

	public void setAgentGroupOutService(String agentGroupOutService) {
		this.agentGroupOutService = agentGroupOutService;
	}

	public String getDesktopDepartment() {
		return desktopDepartment;
	}

	public void setDesktopDepartment(String desktopDepartment) {
		this.desktopDepartment = desktopDepartment;
	}

	public String getCallingListManagedDesktop() {
		return callingListManagedDesktop;
	}

	public void setCallingListManagedDesktop(String callingListManagedDesktop) {
		this.callingListManagedDesktop = callingListManagedDesktop;
	}

	public String getAuth_requestDate() {
		return auth_requestDate;
	}

	public void setAuth_requestDate(String auth_requestDate) {
		this.auth_requestDate = auth_requestDate;
	}

	public String getAuth_connid() {
		return auth_connid;
	}

	public void setAuth_connid(String auth_connid) {
		this.auth_connid = auth_connid;
	}

	public String getAuth_ipAddress() {
		return auth_ipAddress;
	}

	public void setAuth_ipAddress(String auth_ipAddress) {
		this.auth_ipAddress = auth_ipAddress;
	}

	public String getAuth_signature() {
		return auth_signature;
	}

	public void setAuth_signature(String auth_signature) {
		this.auth_signature = auth_signature;
	}

	public String getInteractionDirection() {
		return interactionDirection;
	}

	public void setInteractionDirection(String interactionDirection) {
		this.interactionDirection = interactionDirection;
	}

	public String getInteractionType() {
		return interactionType;
	}

	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}

	public String getOut_GSW_CHAIN_ID() {
		return out_GSW_CHAIN_ID;
	}

	public void setOut_GSW_CHAIN_ID(String out_GSW_CHAIN_ID) {
		this.out_GSW_CHAIN_ID = out_GSW_CHAIN_ID;
	}

	public String getOut_clname() {
		return out_clname;
	}

	public void setOut_clname(String out_clname) {
		this.out_clname = out_clname;
	}

	public String getOut_GSW_CHAIN_ID_CUSTOM() {
		return out_GSW_CHAIN_ID_CUSTOM;
	}

	public void setOut_GSW_CHAIN_ID_CUSTOM(String out_GSW_CHAIN_ID_CUSTOM) {
		this.out_GSW_CHAIN_ID_CUSTOM = out_GSW_CHAIN_ID_CUSTOM;
	}

	public String getOut_ctr_no() {
		return out_ctr_no;
	}

	public void setOut_ctr_no(String out_ctr_no) {
		this.out_ctr_no = out_ctr_no;
	}
    
    
}
