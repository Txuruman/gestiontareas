package es.securitasdirect.tareas.model.tareaexcel;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;

/**
 * @author Team Vision
 */
public class TareaListadoAssistant extends TareaExcel {

    /**
     * Número de Instalación.
     * varchar (12)
     */
    String numeroInstalacion;
    /**
     * Número de Mantenimiento
     */
    Integer maintenanceNumber;

    /**
     * Nombre. Parece ser el nombre de contacto, no se muestra en el detalle sino que debe sobreescribir el dato de Instalacion.
     * varchar(120)
     */
    String nombre;
    /**
     * Telefono.
     * varchar(15)
     */
    String telefono;
    /**
     * Técnico.
     * varchar (10)
     */
    String technician;
    /**
     * Departamento.
     * varchar (50)
     */
    String departamento;
    /**
     * Grupo del Panel.
     * varchar (20)
     */
    String grupoPanel;
    /**
     * Total sin IVA
     */
    Float totalSinIVA;
    /**
     * Total con IVA
     */
    Float totalConIVA;
    /**
     * Número de Parte.
     * varchar(10)
     */
    String numeroParte;
    /**
     * Fecha de Cierre
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    Date fechaCierre;
    /**
     * Fecha de Archivo
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    Date fechaArchivo;
    /**
     * Fecha de Incidencia
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    Date fechaIncidencia;
    /**
     * Fecha de Pago (Cadena de descripcion tipo 05 - RECIBO) (No es una fecha)
     */
     String fechaPago;
    /**
     * Operador.
     * varchar (10)
     */
    String operador;
    /**
     * Tipo de Incidencia.
     * varchar (50)
     */
    String tipoIncicencia;
    /**
     * Subtipo de Incidencia.
     * varchar (50)
     */
    String subtipoIncidencia;
    /**
     * Solicitud al cliente.
     * varchar (200)
     */
    String solicitudCliente;
    /**
     * Incidencia.
     * Varchar(120)
     */
    String incidencia;
    /**
     * Cambios en la incidencia.
     * varchar (40)
     */
    String cambiosIncidencia;

    /*  Datos de Back Office (fecha de gestion, matrícula, datos recuperados, fecha de recepción, tipo y comentarios). */

    /**
     * Fecha de Gestión
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    Date boFechaGestion;
    /**
     * Matrícula.
     * varchar (10)
     */
    String boMatricula;
    /**
     * Datos recuperados.
     * varchar (120)
     */
    String boDatosRecuperados;
    /**
     * Fecha de recepción
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    Date boFechaRecepcion;
    /**
     * Tipo. mpresa o Particular?
     * varchar (20)
     */
    String boTipo;
    /**
     * Comentarios.
     * varchar (500)
     */
    String boComentarios;


    public String getNumeroInstalacion() {
        return numeroInstalacion;
    }

    public void setNumeroInstalacion(String numeroInstalacion) {
        this.numeroInstalacion = numeroInstalacion;
    }

    public Integer getMaintenanceNumber() {
        return maintenanceNumber;
    }

