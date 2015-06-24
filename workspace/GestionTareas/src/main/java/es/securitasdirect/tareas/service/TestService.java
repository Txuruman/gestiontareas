package es.securitasdirect.tareas.service;

import java.util.HashMap;
import java.util.Map;
import es.securitasdirect.tareas.model.TestModel1;

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
