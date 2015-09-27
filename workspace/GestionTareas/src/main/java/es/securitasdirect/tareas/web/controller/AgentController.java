package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.InfopointService;
import es.securitasdirect.tareas.service.SecurityService;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.AgentResponse;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

/**
 * Session controller para la información del agente.
 * http://stackoverflow.com/questions/21286675/scope-session-is-not-active-for-the-current-thread-illegalstateexception-no
 */
@Controller
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequestMapping("/agent")
public class AgentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentController.class);

    @Inject
    protected MessageUtil messageUtil;
    @Inject
    protected InfopointService infopointService;
    @Autowired
    protected SecurityService securityService;

    private Agent agent;

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    Agent getAgent() {
        return agent;
    }

    public Agent loadAgentFromIWS(Map<String, String> parametersMap) {
        //Se llama varias veces a cargar el agente, si ya está cargado no lo sobreescribimos
        if (!isLogged()) {
            //Validate security, se hace para todas las operaciones
            securityService.validateAuthenticationRequest(parametersMap.get(ExternalParams.AUTH_SIGNATURE),parametersMap.get(ExternalParams.AUTH_REQUEST_DATE), parametersMap.get(ExternalParams.AUTH_IPADDRESS), parametersMap.get(ExternalParams.AUTH_CONNID));

            LOGGER.debug("Loading agent from params {}", parametersMap);
            agent = new Agent();
            agent.setAgentCountryJob(parametersMap.get(ExternalParams.AGENT_COUTRY_JOB));
            agent.setDesktopDepartment(parametersMap.get(ExternalParams.DESKTOP_DEPARTMENT));
            agent.setIdAgent(parametersMap.get(ExternalParams.identificadorAgente));
            agent.setAgentIBS(parametersMap.get(ExternalParams.AGENT_IBS));

            agent.setAgentGroupOutService(parametersMap.get(ExternalParams.AGENT_GROUP_OUT_SERVICE));
            agent.setAgentGroupSD(parametersMap.get(ExternalParams.AGENT_GROUP_SD));

            agent.setAgentPlace(parametersMap.get(ExternalParams.AGENT_PLACE));
            agent.setAgentUserSD(parametersMap.get(ExternalParams.AGENT_USER_SD));
            agent.setAuth_connid(parametersMap.get(ExternalParams.AUTH_CONNID));
            agent.setAuth_ipAddress(parametersMap.get(ExternalParams.AUTH_IPADDRESS));
            agent.setAuth_requestDate(parametersMap.get(ExternalParams.AUTH_REQUEST_DATE));
            agent.setAuth_signature(parametersMap.get(ExternalParams.AUTH_SIGNATURE));
            agent.setCallingListManagedDesktop(parametersMap.get(ExternalParams.CALLING_LIST_MANAGED_DESK));
            agent.setConnid(parametersMap.get(ExternalParams.CONN_ID));
            agent.setCurrentLanguage(parametersMap.get(ExternalParams.CURRENT_LANGUAGE));

            agent.setInteractionDirection(parametersMap.get(ExternalParams.INTERACTION_DIRECTION));
            agent.setInteractionType(parametersMap.get(ExternalParams.INTERACTION_TYPE));
            agent.setInteractionId(parametersMap.get(ExternalParams.INTERACTION_ID));

            LOGGER.debug("Loaded Agent info {}", agent);
        }
        return agent;
    }

    /**
     * Indica si el agente ha enviado suficiente información para considerarlo registado
     */
    public boolean isLogged() {
        return agent != null && agent.getAgentCountryJob() != null && !agent.getAgentCountryJob().isEmpty();
    }

    @RequestMapping(value = "/prepareInfopointSession", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse prepareInfopointSession() {
        AgentResponse response = new AgentResponse();
        response.setAgent(agent);

        try {
            //Si el agente no tiene session la creamos
            if (agent.getInfopointSession() == null) {
                infopointService.createSession(agent);
            }

            //Validamos que el agente tiene permisos de crear mantenimiento
            if (infopointService.isAllowedCreateMaintenance(agent)) {
                //El agente tiene permisos
                response.info(messageUtil.getProperty("agent.sessionCreated"));
            } else {
                //No tiene permisos
                response.danger(messageUtil.getProperty("agent.noCeatemaintenanceRight"));
                //Quitamos la session del agente
                infopointService.closeSession(agent);
            }

        } catch (Exception e) {
            LOGGER.error("Error preparing Infopoint session", e);
            return processException(e) ;
        }
        return response;
    }

    @RequestMapping(value = "/closeInfopointSession", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    BaseResponse closeInfopointSession() {
        AgentResponse response = new AgentResponse();
        response.setAgent(agent);
        try {
            //Quitamos la session del agente
            infopointService.closeSession(agent);
            response.info(messageUtil.getProperty("agent.sessionClosed"));
        } catch (Exception e){
            LOGGER.error("Error closing Infopoint session", e);
            return processException(e) ;
        }
        return response;
    }
}
