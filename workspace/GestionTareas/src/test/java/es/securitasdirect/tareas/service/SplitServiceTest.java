package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.DummyGenerator;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaAviso;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wso2.ws.dataservice.*;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de InstallationMonDataService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class SplitServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplitServiceTest.class);

    @Inject
    protected SplitService splitService;
   @Inject
   protected QueryTareaService queryTareaService;
    @Inject
    protected InstallationService installationService;

    @Test
    public void split() throws Exception {
        Agent agent = DummyGenerator.getAgent();

        //TareaAviso tareaAviso = (TareaAviso) queryTareaService.queryTarea(agent,"CL_CCT_ATC_CRA","0");
        TareaAviso tareaAviso = (TareaAviso) queryTareaService.queryTarea(agent,"CL_CCT_ATC_Recla","26");
        assertThat(tareaAviso,notNullValue());

        InstallationData installationData = installationService.getInstallationData(tareaAviso.getNumeroInstalacion());
        assertThat(installationData, notNullValue());

        splitService.getMaintenanceApplication(agent,tareaAviso, installationData);
    }

}
