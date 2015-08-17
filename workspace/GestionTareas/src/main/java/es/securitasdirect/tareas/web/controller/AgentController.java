package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

@Controller
@Scope("session")
@RequestMapping("/agent")
public  class AgentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentController.class);

    @Inject
    protected MessageUtil messageUtil;

    private Agent agent;

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody Agent getAgent() {
        return agent;
    }

    public Agent loadAgentFromIWS(Map<String, String> parametersMap) {
        agent = new Agent();
        //TODO CARGAR PARAMETROS DE EXTERNAL PARAMS
        return agent;
    }
}
