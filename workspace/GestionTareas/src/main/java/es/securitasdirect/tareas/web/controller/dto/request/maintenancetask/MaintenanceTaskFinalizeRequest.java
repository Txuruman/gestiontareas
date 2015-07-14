package es.securitasdirect.tareas.web.controller.dto.request.maintenancetask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class MaintenanceTaskFinalizeRequest extends BaseResponse {

    private Tarea tarea;

    public MaintenanceTaskFinalizeRequest() {
    }

    public MaintenanceTaskFinalizeRequest(Tarea tarea) {
        this.tarea = tarea;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }


    @Override
    public String toString() {
        return super.toString() + "TareaResponse{" +
                "tarea=" + tarea +
                '}';
    }
}
