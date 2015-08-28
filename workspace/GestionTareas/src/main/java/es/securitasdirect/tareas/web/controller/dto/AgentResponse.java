package es.securitasdirect.tareas.web.controller.dto;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * Response with Agent Info
 */
public class AgentResponse extends BaseResponse {

    private Agent agent;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
