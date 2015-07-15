package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.dto.TareaResponse;
import es.securitasdirect.tareas.web.controller.dto.request.notificationtask.*;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author jel
 */

@Controller
@RequestMapping("/visortarea")
public class VisorTareaController {
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

        //Redirigir a la página adecuada con el calling list que nos viene
        String callingList = parametersMap.get(ExternalParams.CALLING_LIST);
        if (callingList != null) {
            String tareaType = queryTareaService.getTaskTypeFromCallingList(callingList);
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

//
//        //Consultar Datos Instalacion, si vienen como parametro
//        ins_no = parametersMap.get(ExternalParams.NUMERO_INSTALACION);
//        if (ins_no != null) {
//            installationData = installationService.getInstallationData(ins_no);
//        } else {
//            throw new Exception("Installation number parameter not received");
//        }
//
//
//        tarea = tareaService.loadTareaFromParameters(parametersMap);
//        //Puede que tengamos tarea o no, si no hay parametros es tarea Nueva de tipo AVISO
//        if (tarea == null) {
//            //Inicializamos una tarea por defecto de tipo Aviso
//            tarea = new TareaAviso();
//        } else {
//            // pa por si tenemos que hacer algo con las tareas
//        }
//
//
//        ModelAndView mv = null;
//        if (installationData == null) {
//            //Si no hay instalacion vamos a la pantalla de buscar
//            mv = new ModelAndView("buscartarea");
//        } else {
//            //Si hay instalacion
//            mv = new ModelAndView("visortarea");
//            /**
//             * Condicional para saber que contenido secundario hay que cargar
//             */
//            String titulo = null;
//            if (tarea instanceof TareaAviso) {
//                titulo = "titulo.TareaAviso";
//                mv.addObject(SECUNDARIA, AVISO);
//                //Cargar combos específicos de TareaAviso
//                List<Pair> datosAdicionalesCierreTareaAviso = externalDataService.getDatosAdicionalesCierreTareaAviso();
//            } else if (tarea instanceof TareaListadoAssistant) {
//                titulo = "titulo.TareaListadoAssistant";
//                mv.addObject(SECUNDARIA, EXCEL_LISTADO_ASSISTANT);
//            } else if (tarea instanceof MaintenanceSurveyTask) {
//                titulo = "titulo.TareaEncuestaMantenimiento";
//                mv.addObject(SECUNDARIA, EXCEL_ENCUESTAS_MANTENIMIENTOS);
//            } else if (tarea instanceof MarketingSurveyTask) {
//                titulo = "titulo.TareaEncuestaMarketing";
//                mv.addObject(SECUNDARIA, EXCEL_ENCUESTAS_MARKETING);
//            } else if (tarea instanceof KeyboxTask) {
//                titulo = "titulo.TareaKeybox";
//                mv.addObject(SECUNDARIA, EXCEL_KEYBOX);
//            } else if (tarea instanceof TareaLimpiezaCuota) {
//                titulo = "titulo.TareaLimpiezaCuota";
//                mv.addObject(SECUNDARIA, EXCEL_LIMPIEZA_DE_CUOTA);
//            } else if (tarea instanceof TareaOtrasCampanas) {
//                titulo = "titulo.TareaOtrasCampanas";
//                mv.addObject(SECUNDARIA, EXCEL_OTRAS_CAMPANIAS);
//            } else if (tarea instanceof TareaMantenimiento) {
//                titulo = "titulo.TareaMantenimiento";
//                mv.addObject(SECUNDARIA, MANTENIMIENTO);
//                //Cargar combo de tarea de Mantenimiento, solo si es tarea de mantenimiento
//                List<Pair> desplegableKey1 = externalDataService.getDesplegableKey1();
//                List<Pair> desplegableKey2 = externalDataService.getDesplegableKey2(null);
//                mv.addObject("desplegableKey1", desplegableKey1);
//            }
//            mv.addObject(TITULO, titulo);
//            mv.addObject("tarea", tarea);
//            mv.addObject("installationData", installationData);
//
//            //TODO Borrar, temporal para ver los parámetros enviados a la página
//            mv.addObject("todosparametros", parametersMap);
//
//        }
//
//        /**
//         * Variable del estilo de las tabs
//         */
//        String tecla1 = "active";
//        String tecla2 = "inactive";
//        String tecla3 = "inactive";
//        mv.addObject("tecla1", tecla1);
//        mv.addObject("tecla2", tecla2);
//        mv.addObject("tecla3", tecla3);
//
//        return mv;
    }


    /**
     * Mapea el tipo de tarea a sus págin as
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
        mv.addObject("titulo", titulo);
        mv.addObject("installationId", parametersMap.get(ExternalParams.NUMERO_INSTALACION));
        mv.addObject("callingList", parametersMap.get(ExternalParams.CALLING_LIST));
        mv.addObject("tareaId", parametersMap.get(ExternalParams.ID_TAREA));
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

    @RequestMapping(value = "/getDesplegableKey1", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    List<Pair> getDesplegableKey1() throws DataServiceFault {
        List<Pair> desplegableKey1 = externalDataService.getDesplegableKey1();
        return desplegableKey1;
    }

    @RequestMapping(value = "/getTareaMantenimiento", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getTareaMantenimiento(@RequestParam(value = "ccUserId", required = true) String ccUserId,
                                             @RequestParam(value = "callingList", required = true) String callingList,
                                             @RequestParam(value = "tareaId", required = true) String tareaId
        ) throws DataServiceFault {
        LOGGER.debug("Get maintenance task for params: \nccUserId:{}\ncallingList:{}\ntareaId:{}",ccUserId, callingList, tareaId);
        TareaMantenimiento tareaMantenimiento = (TareaMantenimiento)queryTareaService.queryTarea(ccUserId, callingList, tareaId);
        LOGGER.debug("Maintenance task obtained from service: \n{}", tareaMantenimiento);
        return toTareaResponse(tareaMantenimiento);
    }

    /**
     *
     * @return
     * @throws DataServiceFault
     */
    @RequestMapping(value = "/getTareaListadoAssistant", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getTareaListadoAssistant(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) throws DataServiceFault {
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

    @RequestMapping(value = "/maintenancesurveytask/getMaintenanceSurveyTask", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    TareaResponse getMaintenanceSurveyTask(
            @RequestParam(value = "ccUserId", required = true) String ccUserId,
            @RequestParam(value = "callingList", required = true) String callingList,
            @RequestParam(value = "tareaId", required = true) String tareaId
    ) throws DataServiceFault {
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
    ) throws DataServiceFault {
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
    ) throws DataServiceFault {
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
    ) throws DataServiceFault {
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
    ) throws DataServiceFault {
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
    ) throws DataServiceFault {
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
    ) throws DataServiceFault  {
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
