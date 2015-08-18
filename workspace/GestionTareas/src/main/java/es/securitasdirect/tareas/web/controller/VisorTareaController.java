package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.service.*;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import es.securitasdirect.tareas.web.controller.task.exceltask.CommonExcelTaskController;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author jel
 */

@Controller
@RequestMapping("/visortarea")
public class VisorTareaController extends TaskController {

    @Autowired
    protected  AgentController agentController;

    //Funciona
//    @Autowired
//    private Agent agent;

    /**
     * clases de avisos
     */
    public String AVISO = "componentes/tarea_avisos.jsp";
    public String EXCEL_LISTADO_ASSISTANT = "componentes/tareaexcel/listadoassistant.jsp";
    public String EXCEL_ENCUESTAS_MANTENIMIENTOS = "componentes/tareaexcel/encuenstasmantenimientos.jsp";
    public String EXCEL_ENCUESTAS_MARKETING = "componentes/tareaexcel/encuestasmarketing.jsp";
    public String EXCEL_KEYBOX = "componentes/tareaexcel/keybox.jsp";
    public String EXCEL_LIMPIEZA_DE_CUOTA = "componentes/tareaexcel/limpiezacuota.jsp";
    public String EXCEL_OTRAS_CAMPANIAS = "componentes/tareaexcel/otrascampanias.jsp";


    public String MANTENIMIENTO = "componentes/tarea_mantenimiento.jsp";
    public String SECUNDARIA = "secundaria";
    public String TITULO = "titulo";

    @Inject
    private TareaService tareaService;
    @Inject
    private InstallationService installationService;
    @Inject
    private ExternalDataService externalDataService;
    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private CommonExcelTaskController commonExcelTaskController;
    @Inject
    private TareaServiceTools tareaServiceTools;

    @Inject
    protected MessageUtil messageUtil;


    private static final Logger LOGGER = LoggerFactory.getLogger(VisorTareaController.class);

    /**
     * Redirects the Main POST Request from IWS to the appropiate page for the Task
     *
     * @param hsr
     * @param hsr1
     * @return
     * @throws Exception
     */
    @RequestMapping
    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        //Crear un mapa con los parámetros
        Map<String, String> parametersMap = createParameterMap(hsr);
        LOGGER.debug("Parameter map from resquest:\n{}", parametersMap);

        //TODO Validador y sino lanzar excepcion

        //Crear el objeto de sesion con los datos del agent
        Agent agent = agentController.loadAgentFromIWS(parametersMap);

