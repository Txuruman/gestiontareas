package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.external.BigIntegerPair;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.model.external.StringPair;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.ClosingTypeAditionalDataRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.ClosingTypeRequest;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.TypeReasonListRequest;
import es.securitasdirect.tareas.web.controller.dto.response.BigIntegerPairResponse;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.dto.response.StringPairListResponse;
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
    private ExternalDataService externalDataService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonsController.class);


    @RequestMapping(value = "/getNotificationTypeList", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PairListResponse getNotificationTypeList(){
        List<Pair> tipoAvisoList;
        PairListResponse response = new PairListResponse();
        try{
            tipoAvisoList = externalDataService.getNotificationType();
            if(tipoAvisoList!=null && !tipoAvisoList.isEmpty()){
                response.setPairList(tipoAvisoList);
                response.success(messageUtil.getProperty("commonService.notificationTypeList.success"));
            }else{
                response.warning(messageUtil.getProperty("commonService.notificationTypeList.notFound"));
            }

        }catch (DataServiceFault dsf){
            LOGGER.error("Error obteniendo los tipos de aviso: \nFaultInfo:{}\nMessage:{}\n{}", dsf.getFaultInfo(), dsf.getMessage(),dsf.toString());
            response.danger(messageUtil.getProperty("commonService.notificationTypeList.error"));
        }
        return response;
    }

    @RequestMapping(value = "/getClosingList", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody StringPairListResponse getClosingList(@RequestBody ClosingTypeRequest closingTypeRequest){
        List<StringPair> closingList;
        StringPairListResponse response = new StringPairListResponse();
        try{
            LOGGER.debug("Getting closing type list for request: {}",  closingTypeRequest);
            closingList = externalDataService.getClosing(closingTypeRequest.getIdType(), closingTypeRequest.getReasonId());
            if(closingList!=null && !closingList.isEmpty()){
                response.setPairList(closingList);
                response.success(messageUtil.getProperty("commonService.closingTypeList.success"));
            }else{
                response.danger(messageUtil.getProperty("commonService.closingTypeList.notFound"));
            }
        }catch (Exception e){
            BaseResponse baseResponse = super.processException(e, messageUtil.getProperty("commonService.closingTypeList.error"));
            response.addMessages(baseResponse.getMessages());
        }
        return response;
    }

    @RequestMapping(value = "/getClosingAditionalDataList", method = {RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody PairListResponse getClosingAditionalDataList(ClosingTypeAditionalDataRequest request){
        List<Pair> closingListAditionalData;
        PairListResponse response = new PairListResponse();
        try{
            closingListAditionalData = externalDataService.getDatosAdicionalesCierreTareaAviso(request.getClosingTypeId());
            if(closingListAditionalData!=null && !closingListAditionalData.isEmpty()){
                response.setPairList(closingListAditionalData);
                response.success(messageUtil.getProperty("commonService.closingAditionalDataList.success"));
            }else{
                response.warning(messageUtil.getProperty("commonService.closingAditionalDataList.notFound"));
            }
        }catch (DataServiceFault dsf){
            LOGGER.error("Error obteniendo los tipos de datos adicionales de tipos de cierre: \nFaultInfo:{}\nMessage:{}\n{}", dsf.getFaultInfo(), dsf.getMessage(),dsf.toString());
            response.danger(messageUtil.getProperty("commonService.closingAditionalDataList.error"));
        }
        return response;
    }

    @RequestMapping(value = "/getTypeReasonList", method = {RequestMethod.PUT},consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody BigIntegerPairResponse getTypeReasonList(@RequestBody TypeReasonListRequest request){
        List<BigIntegerPair> typeReasonList;
        BigIntegerPairResponse response = new BigIntegerPairResponse();
        try{
            LOGGER.debug("Calling for get type reason list with request: {}", request);
            typeReasonList = externalDataService.getTypeReasonList(request.getIdType());
            if(typeReasonList!=null && !typeReasonList.isEmpty()){
                response.setPairList(typeReasonList);
                response.success(messageUtil.getProperty("commonService.taskTypeReasonList.success"));

            }else{
                response.warning(messageUtil.getProperty("commonService.taskTypeReasonList.notFound"));
            }
        }catch (DataServiceFault dsf){
            LOGGER.error("Error loading task type closing reason list: \nFaultInfo:{}\nMessage:{}\n{}", dsf.getFaultInfo(), dsf.getMessage(),dsf.toString());
            response.danger(messageUtil.getProperty("commonService.taskTypeReasonList.error"));
        }
        LOGGER.debug("Type reason list call response: {}", response);
        return response;
    }

}
