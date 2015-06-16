package es.securitasdirect.tareas.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.Iterator;
import java.util.List;

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
        String tipoTarea = null;

        //Recoger parametros
        String ins_no = hsr.getParameter(ExternalParams.NUMERO_INSTALACION);
        String t_tipo = hsr.getParameter(ExternalParams.TIPO_TAREA);
        String idAviso = hsr.getParameter(ExternalParams.ID_AVISO);

        //Consultar Datos Instalacion
        InstallationData installationData = installationService.getInstallationData(ins_no);

        //Consultar datos Tareas
        Tarea tarea = tareaService.getTareaByIdAviso(idAviso);

        LOGGER.info("parameters: ins_no:{}, id_aviso:{} ", ins_no, idAviso);

        ModelAndView mv = null;
        if (ins_no == null) {
            mv = new ModelAndView("buscartarea");

        } else {

            mv = new ModelAndView("visortarea");
            /**
             * Condicional para saber que contenido secundario hay que cargar
             */
            if (t_tipo.equals("aviso")) {
                String titulo = "eti.visortarea.h2.titulo.avisos";
                mv.addObject(SECUNDARIA, AVISO);
                mv.addObject(TITULO, titulo);
            } else if (t_tipo.equals("listadoassistant")) {
                String titulo = "eti.visortarea.h2.titulo.excel";
                mv.addObject(SECUNDARIA, EXCEL_LISTADO_ASSISTANT);
                mv.addObject(TITULO, titulo);
            } else if (t_tipo.equals("encuestasmantenimientos")) {
                String titulo = "eti.visortarea.h2.titulo.excel";
                mv.addObject(SECUNDARIA, EXCEL_ENCUESTAS_MANTENIMIENTOS);
                mv.addObject(TITULO, titulo);
            }else if (t_tipo.equals("encuestasmarketing")) {
                String titulo = "eti.visortarea.h2.titulo.excel";
                mv.addObject(SECUNDARIA, EXCEL_ENCUESTAS_MARKETING);
                mv.addObject(TITULO, titulo);
            }else if (t_tipo.equals("keybox")) {
                String titulo = "eti.visortarea.h2.titulo.excel";
                mv.addObject(SECUNDARIA, EXCEL_KEYBOX);
                mv.addObject(TITULO, titulo);
            }else if (t_tipo.equals("limpiezacuota")) {
                String titulo = "eti.visortarea.h2.titulo.excel";
                mv.addObject(SECUNDARIA, EXCEL_LIMPIEZA_DE_CUOTA);
                mv.addObject(TITULO, titulo);
            }else if (t_tipo.equals("otrascampanias")) {
                String titulo = "eti.visortarea.h2.titulo.excel";
                mv.addObject(SECUNDARIA, EXCEL_OTRAS_CAMPANIAS);
                mv.addObject(TITULO, titulo);
            }else if (t_tipo.equals("mantenimiento")) {
                String titulo = "eti.visortarea.h2.titulo.mantenimiento";
                mv.addObject(SECUNDARIA, MANTENIMIENTO);
                mv.addObject(TITULO, titulo);
            }
        }


        mv.addObject("ins_no", ins_no);


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
}
