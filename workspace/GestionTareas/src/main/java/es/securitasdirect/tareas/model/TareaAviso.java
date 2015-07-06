package es.securitasdirect.tareas.model;

import java.util.Date;
import java.util.List;

/**
 * Tareas de tipo Aviso
 */
public class TareaAviso extends Tarea {

    /**
     * Identificador de Aviso
     */
    Integer idAviso;
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
    Integer identificativoAvisoTarea;
    /**
     * Identificativo Instalación: número de instalación/cliente
     */
    String numeroInstalacion;
    /*	Titular: nombre del cliente, a modo informativo  */
    String titular;
    /*	Requerido por: importante para el reporting y para algunos departamentos  */
    String requeridoPor;
    /*	Datos de Contacto: cliente que inició la incidencia, forma de contacto, horarios de contacto  */
    String datosContacto;
    /*	Fecha de creación: información para trazabilidad y para la gestión de días de vencimiento  */
    Date fechaCreacion;
    /*	Estado  */
    String estado;
    /** Horario desde */
    private String horarioDesde;
    /** Horario hasta */
    private String horarioHasta;
    /**
     * Motivo de Cierre
     */
    String closing;

    /**
     * Datos adicionales del cierre
     */
    List<String> datosAdicionalesCierre;
    /**
     * Fecha de Cierre
     */
    Date fechaCierre;
    /**
     * Nota
     */
    String nota;
    /**
     * Responsable del cierre
     */
    String responsableCierre;

    public String getHorarioDesde() {
        return horarioDesde;
    }

    public void setHorarioDesde(String horarioDesde) {
        this.horarioDesde = horarioDesde;
    }

    public String getHorarioHasta() {
        return horarioHasta;
    }

    public void setHorarioHasta(String horarioHasta) {
        this.horarioHasta = horarioHasta;
    }

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

    public Integer getIdentificativoAvisoTarea() {
        return identificativoAvisoTarea;
    }

    public void setIdentificativoAvisoTarea(Integer identificativoAvisoTarea) {
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


    public Integer getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(Integer idAviso) {
        this.idAviso = idAviso;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public List<String> getDatosAdicionalesCierre() {
        return datosAdicionalesCierre;
    }

    public void setDatosAdicionalesCierre(List<String> datosAdicionalesCierre) {
        this.datosAdicionalesCierre = datosAdicionalesCierre;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getResponsableCierre() {
        return responsableCierre;
    }

    public void setResponsableCierre(String responsableCierre) {
        this.responsableCierre = responsableCierre;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TareaAviso{");
        sb.append("idAviso=").append(idAviso);
        sb.append(", tipoAviso1='").append(tipoAviso1).append('\'');
        sb.append(", tipoAviso2='").append(tipoAviso2).append('\'');
        sb.append(", tipoAviso3='").append(tipoAviso3).append('\'');
        sb.append(", motivo1='").append(motivo1).append('\'');
        sb.append(", motivo2='").append(motivo2).append('\'');
        sb.append(", motivo3='").append(motivo3).append('\'');
        sb.append(", observaciones='").append(observaciones).append('\'');
        sb.append(", identificativoAvisoTarea=").append(identificativoAvisoTarea);
        sb.append(", numeroInstalacion='").append(numeroInstalacion).append('\'');
        sb.append(", titular='").append(titular).append('\'');
        sb.append(", requeridoPor='").append(requeridoPor).append('\'');
        sb.append(", datosContacto='").append(datosContacto).append('\'');
        sb.append(", fechaCreacion=").append(fechaCreacion);
        sb.append(", estado='").append(estado).append('\'');
        sb.append(", horarioDesde='").append(horarioDesde).append('\'');
        sb.append(", horarioHasta='").append(horarioHasta).append('\'');
        sb.append(", closing=").append(closing);
        sb.append(", datosAdicionalesCierre=").append(datosAdicionalesCierre);
        sb.append(", fechaCierre=").append(fechaCierre);
        sb.append(", nota='").append(nota).append('\'');
        sb.append(", responsableCierre='").append(responsableCierre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}



