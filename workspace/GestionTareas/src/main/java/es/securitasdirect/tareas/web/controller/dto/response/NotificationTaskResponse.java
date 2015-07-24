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


}
