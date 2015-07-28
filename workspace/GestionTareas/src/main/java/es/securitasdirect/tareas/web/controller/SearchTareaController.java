package es.securitasdirect.tareas.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.SearchTareaService;
import es.securitasdirect.tareas.web.controller.dto.SearchTareaResponse;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
@RequestMapping("/searchtarea")
public class SearchTareaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTareaController.class);

    @Inject
    private SearchTareaService searchTareaService;
    @Inject
    protected MessageUtil messageUtil;


    @RequestMapping
    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ///Redirect to buscartarea.html
        ModelAndView mv = new ModelAndView("buscartarea");
        return mv;
    }


    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    SearchTareaResponse query(@RequestParam(value = "searchText", required = false) String searchText, @RequestParam(value = "searchOption", required = false) String searchOption) {
        LOGGER.debug("Searching Tareas text:{} option:{}", searchText, searchOption);
        List<Tarea> listaTareas = searchTareaService.findByfindByPhone("652696869");
        SearchTareaResponse searchTareaResponse = new SearchTareaResponse();

        if(listaTareas!=null && !listaTareas.isEmpty()){
            LOGGER.info("Success search of task");
            searchTareaResponse.setTaskList(listaTareas);
            searchTareaResponse.success(messageUtil.getProperty("searchTarea.success","4"));
        }else {
            LOGGER.warn("Tarea search not found result");
            searchTareaResponse.success(messageUtil.getProperty("searchTarea.notFound"));
        }
        return searchTareaResponse;
    }
}
