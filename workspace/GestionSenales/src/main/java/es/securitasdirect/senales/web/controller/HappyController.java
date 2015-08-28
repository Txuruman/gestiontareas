package es.securitasdirect.senales.web.controller;

import es.securitasdirect.senales.model.HappyData;
import es.securitasdirect.senales.service.HappyService;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Controller
public class HappyController {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HappyController.class);

    @Inject
    protected HappyService happyService;

    @PostConstruct
    public void init() {
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public     @ResponseBody
    HappyData getStatus() {
        HappyData happyData = happyService.getHappyData();
        LOGGER.debug("Returning happy data {}", happyData);
        return happyData;
    }

}