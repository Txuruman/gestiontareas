package es.securitasdirect.tareas.web.controller.dto.request.exceltask.marketingsurvey;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.MarketingSurveyTask;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DiscardMarketingSurveyTaskRequest extends BaseRequest {

    private MarketingSurveyTask task;

    private String prueba;

    public DiscardMarketingSurveyTaskRequest() {
    }

    public DiscardMarketingSurveyTaskRequest(MarketingSurveyTask task) {
        this.task = task;
    }

    public MarketingSurveyTask getTask() {
        return task;
    }

    public void setTask(MarketingSurveyTask task) {
        this.task = task;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    @Override
    public String toString() {
        return "DiscardMarketingSurveyTaskRequest{" +
                "task=" + task +
                ", prueba='" + prueba + '\'' +
                '}';
    }
}
