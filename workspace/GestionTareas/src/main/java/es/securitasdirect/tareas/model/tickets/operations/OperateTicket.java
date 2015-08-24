package es.securitasdirect.tareas.model.tickets.operations;

import es.securitasdirect.tareas.model.tickets.Svrq;
import es.securitasdirect.tareas.model.tickets.Ticket;
import es.securitasdirect.tareas.model.tickets.User;

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
public class OperateTicket {


    /*
     * USER
     */
    @XmlElement(name = "USER")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
     * TICKET
     */
    @XmlElement(name = "TICKET")
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /*
     * SVRQ
     */
    @XmlElement(name = "SVRQ")
    private Svrq svrq;

    public Svrq getSvrq() {
        return svrq;
    }

    public void setSvrq(Svrq svrq) {
        this.svrq = svrq;
    }

}
