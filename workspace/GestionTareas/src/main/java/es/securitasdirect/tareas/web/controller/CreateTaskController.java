package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.Constants;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 */

@Controller
@RequestMapping("/createtask")
public class CreateTaskController {


    @Inject
    private TareaService tareaService;
    @Inject
    private InstallationService installationService;
    @Inject
    private ExternalDataService externalDataService;
    @Inject
    private QueryTareaService queryTareaService;

    @Inject
    protected MessageUtil messageUtil;


    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTaskController.class);

    //MV DSE RESPUESTA PARA CREARTAREA EL MAPEO ESTA DENTRO DEL VISOR Y SE LLAMA ASÍ:
    // visortarea/creartarea.htm <---- FIJARSE NO PONER LA BARRA (/) AL PRINCIPIO PARA EVITAR LLAMAR A LA RAIZ DEL SITIO Y NO DE LA APLICACION.

    @RequestMapping("/creartarea.htm")
    public ModelAndView HrCrearTarea(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ModelAndView mv = new ModelAndView("creartarea");
        return mv;
    }


    @RequestMapping(value = "/getDesplegableKey1", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    List<Pair> getDesplegableKey1() throws DataServiceFault {
        List<Pair> desplegableKey1 = externalDataService.getDesplegableKey1();
        return desplegableKey1;
    }

    @RequestMapping(value = "/getDesplegableKey2", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    List<Pair> getDesplegableKey2(@RequestParam(value = "key1", required = true) Integer key1) throws DataServiceFault {
        List<Pair> desplegableKey2 = externalDataService.getDesplegableKey2(key1);
        return desplegableKey2;
    }

    @RequestMapping(value = "/getCancelationType", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    List<Pair> getCancelationType() throws DataServiceFault {
        List<Pair> cancelationTypeList = externalDataService.getCancelationType();
        return cancelationTypeList;
    }

    /**
     *
     * @return
     * @throws org.wso2.ws.dataservice.DataServiceFault
     */
    @RequestMapping(value = "/getTareaListadoAssistant", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getTareaListadoAssistant(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        LOGGER.debug("Get list assistant task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaListadoAssistant task = (TareaListadoAssistant)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("List assistant task obtained from service: \n{}", task);
        return toTareaResponse(task);
    }

    @RequestMapping(value = "/getClosingReason", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    List<Pair> getClosingReason2() throws DataServiceFault {
        List<Pair> closingReasonList = externalDataService.getClosingReason();
        return closingReasonList;
    }

    @RequestMapping(value = "/exceltaskcommon/getClosingReason", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    List<Pair> getClosingReason() throws DataServiceFault {
        LOGGER.debug("Calling external data service: getClosingReason");
        List<Pair> closingReasonList = externalDataService.getClosingReason();
        LOGGER.debug("Closing reason list: {}", closingReasonList);
        return closingReasonList;
    }



    @RequestMapping(value = "/maintenancesurveytask/getMaintenanceSurveyTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getMaintenanceSurveyTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        LOGGER.debug("Creating MaintenanceSurveyTask");
        MaintenanceSurveyTask task = (MaintenanceSurveyTask)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Created MaintenanceSurveyTask: {}", task);
        return toTareaResponse(task);
    }

    @RequestMapping(value = "/marketingsurveytask/getMarketingSurveyTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getMarketingSurveyTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        LOGGER.debug("Get MarketingSurveyTask for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        MarketingSurveyTask task = (MarketingSurveyTask)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Maintenance task obtained from service: \n{}", task);
        return toTareaResponse(task);
    }


    @RequestMapping(value = "/keybox/getkeyboxtask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getKeyboxTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        KeyboxTask task = (KeyboxTask)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Maintenance task obtained from service: \n{}", task);
        return toTareaResponse(task);
    }

    @RequestMapping(value = "anothercampaignstask/getanothercampaginstask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getAnotherCampaignsTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaOtrasCampanas task = (TareaOtrasCampanas)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Maintenance task obtained from service: \n{}", task);
        return toTareaResponse(task);
    }


    @RequestMapping(value = "feecleaningtask/getfeecleaningtask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getFeeCleaningTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        LOGGER.debug("Creating FeeCleaningTask");
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaLimpiezaCuota task = (TareaLimpiezaCuota)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Maintenance task obtained from service: \n{}", task);
        return toTareaResponse(task);
    }



    @RequestMapping(value = "/tarealimpiezacuota/gettarealimpiezacuota", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getTareaLimpiezCuota(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        LOGGER.debug("Creating TareaLimpiezaCuota");
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaLimpiezaCuota task = (TareaLimpiezaCuota)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Maintenance task obtained from service: \n{}", task);
        return toTareaResponse(task);
    }

    @RequestMapping(value = "/listassitanttask/getListAssistantTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getListAssitantTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
            LOGGER.debug("Get TareaListadoAssistant task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
            TareaListadoAssistant task = (TareaListadoAssistant)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            LOGGER.debug("TareaListadoAssistant obtained from service: \n{}", task);
            return toTareaResponse(task);
    }






    TareaResponse toTareaResponse(Tarea tarea){
        TareaResponse tareaResponse = new TareaResponse();
        LOGGER.info("Process task response: {}", tarea);
        if(tarea!=null) {
            tareaResponse.setTarea(tarea);
            tareaResponse.success(messageUtil.getProperty("task.success"));
        }else{
            tareaResponse.danger(messageUtil.getProperty("task.notFound"));
        }
        LOGGER.info("Task Response: {}", tareaResponse);
        return tareaResponse;
    }

}
