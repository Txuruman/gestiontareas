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
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//Evitamos cargar el applicationContext-jms para que funcionen los test
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-bean.xml","classpath*:spring/applicationContext-ws.xml"})
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


    @Value("classpath:notAllowedSignalType.xml")
    private Resource notAllowedSignalType;

    @Test
    public void inject() {
        assertThat(gestionSenalesService, notNullValue());
    }

    @Test
    public void veryOldMessage() throws Exception {
        Message message = fileService.readMessage(veryOldMessage.getFile());
        gestionSenalesService.onMessageSynchonous(message);
    }

    @Test
    public void notAllowedSignal() throws Exception {
        Message message = fileService.readMessage(notAllowedSignal.getFile());
        message.setEntryDate(new Date());//Para que no descarte por fecha
        gestionSenalesService.onMessageSynchonous(message);
    }

    @Test
    public void notAllowedSignalType() throws Exception {
        Message message = fileService.readMessage(notAllowedSignalType.getFile());
        message.setEntryDate(new Date());//Para que no descarte por fecha
        gestionSenalesService.onMessageSynchonous(message);
    }



    /**
     * Test para mensajes que van bien
     * @throws Exception
     */
    @Test
    public void inWorkinHour() throws Exception {
        Message message = fileService.readMessage(exampleMessage.getFile());
        message.setEntryDate(new Date());

        gestionSenalesService.onMessageSynchonous(message);

    }



}
