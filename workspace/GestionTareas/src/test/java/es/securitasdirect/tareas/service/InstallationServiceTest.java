package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.InstallationData;
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
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class InstallationServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstallationServiceTest.class);

    @Inject
    protected InstallationService installationService;
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;


    @Test
    public void testInjection() {
        assertThat (installationService,notNullValue());
        assertThat (installationService.spInstallationMonData,notNullValue());
    }

    @Test
    public void testGetInstallationWSAioTareas2() throws DataServiceFault {
        List<GetInstallationDataResult> installationDataWS2 = spAioTareas2.getInstallationData(70578, 7);
        assertThat(installationDataWS2, notNullValue());
    }


    @Test
    public void key1() throws DataServiceFault {
        List<GetKey1DIYResult> key1DIY = spAioTareas2.getKey1DIY();
        assertThat(key1DIY, notNullValue());
    }

    @Test
    public void key2() throws DataServiceFault {
        List<GetKey2DIYResult> key2DIY = spAioTareas2.getKey2DIY(4848);
        assertThat(key2DIY, notNullValue());
    }

    @Test
    public void installationData() throws DataServiceFault {
        InstallationData installationData = installationService.getInstallationData("111111");
        assertThat (installationData,notNullValue());
        LOGGER.info(installationData.toString());
    }
}
