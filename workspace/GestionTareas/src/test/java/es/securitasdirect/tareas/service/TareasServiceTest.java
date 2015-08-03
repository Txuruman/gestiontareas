package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.TareaAviso;
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
        List<Pair> datosAdicionalesCierreTareaAviso = externalDataService.getDatosAdicionalesCierreTareaAviso(0);
        LOGGER.info("datosAdicionalesCierreTareaAviso {}", datosAdicionalesCierreTareaAviso);
        assertThat(datosAdicionalesCierreTareaAviso, notNullValue());
    }


    @Test
    public void datosCierreTareaExcel() {
        List<Pair> datosCierreTareaExcel = externalDataService.getDatosCierreTareaExcel();
        LOGGER.info("datosCierreTareaExcel {}", datosCierreTareaExcel);
        assertThat(datosCierreTareaExcel, notNullValue());
    }

    @Test
    public void directoWsgetAvisoById() throws DataServiceFault {
        List<GetAvisobyIdResult> avisobyId = spAioTareas2.getAvisobyId(10267236);
        assertThat(avisobyId, notNullValue());
    }




}
