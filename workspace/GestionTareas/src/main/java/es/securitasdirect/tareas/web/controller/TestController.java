package es.securitasdirect.tareas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test.htm")
public class TestController {

    @PostConstruct
    public void postConstruct() {
        System.out.printf("hola");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView initForm(ModelMap model){
        ModelAndView mv = new ModelAndView("test");

        //TODO Borrar, temporal para ver los parámetros enviados a la página
        mv.addObject("todosparametros");

        return mv;
    }

    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ModelAndView mv = new ModelAndView("buscartarea");

        //TODO Borrar, temporal para ver los parámetros enviados a la página
        mv.addObject("todosparametros", createParameterMap(hsr));

        return mv;
    }


    public Map<String, String> createParameterMap(HttpServletRequest hsr) {
        Map<String, String> parameterValues = new HashMap<String, String>();

        for (Object parameterName : hsr.getParameterMap().keySet()) {
            parameterValues.put(parameterName.toString(), hsr.getParameter(parameterName.toString()));
        }
        return parameterValues;
    }
}
