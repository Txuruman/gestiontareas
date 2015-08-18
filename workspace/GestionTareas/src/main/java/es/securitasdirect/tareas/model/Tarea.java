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
        public static final int NO_RECORD_STATUS_= 0;
        public static final int READY_= 1;
        public static final int RETRIEVED_= 2;
        public static final int UPDATED_= 3;
        public static final int STALE_= 4;
        public static final int CANCELED_= 5;
        public static final int AGENT_ERROR_= 6;
        public static final int MISSED_CALLBACK_= 8;
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

    /** Calling list de la tarea, agrupa las tareas por tipos*/
    protected String callingList;

    /** Identificador de la tarea en la calling list, lo que se viene a llamar chain_id , junto con calling list hacen clave para las busquedas */
    protected Integer id;

    /** */
    protected String telefono;

    /** */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "CET")
    protected Date fechaReprogramacion;

    /** */
    protected Integer codigoCliente;

    /*
        TODO PENDIENTE: REVISAR QUÉ OTROS CAMPOS DE GENESYS HAY QUE TENER EN CUENTA
        (CONTRASTARLOS CON LOS DEL DOCUMENTO DPB140808_ENT_DISEÑO TÉCNICO CALLBACK ATC V1.4.DOCX)
     */

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


}
