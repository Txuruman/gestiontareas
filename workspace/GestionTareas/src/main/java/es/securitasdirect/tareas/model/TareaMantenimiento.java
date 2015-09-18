package es.securitasdirect.tareas.model;

/**
 * Tarea de Tipo Mantenimiento.
 * @author Team Vision
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TareaMantenimiento extends Tarea {
    /**
     1.1.1.1.2.	Mapeo tarea mantenimiento
     Campo pantalla	Datos WS
     ESTADO                 NOMBRE_CAMPO            NOMBRE_CAMPO_WS     COMENTARIOS
     --installationData--   numeroInstalacion	    INSTALACION
     --installationdata--   titular	                                    (se lee del WS de getInstalacion [ref])
     --installationData--   Persona de contacto     NOMBRE              (no existe en el modelo)
     --installationData--   panel 	                                    WS de getInstalacion[ref]   (no viene en el modelo)
     --installationdata--   telefono	            CONTACT_INFO
     --installationData--   version                                     (no viene en el modelo)	WS de getInstalacion[ref]
     --CONFIRMAR--          numeroContrato	        CTR_NO              (confirmar)
     --NO_MOSTRADO--        tipoMantenimiento       TIPO_MANTENIMIENTO  (no viene en el modelo)
     --X--                  direccion	            DIRECCION
     --CONFIRMAR--          fechaEvento	            F_CREACION_TAREA?
     --X--                  ciudad	                CIUDAD
     --CONFIRMAR--          agenteAsignado	        AGENT_ID            (CONFIRMAR, SI ES EL LOGIN_ID HAY QUE LLAMAR A UN WS PARA TRADUCIRLO)
     --CONFIRMAR--                                  Tipo cancelacion    (no viene en el modelo, o es tipificacion?)
     --MODIFICACION--       tipoCancelacion         Tipo cancelacion    (no viene en el modelo, o es tipificacion?)
     --CONFIRMAR--                                  Texto cancelacion   (no viene en el modelo, o es tipificacion?)
     --MODIFICACION--       textoCancelacion        Texto cancelacion   (no viene en el modelo, o es tipificacion?)
     --X--                  telefono1               TELEFONO1           (no viene en el modelo)
     --X--                  telefono2               TELEFONO2           (no viene en el modelo)
     --X--                  telefono3               TELEFONO3           (no viene en el modelo)
     --DESCONOCIDO--        Key1
     --DESCONOCIDO--        Key2
     --NO_INDICADO--        agenteCierre
     --NO_INDICADO--        opcionTipificacion
     --NO_INDICADO--        textoCancelacion
     */

    /**
     * Contrato
     */
    String numeroContrato;
    /**
     *
     */
    String tipoMantenimiento;
    /**
     * Dirección
     */
    String direccion;
    /**
     * Fecha y hora del Evento
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    Date fechaEvento;
    /**
     * Ciudad
     */
    String ciudad;
    /**
     * Agente Asignado
     */
    String agenteAsignado;
    /**
     * Tipo cancelacion
     */
    String tipoCancelacion;
    /**
     * Add based in screen interface
     * @return
     */
    String telefono1;
    /**
     * Add based in screen interface
     * @return
     */
    String telefono2;
    /**
     * Add based in screen interface
     * @return
     */
    String telefono3;
    /**
     * Key1
     */
    Integer key1;
    /**
     * Key2
     */
    Integer key2;
    /**
     * Agente de Cierre
     */
    String agenteCierre;
    /**
     * Opcion de tipificación
     */
    Integer opcionTipificacion;
    /**
     * Add based in screen interface
     */
    String textoCancelacion;

    @Override
    public String getNumeroContrato() {
        return numeroContrato;
    }

    @Override
    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getAgenteAsignado() {
        return agenteAsignado;
    }

    public void setAgenteAsignado(String agenteAsignado) {
        this.agenteAsignado = agenteAsignado;
    }

    public String getTipoCancelacion() {
        return tipoCancelacion;
    }

    public void setTipoCancelacion(String tipoCancelacion) {
        this.tipoCancelacion = tipoCancelacion;
    }

    public void setTextoCancelacion(String textoCancelacion) {
        this.textoCancelacion = textoCancelacion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public Integer getKey1() {
        return key1;
    }

    public void setKey1(Integer key1) {
        this.key1 = key1;
    }

    public Integer getKey2() {
        return key2;
    }

    public void setKey2(Integer key2) {
        this.key2 = key2;
    }

    public String getAgenteCierre() {
        return agenteCierre;
    }

    public void setAgenteCierre(String agenteCierre) {
        this.agenteCierre = agenteCierre;
    }

    public Integer getOpcionTipificacion() {
        return opcionTipificacion;
    }

    public void setOpcionTipificacion(Integer opcionTipificacion) {
        this.opcionTipificacion = opcionTipificacion;
    }

    public String getTextoCancelacion() {
        return textoCancelacion;
    }
    
    public String getTypeName(){
    	return "TASK_TYPE_MAINTENANCE";
    }
    
    public void setTypeName(String type){
    	
    }
    
    @Override
    public String toString() {
        return "TareaMantenimiento{" +
                "numeroContrato='" + numeroContrato + '\'' +
                ", tipoMantenimiento='" + tipoMantenimiento + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaEvento=" + fechaEvento +
                ", ciudad='" + ciudad + '\'' +
                ", agenteAsignado='" + agenteAsignado + '\'' +
                ", tipoCancelacion='" + tipoCancelacion + '\'' +
                ", telefono1='" + telefono1 + '\'' +
                ", telefono2='" + telefono2 + '\'' +
                ", telefono3='" + telefono3 + '\'' +
                ", key1=" + key1 +
                ", key2=" + key2 +
                ", agenteCierre='" + agenteCierre + '\'' +
                ", opcionTipificacion=" + opcionTipificacion +
                ", textoCancelacion='" + textoCancelacion + '\'' +
                '}';
    }
}