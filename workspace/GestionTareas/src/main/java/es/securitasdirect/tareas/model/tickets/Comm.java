package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "COMM")
@XmlAccessorType(XmlAccessType.FIELD)
public class Comm {
    @XmlAttribute
    String name;
    @XmlAttribute
    String lName1;
    @XmlAttribute
    String lName2;
    @XmlAttribute
    String inChannel;
    @XmlAttribute
    String value;
    @XmlAttribute
    String coment;
    @XmlAttribute
    String outChannel;
    @XmlAttribute
    String from;
    @XmlAttribute
    String to;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getlName1() {
        return lName1;
    }

    public void setlName1(String lName1) {
        this.lName1 = lName1;
    }

    public String getlName2() {
        return lName2;
    }

    public void setlName2(String lName2) {
        this.lName2 = lName2;
    }

    public String getInChannel() {
        return inChannel;
    }

    public void setInChannel(String inChannel) {
        this.inChannel = inChannel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getOutChannel() {
        return outChannel;
    }

    public void setOutChannel(String outChannel) {
        this.outChannel = outChannel;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