    public void setMaintenanceNumber(Integer maintenanceNumber) {
        this.maintenanceNumber = maintenanceNumber;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getGrupoPanel() {
        return grupoPanel;
    }

    public void setGrupoPanel(String grupoPanel) {
        this.grupoPanel = grupoPanel;
    }

    public Float getTotalSinIVA() {
        return totalSinIVA;
    }

    public void setTotalSinIVA(Float totalSinIVA) {
        this.totalSinIVA = totalSinIVA;
    }

    public Float getTotalConIVA() {
        return totalConIVA;
    }

    public void setTotalConIVA(Float totalConIVA) {
        this.totalConIVA = totalConIVA;
    }

    public String getNumeroParte() {
        return numeroParte;
    }

    public void setNumeroParte(String numeroParte) {
        this.numeroParte = numeroParte;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaArchivo() {
        return fechaArchivo;
    }

    public void setFechaArchivo(Date fechaArchivo) {
        this.fechaArchivo = fechaArchivo;
    }

    public Date getFechaIncidencia() {
        return fechaIncidencia;
    }

    public void setFechaIncidencia(Date fechaIncidencia) {
        this.fechaIncidencia = fechaIncidencia;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getTipoIncicencia() {
        return tipoIncicencia;
    }

    public void setTipoIncicencia(String tipoIncicencia) {
        this.tipoIncicencia = tipoIncicencia;
    }

    public String getSubtipoIncidencia() {
        return subtipoIncidencia;
    }

    public void setSubtipoIncidencia(String subtipoIncidencia) {
        this.subtipoIncidencia = subtipoIncidencia;
    }

    public String getSolicitudCliente() {
        return solicitudCliente;
    }

    public void setSolicitudCliente(String solicitudCliente) {
        this.solicitudCliente = solicitudCliente;
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }

    public String getCambiosIncidencia() {
        return cambiosIncidencia;
    }

    public void setCambiosIncidencia(String cambiosIncidencia) {
        this.cambiosIncidencia = cambiosIncidencia;
    }

    public Date getBoFechaGestion() {
        return boFechaGestion;
    }

    public void setBoFechaGestion(Date boFechaGestion) {
        this.boFechaGestion = boFechaGestion;
    }

    public String getBoMatricula() {
        return boMatricula;
    }

    public void setBoMatricula(String boMatricula) {
        this.boMatricula = boMatricula;
    }

    public String getBoDatosRecuperados() {
        return boDatosRecuperados;
    }

    public void setBoDatosRecuperados(String boDatosRecuperados) {
        this.boDatosRecuperados = boDatosRecuperados;
    }

    public Date getBoFechaRecepcion() {
        return boFechaRecepcion;
    }

    public void setBoFechaRecepcion(Date boFechaRecepcion) {
        this.boFechaRecepcion = boFechaRecepcion;
    }

    public String getBoTipo() {
        return boTipo;
    }

    public void setBoTipo(String boTipo) {
        this.boTipo = boTipo;
    }

    public String getBoComentarios() {
        return boComentarios;
    }

    public void setBoComentarios(String boComentarios) {
        this.boComentarios = boComentarios;
    }
    
    public String getTypeName(){
    	return "TASK_TYPE_ASSISTANT_LIST";
    }
    
    public void setTypeName(String type){
    	
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaListadoAssistant{");
        sb.append("numeroInstalacion='").append(numeroInstalacion).append('\'');
        sb.append(", maintenanceNumber=").append(maintenanceNumber);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", technician='").append(technician).append('\'');
        sb.append(", departamento='").append(departamento).append('\'');
        sb.append(", grupoPanel='").append(grupoPanel).append('\'');
        sb.append(", totalSinIVA=").append(totalSinIVA);
        sb.append(", totalConIVA=").append(totalConIVA);
        sb.append(", numeroParte='").append(numeroParte).append('\'');
        sb.append(", fechaCierre=").append(fechaCierre);
        sb.append(", fechaArchivo=").append(fechaArchivo);
        sb.append(", fechaIncidencia=").append(fechaIncidencia);
        sb.append(", fechaPago=").append(fechaPago);
        sb.append(", operador='").append(operador).append('\'');
        sb.append(", tipoIncicencia='").append(tipoIncicencia).append('\'');
        sb.append(", subtipoIncidencia='").append(subtipoIncidencia).append('\'');
        sb.append(", solicitudCliente='").append(solicitudCliente).append('\'');
        sb.append(", incidencia='").append(incidencia).append('\'');
        sb.append(", cambiosIncidencia='").append(cambiosIncidencia).append('\'');
        sb.append(", boFechaGestion=").append(boFechaGestion);
        sb.append(", boMatricula='").append(boMatricula).append('\'');
        sb.append(", boDatosRecuperados='").append(boDatosRecuperados).append('\'');
        sb.append(", boFechaRecepcion=").append(boFechaRecepcion);
        sb.append(", boTipo='").append(boTipo).append('\'');
        sb.append(", boComentarios='").append(boComentarios).append('\'');
        sb.append(", closingReason=").append(closingReason);
        sb.append(", compensation='").append(compensation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
