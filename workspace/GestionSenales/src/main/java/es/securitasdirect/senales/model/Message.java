package es.securitasdirect.senales.model;

import es.securitasdirect.senales.model.params.PARAMSType;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Model class from the messages, wraps the real model of the JMS XML
 *
 */
@XmlRootElement(name = "message")
public class Message {

	/** Entry date of the message in the process, used to discard old message */
	private Date entryDate;

	private PARAMSType paramsType;

    public Message() {
    }

	public Message(PARAMSType paramsType) {
		entryDate = new Date();
		this.paramsType = paramsType;
	}

     public Integer getId() {
        if (paramsType!=null && paramsType.getCIBB()!=null && paramsType.getCIBB().getEVENTS()!=null) {
            return paramsType.getCIBB().getEVENTS().getInsNumberE();
        } else {
            return null;
        }

    }

	public PARAMSType getParamsType() {
		return paramsType;
	}

	public void setParamsType(PARAMSType paramsType) {
		this.paramsType = paramsType;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Message{");
		sb.append("id=").append(getId()).append(", ");
		sb.append("entryDate=").append(entryDate).append(", ");
		sb.append("paramsType=").append(paramsType);
		sb.append('}');
		return sb.toString();
	}
}
