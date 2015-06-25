package es.securitasdirect.tareas.model.tickets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "REQ")
@XmlAccessorType(XmlAccessType.FIELD)
public class Req {


    @XmlAttribute
    public String idReq;
    @XmlAttribute
    public String reqName;
    @XmlAttribute
    public String reqLname1;
    @XmlAttribute
    public String reqLname2;
    @XmlAttribute
    public String reqCif;
    @XmlAttribute
    public String reqEmpl;

    public String getIdReq() {
        return idReq;
    }

    public void setIdReq(String idReq) {
        this.idReq = idReq;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getReqLname1() {
        return reqLname1;
    }

    public void setReqLname1(String reqLname1) {
        this.reqLname1 = reqLname1;
    }

    public String getReqLname2() {
        return reqLname2;
    }

    public void setReqLname2(String reqLname2) {
        this.reqLname2 = reqLname2;
    }

    public String getReqCif() {
        return reqCif;
    }

    public void setReqCif(String reqCif) {
        this.reqCif = reqCif;
    }

    public String getReqEmpl() {
        return reqEmpl;
    }

    public void setReqEmpl(String reqEmpl) {
        this.reqEmpl = reqEmpl;
    }



}
