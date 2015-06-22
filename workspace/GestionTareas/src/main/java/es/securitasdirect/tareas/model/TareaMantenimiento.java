package es.securitasdirect.tareas.model;

/**
 * Tarea de Tipo Mantenimiento.
 * @author Team Vision
 */

import java.util.Date;

public class TareaMantenimiento extends Tarea {

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getTipificacion() {
        return tipificacion;
    }

    public void setTipificacion(String tipificacion) {
        this.tipificacion = tipificacion;
    }

    public String getAgenteAsignado() {
        return agenteAsignado;
    }

    public void setAgenteAsignado(String agenteAsignado) {
        this.agenteAsignado = agenteAsignado;
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

    /**

     * Contrato
     */
    String contrato;
    /**
     * Dirección
     */
    String direccion;
    /**
     * Ciudad
     */
    String ciudad;
    /**
     * Fecha y hora del Evento
     */
    Date fechaEvento;
    /**
     * Tipificacion
     */
    String tipificacion;
    /**
     * Agente Asignado
     */
    String agenteAsignado;
    /**
     * Agente de Cierre
     */
    String agenteCierre;
    /**
     * Opcion de tipificación
     */
    Integer opcionTipificacion;
    /**
     * Key1
     */
    Integer key1;
    /**
     * Key2
     */
    Integer key2;

    
}
