package es.securitasdirect.tareas.web.controller.dto.support;

import java.util.ArrayList;
import java.util.List;

/**
 * Common data for requests
 * Created by AGS 14/07/2015
 */
public class BaseRequest  {
	
	//Añadimos el campo fromSearch para comprobar si viene del buscador y, si la tarea está
	//en retrieved, no permitir hacer nada ya que esa misma tarea le ha llegado a otro agente
	//desde el OCS.
	private String fromSearch;

    public boolean validateParams(List<Object> paramList){
        boolean result = true;
        for (Object o : paramList) {
            if(o==null){
                result = false;
            }else{
                if(o instanceof String && ((String)o).equals("")){
                    result = false;
                }
                //TODO Añadir validaciones particulares de otros tipos de datos.
            }
        }
        return result;
    }

	public String getFromSearch() {
		return fromSearch;
	}

	public void setFromSearch(String fromSearch) {
		this.fromSearch = fromSearch;
	}

}
