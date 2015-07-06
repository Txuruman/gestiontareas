package es.securitasdirect.tareas.web.controller.dto;

import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * Created by Javier Naval on 06/07/2015.
 */
public class TareaResponse extends BaseResponse {

    private Tarea tarea;

    public TareaResponse() {
    }

    public TareaResponse(Tarea tarea) {
        this.tarea = tarea;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }
}
