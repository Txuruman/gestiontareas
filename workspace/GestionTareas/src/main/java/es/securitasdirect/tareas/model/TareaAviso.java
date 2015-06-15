package es.securitasdirect.tareas.model;

import java.util.Date;

/**
 * Tareas de tipo Aviso
 */
public class TareaAviso extends Tarea {

    /**
     * Tipos de Aviso: el primero es el utilizado, el segundo y el tercero son sólo informativos.
     */
    String tipoAviso1;
    String tipoAviso2;
    String tipoAviso3;
    /**
     * Motivos: el primero es el utilizado, el segundo y el tercero son sólo informativos.
     */
    String motivo1;
    String motivo2;
    String motivo3;
    /*
    Observaciones: información adicional
    */
    String observaciones;
    /**
     * Identificativo Aviso-Tarea: conexión entre el Aviso y la Tarea (para poder seguir la trazabilidad entre ambos aplicativos)
     */
    String identificativoAvisoTarea;
    /**
     * Identificativo Instalación: número de instalación/cliente
     */
    String numeroInstalacion;
    /*	Titular: nombre del cliente, a modo informativo  */
    String titular;
    /*	Panel: tipo de panel de la instalación  */
    String tipoPanel;
    /*	Versión: versión del panel  */
    String versionPanel;
    /*	Requerido por: importante para el reporting y para algunos departamentos  */
    String requeridoPor;
    /*	Datos de Contacto: cliente que inició la incidencia, forma de contacto, horarios de contacto  */
    String datosContacto;
    /*	Fecha de creación: información para trazabilidad y para la gestión de días de vencimiento  */
    Date fechaCreacion;
    /*	Estado  */
    String estado;
    /*	Datos del cierre: motivo, fecha, responsable y datos adicionales  */
    String datosCierre;

    public String getTipoAviso1() {
        return tipoAviso1;
    }

    public void setTipoAviso1(String tipoAviso1) {
        this.tipoAviso1 = tipoAviso1;
    }

    public String getTipoAviso2() {
        return tipoAviso2;
    }

    public void setTipoAviso2(String tipoAviso2) {
        this.tipoAviso2 = tipoAviso2;
    }

    public String getTipoAviso3() {
        return tipoAviso3;
    }

    public void setTipoAviso3(String tipoAviso3) {
        this.tipoAviso3 = tipoAviso3;
    }

    public String getMotivo1() {
        return motivo1;
    }

    public void setMotivo1(String motivo1) {
        this.motivo1 = motivo1;
    }

    public String getMotivo2() {
        return motivo2;
    }

    public void setMotivo2(String motivo2) {
        this.motivo2 = motivo2;
    }

    public String getMotivo3() {
        return motivo3;
    }

    public void setMotivo3(String motivo3) {
        this.motivo3 = motivo3;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdentificativoAvisoTarea() {
        return identificativoAvisoTarea;
    }

    public void setIdentificativoAvisoTarea(String identificativoAvisoTarea) {
        this.identificativoAvisoTarea = identificativoAvisoTarea;
    }

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

    public String getRequeridoPor() {
        return requeridoPor;
    }

    public void setRequeridoPor(String requeridoPor) {
        this.requeridoPor = requeridoPor;
    }

    public String getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(String datosContacto) {
        this.datosContacto = datosContacto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDatosCierre() {
        return datosCierre;
    }

    public void setDatosCierre(String datosCierre) {
        this.datosCierre = datosCierre;
    }
}
