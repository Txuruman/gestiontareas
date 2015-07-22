package es.securitasdirect.tareas.web.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Clase base para las llamadas de aplazar Tarea
 */
public class PostponeRequest {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "CET") //TODO Falta la hora
    protected Date delayDate;

    protected String recallType;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PostponeRequest{");
        sb.append("delayDate=").append(delayDate);
        sb.append(", recallType='").append(recallType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
