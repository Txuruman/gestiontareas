package es.securitasdirect.tareas.web.controller.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;

import java.util.List;

import org.wso2.ws.dataservice.GrpAplazamiento;

/**
 * Created by Txus
 * Datos del combo de aplazamiento del modal, en las tareas de tipo aviso
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TipoAplazaResponse extends BaseResponse {
	
	private List<GrpAplazamiento> listGrpAplazamiento;
	
    public TipoAplazaResponse(){}

    public TipoAplazaResponse(List<GrpAplazamiento> listGrpAplazamiento) {
        this.listGrpAplazamiento = listGrpAplazamiento;
    }

    public TipoAplazaResponse(BaseResponse baseResponse){
        if(baseResponse!=null){
            super.setMessages(baseResponse.getMessages());
        }
    }

	public List<GrpAplazamiento> getListGrpAplazamiento() {
		return listGrpAplazamiento;
	}

	public void setListGrpAplazamiento(List<GrpAplazamiento> listGrpAplazamiento) {
		this.listGrpAplazamiento = listGrpAplazamiento;
	}

	@Override
	public String toString() {
		return "TipoAplazaResponse [listGrpAplazamiento=" + listGrpAplazamiento + "]";
	}

    
   
}
