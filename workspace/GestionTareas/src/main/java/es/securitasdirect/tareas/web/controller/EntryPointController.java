package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.service.*;
import es.securitasdirect.tareas.web.controller.dto.request.searchtask.SearchTaskRequest;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author JAS
 *         Controller para envíar a la página correspondiente según los parámetro recibidos
 */

@Controller
public class EntryPointController extends TaskController {

    @Autowired
    protected AgentController agentController;

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
    private TareaServiceTools tareaServiceTools;
    @Inject
    private SearchTareaService searchTareaService;
    @Inject
    private SearchTareaController searchTareaController;
    @Inject
    protected MessageUtil messageUtil;


    private static final Logger LOGGER = LoggerFactory.getLogger(EntryPointController.class);

    /**
     * Redirects the Main POST Request from IWS to the appropiate page for the Task
     *
     * @param request
     * @param response
     * @return
     * @throws Exception si viene instalacion, consultanos si la instalacion tiene tareas
     *                   si tiene tareas la pestaña activa es la de buscar, si no la de crear
     *                   si no viene instalacion la pestaña activa es buscar
     *                   en ambos casos vamos a la pestaña de buscar
     */
    @RequestMapping("/entry")
    public ModelAndView handleEntryRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> parametersMap = createParameterMap(request);
        //Crear el objeto de sesion con los datos del agent
        Agent agent = agentController.loadAgentFromIWS(parametersMap);
        String callingList = parametersMap.get(ExternalParams.CALLING_LIST);
        String installation = parametersMap.get(ExternalParams.NUMERO_INSTALACION);

        ModelAndView mv = null;

        if (callingList != null && !callingList.isEmpty()) {
            //Con CallingList estamos Gestionando una tarea
            String tareaType = tareaServiceTools.getTaskTypeFromCallingList(callingList);
            if (tareaType != null && !tareaType.isEmpty()) {
                //Redirect to the appropiate page
                mv = loadModelAndViewForTarea(tareaType, parametersMap);
            } else {
                LOGGER.error("The calling list " + callingList + " is not configured in the application.");
                throw new Exception("The calling list " + callingList + " is not configured in the application.");
            }
        } else if (installation != null && !installation.isEmpty()) {
            //Comprobar si la instalacion tiene tareas, si las tiene buscar, sino crear
            List<Tarea> tareas = searchTareaService.findByInstallationNumber(agent, installation);
            if (tareas == null || tareas.isEmpty()) {
                mv = new ModelAndView("creartarea");
                //Enviar a la pantalla los datos de la ultima búsqueda
                mv.addObject("lastSearchTareaRequest", searchTareaController.getLastSearchTareaRequest());
            } else {
                //Se prepara el lastSearchTareaRequest con los datos de la instalación para que realice la búsqueda de las tareas automáticamente
                SearchTaskRequest searchTaskRequest = new SearchTaskRequest();
                searchTaskRequest.setSearchText(tareas.get(0).getNumeroContrato());
                searchTaskRequest.setSearchOption(SearchTaskRequest.CLIENT);
                mv = new ModelAndView("buscartarea");
                mv.addObject("lastSearchTareaRequest", searchTaskRequest);
            }
        } else {
            //Sin tarea ni instalación vamos a buscar tarea
            mv = new ModelAndView("buscartarea");
            //Enviar a la pantalla los datos de la ultima búsqueda
            mv.addObject("lastSearchTareaRequest", searchTareaController.getLastSearchTareaRequest());
        }

        return mv;
    }


    /**
     * Mapea el tipo de tarea a sus páginas
     */
    private ModelAndView loadModelAndViewForTarea(String tareaType, Map<String, String> parametersMap) {
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
        assert parametersMap.get(ExternalParams.CALLING_LIST) != null && !parametersMap.get(ExternalParams.CALLING_LIST).isEmpty();
        mv.addObject("callingList", parametersMap.get(ExternalParams.CALLING_LIST));
        assert parametersMap.get(ExternalParams.ID_TAREA) != null && !parametersMap.get(ExternalParams.ID_TAREA).isEmpty();
        mv.addObject("tareaId", parametersMap.get(ExternalParams.ID_TAREA));

        //TODO TEMPORAL, ELIMINAR
        mv.addObject("params", parametersMap);

        return mv;

    }

    @RequestMapping("/test")
    public ModelAndView handleRequestTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("test");

        String codifications = "<CODIFICATIONS>\n" +
                "  <CODIFICATION>\n" +
                "       <IX>\n" +
                "           Mandatory. An integer number that represents the codification index. \n" +
                "           It must be preserved for further updates or modifications.\n" +
                "       </IX>\n" +
                "       <CALLTYPE>\n" +
                "           Mandatory. Alphanumeric identifier for the reason of the appointment or activity.\n" +
                "       </CALLTYPE>\n" +
                "       <PROBLEM>\n" +
                "           Mandatory. Alphanumeric identifier for the problem of the appointment or activity.\n" +
                "           It is intrinsically linked to CALLTYPE.\n" +
                "       </PROBLEM>\n" +
                "       <CAUSE>\n" +
                "           Optional, yet mandatory when finalizing appointments or activities.\n" +
                "           Alphanumeric identifier for the real cause of the appointment or activity.                  </CAUSE>\n" +
                "       <RESOLUTION>\n" +
                "           Optional, yet mandatory when finalizing appointments or activities.\n" +
                "           Alphanumeric identifier of the solution applied for the appointment or activity.            </RESOLUTION>\n" +
                "       <ITEM ID=\"Item unique identifier\" \n" +
                "             COUNT=\"Number of items that are part of the appointment or activity solution.\" />    </CODIFICATION>\n" +
                "</CODIFICATIONS> ";

        mv.addObject("codifications", codifications);
        return mv;
    }


    /**
     * Redirige a la página que abre la ventana modal de Crear Mantenimiento en la aplicación externa.
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/windowCreateMaintenace")
    public ModelAndView handleCreateMaintenanceRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("windowCreateMaintenance");
        return mv;
    }
}
