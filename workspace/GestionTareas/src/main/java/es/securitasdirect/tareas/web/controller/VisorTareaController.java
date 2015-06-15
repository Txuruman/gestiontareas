/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.securitasdirect.tareas.web.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author jel
 */
public class VisorTareaController implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisorTareaController.class);

    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {

        String tipoTarea = null;

        //Recoger parametros
        String ins_no = hsr.getParameter(ExternalParams.NUMERO_INSTALACION);
        String t_tipo = hsr.getParameter(ExternalParams.TIPO_TAREA);

        LOGGER.info("parameters: ins_no:{}, ",ins_no);


        /**
         * Condicionamos la entrada de datos con la vista, si
         * no hay datos entra por la busqueda y si los hay entra por el visor
         *
         */

        /**
         * Condicional para saber que contenido secundario hay que cargar
         */


        ModelAndView mv = null;
        if (ins_no==null) {
            mv = new ModelAndView("buscartarea");
        } else {
            mv = new ModelAndView("visortarea");
        }

        if (t_tipo.equals("aviso")){
            String secundaria = "componentes/tarea_avisos.jsp";
            mv.addObject(secundaria);
        }

        mv.addObject("ins_no", ins_no);


        String tecla1 = "active";
        String tecla2 = "inactive";
        String tecla3 = "inactive";
        mv.addObject("tecla1", tecla1);
        mv.addObject("tecla2", tecla2);
        mv.addObject("tecla3", tecla3);


        return mv;
    }

}
