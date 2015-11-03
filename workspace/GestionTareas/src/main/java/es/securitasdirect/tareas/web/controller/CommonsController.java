package es.securitasdirect.tareas.web.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.securitasdirect.tareas.model.external.BigIntegerPair;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.model.external.StringPair;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.service.SplitService;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.request.DiscardExcelTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.ClosingTypeRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.CreateMaintenanceAppRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.TypeReasonListRequest;
import es.securitasdirect.tareas.web.controller.dto.response.BigIntegerPairResponse;
import es.securitasdirect.tareas.web.controller.dto.response.CreateMaintenanceAppResponse;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.dto.response.StringPairListResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * @author
 */

@Controller
@RequestMapping("/commons")
public class CommonsController extends BaseController {
    @Inject
    private ExternalDataService externalDataService;
    @Autowired
    private AgentController agentController;
    @Inject
    protected SplitService splitService;
    @Inject
    private TareaService tareaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonsController.class);

    /**
     * Devuelve el tipo de Crear mantenimiento que hay que abrir dependiendo de los datos.
     * @param createMaintenanceAppRequest Tarea e Instalación
     * @return TOA o MMS
     */
    @RequestMapping(value = "/getCreateMaintenanceApp", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse getCreateMaintenanceApp(@RequestBody CreateMaintenanceAppRequest createMaintenanceAppRequest) {
        try {
            CreateMaintenanceAppResponse response = new CreateMaintenanceAppResponse();
            String maintenanceApplication = splitService.getMaintenanceApplication(agentController.getAgent(), createMaintenanceAppRequest.getTareaAviso(), createMaintenanceAppRequest.getInstallationData());
            response.setApp(maintenanceApplication);
            return response;
        } catch (Exception e) {
            return processException(e);
        }
    }

    @RequestMapping(value = "/getNotificationTypeList", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getNotificationTypeList() {
        String SERVICE_MESSAGE = "commonService.notificationTypeList";
        LOGGER.debug("Busqueda de lista de tipos de tarea de aviso");
        List<Pair> tipoAvisoList;
        PairListResponse response;
        try {
            tipoAvisoList = externalDataService.getNotificationType();
            response = new PairListResponse();
            response.setPairList(tipoAvisoList);
            LOGGER.debug("Obtenida lista de tipos de tarea de aviso: {}", tipoAvisoList);
        } catch (Exception exc) {
            response = new PairListResponse(super.processException(exc, SERVICE_MESSAGE));
        }
        LOGGER.debug("GetNotificationTypeList - Response: {}", response);
        return response;
    }

    /**
     * Lista de los tipos de cierre para la tarea tipo aviso
     * primero se comprueba si se han modificado los tipos y motivos de la tarea
     * @param closingTypeRequest
     * @return
     */
    @RequestMapping(value = "/getNotificationClosingList", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    StringPairListResponse getNotificationClosingList(@RequestBody ClosingTypeRequest closingTypeRequest) {
        String SERVICE_MESSAGE = "commonService.closingTypeList";
        List<StringPair> closingList;
        StringPairListResponse response;
        try {
        	
        	
            LOGGER.debug("Getting closing type list for request: {}", closingTypeRequest);
            closingList = externalDataService.getNotificationClosing(closingTypeRequest.getTarea(), agentController.getAgent().getAgentIBS());
            LOGGER.debug("Loaded closing type list {}", closingList);
            response = new StringPairListResponse();
            response.setPairList(closingList);
        } catch (Exception e) {
            LOGGER.error("Error in closing type list service request:");
            response = new StringPairListResponse(super.processException(e, SERVICE_MESSAGE));
        }
        LOGGER.debug("GetClosingListResponse = {}", response);
        return response;
    }

    /**
     * Consultar datos ADICIONALES de cierre para tareas tipo aviso
     *
     * @return
     */
    @RequestMapping(value = "/getClosingAditionalDataList", method = {RequestMethod.GET},  produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getClosingAditionalDataList() {
        String SERVICE_MESSAGE = "commonService.closingAditionalDataList";
        LOGGER.debug("Loading closing type aditional data list ");
        List<Pair> closingListAditionalData;
        PairListResponse response;
        try {
            closingListAditionalData = externalDataService.getDatosAdicionalesCierreTareaAviso();
            response = new PairListResponse();
            response.setPairList(closingListAditionalData);
            LOGGER.debug("Closing aditional data list: {}", closingListAditionalData);
        } catch (Exception exc) {
            LOGGER.error("Error in closing aditional data list request:");
            response = new PairListResponse(super.processException(exc, SERVICE_MESSAGE));
        }
        LOGGER.debug("GetClosingAditionalDataListResponse = {}", response);
        return response;

    }

    /**
     * Tipos para los combos de Tarea Tivo Aviso
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTypeReasonList", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BigIntegerPairResponse getTypeReasonList(@RequestBody TypeReasonListRequest request) {
        String SERVICE_MESSAGE = "commonService.taskTypeReasonList";
        List<BigIntegerPair> typeReasonList;
        BigIntegerPairResponse response;
        try {
            LOGGER.debug("Calling for get type reason list with request: {}", request);
            typeReasonList = externalDataService.getTypeReasonList(request.getIdType());
            LOGGER.debug("Type reason list: {}", typeReasonList);
            response = new BigIntegerPairResponse();
            response.setPairList(typeReasonList);
        } catch (Exception exc) {
            LOGGER.error("Error in type reason list request");
            response = new BigIntegerPairResponse(super.processException(exc, SERVICE_MESSAGE));
        }
        LOGGER.debug("Type reason list call response: {}", response);
        return response;
    }

	/**
     * ReportingTareas para Tarea Excel con interacion sin llamada.
     *
     * @return
     */
    @RequestMapping(value = "/reportingTareas", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse reportingTareas(@RequestBody DiscardExcelTaskRequest request) {
        BaseResponse response;
        try {
            tareaService.wsReportingTareas(request.getTask(), agentController.getAgent(), "DESCARTAR");
            response = new BaseResponse();
            response.setSuccess(true);
        } catch (Exception e) {
            return  processException(e);
        }
        return response;
    }
}
