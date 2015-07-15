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
public class MaintenanceTaskCreateRequest extends BaseRequest {

    private TareaMantenimiento tarea;

    private String prueba;

    public MaintenanceTaskCreateRequest() {
    }

    public MaintenanceTaskCreateRequest(TareaMantenimiento tarea) {
        this.tarea = tarea;
    }

    public TareaMantenimiento getTarea() {
        return tarea;
    }

    public void setTarea(TareaMantenimiento tarea) {
        this.tarea = tarea;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    @Override
    public String toString() {
        return "MaintenanceTaskCreateRequest{" +
                "tarea=" + tarea +
                ", prueba='" + prueba + '\'' +
                '}';
    }
}
