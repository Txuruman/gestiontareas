package es.securitasdirect.senales.model;

import es.securitasdirect.senales.model.cibb.CIBB;


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

	private CIBB cibb;

    public Message() {
    }

	public Message(CIBB cibb) {
		entryDate = new Date();
		this.cibb = cibb;
	}

     public Integer getId() {
        if (cibb.getEVENTS()!=null) {
            return cibb.getEVENTS().getInsNumberE();
        } else {
            return null;
        }
    }


    /**
     * Returns the message type.
     * @return
     */
    public String getType(){
        if (cibb!=null &&  cibb.getEVENTS()!=null&& cibb.getEVENTS().getEVENT()!=null) {
            return cibb.getEVENTS().getEVENT().getId();
        }else{
            return null;
        }
    }

    /**
     * Returns the language location key
     */
    public String getLanguageLocationKey(){
        if(cibb!=null && cibb.getPROPS()!=null){
            return cibb.getPROPS().getPais();
        }else{
            return null;
        }
    }



	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public CIBB getCibb() {
		return cibb;
	}

	public void setCibb(CIBB cibb) {
		this.cibb = cibb;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Message{");
		sb.append("id=").append(getId()).append(", ");
		sb.append("entryDate=").append(entryDate).append(", ");
		sb.append("cibb=").append(cibb);
		sb.append('}');
		return sb.toString();
	}
}
