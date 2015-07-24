package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.web.controller.dto.request.createtask.CreateMaintenanceRequest;
import es.securitasdirect.tareas.web.controller.dto.request.createtask.CreateTaskRequest;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.dto.support.DummyResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;
import java.util.List;

/**
 * @author
 */

@Controller
@RequestMapping("/commons")
public class CommonsController extends BaseController {

    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Inject
    private ExternalDataService externalDataService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonsController.class);


    @RequestMapping(value = "/getNotificationTypeList", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PairListResponse getNotificationTypeList(){
        List<Pair> tipoAvisoList;
        PairListResponse response = new PairListResponse();
        try{
            tipoAvisoList = externalDataService.getNotificationType();
            response.setPairList(tipoAvisoList);
            response.success("Obtenidos los de tipos de avisos");
        }catch (DataServiceFault dsf){
            LOGGER.error("Error obteniendo los tipos de aviso: \nFaultInfo:{}\nMessage:{}\n{}", dsf.getFaultInfo(), dsf.getMessage(),dsf.toString());
            response.danger("Error obteniendo tipos de aviso");
        }
        return response;
    }

    @RequestMapping(value = "/getClosingList", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PairListResponse getClosingList(){
        List<Pair> closingList;
        PairListResponse response = new PairListResponse();
        try{
            closingList = externalDataService.getClosing();
            if(closingList!=null && !closingList.isEmpty()){
                response.setPairList(closingList);
                response.success("Obtenidos los tipos de cierre");
            }else{
                response.danger("No se han obtenido tipos de cierre");
            }
        }catch (Exception e){
            response = (PairListResponse)super.processException(e, "Error obteniendo tipos de cierre");
        }
        return response;
    }

    @RequestMapping(value = "/getClosingAditionalDataList", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PairListResponse getClosingAditionalDataList(){
        List<Pair> closingListAditionalData;
        PairListResponse response = new PairListResponse();
        try{
            closingListAditionalData = externalDataService.getDatosAdicionalesCierreTareaAviso();
            if(closingListAditionalData!=null && !closingListAditionalData.isEmpty()){
                response.setPairList(closingListAditionalData);
                response.success("Obtenidos los tipos de datos adicionales de tipos de cierre");
            }else{
                response.warning("No se han recuperado tipos de datos adicionales de tipos de cierre");
            }
        }catch (DataServiceFault dsf){
            LOGGER.error("Error obteniendo los tipos de datos adicionales de tipos de cierre: \nFaultInfo:{}\nMessage:{}\n{}", dsf.getFaultInfo(), dsf.getMessage(),dsf.toString());
            response.danger("Error obteniendo los tipos de datos adicionales de tipos de cierre");
        }
        return response;
    }

    @RequestMapping(value = "/getTypeReasonList", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PairListResponse getTypeReasonList(){
        List<Pair> typeReasonList;
        PairListResponse response = new PairListResponse();
        try{
            typeReasonList = externalDataService.getTypeReasonList();
            if(typeReasonList!=null && !typeReasonList.isEmpty()){
                response.setPairList(typeReasonList);
                response.success("Obtenidos los tipos de motivos");
            }else{
                response.warning("No se han recuperado tipos de motivos");
            }
        }catch (DataServiceFault dsf){
            LOGGER.error("Error obteniendo los datos adicionales de tipos de cierre: \nFaultInfo:{}\nMessage:{}\n{}", dsf.getFaultInfo(), dsf.getMessage(),dsf.toString());
            response.danger("Error obteniendo los datos adicionales de tipos de cierre");
        }
        return response;
    }

}