        //Redirigir a la página adecuada con el calling list que nos viene
        String callingList = parametersMap.get(ExternalParams.CALLING_LIST);
        if (callingList != null) {
            String tareaType = tareaServiceTools.getTaskTypeFromCallingList(callingList);
            if (tareaType != null) {
                //Redirect to the appropiate page
                return loadModelAndViewForTarea(tareaType, parametersMap);
            } else {
                LOGGER.error("The calling list " + callingList + " is not configured in the application.");
                throw new Exception("The calling list " + callingList + " is not configured in the application.");
            }
        } else {
            LOGGER.error("No calling list parameter received");
            throw new Exception("No calling list parameter received");
        }
    }


    /**
     * Mapea el tipo de tarea a sus páginas
     */
    private ModelAndView loadModelAndViewForTarea(String tareaType,  Map<String, String> parametersMap ) {
        assert tareaType != null;

        ModelAndView mv = new ModelAndView("visortarea");
        String titulo = null;

        if (tareaType.equals(Constants.TAREA_AVISO)) {
            titulo = "titulo.TareaAviso";
            mv.addObject(SECUNDARIA, AVISO);
        } else if (tareaType.equals(Constants.TAREA_LISTADOASSISTANT)) {
            titulo = "titulo.TareaListadoAssistant";
            mv.addObject(SECUNDARIA, EXCEL_LISTADO_ASSISTANT);
        } else if (tareaType.equals(Constants.TAREA_ENCUESTAMANTENIMIENTO)) {
            titulo = "titulo.TareaEncuestaMantenimiento";
            mv.addObject(SECUNDARIA, EXCEL_ENCUESTAS_MANTENIMIENTOS);
        } else if (tareaType.equals(Constants.TAREA_ENCUESTAMARKETING)) {
            titulo = "titulo.TareaEncuestaMarketing";
            mv.addObject(SECUNDARIA, EXCEL_ENCUESTAS_MARKETING);
        } else if (tareaType.equals(Constants.TAREA_KEYBOX)) {
            titulo = "titulo.TareaKeybox";
            mv.addObject(SECUNDARIA, EXCEL_KEYBOX);
        } else if (tareaType.equals(Constants.TAREA_LIMPIEZACUOTA)) {
            titulo = "titulo.TareaLimpiezaCuota";
            mv.addObject(SECUNDARIA, EXCEL_LIMPIEZA_DE_CUOTA);
        } else if (tareaType.equals(Constants.TAREA_OTRASCAMPANAS)) {
            titulo = "titulo.TareaOtrasCampanas";
            mv.addObject(SECUNDARIA, EXCEL_OTRAS_CAMPANIAS);
        } else if (tareaType.equals(Constants.TAREA_MANTENIMIENTO)) {
            titulo = "titulo.TareaMantenimiento";
            mv.addObject(SECUNDARIA, MANTENIMIENTO);
        }
        mv.addObject(TITULO, titulo);
        assert parametersMap.get(ExternalParams.NUMERO_INSTALACION)!=null && !parametersMap.get(ExternalParams.NUMERO_INSTALACION).isEmpty();
        mv.addObject("installationId", parametersMap.get(ExternalParams.NUMERO_INSTALACION));
        assert parametersMap.get(ExternalParams.CALLING_LIST)!=null && !parametersMap.get(ExternalParams.CALLING_LIST).isEmpty();
        mv.addObject("callingList", parametersMap.get(ExternalParams.CALLING_LIST));
        assert parametersMap.get(ExternalParams.ID_TAREA)!=null && !parametersMap.get(ExternalParams.ID_TAREA).isEmpty();
        mv.addObject("tareaId", parametersMap.get(ExternalParams.ID_TAREA));
        assert parametersMap.get(ExternalParams.identificadorAgente)!=null && !parametersMap.get(ExternalParams.identificadorAgente).isEmpty();
        mv.addObject("ccUserId", parametersMap.get(ExternalParams.identificadorAgente));
        return mv;

    }

    //MV DSE RESPUESTA PARA CREARTAREA EL MAPEO ESTA DENTRO DEL VISOR Y SE LLAMA ASÍ:
    // visortarea/creartarea.htm <---- FIJARSE NO PONER LA BARRA (/) AL PRINCIPIO PARA EVITAR LLAMAR A LA RAIZ DEL SITIO Y NO DE LA APLICACION.

    @RequestMapping("/creartarea.htm")
    public ModelAndView HrCrearTarea(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ModelAndView mv = new ModelAndView("creartarea");
        return mv;
    }

    public void aplazar() {
        //TODO Jesus me tienes que pasar el idAviso
        LOGGER.debug("Aplazando tarea idAviso:{}", "xxxxxx");
    }

    public void llamar() {

    }

    public void finalizar() {

    }

    public Map<String, String> createParameterMap(HttpServletRequest hsr) {
        Map<String, String> parameterValues = new HashMap<String, String>();

        for (Object parameterName : hsr.getParameterMap().keySet()) {
            parameterValues.put(parameterName.toString(), hsr.getParameter(parameterName.toString()));
        }
        return parameterValues;
    }

    @RequestMapping(value = "/exceltaskcommon/getClosingReason", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getClosingReason()  {
        String SERVICE_MESSAGE = "exceltaskcommon.getclosigreason";
        PairListResponse response;
        LOGGER.debug("Calling external data service: getClosingReason");
        try{
            List<Pair> closingReasonList = externalDataService.getClosingReason();
            response = new PairListResponse(processSuccessMessages(closingReasonList, SERVICE_MESSAGE));
            response.setPairList(closingReasonList);
            LOGGER.debug("Closing reason list: {}", closingReasonList);
        }catch(Exception e){
            LOGGER.error("Error loading closing reason list:");
            response = new PairListResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }



    @RequestMapping(value = "/maintenancesurveytask/getMaintenanceSurveyTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getMaintenanceSurveyTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        String SERVICE_MESSAGE = "maintenanncesurveytask.getmaintenancesurveytask";
        TareaResponse response;
        LOGGER.debug("Creating MaintenanceSurveyTask");
        try{
            MaintenanceSurveyTask task = (MaintenanceSurveyTask)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            LOGGER.debug("Maintenance survey task creation success");
            response = super.processSuccessTask(task,SERVICE_MESSAGE);
        }catch(Exception e){
            LOGGER.error("Error creating maintenance survey task:");
            response = new TareaResponse(processException(e,SERVICE_MESSAGE));
        }
        return response;
    }

    @RequestMapping(value = "/marketingsurveytask/getMarketingSurveyTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getMarketingSurveyTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        String SERVICE_MESSAGE = "marketingsurvey.getmarketingsurveytask";
        LOGGER.debug("Get MarketingSurveyTask for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaResponse response;
        try{
            MarketingSurveyTask task = (MarketingSurveyTask)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(task,SERVICE_MESSAGE);
            LOGGER.debug("Maintenance task obtained from service: \n{}", task);
        }catch(Exception e){
            LOGGER.error("Error loading marketing survey task");
            response = new TareaResponse(processException(e,SERVICE_MESSAGE));
        }
        return response;
    }


    @RequestMapping(value = "/keybox/getkeyboxtask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getKeyboxTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        String SERVICE_MESSAGE = "keybox.getkeyboxtask";
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaResponse response;
        try{
            KeyboxTask task = (KeyboxTask)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(task,SERVICE_MESSAGE);
            LOGGER.debug("Maintenance task obtained from service: \n{}", task);
        }catch(Exception e){
            LOGGER.error("Error loading keybox task");
            response = new TareaResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }

    @RequestMapping(value = "anothercampaignstask/getanothercampaginstask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getAnotherCampaignsTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        String SERVICE_MESSAGE = "anothercampaignstask.getanothercampaignstask";
        TareaResponse response;
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        try{
            TareaOtrasCampanas task = (TareaOtrasCampanas)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            response = processSuccessTask(task, SERVICE_MESSAGE);
            LOGGER.debug("Maintenance task obtained from service: \n{}", task);
        }catch(Exception e){
            LOGGER.error("Error loading another campaign task");
            response = new TareaResponse(processException(e,SERVICE_MESSAGE));
        }
        return response;
    }


    @RequestMapping(value = "feecleaningtask/getfeecleaningtask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getFeeCleaningTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        String SERVICE_MESSAGE = "feecleaningtask.getfeecleaningtask";
        LOGGER.debug("Creating FeeCleaningTask");
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaResponse response;
        try{
            TareaLimpiezaCuota task = (TareaLimpiezaCuota)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            LOGGER.debug("Maintenance task obtained from service: \n{}", task);
            response = super.processSuccessTask(task,SERVICE_MESSAGE);
        }catch(Exception e){
            LOGGER.error("Error loading maintenance task");
            response = new TareaResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }



    @RequestMapping(value = "/tarealimpiezacuota/gettarealimpiezacuota", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getTareaLimpiezCuota(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) {
        String SERVICE_MESSAGE = "tarealimpiezacuota.gettarealimpiezacuota";
        LOGGER.debug("Creating TareaLimpiezaCuota");
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaResponse response;
        try{
            TareaLimpiezaCuota task = (TareaLimpiezaCuota)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            LOGGER.debug("Fee cleaning task obtained from service: \n{}", task);
            response = processSuccessTask(task,SERVICE_MESSAGE);
        }catch(Exception e){
            LOGGER.error("Error loading fee cleaning task");
            response = new TareaResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }

    @RequestMapping(value = "/listassitanttask/getListAssistantTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getListAssitantTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    )  {
        String SERVICE_MESSAGE = "listassistanttask.getlistassistanttask";
        LOGGER.debug("Get TareaListadoAssistant task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaResponse response;
        try{
            TareaListadoAssistant task = (TareaListadoAssistant)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
            LOGGER.debug("TareaListadoAssistant obtained from service: \n{}", task);
            response = processSuccessTask(task,SERVICE_MESSAGE);
        }catch (Exception e){
            LOGGER.error("Error loading list assitant task");
            response = new TareaResponse(processException(e, SERVICE_MESSAGE));
        }
        return response;
    }
}
