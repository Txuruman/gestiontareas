package es.securitasdirect.senales.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

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
}
