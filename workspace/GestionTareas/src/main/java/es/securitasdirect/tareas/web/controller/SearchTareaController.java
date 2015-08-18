package es.securitasdirect.tareas.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.service.SearchTareaService;
import es.securitasdirect.tareas.web.controller.dto.SearchTareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.searchtask.SearchTaskRequest;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/searchtarea")
public class SearchTareaController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTareaController.class);

    @Inject
    private SearchTareaService searchTareaService;
    @Inject
    private AgentController agentController;
    @Inject
    protected MessageUtil messageUtil;


    @RequestMapping
    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ///Redirect to buscartarea.html
        ModelAndView mv = new ModelAndView("buscartarea");

        //Crear un mapa con los parámetros
        Map<String, String> parametersMap = createParameterMap(hsr);
        //Crear el objeto de sesion con los datos del agent
        Agent agent = agentController.loadAgentFromIWS(parametersMap);

        return mv;
    }


    @RequestMapping(value = "/query", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    SearchTareaResponse query(@RequestBody SearchTaskRequest request) {
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
                            agentController.getAgent().getDesktopDepartment(),
                            agentController.getAgent().getIdAgent(),
                            agentController.getAgent().getAgentCountryJob(),
                            request.getSearchText());
                } else if (request.getSearchOption().equals(SearchTaskRequest.CLIENT)) {
                    listaTareas = searchTareaService.findByCustomer(
                            agentController.getAgent().getDesktopDepartment(),
                            agentController.getAgent().getIdAgent(),
                            agentController.getAgent().getAgentCountryJob(),
                            request.getSearchText());
                } else {
                    listaTareas = new ArrayList<Tarea>();
                }
                response = new SearchTareaResponse(processSuccessMessages(listaTareas, SERVICE_MESSAGE));
                response.setTaskList(listaTareas);
            } catch (Exception e) {
                response = new SearchTareaResponse(processException(e, SERVICE_MESSAGE));
            }
        }
        return response;
    }

}
