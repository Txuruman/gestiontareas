package es.securitasdirect.tareas.web.controller.task;

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
import org.wso2.ws.dataservice.GrpAplazamiento;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.service.model.DiscardNotificationTaskResult;
import es.securitasdirect.tareas.web.controller.AgentController;
import es.securitasdirect.tareas.web.controller.TaskController;
import es.securitasdirect.tareas.web.controller.dto.DiscardNotificationTaskResponse;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.GetInstallationAndTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.DiscardNotificationTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.FinalizeNotificationTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.PostponeNotificationTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.dto.response.TipoAplazaResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * @author jel
 */

@Controller
@RequestMapping("/notificationtask")
public class NotificationTaskController extends TaskController {

    @Inject
    private TareaService tareaService;
    @Autowired
    private AgentController agentController;


    @RequestMapping(value = "/getInstallationAndTask", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse getInstallationAndTask(@RequestBody GetInstallationAndTaskRequest request) {
    	TareaResponse response=new TareaResponse();
    	try{
	        response= (TareaResponse) super.getInstallationAndTask(request.getCallingList(),request.getTaskId(), request.getParams());
	        if (response.getTarea()==null) {
//	        	response.danger(messageUtil.getProperty("ERROR_FIND_TICKET"));
	            response.setNoTicked(true);
	            response.setNoInstallationMsg(messageUtil.getProperty("ERROR_FIND_TICKET"));
			} 
    	}catch(Exception e){
    		return processException(e);
    	}
        return response;
    }


    @RequestMapping(value = "/getClosing", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getClosing() {
        String SERVICE_MESSAGE = "notificationtask.getclosing";
        PairListResponse response;
        List<Pair> closingList = null;
        try {

            response = new PairListResponse();
            response.setPairList(closingList);
            //processresponse
        } catch (Exception e) {
            response = new PairListResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }


    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTaskController.class);

    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse postpone(@RequestBody PostponeNotificationTaskRequest request) {
        return super.delayTask(request.getTask(), request.getRecallType(), request.getDelayDate(), request.getMotive(),request.getFromSearch());
    }

    /**
     * Finalización de Tarea tipo Aviso
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/finalizar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse finalizeTask(@RequestBody FinalizeNotificationTaskRequest request) {
        LOGGER.debug("Finalizando tarea aviso {}  ", request);
        BaseResponse response = new BaseResponse();

        //Validación de los datos de respuesta si se finaliza por Crear Mantenimiento
        if (request.isFinalizedByCreateMaintenance()) {
            if (request.getStatus() == null) {
                response.danger(messageUtil.getProperty("createMaintenance.response.unknown"));
                return response;
            } else if (request.getStatus() != 0) { //Detectamos error en la respuesta , TODO Hay que ver que datos vienen en varios casos
                response.danger(messageUtil.getProperty("createMaintenance.response.error", request.getStatus(), request.getMessage()));
                return response;
            } else{
            	response.danger(messageUtil.getProperty("createMaintenance.response.success"));
                return response;
            }
        }


        //Llamada al servicio para finalizar
        try {
            Agent agent = agentController.getAgent();
            int resultado = tareaService.finalizeNotificationTask(agent, request.getTask(), request.isFinalizedByCreateMaintenance(), request.getAppointmentNumber(),
            		request.getFromSearch());
            
            if (resultado==1)
            	response.setTareaRetrieved(true);
            
            if (resultado==0) {
                response.info(messageUtil.getProperty("finalize.success"));
            } else {
                response.danger(messageUtil.getProperty("finalize.error"));
            }
        } catch (Exception e) {
            response = processException(e);
        }
        LOGGER.debug("Finalizado de tarea\nResponse:{}", response);
        return response;
    }


    @RequestMapping(value = "/descartaraviso", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse discardNotificationTask(@RequestBody DiscardNotificationTaskRequest request) {
        LOGGER.debug("Descartar tareaAviso\nRequest: {}", request);
        DiscardNotificationTaskResponse response = new DiscardNotificationTaskResponse();
        Agent agent = agentController.getAgent();
        try {
            DiscardNotificationTaskResult discardNotificationTaskResult = tareaService.discardNotificationTask(agent, request.getTask(), request.getInstallation(), true, request.isCallDone(), request.isWithInteaction(),
            		request.getFromSearch());
            response.setResult(discardNotificationTaskResult);
            response.setTareaRetrieved(discardNotificationTaskResult.isFromSearch());
            response.success(messageUtil.getProperty("notificationTask.modify.success"));
        } catch (Exception e) {
            return processException(e);
        }

        LOGGER.debug("Descartar tareaAviso\nResponse:{}", response);
        return response;
    }



    @RequestMapping(value = "/descartaravisosinsalvartarea", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse discardNotificationWithoutSaveTask(@RequestBody DiscardNotificationTaskRequest request) {
        LOGGER.debug("Descartar tareaAviso\nRequest: {}", request);
        DiscardNotificationTaskResponse response = new DiscardNotificationTaskResponse();
        Agent agent = agentController.getAgent();
        try {
        	 DiscardNotificationTaskResult discardNotificationTaskResult = tareaService.discardNotificationTask(agent, request.getTask(), request.getInstallation(), false, request.isCallDone(),request.isWithInteaction(),
        			 request.getFromSearch());
            response.setResult(discardNotificationTaskResult);
            response.setTareaRetrieved(discardNotificationTaskResult.isFromSearch());
            response.success(messageUtil.getProperty("notificationTask.modify.success"));
        } catch (Exception e) {
           return processException(e);
        }


        LOGGER.debug("Descartar tareaAviso\nResponse:{}", response);
        return response;
    }
    
    @RequestMapping(value = "/getTipoAplaza", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse getTipoAplaza() {
    	LOGGER.debug("Obtener tipos de aplazamiento");
    	TipoAplazaResponse response = new TipoAplazaResponse();
        try {
            List<GrpAplazamiento> lista = tareaService.getTipoAplaza();
            response.setListGrpAplazamiento(lista);
        } catch (Exception e) {
            response = (TipoAplazaResponse) processException(e);
        }
        return response;
    }


}
