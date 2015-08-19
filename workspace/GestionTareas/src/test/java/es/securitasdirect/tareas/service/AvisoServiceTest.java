package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.DummyGenerator;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de Aviso
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class AvisoServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTareasServiceTest.class);

    @Inject
    protected AvisoService avisoService;
    @Inject
    protected QueryTareaService queryTareaService;


    /**
     * <DATA>
     * <USER idUser='I24311' idCountry='1' idLanguage='ES' t='NOSESSION'/>
     * <TICKET numInst='1606430' observ="Texto Aviso"  codZIP="28030" closeTicket="0" dataAditional="" noteClose="" morDebt="0" typePanel="SDVFAST">
     * <REQ idReq="ATC" reqName="" reqLname1="" reqLname2="" reqCif="" reqEmpl="I24311" />
     * <ASGTO idAsg="" idUser=""/><COMM name="" lName1="" lName2="" inChannel="AUTO" value="" coment="" outChannel="" from="11" to="18" />
     * <OPCOD codKey1="200" codKey2="210" />
     * <CLCOD codKey3="" codKey4="" /></TICKET>
     * <SVRQ makeSVRQ="0" idTec="" insBoli="0">
     * <ITEMS><item idType="200" idProblem="210" count="1" idItemIBS="" />
     * <item idType="200" idProblem="212" count="1" idItemIBS="" />
     * </ITEMS>
     * </SVRQ>
     * </DATA>
     */
    @Test
    public void createTicketTest() throws Exception {

        String idUser = "I24311";
        String idCountry = "1";
        String idLanguage = "ES";

        avisoService.createTicket(
                idUser,
                idCountry,
                idLanguage);
    }


    @Test
    public void updateTicketTest() throws Exception {
/*
        String idUser = "I24311";
        String idCountry = "1";
        String idLanguage = "ES";

        avisoService.updateTicket(
                idUser,
                idCountry,
                idLanguage);
                */
    }

    @Test
    public void delayTicketTest() throws Exception {
        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_CCT_ATT_Averia_Test";
        String idTarea = "1";
        Tarea tarea = queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);
        assertThat(tarea, notNullValue());

        Integer naviso = ((TareaAviso)tarea).getIdAviso();
        String gblidusr = "1"; // TODO
        String idaplaza = "2"; // TODO
        // sumamos un día a la fecha actual para aplazar
        Date fhasta = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fhasta);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        fhasta = calendar.getTime();

        String cnota = "3"; // TODO
        boolean ok = avisoService.delayTicket(naviso, gblidusr, idaplaza, fhasta, cnota);

        assertThat(ok, is(true));

    }

    @Test
    public void reassignmentTicketTest() throws Exception {
        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_CCT_ATT_Averia_Test";
        String idTarea = "1";
        Tarea tarea = queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);
        assertThat(tarea, notNullValue());

        Integer naviso = ((TareaAviso)tarea).getIdAviso();
        String idempleado = "1"; // TODO
        String gblidusr = "2";   // TODO
        boolean ok = avisoService.reassignmentTicket(naviso, idempleado, gblidusr);

        assertThat(ok, is(true));

    }


    @Test
    public void closeTicketTest() throws Exception {
        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_CCT_ATT_Averia_Test";
        String idTarea = "1";
        Tarea tarea = queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);
        assertThat(tarea, notNullValue());

        Integer naviso = ((TareaAviso)tarea).getIdAviso();
        String idmat = "1"; // TODO
        String cnota = "2";   // TODO
        String statusdest = "3"; // TODO
        Integer deuda = new Integer("4"); // TODO
        Integer idmante = new Integer("5"); // TODO
        Integer branch = new Integer("6"); // TODO
        Integer tcierre = new Integer("7"); // TODO
        String adicional = "8"; // TODO

        boolean ok = avisoService.closeTicket(naviso,
                idmat,
                cnota,
                statusdest,
                deuda,
                idmante,
                branch,
                tcierre,
                adicional);

        assertThat(ok, is(true));

    }




}
