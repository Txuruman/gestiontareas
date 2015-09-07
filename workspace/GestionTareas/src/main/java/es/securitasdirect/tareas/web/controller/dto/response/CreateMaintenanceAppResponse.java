package es.securitasdirect.tareas.web.controller.dto.response;

import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

/**
 * Respuesta del servicio de Split para saber con que aplicación externa abrir la pantalla de Crear Mantenimiento;
 */
public class CreateMaintenanceAppResponse  extends BaseResponse{

    /** Alguno de los valores de SplitService */
    private String app;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
