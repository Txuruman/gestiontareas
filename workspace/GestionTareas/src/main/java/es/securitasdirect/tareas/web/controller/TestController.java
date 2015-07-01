package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.TestModel1;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {


        ModelAndView mv = new ModelAndView("test");

        return mv;
    }


    public Map<String, String> createParameterMap(HttpServletRequest hsr) {
        Map<String, String> parameterValues = new HashMap<String, String>();

        for (Object parameterName : hsr.getParameterMap().keySet()) {
            parameterValues.put(parameterName.toString(), hsr.getParameter(parameterName.toString()));
        }
        return parameterValues;
    }


    @RequestMapping(value = "/desplegableKey1", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody  List<TestModel1> getDesplegableKey1() {
        List<TestModel1> list = new ArrayList<TestModel1>();
        TestModel1 m1 = new TestModel1(); m1.setNombre("nom1");m1.setApellido("ape1");
        TestModel1 m2= new TestModel1(); m2.setNombre("nom1");m2.setApellido("ape1");
        list.add(m1);
        list.add(m2);
        return list;
    }

}
