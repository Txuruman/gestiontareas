package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import com.webservice.Item;
import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.web.controller.params.TaskServiceParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servicio para consulta de una tarea
 */
@Named(value = "queryTareaService")
@Singleton
public class QueryTareaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTareaService.class);

    @Inject
    protected CCLIntegration cclIntegration;

    @Inject
    protected TareaServiceTools tareaServiceTools;

    /**
     * Maps the name of the Calling List to the specific tipe of Tarea
     */
    @Resource(name = "callingListToModel")
    private Map<String, List<String>> callingListToModel;

    @Resource(name = "applicationUser")
    private String applicationUser;

    public Tarea queryTarea(String ccUserId ,
                            String callingList,
                            String id) throws Exception{
        return null;//TODO BORRAR
    }

    public Tarea queryTarea(String ccUserId, String country, String desktopDepartment,
                            String callingList,
                            String id) throws Exception{

        assert Integer.valueOf(id)!=null: "Hay que meter un número"; //TODO Quitar esto y pasar el parametro a Integer cuando se vea más claro
        assert country!=null;
        assert desktopDepartment!=null;

        String filter               = "chain_id=" + id;

        Tarea tarea;

        LOGGER.debug("Calling to service CallingList with values:\n ccIdentifier: {}\n applicationUser: {}\n ccUserId: {}\n filter: {}\n callingList: {}\n country: {}", desktopDepartment, applicationUser, ccUserId, filter, callingList, country);
        Map<String, String> responseMap = checkCallingListContact(desktopDepartment,
                applicationUser,
                ccUserId,
                filter,
                callingList,
                country);
        tarea = tareaServiceTools.createTareaFromParameters(responseMap);
        return tarea;
    }

    private void tareaToLog(String tipoTarea, Tarea tarea) {
        if (tipoTarea != null) {
            if (tipoTarea.equals(Constants.TAREA_AVISO)) {
                TareaAviso castedTarea = (TareaAviso) tarea;
                LOGGER.debug("Tarea - TareaAviso: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_LISTADOASSISTANT)) {
                TareaListadoAssistant castedTarea = (TareaListadoAssistant) tarea;
                LOGGER.debug("Tarea - TareaListadoAssistant: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_ENCUESTAMANTENIMIENTO)) {
                MaintenanceSurveyTask castedTarea = (MaintenanceSurveyTask) tarea;
                LOGGER.debug("Tarea - MaintenanceSurveyTask: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_ENCUESTAMARKETING)) {
                MarketingSurveyTask castedTarea = (MarketingSurveyTask) tarea;
                LOGGER.debug("Tarea - MarketingSurveyTask: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_KEYBOX)) {
                KeyboxTask castedTarea = (KeyboxTask) tarea;
                LOGGER.debug("Tarea - KeyboxTask: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_LIMPIEZACUOTA)) {
                TareaLimpiezaCuota castedTarea = (TareaLimpiezaCuota) tarea;
                LOGGER.debug("Tarea - TareaLimpiezaCuota: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_OTRASCAMPANAS)) {
                TareaOtrasCampanas castedTarea = (TareaOtrasCampanas) tarea;
                LOGGER.debug("Tarea - TareaOtrasCampanas: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_MANTENIMIENTO)) {
                TareaMantenimiento castedTarea = (TareaMantenimiento) tarea;
                LOGGER.debug("Tarea - TareaMantenimiento: {}", castedTarea);
            }
        }
    }




    private boolean cclResponseHasError(CclResponse response) {
        boolean hasError = true;
        if (response != null && response.getOperationResult() != null) {
            if (response.getOperationResult().getResultCode() == 200) {
                hasError = false;
            }
        }
        return hasError;
    }

    private void printCclResponseError(CclResponse response) {
        StringBuilder sb = new StringBuilder();
        if (response != null && response.getOperationResult() != null) {
            sb.append("ERROR CODE: [");
            sb.append(response.getOperationResult().getResultCode());
            sb.append("]");
            sb.append(" - ERROR MESSAGE: [");
            if (response.getOperationResult().getResultMessage() != null) {
                sb.append(response.getOperationResult().getResultMessage());
            } else {
                sb.append("Not found");
            }
            sb.append("]");
        } else {
            sb.append("ERROR DATA NOT FOUND");
        }
        LOGGER.error(sb.toString());
    }

    /**
     * Web Service Calling and transformation of the results to Map
     *
     * @param ccIdentifier
     * @param applicationUser
     * @param ccUserId
     * @param filter
     * @param callingList
     * @param country
     * @return
     */
    private Map<String, String> checkCallingListContact(String ccIdentifier,
                                                        String applicationUser,
                                                        String ccUserId,
                                                        String filter,
                                                        String callingList,

                                                        String country) throws Exception {
        //WS Call
        CclResponse response = null;
        try {
            LOGGER.debug("Call CCLIntegration WS for Calling list with params: ccIdentifier:{}, {}");
            response = cclIntegration.checkCallingListContact(ccIdentifier,
                    applicationUser,
                    ccUserId,
                    filter,
                    Arrays.asList(""), //Allways ask for all the parameters
                    Arrays.asList(callingList),
                    country);
        } catch (Exception e) {
            LOGGER.error("Error calling CCL checkCallingListContact.", e);
            throw e;
        }

        //Results Map
        Map<String, String> responseMap = null;

        //Comprobar respuesta si tiene error:
        if (cclResponseHasError(response)) {
            printCclResponseError(response);
            //TODO Definir si realizar otras operaciones en caso de error
        } else {
            //Si la respuesta no tiene error
            //Obtener el tipo de respuesta
            responseMap = tareaServiceTools.loadCclUniqueResponseMap(response);
            LOGGER.debug("checkCallingListContact result {}", responseMap);
        }
        return responseMap;
    }














}
