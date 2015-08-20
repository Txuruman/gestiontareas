package es.securitasdirect.tareas.web.controller.dto.request.exceltask.anothercampaigns;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.tareaexcel.MaintenanceSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaOtrasCampanas;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FinalizeAnotherCampaignsTaskRequest extends BaseRequest {

    private TareaOtrasCampanas task;


    public FinalizeAnotherCampaignsTaskRequest() {
    }

    public FinalizeAnotherCampaignsTaskRequest(TareaOtrasCampanas task) {
        this.task = task;
    }

    public TareaOtrasCampanas getTask() {
        return task;
    }

    public void setTask(TareaOtrasCampanas task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "FinalizeAnotherCampaignsTaskRequest{" +
                "task=" + task +
                '}';
    }
}
