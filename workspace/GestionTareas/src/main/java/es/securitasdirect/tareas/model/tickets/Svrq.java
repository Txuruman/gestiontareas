package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "SVRQ")
@XmlAccessorType(XmlAccessType.FIELD)
public class Svrq {

    @XmlElementWrapper( name="LISTADE" )
    @XmlElement(name = "item")
    List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

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
