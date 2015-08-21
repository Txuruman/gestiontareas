package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.model.external.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.GetAvisobyIdResult;
import org.wso2.ws.dataservice.SPAIOTAREAS2PortType;
import org.wso2.ws.dataservice.SPAVISOSOPERACIONESPortType;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de InstallationMonDataService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class TareasServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TareasServiceTest.class);

    @Inject
    protected ExternalDataService externalDataService;

    //Web Services para hacer pruebas directamente
    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;
    @Inject
    protected TareaService tareaService;
    @Inject
    protected QueryTareaService queryTareaService;


    @Test
    public void keys() throws DataServiceFault {
        List<Pair> listakey1 = externalDataService.getDesplegableKey1();
        assertThat(listakey1, notNullValue());
        assertThat(listakey1.isEmpty(), is(false));
        for (Pair pair : listakey1) {
            LOGGER.info("KEY1 id:{}  text:{}", pair.getId(), listakey1.get(pair.getId()));

            List<Pair> listaKey2 = externalDataService.getDesplegableKey2(pair.getId());
            assertThat(listaKey2, notNullValue());
            assertThat(listaKey2.isEmpty(), is(false));
            for (Pair pair2 : listaKey2) {
                LOGGER.info("\t\tKEY2 id:{}  Text:{}", pair2.getId(), listaKey2.get(pair2.getId()));
            }
        }
    }

    @Test
    public void datosAdicionalesCierreTareaAviso() throws DataServiceFault {
        List<Pair> datosAdicionalesCierreTareaAviso = externalDataService.getDatosAdicionalesCierreTareaAviso();
        LOGGER.info("datosAdicionalesCierreTareaAviso {}", datosAdicionalesCierreTareaAviso);
        assertThat(datosAdicionalesCierreTareaAviso, notNullValue());
    }

    @Test
    public void directoWsgetAvisoById() throws DataServiceFault {
        List<GetAvisobyIdResult> avisobyId = spAioTareas2.getAvisobyId(10267236);
        assertThat(avisobyId, notNullValue());
    }

    @Test
    public void delayTask() throws Exception {
        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_CCT_XLS_LIMPIEZA_CUOTA";
        String idTarea = "2";
        Tarea tarea = queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);

        Date schedTime = new Date();
        String recordType="5";
        boolean ok = tareaService.delayTask(agent, tarea,
                schedTime,
                recordType);

        assertThat(ok, is(true));
    }


    @Test
    public void finalizarTareaAviso() throws Exception {
        //Cada vez que se ejecuta hay que cambiar la tarea porque no la contrará
        Agent agent = DummyGenerator.getAgent();
        TareaAviso tarea = (TareaAviso) queryTareaService.queryTarea(agent, "CL_CCT_ATC_CRA", "1");

        tarea.setClosing("ILOC");
        tarea.setDatosAdicionalesCierre("1");

        boolean ok = tareaService.finalizeNotificationTask(agent, tarea);
        assertThat(ok,is(true));
    }

    @Test
    public void finalizeTask() throws Exception {
        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_CCT_XLS_LIMPIEZA_CUOTA";
        String idTarea = "2";
        Tarea tarea = queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);


        boolean ok = tareaService.finalizeTask(agent,tarea );

        assertThat(ok, is(true));
    }



    @Test
    public void createTask() throws Exception {

        Agent agent = DummyGenerator.getAgent();
        String callingList = "CL_TAREAS_DIY";
        String idTarea = "1";
        TareaMantenimiento tareaMantenimiento = (TareaMantenimiento)queryTareaService.queryTarea(agent.getIdAgent(), agent.getAgentCountryJob(), agent.getDesktopDepartment(), callingList, idTarea);
        assertThat(tareaMantenimiento, notNullValue());

        tareaService.createTask(agent, tareaMantenimiento);

    }


}
