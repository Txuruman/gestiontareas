package es.securitasdirect.tareas.web.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CrearTareaController implements Controller{
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        ModelAndView mv = new ModelAndView("creartarea");
        String tecla1 = "inactive";
        String tecla2 = "inactive";
        String tecla3 = "active";
        mv.addObject("tecla1", tecla1);
        mv.addObject("tecla2", tecla2);
        mv.addObject("tecla3", tecla3);
        return null;
    }
}
