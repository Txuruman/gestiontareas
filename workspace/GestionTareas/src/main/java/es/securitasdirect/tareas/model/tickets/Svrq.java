package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SVRQ")
@XmlAccessorType(XmlAccessType.FIELD)
public class Svrq {
    @XmlAttribute
    String makeSVRQ;
    @XmlAttribute
    String idTec;
    @XmlAttribute
    String insBoli;

    public String getMakeSVRQ() {
        return makeSVRQ;
    }

    public void setMakeSVRQ(String makeSVRQ) {
        this.makeSVRQ = makeSVRQ;
    }

    public String getIdTec() {
        return idTec;
    }

    public void setIdTec(String idTec) {
        this.idTec = idTec;
    }

    public String getInsBoli() {
        return insBoli;
    }

    public void setInsBoli(String insBoli) {
        this.insBoli = insBoli;
    }

}
