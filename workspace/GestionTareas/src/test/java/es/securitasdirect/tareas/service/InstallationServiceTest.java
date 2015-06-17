package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.InstallationData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(InstallationServiceTest.class);

    @Inject
    protected InstallationService installationService;


    @Test
    public void testInjection() {
        assertThat (installationService,notNullValue());
        assertThat (installationService.spInstallationMonData,notNullValue());
    }


    @Test
    public void installationData() throws DataServiceFault {
        InstallationData installationData = installationService.getInstallationData("111111");
        assertThat (installationData,notNullValue());
        LOGGER.info(installationData.toString());
    }
}
