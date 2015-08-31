package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wso2.ws.dataservice.GetInstallationDataInput;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
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
    @Inject
    protected InstallationService installationService;

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

        Agent agent = DummyGenerator.getAgent();


        TareaAviso tareaAviso = new TareaAviso();

        tareaAviso.setTelefono("911234567");
        tareaAviso.setHorarioDesde("10");
        tareaAviso.setHorarioHasta("15");

        tareaAviso.setTipoAviso1("200");
        tareaAviso.setMotivo1("210");
        tareaAviso.setTipoAviso2("200");
        tareaAviso.setMotivo2("210");
        tareaAviso.setTipoAviso3("200");
        tareaAviso.setMotivo3("210");


        tareaAviso.setNumeroInstalacion("1829827");
        tareaAviso.setObservaciones("observaciones");




        InstallationData  installationData = installationService.getInstallationData("1829827");

        avisoService.createTicket(agent, tareaAviso, installationData);
    }

    @Test
    public void updateTicketTest() throws Exception {

        Agent agent = DummyGenerator.getAgent();
        agent.setIdAgent("I24311"); // usuario con permiso para actualizar

        String callingList = "CL_CCT_ATC_CRA";
        String idTarea = "4";
        TareaAviso tareaAviso = (TareaAviso)queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);
        assertThat(tareaAviso, notNullValue());

        InstallationData  installationData = installationService.getInstallationData("1829827");

        avisoService.updateTicket(agent, tareaAviso, installationData);

    }

    @Test
    public void delayTicketTest() throws Exception {
        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_CCT_ATC_CRA";
        String idTarea = "3";
        TareaAviso tarea = (TareaAviso)queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);
        assertThat(tarea, notNullValue());

        Integer naviso = tarea.getIdAviso();
        String gblidusr = agent.getIdAgent();
        String idaplaza = "";
        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        String fhasta =format.format(fecha);
        boolean ok = avisoService.delayTicket(naviso, gblidusr, idaplaza, fhasta);

        assertThat(ok, is(true));

    }

    @Test
    public void closeTicketTest() throws Exception {
        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_CCT_ATC_CRA";
        String idTarea = "3";
        Tarea tarea = queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);
        assertThat(tarea, notNullValue());

        Integer naviso = ((TareaAviso)tarea).getIdAviso();
        String idmat = agent.getIdAgent();
        String cnota = ((TareaAviso)tarea).getObservaciones();
        boolean finalizarDesdeCrearMantenimiento = false;
        String tcierre = ((TareaAviso)tarea).getClosing();
        Integer adicional = 0;
        if( ((TareaAviso)tarea).getDatosAdicionalesCierre()  != null)
        {
            adicional = Integer.valueOf(((TareaAviso)tarea).getDatosAdicionalesCierre());
        }

        boolean ok = avisoService.closeTicket(naviso,
                idmat,
                tcierre,
                adicional,
                finalizarDesdeCrearMantenimiento
                );

        assertThat(ok, is(true));

    }

    @Test
    public void unmarkTicketTest() throws Exception {


        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_CCT_ATC_CRA";
        String idTarea = "1";
        Tarea tarea = queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);
        assertThat(tarea, notNullValue());

        Integer naviso = ((TareaAviso)tarea).getIdAviso();


        boolean ok = avisoService.unmarkTicket(naviso);

        assertThat(ok, is(true));
    }




}
