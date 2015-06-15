package es.securitasdirect.tareas.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.securitasdirect.tareas.model.IdAvisoModel;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.TareaService;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author jel
 */

@Controller
public class VisorTareaController {
    public String aviso = "componentes/tarea_avisos.jsp";
    public String excel = "componentes/tareaexcel/excellistado.jsp";
    public String mantenimiento = "componentes/tarea_mantenimiento.jsp";
    public String SECUNDARIA = "secundaria";
    public String TITULO = "secundaria";

    @Inject
    private TareaService tareaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(VisorTareaController.class);


    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        String tipoTarea = null;

        //Recoger parametros
        String ins_no = hsr.getParameter(ExternalParams.NUMERO_INSTALACION);
        String t_tipo = hsr.getParameter(ExternalParams.TIPO_TAREA);
        String idAviso = hsr.getParameter(ExternalParams.ID_AVISO);
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
                mv.addObject(SECUNDARIA, aviso);
                mv.addObject(TITULO, titulo);
            } else if (t_tipo.equals("excel")) {
                String titulo = "eti.visortarea.h2.titulo.excel";
                mv.addObject(SECUNDARIA, excel);
                mv.addObject(TITULO, titulo);
            }else if (t_tipo.equals("mantenimiento")) {
                String titulo = "eti.visortarea.h2.titulo.mantenimiento";
                mv.addObject(SECUNDARIA, mantenimiento);
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
