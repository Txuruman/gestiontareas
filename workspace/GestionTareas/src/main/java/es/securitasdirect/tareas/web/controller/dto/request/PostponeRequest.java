package es.securitasdirect.tareas.web.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

import java.util.Date;

/**
 * Clase base para las llamadas de aplazar Tarea
 */
public class PostponeRequest extends BaseRequest{

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    protected Date delayDate;

    protected String recallType;
    
    protected String motive;

    public Date getDelayDate() {
        return delayDate;
    }

    public void setDelayDate(Date delayDate) {
        this.delayDate = delayDate;
    }

    public String getRecallType() {
        return recallType;
    }

    public void setRecallType(String recallType) {
        this.recallType = recallType;
    }

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	@Override
	public String toString() {
		return "PostponeRequest [delayDate=" + delayDate + ", recallType=" + recallType + ", motive=" + motive + "]";
	}

   
}
