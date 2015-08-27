package es.securitasdirect.tareas.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.service.SearchTareaService;
import es.securitasdirect.tareas.web.controller.dto.SearchTareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeGenericTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.PostponeRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.PostponeNotificationTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.request.searchtask.SearchTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping("/searchtarea")
public class SearchTareaController extends TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTareaController.class);

    @Inject
    private SearchTareaService searchTareaService;
    @Autowired
    private AgentController agentController;
    @Inject
    protected MessageUtil messageUtil;

    protected SearchTaskRequest lastSearchTareaRequest = new SearchTaskRequest();



    @RequestMapping(value = "/aplazar", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse postpone(@RequestBody PostponeGenericTaskRequest request) {
        return super.delayTask(request.getTask(), request.getRecallType(), request.getDelayDate());
    }

    @RequestMapping(value = "/query", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    SearchTareaResponse query(@RequestBody SearchTaskRequest request) {
        this.lastSearchTareaRequest = request;
        String SERVICE_MESSAGE = "searchTarea";
        LOGGER.debug("Searching Tareas text:{} option:{}", request.getSearchText(), request.getSearchOption());
        SearchTareaResponse response;
        if (!request.validateParams()) {
            response = new SearchTareaResponse(processParamsError(SERVICE_MESSAGE));
        } else if (!agentController.isLogged()) {
            response = new SearchTareaResponse();
            response.danger(messageUtil.getProperty("agent.notLoggedIn"));
        } else {
            try {
                List<Tarea> listaTareas;
                if (request.getSearchOption().equals(SearchTaskRequest.TELEPHONE)) {
                    agentController.getAgent().getIdAgent();
                    agentController.getAgent().getAgentCountryJob();
                    ;
                    listaTareas = searchTareaService.findByPhone(
                            agentController.getAgent() ,
                            request.getSearchText());
                } else if (request.getSearchOption().equals(SearchTaskRequest.CLIENT)) {
                    listaTareas = searchTareaService.findByContractNumber(
                            agentController.getAgent() ,
                            request.getSearchText());
                } else {
                    listaTareas = new ArrayList<Tarea>();
                }
                response = new SearchTareaResponse( listaTareas );
            } catch (Exception e) {
                response = new SearchTareaResponse(processException(e, SERVICE_MESSAGE));
            }
        }
        return response;
    }

    public SearchTaskRequest getLastSearchTareaRequest() {
        return lastSearchTareaRequest;
    }
}
