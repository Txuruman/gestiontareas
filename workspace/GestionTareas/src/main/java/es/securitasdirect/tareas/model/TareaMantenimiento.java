package es.securitasdirect.tareas.model;

/**
 * Tarea de Tipo Mantenimiento.
 * @author Team Vision
 */

import java.util.Date;

public class TareaMantenimiento extends Tarea {

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


    @Override
    public String toString() {
        return "TareaMantenimiento{" +
                "contrato='" + contrato + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fechaEvento=" + fechaEvento +
                ", tipificacion='" + tipificacion + '\'' +
                ", agenteAsignado='" + agenteAsignado + '\'' +
                ", agenteCierre='" + agenteCierre + '\'' +
                ", opcionTipificacion=" + opcionTipificacion +
                ", key1=" + key1 +
                ", key2=" + key2 +
                '}';
    }
}