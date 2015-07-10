package es.securitasdirect.tareas.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
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
