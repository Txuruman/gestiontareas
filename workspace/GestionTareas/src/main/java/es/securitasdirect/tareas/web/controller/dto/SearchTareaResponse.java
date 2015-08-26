package es.securitasdirect.tareas.web.controller.dto;

import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto on 13/07/2015.
 */
public class SearchTareaResponse extends BaseResponse {

    public SearchTareaResponse(){

    }

    public SearchTareaResponse(List<Tarea> taskList) {
        this.taskList = taskList;
    }

    public SearchTareaResponse(BaseResponse baseResponse){
        super.setMessages(baseResponse.getMessages());
    }



    private List<Tarea> taskList;

    public List<Tarea> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Tarea> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "SearchTareaResponse{" +
                "taskList=" + taskList +
                '}';
    }
}
