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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de InstallationMonDataService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class InfopointServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfopointServiceTest.class);

    @Inject
    protected InfopointService infopointService;


    @Test
    public void completeFlow() throws Exception {
        String matriculaBuenaConPermisosParaCrearMantenimiento = "J50010";
        String codigoProcesoCrearMantenimiento = "829";
        String session = infopointService.createSession(matriculaBuenaConPermisosParaCrearMantenimiento, "0.0.0.0");
        LOGGER.info("Creada Session IP: {}" ,session);
        assertThat(session, notNullValue());


        boolean valid = infopointService.validateSession(session);
        assertThat(valid, is(true));

        boolean okAccess = infopointService.validarProceso(session, matriculaBuenaConPermisosParaCrearMantenimiento, codigoProcesoCrearMantenimiento);
        assertThat(okAccess, is(true));

        boolean notAccess = infopointService.validarProceso(session,matriculaBuenaConPermisosParaCrearMantenimiento,"677");
        assertThat(notAccess, is(true));


        infopointService.closeSession(session);
         valid = infopointService.validateSession(session);
        assertThat(valid, is(false));

    }


}
