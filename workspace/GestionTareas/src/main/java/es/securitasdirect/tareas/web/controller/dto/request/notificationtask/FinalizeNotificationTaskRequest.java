package es.securitasdirect.tareas.web.controller.dto.request.notificationtask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

/**
 * Datos de Cierre para una tarea tipo Aviso.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FinalizeNotificationTaskRequest extends BaseRequest {

    private TareaAviso task;

    /*
    Ejemplos de los JSON que devuelve la aplicacion de crear mantenimiento:
    {"AppointmentNumber":"","Status":2,"Message":"Error generico creando actividad en TOA"}
     */

    /**
     * Resultado de la ventana emergente de mantenimiento.
     * Estos datos no se rellenan cuando no se viene de mantenimiento.
     */
    private boolean finalizedByCreateMaintenance = false;
    private Integer appointmentNumber; //TODO Pedniente saber si es numerico o no, debería de serlo
    private Integer status;
    private String message;


    public FinalizeNotificationTaskRequest() {
    }

    public FinalizeNotificationTaskRequest(TareaAviso task) {
        this.task = task;
    }

    public TareaAviso getTask() {
        return task;
    }

    public void setTask(TareaAviso task) {
        this.task = task;
    }

    public Integer getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(Integer appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFinalizedByCreateMaintenance() {
        return finalizedByCreateMaintenance;
    }

    public void setFinalizedByCreateMaintenance(boolean finalizedByCreateMaintenance) {
        this.finalizedByCreateMaintenance = finalizedByCreateMaintenance;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FinalizeNotificationTaskRequest{");
        sb.append("task=").append(task);
        sb.append(", finalizedByCreateMaintenance=").append(finalizedByCreateMaintenance);
        sb.append(", appointmentNumber='").append(appointmentNumber).append('\'');
        sb.append(", status=").append(status);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
