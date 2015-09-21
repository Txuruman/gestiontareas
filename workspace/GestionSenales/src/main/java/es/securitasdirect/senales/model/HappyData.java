package es.securitasdirect.senales.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * General Info of the status of the app
 */
public class HappyData {

    /* http://wiki.fasterxml.com/JacksonFAQDateHandling */
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone="CET")
    private Date upSince;
    /** Mensajes procesados correctamente */
    protected  Integer successfulMessages;
    /** Mensajes con errores */
    protected  Integer errorMessages;
    /** Mensajes recibidos con agentes logados */
    protected  Integer withLoggedInAgentsMessages;
    /** Mensajes recibidos sin agentes logados */
    protected  Integer withoutAgentsMessages;
    protected Map<String,String> jmsReaderStatus = new HashMap<String, String>();

    public Date getUpSince() {
        return upSince;
    }

    public void setUpSince(Date upSince) {
        this.upSince = upSince;
    }

    public Integer getSuccessfulMessages() {
        return successfulMessages;
    }

    public void setSuccessfulMessages(Integer successfulMessages) {
        this.successfulMessages = successfulMessages;
    }

    public Integer getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Integer errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Integer getWithLoggedInAgentsMessages() {
        return withLoggedInAgentsMessages;
    }

    public void setWithLoggedInAgentsMessages(Integer withLoggedInAgentsMessages) {
        this.withLoggedInAgentsMessages = withLoggedInAgentsMessages;
    }

    public Integer getWithoutAgentsMessages() {
        return withoutAgentsMessages;
    }

    public void setWithoutAgentsMessages(Integer withoutAgentsMessages) {
        this.withoutAgentsMessages = withoutAgentsMessages;
    }

    public void addJmsReaderStatus(String name, boolean ok, String description) {
        jmsReaderStatus.put(name, (ok?"Ok":"Error") + " " + (description==null?"":description) );
    }

    public Map<String, String> getJmsReaderStatus() {
        return jmsReaderStatus;
    }

    public void setJmsReaderStatus(Map<String, String> jmsReaderStatus) {
        this.jmsReaderStatus = jmsReaderStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HappyData{");
        sb.append("upSince=").append(upSince);
        sb.append(", successfulMessages=").append(successfulMessages);
        sb.append(", errorMessages=").append(errorMessages);
        sb.append(", withLoggedInAgentsMessages=").append(withLoggedInAgentsMessages);
        sb.append(", withoutAgentsMessages=").append(withoutAgentsMessages);
        sb.append('}');
        return sb.toString();
    }
}
