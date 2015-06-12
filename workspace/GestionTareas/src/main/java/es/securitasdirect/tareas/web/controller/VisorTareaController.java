/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.securitasdirect.tareas.web.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author jel
 */
public class VisorTareaController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ModelAndView mv = new ModelAndView("visortarea");

        String tecla1 = "active";
        String tecla2 = "inactive";
        String tecla3 = "inactive";
        mv.addObject("tecla1", tecla1);
        mv.addObject("tecla2", tecla2);
        mv.addObject("tecla3", tecla3);

        return mv;
    }

}
