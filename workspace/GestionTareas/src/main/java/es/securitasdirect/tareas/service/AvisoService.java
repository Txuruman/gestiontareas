package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.WsResponse;
import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.tickets.*;
import es.securitasdirect.tareas.model.tickets.operations.CreateTicket;
import es.securitasdirect.tareas.model.tickets.responses.DATA;
import es.securitasdirect.tareas.support.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.*;
import wsticketsv2.WsTicketsSoap;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    @Inject
    protected QueryTareaService queryTareaService;
    @Inject
    protected CCLIntegration cclIntegration;
    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;
    @Resource(name = "applicationUser")
    private String applicationUser;


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
        DATA data = xmlMarshaller.unmarshalData(xmlResult);

        LOGGER.debug("xmlCreateTicket: {} xmlResult:{}", xmlCreateTicket,xmlResult);
    }

    /**
     * creacion del XML para actualizar un Aviso. Se hace a través de un WS disponible para la aplicación de Tickets.
     */
    public void updateTicket(String idUser, String idCountry, String idLanguage){



    }


    public boolean delayTicket(Integer naviso, String gblidusr, String idaplaza, String fhasta, String cnota) throws Exception {

        boolean  result = false;
        try {
            List<RowErrorAA> rowErrorAAs = spAvisosOperaciones.aplazarAviso(naviso, gblidusr, idaplaza, fhasta, cnota);
            //TODO Debug para ver que devuelve y controlar si hay errores devolver
            if(rowErrorAAs != null && rowErrorAAs.size()==1
                    && ((RowErrorAA)((List)rowErrorAAs).get(0)).getReturnCode() != null
                    && ((RowErrorAA)((List)rowErrorAAs).get(0)).getReturnCode().equals(new BigInteger("0"))   )
            {
                result = true;
            } else
            if (rowErrorAAs != null && !rowErrorAAs.isEmpty()) {
                LOGGER.error("Error aplazando aviso {}", naviso);
                result = false;
            }

        } catch (DataServiceFault e) {
            LOGGER.error("Error aplazando aviso", e);
            return false;
        }
        return result;

    }

    /*
    public boolean reassignmentTicket(Integer naviso, String idempleado, String gblidusr) throws Exception {

        boolean  result = false;
        try {
            List<RowErrorRA> rowErrorRAs = spAvisosOperaciones.reasignarAviso(naviso, idempleado, gblidusr);
            //TODO Debug para ver que devuelve y controlar si hay errores devolver
            if(rowErrorRAs != null && rowErrorRAs.size()==1
                    && ((RowErrorCA)((List)rowErrorRAs).get(0)).getReturnCode() != null
                    && ((RowErrorCA)((List)rowErrorRAs).get(0)).getReturnCode().equals(new BigInteger("0"))   )
            {
                result = true;
            } else

            if (rowErrorRAs != null && !rowErrorRAs.isEmpty()) {
                LOGGER.error("Error reasignando aviso {}", naviso);
                return false;
            }
        } catch (DataServiceFault e) {
            LOGGER.error("Error reasignando aviso", e);
            return false;
        }

        return  true;
    }
    */


    public boolean closeTicket(Integer naviso,
                               String idmat,
                               String cnota,
                               //String statusdest,
                               Integer deuda,
                               Integer idmante,
                               Integer branch,
                               Integer tcierre,
                               String adicional,
                               boolean finalizarDesdeCrearMantenimiento) throws Exception {

        // “2” si se finaliza 	“3” si se finaliza por crear un Mantenimiento
        String statusdest = "2";
        if(finalizarDesdeCrearMantenimiento) statusdest = "3";

        boolean  result = false;
        try {
            List<RowErrorCA> rowErrorCAs = spAvisosOperaciones.cerrarAviso(naviso, idmat, cnota, statusdest,
                    deuda, idmante, branch, tcierre, adicional);
            //TODO Debug para ver que devuelve y controlar si hay errores devolver
            if(rowErrorCAs != null && rowErrorCAs.size()==1
                    && ((RowErrorCA)((List)rowErrorCAs).get(0)).getReturnCode() != null
                    && ((RowErrorCA)((List)rowErrorCAs).get(0)).getReturnCode().equals(new BigInteger("0"))   )
            {
                result = true;
            } else
            if (rowErrorCAs != null && !rowErrorCAs.isEmpty()) {
                    LOGGER.error("Error cerrando aviso {}", naviso);
                    return false;
             }
        } catch (DataServiceFault e) {
            LOGGER.error("Error cerrando aviso", e);
            return false;
        }

        return  true;
    }



}
