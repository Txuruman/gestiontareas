package es.securitasdirect.senales.web.controller;

import es.securitasdirect.senales.model.HappyData;
import es.securitasdirect.senales.service.AppControllerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Controller
public class HappyController {


    private static final Logger LOGGER = LoggerFactory.getLogger(HappyController.class);

    @Inject
    protected AppControllerService appControllerService;

    @PostConstruct
    public void init() {
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public     @ResponseBody
    HappyData getStatus() {
        HappyData happyData = appControllerService.getHappyData();
        LOGGER.debug("Returning happy data {}", happyData);
        return happyData;
    }



    /** Hace que todos los JMSReader envien mensajes de pruebas a sus colas */
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public     @ResponseBody
    HappyData testMessages() {
        appControllerService.testMessages();

        //Return happy
        return appControllerService.getHappyData();
    }


    /** Hace que todos los JMSReader se desconecten de las colas */
    @RequestMapping(value = "/stop", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public     @ResponseBody
    HappyData stopJMSreaders() {
        appControllerService.stopJMSReaders();

        //Return happy
        return appControllerService.getHappyData();

    }



    /** Hace que todos los JMSReader se conecten a sus colas */
    @RequestMapping(value = "/start", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public     @ResponseBody
    HappyData startJMSReaders() {
        appControllerService.startJMSReaders();

        //Return happy
        return appControllerService.getHappyData();

    }

}