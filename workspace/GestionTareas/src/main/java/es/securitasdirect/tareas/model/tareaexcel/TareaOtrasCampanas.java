package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.List;

/**
 * Tarea Otras Campañas.
 * En este caso, en función del grupo origen (Tipo de Campaña)  se asigna una Calling List determinada.
 *
 * @author Team Vision
 */
public class TareaOtrasCampanas extends TareaExcel {

    /**
     * Tipo de Campaña (origen y motivo)
     * varchar (10)
     */
    String tipoCampana;
    /**
     * Comentarios.
     * varchar (1000)
     */
    String comentario;

    /**
     * Campo 1
     */
    String campo1;
    /**
     * Campo 2
     */
    String campo2;
    /**
     * Campo 3
     */
    String campo3;
    /**
     * Motivo de Cierre, lista de valores
     */
    List<String> motivosCierre;
    /**
     * Compensación.
     */
    String compensacion;

    public String getTipoCampana() {
        return tipoCampana;
    }

    public void setTipoCampana(String tipoCampana) {
        this.tipoCampana = tipoCampana;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public String getCampo2() {
        return campo2;
    }

    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    public String getCampo3() {
        return campo3;
    }

    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }

    public List<String> getMotivosCierre() {
        return motivosCierre;
    }

    public void setMotivosCierre(List<String> motivosCierre) {
        this.motivosCierre = motivosCierre;
    }

    public String getCompensacion() {
        return compensacion;
    }

    public void setCompensacion(String compensacion) {
        this.compensacion = compensacion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TareaOtrasCampanas{");
        sb.append("tipoCampana='").append(tipoCampana).append('\'');
        sb.append(", comentario='").append(comentario).append('\'');
        sb.append(", campo1='").append(campo1).append('\'');
        sb.append(", campo2='").append(campo2).append('\'');
        sb.append(", campo3='").append(campo3).append('\'');
        sb.append(", motivosCierre=").append(motivosCierre);
        sb.append(", compensacion='").append(compensacion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
