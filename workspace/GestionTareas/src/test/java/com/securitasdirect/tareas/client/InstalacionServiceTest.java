package com.securitasdirect.tareas.client;

import com.securitasdirect.tareas.service.InstalacionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wso2.ws.dataservice.SPInstallationMonData;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de InstallationMonDataService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class InstalacionServiceTest {

    @Inject
    InstalacionService instalacionService;

    @Test
    public void hola() {
        assertThat (instalacionService,notNullValue());
    }
}
