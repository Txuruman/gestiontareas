package es.securitasdirect.tareas.web.controller.dto.request.maintenancetask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class MaintenanceTaskFinalizeRequest extends BaseRequest {

    private TareaMantenimiento task;

    public MaintenanceTaskFinalizeRequest() {
    }

    public TareaMantenimiento getTask() {
        return task;
    }

    public void setTask(TareaMantenimiento task) {
        this.task = task;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MaintenanceTaskFinalizeRequest{");
        sb.append("task=").append(task);
        sb.append('}');
        return sb.toString();
    }
}
