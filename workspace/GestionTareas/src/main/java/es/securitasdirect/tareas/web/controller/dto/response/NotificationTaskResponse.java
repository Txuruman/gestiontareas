package es.securitasdirect.tareas.web.controller.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

import java.util.List;

/**
 * Created by Roberto López 17/07/2015
 * Almacena los campos propios de la respuesta para tarea de aviso
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class NotificationTaskResponse extends TareaResponse {

    private List<Pair> tipoAvisoList;

    private List<Pair> motivoAvisoList;

    private List<Pair> closingList;

    private List<Pair> closingListAditionalData;

    public List<Pair> getMotivoAvisoList() {
        return motivoAvisoList;
    }

    public void setMotivoAvisoList(List<Pair> motivoAvisoList) {
        this.motivoAvisoList = motivoAvisoList;
    }

    public List<Pair> getClosingList() {
        return closingList;
    }

    public void setClosingList(List<Pair> closingList) {
        this.closingList = closingList;
    }

    public List<Pair> getClosingListAditionalData() {
        return closingListAditionalData;
    }

    public void setClosingListAditionalData(List<Pair> closingListAditionalData) {
        this.closingListAditionalData = closingListAditionalData;
    }

    public List<Pair> getTipoAvisoList() {

        return tipoAvisoList;
    }

    public void setTipoAvisoList(List<Pair> tipoAvisoList) {
        this.tipoAvisoList = tipoAvisoList;
    }

    @Override
    public String toString() {
        return "NotificationTaskResponse{" +
                "tipoAvisoList=" + tipoAvisoList +
                ", motivoAvisoList=" + motivoAvisoList +
                ", closingList=" + closingList +
                ", closingListAditionalData=" + closingListAditionalData +
                '}';
    }
}
