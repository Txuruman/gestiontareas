package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.TestModel1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

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


    @RequestMapping(value = "/dropDown.web", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        LOGGER.info("Redirigiendo a dropdown");
        return "dropDown";
    }

    @RequestMapping(value = "/populatePersonDataFromServer.web", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    List<PersonData> populateActivePSwapBasket() {
        LOGGER.info("Cargando datos Bascket");
        PersonData personData = new PersonData();
        personData.setPersonId(10);
        personData.setPersonName("Java Honk");

        List<PersonData> personDatas = new ArrayList<PersonData>();
        personDatas.add(personData);

        personData = new PersonData();
        personData.setPersonId(11);
        personData.setPersonName("AngularJS combo box");
        personDatas.add(personData);

        personData = new PersonData();
        personData.setPersonId(11);
        personData.setPersonName("Select box list");
        personDatas.add(personData);

        return personDatas;
    }

}
