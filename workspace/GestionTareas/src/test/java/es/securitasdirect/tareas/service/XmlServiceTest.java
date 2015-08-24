package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.tickets.*;
import es.securitasdirect.tareas.model.tickets.operations.OperateTicket;
import es.securitasdirect.tareas.support.XmlMarshaller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

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
        OperateTicket operateTicket = new OperateTicket();
        Req createReq = new Req();
        Asgto createAsgto = new Asgto();
        Comm createComm = new Comm();
        Opcod createOpcod = new Opcod();
        Clcod createClcod = new Clcod();
        Item createItem = new Item();
        Item createItem2 = new Item();
        List create_list_item = new ArrayList();


        operateTicket.setTicket(new Ticket());
        operateTicket.setUser(new User());
        operateTicket.setSvrq(new Svrq());
        //TODO VOY POR AQUI Y FALTA LA LISTA DE ITEMS






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

        /*<ITEM></ITEM>*/

        createItem.setIdType("200");
        createItem.setIdProblem("210");
        createItem.setCount("1");
        createItem.setIdItemIBS("");
        create_list_item.add(createItem);

        createItem2.setIdType("300");
        createItem2.setIdProblem("500");
        createItem2.setCount("1");
        createItem2.setIdItemIBS("");
        create_list_item.add(createItem2);

        /*
         * <USER></USER>
         */

        operateTicket.getUser().setIdUser("A13003");
        operateTicket.getUser().setIdCountry("01");
        operateTicket.getUser().setIdLanguage("ES");
        operateTicket.getUser().setT("NOSESSION");

        /*
         *
         * <TICKET></TICKET>
         */
        operateTicket.getTicket().setNumInst("731483");
        operateTicket.getTicket().setObserv("Texto Aviso");
        operateTicket.getTicket().setCodZIP("28033");
        operateTicket.getTicket().setCloseTicket("1");
        operateTicket.getTicket().setDataAditional("");
        operateTicket.getTicket().setNoteClose("");
        operateTicket.getTicket().setMorDebt("0");
        operateTicket.getTicket().setTypePanel("SDMF");
            /* <REQ></REQ>
             */
        operateTicket.getTicket().setReq(createReq);
        operateTicket.getTicket().setAsgto(createAsgto);
        operateTicket.getTicket().setComm(createComm);
        operateTicket.getTicket().setOpcod(createOpcod);
        operateTicket.getTicket().setClcod(createClcod);

        /*
         * <SVRQ></SVRQ>
         */

        operateTicket.getSvrq().setMakeSVRQ("1");
        operateTicket.getSvrq().setIdTec("CUSTVER");
        operateTicket.getSvrq().setInsBoli("1");

        operateTicket.getSvrq().setItems(new ArrayList<Item>());
        operateTicket.getSvrq().setItems(create_list_item);




        /* <ITEMS</ITEMS*/





        String xmlCreateTicket = xmlMarshaller.marshalObject(operateTicket);
        LOGGER.info("xmlCreateTicket: {}", xmlCreateTicket);
    }


}
