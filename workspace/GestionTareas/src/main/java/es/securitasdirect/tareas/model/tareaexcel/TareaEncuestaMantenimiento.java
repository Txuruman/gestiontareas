package es.securitasdirect.tareas.model.tareaexcel;

import es.securitasdirect.tareas.model.TareaExcel;

/**
 * Encuestas Mantenimientos.
 *
 * MANTENIMIENTO	int
 * TECNICO	varchar (10)
 * JE	varchar (10)
 * CC	varchar (5)
 * RAZON	varchar (500)
 * SOLUCION	varchar (500)
 * COMPROMISO	varchar(500)
 * DPTO_DESTINO	varchar (15)
 * INSTALACION	varchar (12)
 * NOMBRE	varchar (120)
 * TELEFONO	varchar (15)
 *
 *
 * @author Team Vision
 */
public class TareaEncuestaMantenimiento extends TareaExcel {


    /**
     * Número de Mantenimiento
     */
    Integer numeroMantenimiento;
    /**
     * Técnico.
     * varchar (10)
     */
    String tecnico;
    /**
     * Responsable, alias JE?
     * varchar(10)
     */
    String responsable;
    /**
     * Centro de Coste.
     * varchar(5)
     */
    String centroCoste;
    /**
     * Razón clave de la valoración.
     * varchar (500)
     */
    String razonClaveValoracion;
    /**
     * Solución.
     * varchar (500)
     */
    String solucion;
    /**
     * Compromiso.
     * varchar(500)
     */
    String compromiso;
    /**
     * Departamento destino.
     * varchar (15)
     */
    String departamentoDestino;

}
