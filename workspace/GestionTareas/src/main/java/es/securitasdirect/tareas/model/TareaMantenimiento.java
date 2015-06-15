package es.securitasdirect.tareas.model;

/**
 * Tarea de Tipo Mantenimiento.
 * @author Team Vision
 */

import java.util.Date;

public class TareaMantenimiento extends Tarea {
    /**
     * Identificativo Instalación: número de instalación/cliente
     */
    String numeroInstalacion;
    /**
     * Contrato
     */
    String contrato;
    /**
     * Telefónos de contacto: los dos primeros del Plan de Acción y el de la instalación
     */
    String telefonoContactoPlanAccion1;
    String telefonoContactoPlanAccion2;
    /** Telefono de la instalación */
    String telefonoContactoInstalacion;
    /**
     * Dirección
     */
    String direccion;
    /**
     * Ciudad
     */
    String ciudad;
    /**
     * Panel: tipo de panel de la instalación
     */
    String tipoPanel;
    /**
     * Versión: versión del panel
     */
    String versionPanel;
    /**
     * Fecha y hora del evento
     */
    Date fechaEvento;
    /**
     * Tipificación
     */
    String tipificacion;
    /**
     * Agente asignado
     */
    String agenteAsignado;
    /**
     * Agente de cierre
     */
    String agenteCierre;
    /**
     * Tipo de Mantenimiento
     */
    String tipoMantenimiento;


    public String getNumeroInstalacion() {
        return numeroInstalacion;
    }

    public void setNumeroInstalacion(String numeroInstalacion) {
        this.numeroInstalacion = numeroInstalacion;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getTelefonoContactoPlanAccion1() {
        return telefonoContactoPlanAccion1;
    }

    public void setTelefonoContactoPlanAccion1(String telefonoContactoPlanAccion1) {
        this.telefonoContactoPlanAccion1 = telefonoContactoPlanAccion1;
    }

    public String getTelefonoContactoPlanAccion2() {
        return telefonoContactoPlanAccion2;
    }

    public void setTelefonoContactoPlanAccion2(String telefonoContactoPlanAccion2) {
        this.telefonoContactoPlanAccion2 = telefonoContactoPlanAccion2;
    }

    public String getTelefonoContactoInstalacion() {
        return telefonoContactoInstalacion;
    }

    public void setTelefonoContactoInstalacion(String telefonoContactoInstalacion) {
        this.telefonoContactoInstalacion = telefonoContactoInstalacion;
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

    public String getTipoPanel() {
        return tipoPanel;
    }

    public void setTipoPanel(String tipoPanel) {
        this.tipoPanel = tipoPanel;
    }

    public String getVersionPanel() {
        return versionPanel;
    }

    public void setVersionPanel(String versionPanel) {
        this.versionPanel = versionPanel;
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

    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaMantenimiento{");
        sb.append("numeroInstalacion='").append(numeroInstalacion).append('\'');
        sb.append(", contrato='").append(contrato).append('\'');
        sb.append(", telefonoContactoPlanAccion1='").append(telefonoContactoPlanAccion1).append('\'');
        sb.append(", telefonoContactoPlanAccion2='").append(telefonoContactoPlanAccion2).append('\'');
        sb.append(", telefonoContactoInstalacion='").append(telefonoContactoInstalacion).append('\'');
        sb.append(", direccion='").append(direccion).append('\'');
        sb.append(", ciudad='").append(ciudad).append('\'');
        sb.append(", tipoPanel='").append(tipoPanel).append('\'');
        sb.append(", versionPanel='").append(versionPanel).append('\'');
        sb.append(", fechaEvento=").append(fechaEvento);
        sb.append(", tipificacion='").append(tipificacion).append('\'');
        sb.append(", agenteAsignado='").append(agenteAsignado).append('\'');
        sb.append(", agenteCierre='").append(agenteCierre).append('\'');
        sb.append(", tipoMantenimiento='").append(tipoMantenimiento).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
