package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ITEMS")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    @XmlAttribute
    String idType;
    @XmlAttribute
    String idProblem;
    @XmlAttribute
    String count;
    @XmlAttribute
    String idItemIBS;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdProblem() {
        return idProblem;
    }

    public void setIdProblem(String idProblem) {
        this.idProblem = idProblem;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIdItemIBS() {
        return idItemIBS;
    }

    public void setIdItemIBS(String idItemIBS) {
        this.idItemIBS = idItemIBS;
    }
}
