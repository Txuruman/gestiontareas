package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ASGTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class Asgto {

    @XmlAttribute
    String idAsg;
    @XmlAttribute
    String idUser;

    public String getIdAsg() {
        return idAsg;
    }

    public void setIdAsg(String idAsg) {
        this.idAsg = idAsg;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
