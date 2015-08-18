package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
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
        //Se llama varias veces a cargar el agente, si ya está cargado no lo sobreescribimos
        if (!isLogged()) {
            agent = new Agent();
            agent.setAgentCountryJob(parametersMap.get(ExternalParams.AGENT_COUTRY_JOB));
            agent.setDesktopDepartment(parametersMap.get(ExternalParams.DESKTOP_DEPARTMENT));

            agent.setAgentGroupOutService(parametersMap.get(ExternalParams.AGENT_GROUP_OUT_SERVICE));
            agent.setAgentGroupSD(parametersMap.get(ExternalParams.AGENT_GROUP_SD));
            agent.setAgentIBS(parametersMap.get(ExternalParams.AGENT_IBS));
            agent.setAgentPlace(parametersMap.get(ExternalParams.AGENT_PLACE));
            agent.setAgentUserSD(parametersMap.get(ExternalParams.AGENT_USER_SD));
            agent.setAuth_connid(parametersMap.get(ExternalParams.AUTH_CONNID));
            agent.setAuth_ipAddress(parametersMap.get(ExternalParams.AUTH_IPADDRESS));
            agent.setAuth_requestDate(parametersMap.get(ExternalParams.AUTH_REQUEST_DATE));
            agent.setAuth_signature(parametersMap.get(ExternalParams.AUTH_SIGNATURE));
            agent.setCallingListManagedDesktop(parametersMap.get(ExternalParams.CALLING_LIST_MANAGED_DESK));
            agent.setConnid(parametersMap.get(ExternalParams.CONN_ID));
            agent.setCurrentLanguage(parametersMap.get(ExternalParams.CURRENT_LANGUAGE));
            agent.setIdAgent(parametersMap.get(ExternalParams.identificadorAgente));
            agent.setInteractionDirection(parametersMap.get(ExternalParams.INTERACTION_DIRECTION));
            agent.setInteractionType(parametersMap.get(ExternalParams.INTERACTION_TYPE));

            LOGGER.debug("Loaded Agent info {}", agent);
        }
        return agent;
    }

    /** Indica si el agente ha enviado suficiente información para considerarlo registado */
    public boolean isLogged(){
        return agent.getAgentCountryJob()!=null && ! agent.getAgentCountryJob().isEmpty();
    }
}
