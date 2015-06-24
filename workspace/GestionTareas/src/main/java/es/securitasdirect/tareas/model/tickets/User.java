package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "USER")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    @XmlAttribute
    String idUser;
    @XmlAttribute
    String idUsidCountry;
    @XmlAttribute
    String idLanguage;
    @XmlAttribute
    String t;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUsidCountryer() {
        return idUsidCountry;
    }

    public void setIdUsidCountry(String idUsidCountryer) {
        this.idUsidCountry = idUsidCountryer;
    }

    public String getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(String idLanguage) {
        this.idLanguage = idLanguage;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
}
