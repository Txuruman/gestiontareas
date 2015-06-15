package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

import java.util.Date;

/**
 * Tarea Keybox
 *
 * @author Team Vision
 */
public class TareaKeybox extends TareaExcel {

    /**
     * Número de Instalación
     */
    String numeroInstalacion;
    /**
     * Número de Factura
     */
    String numeroFactura;
    /**
     * Fecha de la Factura
     */
    Date fechaFactura;
    /**
     * Importe de la Línea
     */
    Double importeLinea;

    /**
     * Identificador de ítem
     */
    String identificadorItem;
    /**
     * Tipo de Panel.
     */
    String tipoPanel;


}
