/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.securitasdirect.tareas.web.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        ModelAndView mv = new ModelAndView("visortarea");
        String ins_no = "void";

        if (hsr.getParameter("ins_no") != null){
        ins_no = hsr.getParameter("ins_no");}



        mv.addObject("ins_no",ins_no);


        LOGGER.info("recogida de numero de instalación ");






        String tecla1 = "active";
        String tecla2 = "inactive";
        String tecla3 = "inactive";
        mv.addObject("tecla1", tecla1);
        mv.addObject("tecla2", tecla2);
        mv.addObject("tecla3", tecla3);


        return mv;
    }

}
