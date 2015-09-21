package es.securitasdirect.tareas.web.controller.dto.response;

import es.securitasdirect.tareas.service.model.FinalizeMaintenanceTaskResult;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * Respuesta del servicio Finalizar Tarea Tipo Mantenimiento
 */
public class FinalizeMaintenanceTaskResponse extends BaseResponse{

    /** Resultado de finalizar tarea manteniniento  */
    private FinalizeMaintenanceTaskResult result;

    public FinalizeMaintenanceTaskResult getResult() {
        return result;
    }

    public void setResult(FinalizeMaintenanceTaskResult result) {
        this.result = result;
    }
}
