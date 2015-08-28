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
	private String infopointSession;

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

	public String getInfopointSession() {
		return infopointSession;
	}

	public void setInfopointSession(String infopointSession) {
		this.infopointSession = infopointSession;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Agent{");
		sb.append("idAgent='").append(idAgent).append('\'');
		sb.append(", connid='").append(connid).append('\'');
		sb.append(", agentIBS='").append(agentIBS).append('\'');
		sb.append(", agentUserSD='").append(agentUserSD).append('\'');
		sb.append(", agentCountryJob='").append(agentCountryJob).append('\'');
		sb.append(", currentLanguage='").append(currentLanguage).append('\'');
		sb.append(", agentPlace='").append(agentPlace).append('\'');
		sb.append(", agentGroupSD='").append(agentGroupSD).append('\'');
		sb.append(", agentGroupOutService='").append(agentGroupOutService).append('\'');
		sb.append(", desktopDepartment='").append(desktopDepartment).append('\'');
		sb.append(", callingListManagedDesktop='").append(callingListManagedDesktop).append('\'');
		sb.append(", auth_requestDate='").append(auth_requestDate).append('\'');
		sb.append(", auth_connid='").append(auth_connid).append('\'');
		sb.append(", auth_ipAddress='").append(auth_ipAddress).append('\'');
		sb.append(", auth_signature='").append(auth_signature).append('\'');
		sb.append(", interactionDirection='").append(interactionDirection).append('\'');
		sb.append(", interactionType='").append(interactionType).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
