package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.tickets.*;
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
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class XmlServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlServiceTest.class);

    @Inject
    protected XmlMarshaller xmlMarshaller;


    @Test
    public void testInjection() {
        assertThat(xmlMarshaller, notNullValue());
    }

    /**
     *
     */
    @Test
    public void createTicket() {
        /**
         * creacion del XML
         */
        CreateTicket createTicket = new CreateTicket();
        Req createReq = new Req();
        Asgto createAsgto = new Asgto();
        Comm createComm = new Comm();
        Opcod createOpcod = new Opcod();
        Clcod createClcod = new Clcod();
        createTicket.setTicket(new Ticket());
        createTicket.setUser(new User());
        createTicket.setSvrq(new Svrq());






        /*
         * Estructura del XML
         */

        /*
        * SUBCAMPOS
        *
        * */

        /* <REQ></REQ> */
        createReq.setIdReq("GPD");
        createReq.setReqName("");
        createReq.setReqLname1("");
        createReq.setReqLname2("");
        createReq.setReqCif("");
        createReq.setReqEmpl("A13003");
        /* <ASGTO></ASGTO>*/
        createAsgto.setIdAsg("GPD");
        createAsgto.setIdUser("");
        /* <COMM></COMM>*/
        createComm.setName("");
        createComm.setlName1("");
        createComm.setlName2("");
        /* <OPCOD></OPCOD>*/
        createOpcod.setCodKey1("200");
        createOpcod.setCodKey2("210");
        /* <CLCOD></CLCOD>*/
        createClcod.setCodKey3("");
        createClcod.setCodKey4("MANTEN");




        /*
         * <USER></USER>
         */

        createTicket.getUser().setIdUser("A13003");
        createTicket.getUser().setIdUsidCountry("01");
        createTicket.getUser().setIdLanguage("ES");
        createTicket.getUser().setT("NOSESSION");

        /*
         *
         * <TICKET></TICKET>
         */
        createTicket.getTicket().setNumInst("731483");
        createTicket.getTicket().setObserv("Texto Aviso");
        createTicket.getTicket().setCodZIP("28033");
        createTicket.getTicket().setCloseTicket("1");
        createTicket.getTicket().setDataAditional("");
        createTicket.getTicket().setNoteClose("");
        createTicket.getTicket().setMorDebt("0");
        createTicket.getTicket().setTypePanel("SDMF");
            /* <REQ></REQ>
             */
        createTicket.getTicket().setReq(createReq);
        createTicket.getTicket().setAsgto(createAsgto);
        createTicket.getTicket().setComm(createComm);
        createTicket.getTicket().setOpcod(createOpcod);
        createTicket.getTicket().setClcod(createClcod);




        /*
         * <SVRQ></SVRQ>
         */

        createTicket.getSvrq().setMakeSVRQ("1");
        createTicket.getSvrq().setIdTec("CUSTVER");
        createTicket.getSvrq().setInsBoli("1");


        String xmlCreateTicket = xmlMarshaller.marshalObject(createTicket);
        LOGGER.info("xmlCreateTicket: {}", xmlCreateTicket);
    }


}
