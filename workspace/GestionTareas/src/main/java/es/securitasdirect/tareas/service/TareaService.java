package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.RowErrorAA;
import org.wso2.ws.dataservice.SPAVISOSOPERACIONESPortType;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Named(value = "tareaService")
@Singleton
public class TareaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TareaService.class);

    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;

    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * Si confirma, se invocará un web service para marcar la Tarea como tratada (mark done) y
     * se invocará un web service para actualizar el Aviso.
     */
    public boolean aplazar(Integer idAviso) {
        LOGGER.info("Aplazando idAviso:{}", idAviso);

        //TODO se invocará un web service para marcar la Tarea como tratada (mark done) y

        // Invocará un web service para actualizar el Aviso.
        Integer naviso = idAviso;
        String gblidusr = null; //TODO PENDIENTES
        String idaplaza= null; //TODO PENDIENTES
        String fhasta= null; //TODO PENDIENTES
        String cnota= null; //TODO PENDIENTES

        try {
            List<RowErrorAA> rowErrorAAs = spAvisosOperaciones.aplazarAviso(naviso, gblidusr, idaplaza, fhasta, cnota);
            //TODO Debug para ver que devuelve y controlar si hay errores devolver
            if (rowErrorAAs != null && !rowErrorAAs.isEmpty()) {
                LOGGER.error("Error aplazando aviso {}", idAviso);
                return false;
            }
        } catch (DataServiceFault e) {
            LOGGER.error("Error aplazando aviso", e);
            return false;
        }
        return true;
    }

    /* Descartar: se invocará un web service para marcar la Tarea como tratada (mark done) y se invocará un web service para actualizar el Aviso. */
    public boolean descartar(String idAviso) {
        LOGGER.info("Descartar idAviso:{}", idAviso);
        return true;
    }

    /* Finalizar: se validará que haya rellenado la información del Cierre, se invocará un web service para marcar la Tarea como tratada (mark done) y se invocará un web service para finalizar el Aviso. */
    public boolean finalizar(String idAviso) {
        LOGGER.info("Finalizando idAviso:{}", idAviso);
        return true;
    }

    /* Llamar: realiza una llamada telefónica al número indicado en el campo de teléfono de contacto. Para ello, ejecutará un web service disponible en CCL. */
    public boolean llamar(String idAviso) {
        LOGGER.info("Llamando idAviso:{}", idAviso);

        return true;
    }

    /**
     * Obtiene una Tarea by?????
     *
     * @param idAviso
     * @return
     */
    public Tarea getTareaByIdAviso(String idAviso) {
        TareaAviso tarea = new TareaAviso();
        tarea.setObservaciones("Las observaciones");
        return tarea;
    }

    public List<Tarea> findByTelefono(String telefono){

    }
    public List<Tarea> findByInstalacion(String instalacion){

    }
    private List<Tarea> createDummy(){
        List<Tarea> tareas = new ArrayList<Tarea>();
        Tarea ejemploAviso = new TareaAviso();
        ejemploAviso.setEstado("estado1");
        tareas.add(ejemploAviso);

        Tarea ejemploInstalacion = new TareaAviso();
        ejemploInstalacion.setNumeroContrato("111111");
        tareas.add(ejemploInstalacion);
    }
}
