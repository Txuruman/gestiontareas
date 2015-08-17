package es.securitasdirect.tareas.service;

import org.wso2.ws.dataservice.SPAVISOSOPERACIONESPortType;
import wsticketsv2.WsTickets;
import wsticketsv2.WsTicketsSoap;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 *
 */
@Named(value = "avisoService")
@Singleton
public class AvisoService {

    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;
    protected WsTicketsSoap wsTickets;

    public void createTicket(){
        wsTickets.create("");
    }


    public void updateTicket(){
        wsTickets.updateTicket("");
    }

    public void finalizeTicket(){
        //wsTickets.finalizeTicket("");
    }

}
