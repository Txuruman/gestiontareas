package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jel
 */


public class VisorTareaController implements Controller {
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


    private static final Logger LOGGER = LoggerFactory.getLogger(VisorTareaController.class);


    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        InstallationData installationData = null;
        Tarea tarea = null;


        String tipoTarea = null;
        String t_tipo = null;
        String idAviso = null;
        String ins_no = null;
        String install_number = "";

        //Consultar Datos Instalacion, si vienen como parametro
        ins_no = hsr.getParameter(ExternalParams.NUMERO_INSTALACION);
        if (ins_no != null) {
            installationData = installationService.getInstallationData(ins_no);
        }


        tarea = tareaService.loadTareaFromParameters(createParameterMap(hsr));
        //Puede que tengamos tarea o no, si no hay parametros es tarea Nueva de tipo AVISO
        if (tarea == null) {
            //Inicializamos una tarea por defecto de tipo Aviso
            tarea = new TareaAviso();
        } else {
            // pa por si tenemos que hacer algo con las tareas
        }


        ModelAndView mv = null;
        if (installationData == null) {
            //Si no hay instalacion vamos a la pantalla de buscar
            mv = new ModelAndView("buscartarea");
        } else {
            //Si hay instalacion
            mv = new ModelAndView("visortarea");
            /**
             * Condicional para saber que contenido secundario hay que cargar
             */
            String titulo = null;
            if (tarea instanceof TareaAviso) {
                titulo = "eti.visortarea.h2.titulo.TareaAviso";
                mv.addObject(SECUNDARIA, AVISO);
            } else if (tarea instanceof TareaListadoAssistant) {
                titulo = "eti.visortarea.h2.titulo.TareaListadoAssistant";
                mv.addObject(SECUNDARIA, EXCEL_LISTADO_ASSISTANT);
            } else if (tarea instanceof TareaEncuestaMantenimiento) {
                titulo = "eti.visortarea.h2.titulo.TareaEncuestaMantenimiento";
                mv.addObject(SECUNDARIA, EXCEL_ENCUESTAS_MANTENIMIENTOS);
            } else if (tarea instanceof TareaEncuestaMarketing) {
                titulo = "eti.visortarea.h2.titulo.TareaEncuestaMarketing";
                mv.addObject(SECUNDARIA, EXCEL_ENCUESTAS_MARKETING);
            } else if (tarea instanceof TareaKeybox) {
                titulo = "eti.visortarea.h2.titulo.TareaKeybox";
                mv.addObject(SECUNDARIA, EXCEL_KEYBOX);
            } else if (tarea instanceof TareaLimpiezaCuota) {
                titulo = "eti.visortarea.h2.titulo.TareaLimpiezaCuota";
                mv.addObject(SECUNDARIA, EXCEL_LIMPIEZA_DE_CUOTA);
            } else if (tarea instanceof TareaOtrasCampanas) {
                titulo = "eti.visortarea.h2.titulo.TareaOtrasCampanas";
                mv.addObject(SECUNDARIA, EXCEL_OTRAS_CAMPANIAS);
            } else if (tarea instanceof TareaMantenimiento) {
                titulo = "eti.visortarea.h2.titulo.TareaMantenimiento";
                mv.addObject(SECUNDARIA, MANTENIMIENTO);
                //Cargar combo de tarea de Mantenimiento, solo si es tarea de mantenimiento
                Map<Integer, String> desplegableKey1 = tareaService.getDesplegableKey1();
                mv.addObject("desplegableKey1", desplegableKey1);
            }
            mv.addObject(TITULO, titulo);
            mv.addObject("tarea", tarea);
            mv.addObject("installationData", installationData);

        }

        /**
         * Variable del estilo de las tabs
         */
        String tecla1 = "active";
        String tecla2 = "inactive";
        String tecla3 = "inactive";
        mv.addObject("tecla1", tecla1);
        mv.addObject("tecla2", tecla2);
        mv.addObject("tecla3", tecla3);

        return mv;
    }

    public void aplazar() {
        //TODO Jesus me tienes que pasar el idAviso
        LOGGER.debug("Aplazando tarea idAviso:{}", "xxxxxx");
    }

    public void descartar() {

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
}
