package es.securitasdirect.senales.service;

import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.support.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.File;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Javier Naval on 23/06/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class GestionSenalesMessagesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestionSenalesMessagesTest.class);

    @Inject
    GestionSenalesService gestionSenalesService;
    @Inject
    FileService fileService;

    @Value("classpath:veryOldMessage.xml")
    private Resource veryOldMessage;

    @Value("classpath:exampleMessage.xml")
    private Resource exampleMessage;


    @Value("classpath:notAllowedSignal.xml")
    private Resource notAllowedSignal;

    @Test
    public void inject() {
        assertThat(gestionSenalesService, notNullValue());
    }

    @Test
    public void veryOldMessage() throws Exception {
        Message message = fileService.readMessage(veryOldMessage.getFile());
        gestionSenalesService.onMessage(message);
    }

    @Test
    public void notAllowedSignal() throws Exception {
        Message message = fileService.readMessage(notAllowedSignal.getFile());
        message.setEntryDate(new Date());
        gestionSenalesService.onMessage(message);
    }

    @Test
    public void outofWorkinHour() throws Exception {
        Message message = fileService.readMessage(exampleMessage.getFile());
        message.setEntryDate(new Date());

        Integer previousEndWorkHour = gestionSenalesService.endWorkHour;
        Integer previousEndWorkMinute = gestionSenalesService.endWorkMinute;
        //Machacamos las fechas para la prueba
        gestionSenalesService.endWorkHour=gestionSenalesService.startWorkHour;
        gestionSenalesService.endWorkMinute=gestionSenalesService.startWorkMinute;

        gestionSenalesService.onMessage(message);

        gestionSenalesService.endWorkHour = previousEndWorkHour;
        gestionSenalesService.endWorkMinute = previousEndWorkMinute;
    }


}
