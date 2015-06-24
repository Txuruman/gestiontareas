package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CLCOD")
@XmlAccessorType(XmlAccessType.FIELD)
public class Clcod {
    @XmlAttribute
    String codKey3;
    @XmlAttribute
    String codKey4;

    public String getCodKey3() {
        return codKey3;
    }

    public void setCodKey3(String codKey3) {
        this.codKey3 = codKey3;
    }

    public String getCodKey4() {
        return codKey4;
    }

    public void setCodKey4(String codKey4) {
        this.codKey4 = codKey4;
    }
}
