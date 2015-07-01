package es.securitasdirect.tareas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.securitasdirect.tareas.model.TestModel1;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jel on 24/06/2015.
 */



public class TestService {
    public Map<String,String> TestearJson (){

        Map<String,String> mapa = new HashMap<String, String>();

        return mapa;
    }

    public TestModel1 seteotest(){
        TestModel1 testset = new TestModel1();
        testset.setNombre("manolo");
        testset.setApellido("garcia");
        testset.setEdad("50");

        return testset;
    }




}
