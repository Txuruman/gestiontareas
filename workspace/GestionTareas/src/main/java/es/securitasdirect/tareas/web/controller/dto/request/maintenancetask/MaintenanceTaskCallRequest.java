package es.securitasdirect.tareas.web.controller.dto.request.maintenancetask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Created by Pedro on 24/09/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class MaintenanceTaskCallRequest {


    private TareaMantenimiento task;


    public MaintenanceTaskCallRequest() {
    }

    public MaintenanceTaskCallRequest(TareaMantenimiento task) {
        this.task = task;
    }

    public TareaMantenimiento getTask() {
        return task;
    }

    public void setTask(TareaMantenimiento task) {
        this.task = task;
    }


    @Override
    public String toString() {
        return "MaintenanceTaskCallRequest{" +
                "task=" + task +
                '}';
    }
}
