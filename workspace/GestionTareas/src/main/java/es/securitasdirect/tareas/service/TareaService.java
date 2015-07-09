package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.TareaExcel;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;

/**
 *
 */
@Named(value = "tareaService")
@Singleton
public class TareaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TareaService.class);

    //Web Services para hacer pruebas directamente
    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;



    @Resource(name="datosCierreTareaAviso")
    protected Map<String,Map<Integer, String>> datosCierreTareaAviso;

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
        String idaplaza = null; //TODO PENDIENTES
        String fhasta = null; //TODO PENDIENTES
        String cnota = null; //TODO PENDIENTES

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


}
