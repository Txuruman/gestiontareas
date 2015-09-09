package es.securitasdirect.tareas.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Clase base de todos los tipos de tareas a tratar.
 *
 * @author Team Vision
 */
public class Tarea {

    private interface TaskStatus {
        public static final int NO_RECORD_STATUS_ = 0;
        public static final int READY_ = 1;
        public static final int RETRIEVED = 2; //EN MEMORIA
        public static final int UPDATED = 3;
        public static final int STALE_ = 4;
        public static final int CANCELED_ = 5;
        public static final int AGENT_ERROR_ = 6;
        public static final int MISSED_CALLBACK = 8;
    }

    /**
     * Número de Instalación
     */
    protected String numeroInstalacion;

    /**
     * Estado de la Tarea (record_status): ready, retrieved, …
     */
    protected Integer estado;

    protected String personaContacto;
    /**
     * ctr_no = número de contrato
     * varchar(12)
     */
    protected String numeroContrato;

    /**
     * Calling list de la tarea, agrupa las tareas por tipos
     */
    protected String callingList;

    /**
     * Identificador de la tarea en la calling list, lo que se viene a llamar chain_id , junto con calling list hacen clave para las busquedas
     */
    protected Integer id;

    /** */
    protected String telefono;

    /** */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    protected Date fechaReprogramacion;

    /** */
    protected Integer codigoCliente;

    protected String  campana;

    /*
        TODO PENDIENTE: REVISAR QUÉ OTROS CAMPOS DE GENESYS HAY QUE TENER EN CUENTA
        (CONTRASTARLOS CON LOS DEL DOCUMENTO DPB140808_ENT_DISEÑO TÉCNICO CALLBACK ATC V1.4.DOCX)
     */

    /*
    Parametros que se reciven por POST y que se utilizarán para realizar operaciones con la tarea en memoria,
    estos parametros no se cargan desde BBDD.
     */
    private String outCampaignName;
    private String outClName;
    private String outRecordHandle;
    private String outAgentPlace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroInstalacion() {
        return numeroInstalacion;
    }

    public void setNumeroInstalacion(String numeroInstalacion) {
        this.numeroInstalacion = numeroInstalacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getCallingList() {
        return callingList;
    }

    public void setCallingList(String callingList) {
        this.callingList = callingList;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaReprogramacion() {
        return fechaReprogramacion;
    }

    public void setFechaReprogramacion(Date fechaReprogramacion) {
        this.fechaReprogramacion = fechaReprogramacion;
    }

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public boolean isRetrieved() {
        return this.estado!= null && this.estado==TaskStatus.RETRIEVED;
    }

    public String getCampana() {
        return campana;
    }

    public void setCampana(String campana) {
        this.campana = campana;
    }


    public String getOutCampaignName() {
        return outCampaignName;
    }

    public void setOutCampaignName(String outCampaignName) {
        this.outCampaignName = outCampaignName;
    }

    public String getOutClName() {
        return outClName;
    }

    public void setOutClName(String outClName) {
        this.outClName = outClName;
    }

    public String getOutRecordHandle() {
        return outRecordHandle;
    }

    public void setOutRecordHandle(String outRecordHandle) {
        this.outRecordHandle = outRecordHandle;
    }

    public String getOutAgentPlace() {
        return outAgentPlace;
    }

    public void setOutAgentPlace(String outAgentPlace) {
        this.outAgentPlace = outAgentPlace;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Tarea{");
        sb.append("numeroInstalacion='").append(numeroInstalacion).append('\'');
        sb.append(", estado=").append(estado);
        sb.append(", personaContacto='").append(personaContacto).append('\'');
        sb.append(", numeroContrato='").append(numeroContrato).append('\'');
        sb.append(", callingList='").append(callingList).append('\'');
        sb.append(", id=").append(id);
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", fechaReprogramacion=").append(fechaReprogramacion);
        sb.append(", codigoCliente=").append(codigoCliente);
        sb.append('}');
        return sb.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callingList == null) ? 0 : callingList.hashCode());
		result = prime * result + ((campana == null) ? 0 : campana.hashCode());
		result = prime * result + ((codigoCliente == null) ? 0 : codigoCliente.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaReprogramacion == null) ? 0 : fechaReprogramacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numeroContrato == null) ? 0 : numeroContrato.hashCode());
		result = prime * result + ((numeroInstalacion == null) ? 0 : numeroInstalacion.hashCode());
		result = prime * result + ((personaContacto == null) ? 0 : personaContacto.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarea other = (Tarea) obj;
		if (callingList == null) {
			if (other.callingList != null)
				return false;
		} else if (!callingList.equals(other.callingList))
			return false;
		if (campana == null) {
			if (other.campana != null)
				return false;
		} else if (!campana.equals(other.campana))
			return false;
		if (codigoCliente == null) {
			if (other.codigoCliente != null)
				return false;
		} else if (!codigoCliente.equals(other.codigoCliente))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaReprogramacion == null) {
			if (other.fechaReprogramacion != null)
				return false;
		} else if (!fechaReprogramacion.equals(other.fechaReprogramacion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numeroContrato == null) {
			if (other.numeroContrato != null)
				return false;
		} else if (!numeroContrato.equals(other.numeroContrato))
			return false;
		if (numeroInstalacion == null) {
			if (other.numeroInstalacion != null)
				return false;
		} else if (!numeroInstalacion.equals(other.numeroInstalacion))
			return false;
		if (personaContacto == null) {
			if (other.personaContacto != null)
				return false;
		} else if (!personaContacto.equals(other.personaContacto))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

    
}
