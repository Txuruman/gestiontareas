package es.securitasdirect.tareas.web.controller.dto.request.exceltask.anothercampaigns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.MarketingSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaOtrasCampanas;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DiscardAnotherCampaignsTaskRequest extends BaseRequest {

    private TareaOtrasCampanas task;

    private String prueba;

    public DiscardAnotherCampaignsTaskRequest() {
    }

    public DiscardAnotherCampaignsTaskRequest(TareaOtrasCampanas task) {
        this.task = task;
    }

    public TareaOtrasCampanas getTask() {
        return task;
    }

    public void setTask(TareaOtrasCampanas task) {
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
        return "DiscardAnotherCampaignsTaskRequest{" +
                "task=" + task +
                ", prueba='" + prueba + '\'' +
                '}';
    }
}
