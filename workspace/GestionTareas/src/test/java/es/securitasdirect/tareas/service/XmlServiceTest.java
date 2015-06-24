package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.tickets.Ticket;
import es.securitasdirect.tareas.model.tickets.operations.CreateTicket;
import es.securitasdirect.tareas.support.XmlMarshaller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de InstallationMonDataService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class XmlServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlServiceTest.class);

    @Inject
    protected XmlMarshaller xmlMarshaller;


    @Test
    public void testInjection() {
        assertThat (xmlMarshaller,notNullValue());
    }

    @Test
    public void createTicket()  {
        CreateTicket createTicket = new CreateTicket();
        createTicket.setTicket(new Ticket());

        createTicket.getTicket().setNumInst("111111");

        String xmlCreateTicket = xmlMarshaller.marshalObject(createTicket);
        LOGGER.info("xmlCreateTicket: {}" , xmlCreateTicket);
    }


}
