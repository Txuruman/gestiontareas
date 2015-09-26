package es.securitasdirect.tareas.web.controller.dto.request.notificationtask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Request para la consulta de Tipos de Cierre de Tarea Tipo Aviso.
 * Recive el Tipo 1 y el Motivo 1
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ClosingTypeRequest extends BaseRequest {

    Integer idType;

    Integer reasonId;
    
    TareaAviso tarea;

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

	public TareaAviso getTarea() {
		return tarea;
	}

	public void setTarea(TareaAviso tarea) {
		this.tarea = tarea;
	}

	@Override
	public String toString() {
		return "ClosingTypeRequest [idType=" + idType + ", reasonId=" + reasonId + ", tarea=" + tarea + "]";
	}

   
}
