package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
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
import javax.jws.WebParam;
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
    @Inject
    protected QueryTareaService queryTareaService;
    @Inject
    protected CCLIntegration cclIntegration;


    @Resource(name = "datosCierreTareaAviso")
    protected Map<String, Map<Integer, String>> datosCierreTareaAviso;

    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * <p/>
     * 1.Comenzamos consultando de nuevo la tarea
     * 2.Si está en memoria...
     * <p/>
     * o	Record_status = 1 (ready)
     * o	Dial_sched_time = dd/mm/aaaa hh:mm:ss
     * o	Recort_type = 5 (personal callback) / 6 (campaing callback)
     */
    public boolean delayTask(String ccUserId, String country, String desktopDepartment,
                           String callingList,
                           String idTarea, Date schedTime, Integer recordType) throws Exception {
        LOGGER.info("Delaying Task : {} {}", callingList, idTarea);

        //Consultar la tarea de nuevo
        Tarea tarea = queryTareaService.queryTarea(ccUserId, country, desktopDepartment, callingList, idTarea);
        if (tarea != null) {
            //Si no está en memoria se puede ejecutar
            if (!tarea.isRetrieved()) {
             ccdDelayTask();//TODO VOY POR AQUI
            } else {
                LOGGER.warn("Can't delay task because is in Retrieved {}" , tarea);
                return false;
            }

//            // Invocará un web service para actualizar el Aviso.
//            Integer naviso = idAviso;
//            String gblidusr = null; //TODO PENDIENTES
//            String idaplaza = null; //TODO PENDIENTES
//            String fhasta = null; //TODO PENDIENTES
//            String cnota = null; //TODO PENDIENTES
//
//            try {
//                List<RowErrorAA> rowErrorAAs = spAvisosOperaciones.aplazarAviso(naviso, gblidusr, idaplaza, fhasta, cnota);
//                //TODO Debug para ver que devuelve y controlar si hay errores devolver
//                if (rowErrorAAs != null && !rowErrorAAs.isEmpty()) {
//                    LOGGER.error("Error aplazando aviso {}", idAviso);
//                    return false;
//                }
//            } catch (DataServiceFault e) {
//                LOGGER.error("Error aplazando aviso", e);
//                return false;
//            }
            return true;
        } else {
            LOGGER.error("Can't find task to delay");
            //TODO CREAR EXCEPCIONES DE NEGOCIO
            return false;
        }

    }

    public boolean createTask(Tarea tarea) {
        LOGGER.debug("Creating task: {}", tarea);
        boolean result;
        try {
            //TODO Llamada WS crear tarea
            //TODO establecer criterio de OK y KO
            if (true) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("Error creating task: {}", e);
            result = false;
        }
        return result;
    }


    public boolean createMaintenance(Tarea task) {
        LOGGER.debug("Creating maintenance: {}", task);
        boolean result;
        try {
            //TODO Llamada WS crear tarea
            //TODO establecer criterio de OK y KO
            if (true) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("Error creating maintenance: {}", e);
            result = false;
        }
        return result;
    }


    private boolean ccdDelayTask() {
//        o	Record_status = 1 (ready)
//                o	Dial_sched_time = dd/mm/aaaa hh:mm:ss
//        o	Recort_type = 5 (personal callback) / 6 (campaing callback)



        String ccIdentifier=null;
        String applicationUser=null;
        String ccUserId=null;
        String filter=null;
        List<net.java.dev.jaxb.array.StringArray> modifyValues=null;
        List<java.lang.String> callingList=null;
        List<java.lang.String> campaign=null;
        String contactInfo=null;
        String country=null;

        cclIntegration.updateCallingListContact(ccIdentifier,applicationUser,ccUserId,filter,modifyValues,callingList,campaign,contactInfo,country);

                return true;
    }
}
