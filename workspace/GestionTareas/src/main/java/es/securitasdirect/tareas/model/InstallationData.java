package es.securitasdirect.tareas.model;

/**
 * Subconjunto de datos para mostrar en la pantalla sobre la instalación.
 */
public class InstallationData {

    private String numeroInstalacion;

    private String titular;

    private String panel;

    private String version;

    private String personaContacto;

    private String telefono;

    private String codigoPostal;

    private String monitoringStatus;

    /** Codigo de Grandes cuentas */
    private String member;

    private String clazz;
    
    /**
     * Nombre contacto plan
     */
    private String contactoPlan;
    /**
     * Teléfono contacto plan
     */
    private String telefonoPlan;
    
    public String getNumeroInstalacion() {
        return numeroInstalacion;
    }

    public void setNumeroInstalacion(String numeroInstalacion) {
        this.numeroInstalacion = numeroInstalacion;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getPanel() {
        return panel;
    }

    public void setPanel(String panel) {
        this.panel = panel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMonitoringStatus() {
        return monitoringStatus;
    }

    public void setMonitoringStatus(String monitoringStatus) {
        this.monitoringStatus = monitoringStatus;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

	public String getContactoPlan() {
		return contactoPlan;
	}

	public void setContactoPlan(String contactoPlan) {
		this.contactoPlan = contactoPlan;
	}

	public String getTelefonoPlan() {
		return telefonoPlan;
	}

	public void setTelefonoPlan(String telefonoPlan) {
		this.telefonoPlan = telefonoPlan;
	}

	@Override
	public String toString() {
		return "InstallationData [numeroInstalacion=" + numeroInstalacion + ", titular=" + titular + ", panel=" + panel
				+ ", version=" + version + ", personaContacto=" + personaContacto + ", telefono=" + telefono
				+ ", codigoPostal=" + codigoPostal + ", monitoringStatus=" + monitoringStatus + ", member=" + member
				+ ", clazz=" + clazz + ", contactoPlan=" + contactoPlan + ", telefonoPlan=" + telefonoPlan + "]";
	}

    
}
