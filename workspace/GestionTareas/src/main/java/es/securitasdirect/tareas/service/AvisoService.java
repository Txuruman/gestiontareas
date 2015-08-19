package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.tickets.*;
import es.securitasdirect.tareas.model.tickets.operations.CreateTicket;
import es.securitasdirect.tareas.support.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wsticketsv2.WsTicketsSoap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
@Named(value = "avisoService")
@Singleton
public class AvisoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvisoService.class);

    @Inject
    protected WsTicketsSoap wsTickets;
    @Inject
    protected XmlMarshaller xmlMarshaller;

    /**
     * creacion del XML para crear un Aviso. Se hace a través de un WS disponible para la aplicación de Tickets.
     */
    public void createTicket(String idUser, String idCountry, String idLanguage){
        /*
         * Estructura del XML
         */
        CreateTicket createTicket = new CreateTicket();

        Req createReq = new Req();
        Asgto createAsgto = new Asgto();
        Comm createComm = new Comm();
        Opcod createOpcod = new Opcod();
        Clcod createClcod = new Clcod();
        Item createItem = new Item();
        List create_list_item = new ArrayList();

        createTicket.setUser(new User());
        createTicket.setTicket(new Ticket());
        createTicket.setSvrq(new Svrq());


        /* <REQ></REQ> */
        createReq.setIdReq("ATC");
        createReq.setReqName("");
        createReq.setReqLname1("");
        createReq.setReqLname2("");
        createReq.setReqCif("");
        createReq.setReqEmpl("I24311");
        /* <ASGTO></ASGTO>*/
        createAsgto.setIdAsg("");
        createAsgto.setIdUser("");
        /* <COMM></COMM>*/
        createComm.setName("");
        createComm.setlName1("");
        createComm.setlName2("");
        createComm.setInChannel("AUTO");
        createComm.setValue("");
        createComm.setComent("");
        createComm.setOutChannel("");
        createComm.setFrom("11");
        createComm.setTo("18");
        /* <OPCOD></OPCOD>*/
        createOpcod.setCodKey1("200");
        createOpcod.setCodKey2("210");
        /* <CLCOD></CLCOD>*/
        createClcod.setCodKey3("");
        createClcod.setCodKey4("");

        /* <ITEM></ITEM> */
        createItem.setIdItemIBS("");
        createItem.setCount("1");
        createItem.setIdProblem("210");
        createItem.setIdType("200");
        create_list_item.add(createItem);

        /* <ITEM></ITEM> */
        createItem.setIdItemIBS("");
        createItem.setCount("1");
        createItem.setIdProblem("210");
        createItem.setIdType("200");
        create_list_item.add(createItem);


        /*
         * <USER></USER>
         */
        createTicket.getUser().setIdUser("I24311");
        createTicket.getUser().setIdCountry("1");
        createTicket.getUser().setIdLanguage("ES");
        createTicket.getUser().setT("NOSESSION");

        /*
         *
         * <TICKET></TICKET>
         */
        createTicket.getTicket().setNumInst("1606430");
        createTicket.getTicket().setObserv("Texto Aviso");
        createTicket.getTicket().setCodZIP("28030");
        createTicket.getTicket().setCloseTicket("0");
        createTicket.getTicket().setDataAditional("");
        createTicket.getTicket().setNoteClose("");
        createTicket.getTicket().setMorDebt("0");
        createTicket.getTicket().setTypePanel("SDVFAST");

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
        createTicket.getSvrq().setMakeSVRQ("0");
        createTicket.getSvrq().setIdTec("");
        createTicket.getSvrq().setInsBoli("0");
        createTicket.getSvrq().setItems(new ArrayList<Item>());
        createTicket.getSvrq().setItems(create_list_item);




        String xmlCreateTicket = xmlMarshaller.marshalObject(createTicket);
        xmlCreateTicket=xmlCreateTicket.replaceAll("\n", "");

        String xmlResult = wsTickets.create(xmlCreateTicket);

        LOGGER.debug("xmlCreateTicket: {} xmlResult:{}", xmlCreateTicket,xmlResult);
    }


    public void updateTicket(){
        wsTickets.updateTicket("");
    }

    public void finalizeTicket(){
        //wsTickets.finalizeTicket("");
    }

}
