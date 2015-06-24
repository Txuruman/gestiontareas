package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OPCOD")
@XmlAccessorType(XmlAccessType.FIELD)
public class Opcod {

    @XmlAttribute
    String codKey1;
    @XmlAttribute
    String codKey2;

    public String getCodKey1() {
        return codKey1;
    }

    public void setCodKey1(String codKey1) {
        this.codKey1 = codKey1;
    }

    public String getCodKey2() {
        return codKey2;
    }

    public void setCodKey2(String codKey2) {
        this.codKey2 = codKey2;
    }
}
