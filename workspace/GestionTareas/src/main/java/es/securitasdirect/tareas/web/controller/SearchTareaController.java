package es.securitasdirect.tareas.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.TareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/searchtarea")
public class SearchTareaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTareaController.class);

    @Inject
    private TareaService tareaService;
    @Inject
    private InstallationService installationService;


    @RequestMapping
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


    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    List<Tarea> query(@RequestParam(value = "searchText", required = false) String searchText, @RequestParam(value = "searchOption", required = false) String searchOption) {
        LOGGER.debug("Searching Tareas text:{} option:{}", searchText, searchOption);
        List<Tarea> listaTareas = tareaService.findByTelefono("652696869");
        return listaTareas;
    }
}
