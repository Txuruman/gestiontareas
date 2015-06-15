package es.securitasdirect.tareas.model;

/**
 * Clase base de todos los tipos de tareas a tratar.
 *
 * @author Team Vision
 */
public class Tarea {

    /**
     * Estado de la Tarea (record_status): ready, retrieved, …
     */
    String estado;

    /**
     * ctr_no = número de contrato
     * varchar(12)
     */
    String numeroContrato;

    /*
        TODO PENDIENTE: REVISAR QUÉ OTROS CAMPOS DE GENESYS HAY QUE TENER EN CUENTA
        (CONTRASTARLOS CON LOS DEL DOCUMENTO DPB140808_ENT_DISEÑO TÉCNICO CALLBACK ATC V1.4.DOCX)
     */

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }
}
