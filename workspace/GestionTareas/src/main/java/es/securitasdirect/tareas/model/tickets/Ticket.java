package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.*;

/**
 * TODO COMENTAR
 *
 * https://jaxb.java.net/tutorial/
 */
@XmlRootElement(name = "TICKET")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket {

    @XmlAttribute
    private String numInst;

    public String getNumInst() {
        return numInst;
    }

    public void setNumInst(String numInst) {
        this.numInst = numInst;
    }
}
