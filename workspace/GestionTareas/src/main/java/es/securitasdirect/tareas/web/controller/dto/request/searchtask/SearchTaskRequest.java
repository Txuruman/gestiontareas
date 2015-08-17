package es.securitasdirect.tareas.web.controller.dto.request.searchtask;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.web.controller.dto.support.BaseRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javier Naval on 06/07/2015.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SearchTaskRequest extends BaseRequest {

    public static String TELEPHONE = "phone";
    public static String CLIENT = "customer";

    private String searchText;
    private String searchOption;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(String searchOption) {
        this.searchOption = searchOption;
    }

    private List<Object> listParams(){
        List<Object> list = new ArrayList<Object>();
        list.add(searchText);
        list.add(searchOption);
        return list;
    }

    public boolean validateParams(){
        return super.validateParams(listParams());
    }

    @Override
    public String toString() {
        return "SearchTaskRequest{" +
                "searchText='" + searchText + '\'' +
                ", searchOption='" + searchOption + '\'' +
                '}';
    }
}
