package es.securitasdirect.tareas.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

//Esto funciona para meter un componente en la sesion
//@Component
//@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Agent {

    private String idAgent;

    public String getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }
}
