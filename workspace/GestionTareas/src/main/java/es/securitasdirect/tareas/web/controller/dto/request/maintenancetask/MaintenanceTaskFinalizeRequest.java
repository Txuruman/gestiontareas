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
    
    /**
     * Último teléfono al que se ha llamado
     */
    private Integer lastCalledPhone;

    public MaintenanceTaskFinalizeRequest() {
    }

    public TareaMantenimiento getTask() {
        return task;
    }

    public void setTask(TareaMantenimiento task) {
        this.task = task;
    }
    
    
    public Integer getLastCalledPhone() {
		return lastCalledPhone;
	}

	public void setLastCalledPhone(Integer lastCalledPhone) {
		this.lastCalledPhone = lastCalledPhone;
	}

	@Override
	public String toString() {
		return "MaintenanceTaskFinalizeRequest [task=" + task + ", lastCalledPhone=" + lastCalledPhone + "]";
	}

	
}
