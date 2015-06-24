package es.securitasdirect.tareas.model.tickets.operations;

import es.securitasdirect.tareas.model.tickets.Ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO COMENTAR
 *
 * https://jaxb.java.net/tutorial/
 */
@XmlRootElement(name = "DATA")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateTicket {


    @XmlElement(name = "TICKET")
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
