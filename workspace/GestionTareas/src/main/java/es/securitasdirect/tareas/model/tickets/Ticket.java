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
    /*-------SUB ELEMENTOS DE TICKET-----*/

    /* -------REQ-----------------------*/
    @XmlElement(name = "REQ")
    private Req req;

    public Req getReq() {
        return req;
    }

    public void setReq(Req req) {
        this.req = req;
    }
    /*----ASGTO---------*/
    @XmlElement(name = "ASGTO")
    private Asgto asgto;

    public Asgto getAsgto() {
        return asgto;
    }

    public void setAsgto(Asgto asgto) {
        this.asgto = asgto;
    }

    /*----COMM----------*/
    @XmlElement(name = "COMM")
    private Comm comm;

    public Comm getComm() {
        return comm;
    }

    public void setComm(Comm comm) {
        this.comm = comm;
    }
    /*----OPCOD--------*/
    @XmlElement(name = "OPCOD")
    private Opcod opcod;

    public Opcod getOpcod() {
        return opcod;
    }

    public void setOpcod(Opcod opcod) {
        this.opcod = opcod;
    }
    /*-----CLCOD-------*/
    @XmlElement(name = "CLCOD")
    private Clcod clcod;

    public Clcod getClcod() {
        return clcod;
    }

    public void setClcod(Clcod clcod) {
        this.clcod = clcod;
    }

    /* -------- ATRIBUTOS TICKET -------*/
    @XmlAttribute
    private String numInst;
    @XmlAttribute
    private String observ;
    @XmlAttribute
    private String codZIP;
    @XmlAttribute
    private String closeTicket;
    @XmlAttribute
    private String dataAditional;
    @XmlAttribute
    private String noteClose;
    @XmlAttribute
    private String morDebt;
    @XmlAttribute
    private String typePanel;
    public String getNumInst() {
        return numInst;
    }

    public void setNumInst(String numInst) {
        this.numInst = numInst;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public String getCodZIP() {
        return codZIP;
    }

    public void setCodZIP(String codZIP) {
        this.codZIP = codZIP;
    }

    public String getCloseTicket() {
        return closeTicket;
    }

    public void setCloseTicket(String closeTicket) {
        this.closeTicket = closeTicket;
    }

    public String getDataAditional() {
        return dataAditional;
    }

    public void setDataAditional(String dataAditional) {
        this.dataAditional = dataAditional;
    }

    public String getNoteClose() {
        return noteClose;
    }

    public void setNoteClose(String noteClose) {
        this.noteClose = noteClose;
    }

    public String getMorDebt() {
        return morDebt;
    }

    public void setMorDebt(String morDebt) {
        this.morDebt = morDebt;
    }

    public String getTypePanel() {
        return typePanel;
    }

    public void setTypePanel(String typePanel) {
        this.typePanel = typePanel;
    }
}


