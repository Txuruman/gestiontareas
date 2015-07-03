package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.TareaAviso;
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
        Map<Integer, String> listakey1 = externalDataService.getDesplegableKey1();
        assertThat(listakey1, notNullValue());
        assertThat(listakey1.isEmpty(), is(false));
        for (Integer id : listakey1.keySet()) {
            LOGGER.info("KEY1 id:{}  text:{}", id, listakey1.get(id));

            Map<Integer, String> listaKey2 = externalDataService.getDesplegableKey2(id);
            assertThat(listaKey2, notNullValue());
            assertThat(listaKey2.isEmpty(), is(false));
            for (Integer id2 : listaKey2.keySet()) {
                LOGGER.info("\t\tKEY2 id:{}  Text:{}", id2, listaKey2.get(id2));
            }
        }
    }

    @Test
    public void datosAdicionalesCierreTareaAviso() {
        Map<Integer, String> datosAdicionalesCierreTareaAviso = externalDataService.getDatosAdicionalesCierreTareaAviso();
        LOGGER.info("datosAdicionalesCierreTareaAviso {}", datosAdicionalesCierreTareaAviso);
        assertThat(datosAdicionalesCierreTareaAviso, notNullValue());
    }


    @Test
    public void datosCierreTareaExcel() {
        Map<Integer, String> datosCierreTareaExcel = externalDataService.getDatosCierreTareaExcel();
        LOGGER.info("datosCierreTareaExcel {}", datosCierreTareaExcel);
        assertThat(datosCierreTareaExcel, notNullValue());
    }

    @Test
    public void directoWsgetAvisoById() throws DataServiceFault {
        List<GetAvisobyIdResult> avisobyId = spAioTareas2.getAvisobyId(10267236);
        assertThat(avisobyId, notNullValue());
    }




}
