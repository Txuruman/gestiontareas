package es.securitasdirect.senales.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * General Info of the status of the app
 */
public class HappyData {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date upSince;
    protected  Integer successfulMessages;
    protected  Integer errorMessages;
    protected  Integer inWorkingHoursMessages;
    protected  Integer outWorkingHousMessages;
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

    public Integer getInWorkingHoursMessages() {
        return inWorkingHoursMessages;
    }

    public void setInWorkingHoursMessages(Integer inWorkingHoursMessages) {
        this.inWorkingHoursMessages = inWorkingHoursMessages;
    }

    public Integer getOutWorkingHousMessages() {
        return outWorkingHousMessages;
    }

    public void setOutWorkingHousMessages(Integer outWorkingHousMessages) {
        this.outWorkingHousMessages = outWorkingHousMessages;
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
        sb.append(", inWorkingHoursMessages=").append(inWorkingHoursMessages);
        sb.append(", outWorkingHousMessages=").append(outWorkingHousMessages);
        sb.append('}');
        return sb.toString();
    }
}
