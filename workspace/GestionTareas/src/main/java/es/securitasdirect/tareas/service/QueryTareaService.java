package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import com.webservice.Item;
import es.securitasdirect.tareas.exceptions.BusinessException;
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

    /**
     * Consulta de una única tarea.
     *
     * @param agent
     * @param callingList
     * @param id
     * @param parameters      lista de parametros que se han pasado por post
     * @return
     * @throws Exception
     */
    public Tarea queryTarea(Agent agent, String callingList, String id, Map<String, String> parameters) throws Exception {
        assert agent != null : "El Agente es parametro requerido";
        return queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, id, parameters);
    }

    /**
     * Consulta de una única tarea cuando no se tiene el mapa de parametros en recividos
     *

     */
    public Tarea queryTarea(Agent agent, String callingList, String id) throws Exception {
        assert agent != null : "El Agente es parametro requerido";
        return queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, id, null);
    }


    protected Tarea queryTarea(String ccUserId, String country, String desktopDepartment,
                               String callingList,
                               String id,
                               Map<String, String> parameters) throws Exception {

        assert country != null : "country es un parametro requerido";
        assert desktopDepartment != null : "desktopDepartment es un parametro requerido";

        String filter = "chain_id=" + id;

        LOGGER.debug("Calling to service CallingList with values:\n ccIdentifier: {}\n applicationUser: {}\n ccUserId: {}\n filter: {}\n callingList: {}\n country: {}", desktopDepartment, applicationUser, ccUserId, filter, callingList, country);
        Map<String, String> responseMap = checkCallingListContact(desktopDepartment,
                applicationUser,
                ccUserId,
                filter,
                callingList,
                country);

        if (responseMap == null) {
            LOGGER.error("Can't find Task {}-{}", callingList, filter);
            throw new BusinessException(BusinessException.ErrorCode.ERROR_FIND_TASK, callingList, filter);
        }

        Tarea tarea = tareaServiceTools.createTareaFromParameters(responseMap);
        tarea = tareaServiceTools.loadPostParametersInTask(tarea, parameters);
        return tarea;
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
     * @param desktopDepartment
     * @param applicationUser
     * @param ccUserId
     * @param filter
     * @param callingList
     * @param country
     * @return
     */
    private Map<String, String> checkCallingListContact(String desktopDepartment,
                                                        String applicationUser,
                                                        String ccUserId,
                                                        String filter,
                                                        String callingList,

                                                        String country) throws Exception {
        //WS Call
        CclResponse response = null;
        try {
            LOGGER.debug("Call CCLIntegration WS for Calling list with params: ccIdentifier:{}, {}");
            response = cclIntegration.checkCallingListContact(desktopDepartment,
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
