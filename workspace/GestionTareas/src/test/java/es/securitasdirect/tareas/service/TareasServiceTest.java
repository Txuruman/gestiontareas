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
    protected TareaService tareaService;

    //Web Services para hacer pruebas directamente
    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;


    @Test
    public void keys() throws DataServiceFault {
        Map<Integer, String> listakey1 = tareaService.getDesplegableKey1();
        assertThat(listakey1, notNullValue());
        assertThat(listakey1.isEmpty(), is(false));
        for (Integer id : listakey1.keySet()) {
            LOGGER.info("KEY1 id:{}  text:{}", id, listakey1.get(id));

            Map<Integer, String> listaKey2 = tareaService.getDesplegableKey2(id);
            assertThat(listaKey2, notNullValue());
            assertThat(listaKey2.isEmpty(), is(false));
            for (Integer id2 : listaKey2.keySet()) {
                LOGGER.info("\t\tKEY2 id:{}  Text:{}", id2, listaKey2.get(id2));
            }
        }
    }

    @Test
    public void directoWsgetAvisoById() throws DataServiceFault {
        List<GetAvisobyIdResult> avisobyId = spAioTareas2.getAvisobyId(10267236);
        assertThat(avisobyId, notNullValue());
    }

    /**
     * Crea un objeto de tipo TareaAviso consultando el WS
     * @param avisobyIdResult
     * @return
     */
    private TareaAviso createFromWS(GetAvisobyIdResult avisobyIdResult) {
        TareaAviso tarea = new TareaAviso();
        tarea.setIdentificativoAvisoTarea(avisobyIdResult.getIdaviso().intValue());
        tarea.setObservaciones(avisobyIdResult.getObservaciones());
        tarea.setEstado(avisobyIdResult.getEstado().toString()); //TODO
        tarea.setTitular(avisobyIdResult.getTitular());
        tarea.setFechaCreacion((avisobyIdResult.getFechaCreacion() == null ? null : avisobyIdResult.getFechaCreacion().toGregorianCalendar().getTime()));

        // Tipos de Aviso: el primero es el utilizado, el segundo y el tercero son sólo informativos.
        tarea.setTipoAviso1(avisobyIdResult.getTipo()); //TODO Pendiente sacar estos tipos
        tarea.setTipoAviso2(null);
        tarea.setTipoAviso3(null);

        //Motivos: el primero es el utilizado, el segundo y el tercero son sólo informativos.
        tarea.setMotivo1(avisobyIdResult.getMotivo()); //TODO Pendiente sacar estos motivos
        tarea.setMotivo2(null);
        tarea.setMotivo3(null);
        tarea.setRequeridoPor(avisobyIdResult.getRequeridoPor());
        tarea.setNumeroInstalacion(avisobyIdResult.getInsNo());
        tarea.setHorarioDesde(avisobyIdResult.getDesde().toString());//TODO FORMATO
        tarea.setHorarioHasta(avisobyIdResult.getHasta().toString());//TODO FORMATO
        tarea.setDatosContacto(avisobyIdResult.getContacto() + "??" + avisobyIdResult.getFormaContacto());

        return tarea;
    }


}
