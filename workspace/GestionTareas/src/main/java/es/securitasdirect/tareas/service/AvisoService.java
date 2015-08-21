package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.WsResponse;
import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
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
    public void createTicket(Agent agent, TareaAviso tareaAviso, InstallationData installationData) {

        String idUser = agent.getIdAgent();

        // TODO Hacerlo por spring
        String idCountry = agent.getAgentCountryJob();
        if("SPAIN".equals(agent.getAgentCountryJob())) idCountry = "1";
        else if("PORTUGAL".equals(agent.getAgentCountryJob())) idCountry = "2";
        else if("FRANCE".equals(agent.getAgentCountryJob())) idCountry = "3";

        String idLanguage = agent.getCurrentLanguage();
        String idReq = agent.getDesktopDepartment();

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
        createReq.setIdReq(idReq);
        createReq.setReqName(""); // constante
        createReq.setReqLname1(""); // constante
        createReq.setReqLname2(""); // constante
        createReq.setReqCif(""); // constante
        createReq.setReqEmpl(idUser);
        /* <ASGTO></ASGTO>*/
        createAsgto.setIdAsg(""); // constante
        createAsgto.setIdUser(idUser);
        /* <COMM></COMM>*/
        createComm.setName(""); // constante
        createComm.setlName1(""); // constante
        createComm.setlName2(""); // constante
        createComm.setInChannel("TELF"); // constante
        createComm.setValue(tareaAviso.getTelefono());
        createComm.setComent(""); // constante
        createComm.setOutChannel(""); // constante
        createComm.setFrom(tareaAviso.getHorarioDesde());
        createComm.setTo(tareaAviso.getHorarioHasta());
        /* <OPCOD></OPCOD>*/
        createOpcod.setCodKey1(tareaAviso.getTipoAviso1());
        createOpcod.setCodKey2(tareaAviso.getMotivo1());
        /* <CLCOD></CLCOD>*/
        createClcod.setCodKey3(""); // constante
        createClcod.setCodKey4(""); // constante

        /* <ITEM></ITEM> */
        createItem.setIdItemIBS(""); // constante
        createItem.setCount("1");    // constante
        createItem.setIdProblem(tareaAviso.getMotivo1());
        createItem.setIdType(tareaAviso.getTipoAviso1());
        create_list_item.add(createItem);

        /* <ITEM></ITEM> */
        createItem.setIdItemIBS(""); // constante
        createItem.setCount("1");    // constante
        createItem.setIdProblem(tareaAviso.getMotivo2());
        createItem.setIdType(tareaAviso.getTipoAviso2());
        create_list_item.add(createItem);

        /* <ITEM></ITEM> */
        createItem.setIdItemIBS(""); // constante
        createItem.setCount("1");    // constante
        createItem.setIdProblem(tareaAviso.getMotivo3());
        createItem.setIdType(tareaAviso.getTipoAviso3());
        create_list_item.add(createItem);

        /*
         * <USER></USER>
         */
        createTicket.getUser().setIdUser(idUser);
        createTicket.getUser().setIdCountry(idCountry);
        createTicket.getUser().setIdLanguage(idLanguage);
        createTicket.getUser().setT("NOSESSION");  // constante

        /*
         *
         * <TICKET></TICKET>
         */
        createTicket.getTicket().setNumInst(tareaAviso.getNumeroInstalacion());
        createTicket.getTicket().setObserv(tareaAviso.getObservaciones());
        createTicket.getTicket().setCodZIP("28030"); // TODO InstallationData no tiene codigo postal
        createTicket.getTicket().setCloseTicket("0"); // constante
        createTicket.getTicket().setDataAditional(""); // constante
        createTicket.getTicket().setNoteClose(""); // constante
        createTicket.getTicket().setMorDebt("0"); // constante
        createTicket.getTicket().setTypePanel(installationData.getPanel());

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
        createTicket.getSvrq().setMakeSVRQ("0"); // constante
        createTicket.getSvrq().setIdTec(""); // constante
        createTicket.getSvrq().setInsBoli("0"); // constante
        createTicket.getSvrq().setItems(new ArrayList<Item>());
        createTicket.getSvrq().setItems(create_list_item);


        String xmlCreateTicket = xmlMarshaller.marshalObject(createTicket);
        xmlCreateTicket = xmlCreateTicket.replaceAll("\n", "");

        String xmlResult = wsTickets.create(xmlCreateTicket);
        //TODO Debug para ver que devuelve y controlar si hay errores devolver
        DATA data = xmlMarshaller.unmarshalData(xmlResult);

        LOGGER.debug("xmlCreateTicket: {} xmlResult:{}", xmlCreateTicket, xmlResult);
    }

    /**
     * creacion del XML para actualizar un Aviso. Se hace a través de un WS disponible para la aplicación de Tickets.
     */
    public void updateTicket(Agent agent) {

        String idUser = agent.getIdAgent();
        String idCountry = agent.getAgentCountryJob();
        String idLanguage = agent.getCurrentLanguage();
        String idReq = agent.getDesktopDepartment();

    }

    /**
     * @param naviso   nº de aviso
     * @param gblidusr matrícula del usuario: recibido de IWS en parámetro bp_agent
     * @param idaplaza tipo de aplazamiento: si lo admite, dejarlo vacío. Si no, poner “APR”
     * @param fhasta   fecha a la que se aplaza dd/mm/aaaa
     * @return
     * @throws Exception
     */
    public boolean delayTicket(Integer naviso, String gblidusr, String idaplaza, String fhasta) throws Exception {

        String cnota = ""; // constante
        boolean result = false;
        try {
            List<RowErrorAA> rowErrorAAs = spAvisosOperaciones.aplazarAviso(naviso, gblidusr, idaplaza, fhasta, cnota);
            //TODO Debug para ver que devuelve y controlar si hay errores devolver
            if (rowErrorAAs != null && rowErrorAAs.size() == 1
                    && ((RowErrorAA) ((List) rowErrorAAs).get(0)).getReturnCode() != null
                    && ((RowErrorAA) ((List) rowErrorAAs).get(0)).getReturnCode().equals(new BigInteger("0"))) {
                result = true;
            } else if (rowErrorAAs != null && !rowErrorAAs.isEmpty()) {
                LOGGER.error("Error aplazando aviso {}", naviso);
                result = false;
            }

        } catch (DataServiceFault e) {
            LOGGER.error("Error aplazando aviso", e);
            return false;
        }
        return result;

    }


    /**
     * @param idAviso                          nº de aviso
     * @param idAgente                         matrícula del usuario: recibido de IWS en parámetro bp_agent
     * @param codTipoCierre                    código del tipo de cierre, seleccionado por pantalla. Valor de la tabla TIPOCIERRE
     * @param codTipoCierreAdicional           datos adicionales de cierre, seleccionado por pantalla. Valor de la tabla DATOADICIONAL
     * @param finalizarDesdeCrearMantenimiento valor de la tabla ESTADOS: “2” si se finaliza, “3” si se finaliza por crear un Mantenimiento
     * @return
     * @throws Exception
     */
    public boolean closeTicket(Integer idAviso,
                               String idAgente,
                               Integer codTipoCierre,
                               String codTipoCierreAdicional,
                               boolean finalizarDesdeCrearMantenimiento) throws Exception {

        Integer deuda = 0; // constante
        Integer idmante = 0; // constante
        Integer branch = 0; // constante
        String nota = ""; // nota de cierre asociada a las observaciones del Aviso

        // “2” si se finaliza 	“3” si se finaliza por crear un Mantenimiento
        String statusdest = finalizarDesdeCrearMantenimiento ? "2" : "3";

        boolean result = false;
        try {
            List<RowErrorCA> rowErrorCAs = spAvisosOperaciones.cerrarAviso(idAviso, idAgente, nota, statusdest,
                    deuda, idmante, branch, codTipoCierre, codTipoCierreAdicional);
            //TODO Debug para ver que devuelve y controlar si hay errores devolver
            if (rowErrorCAs != null && rowErrorCAs.size() == 1
                    && ((RowErrorCA) ((List) rowErrorCAs).get(0)).getReturnCode() != null
                    && ((RowErrorCA) ((List) rowErrorCAs).get(0)).getReturnCode().equals(new BigInteger("0"))) {
                result = true;
            } else if (rowErrorCAs != null && !rowErrorCAs.isEmpty()) {
                LOGGER.error("Error cerrando aviso {}", idAviso);
                result = false;
            }
        } catch (DataServiceFault e) {
            LOGGER.error("Error cerrando aviso", e);
            result = false;
        }

        return result;
    }


}
