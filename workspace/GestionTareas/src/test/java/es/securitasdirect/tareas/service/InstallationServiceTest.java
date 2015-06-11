package es.securitasdirect.tareas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de InstallationMonDataService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class InstallationServiceTest {

    @Inject
    protected InstallationService installationService;


    @Test
    public void testInjection() {
        assertThat (installationService,notNullValue());
        assertThat (installationService.spInstallationMonData,notNullValue());
    }


    @Test
    public void installationData() throws DataServiceFault {
        installationService.getInstallationData("111111");
        assertThat (installationService,notNullValue());
    }
}
