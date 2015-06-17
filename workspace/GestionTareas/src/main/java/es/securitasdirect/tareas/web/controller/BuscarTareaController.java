package es.securitasdirect.tareas.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.TareaService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuscarTareaController implements Controller {

    @Inject
    private TareaService tareaService;
    @Inject
    private InstallationService installationService;



    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        int i = 0;
        ModelAndView mv = new ModelAndView("buscartarea");
        List<Tarea> listaTareas = tareaService.findByTelefono("652696869");


        String tecla2 = "active";
        String tecla3 = "inactive";
        mv.addObject("listaTareas", listaTareas);
        mv.addObject("tecla2", tecla2);
        mv.addObject("tecla3", tecla3);
        return mv;

    }

}
