package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.tickets.operations.CreateTicket;
import es.securitasdirect.tareas.support.XmlMarshaller;
import org.wso2.ws.dataservice.SPAVISOSOPERACIONESPortType;
import wsticketsv2.WsTickets;
import wsticketsv2.WsTicketsSoap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 *
 */
@Named(value = "avisoService")
@Singleton
public class AvisoService {

    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;
    @Inject
    protected WsTicketsSoap wsTickets;
    @Inject
    protected XmlMarshaller xmlMarshaller;

    public void createTicket(){
        CreateTicket createTicket = new CreateTicket();
        //TODO COPIAR TODO DEDE EL TEST DE XMLSERVICETEST Y MODIFICAR CON EL DISEÑO TECNICO

        String xmlCreateTicket = xmlMarshaller.marshalObject(createTicket);

        wsTickets.create(xmlCreateTicket);
    }


    public void updateTicket(){
        wsTickets.updateTicket("");
    }

    public void finalizeTicket(){
        //wsTickets.finalizeTicket("");
    }

}
