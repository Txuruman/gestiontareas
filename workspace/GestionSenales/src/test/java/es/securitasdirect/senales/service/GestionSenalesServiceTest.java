package es.securitasdirect.senales.service;

import es.securitasdirect.senales.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.GetInstallationDataResult;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Javier Naval on 23/06/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//Evitamos cargar el applicationContext-jms para que funcionen los test
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-bean.xml","classpath*:spring/applicationContext-ws.xml"})
public class GestionSenalesServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestionSenalesServiceTest.class);

    @Inject
    GestionSenalesService gestionSenalesService;

    @Test
    public void inject() {
        assertThat(gestionSenalesService, notNullValue());
    }

    @Test
    public void workingHour() {
        Date date = new Date();
        date.setHours(5);
        boolean workingHour = gestionSenalesService.isWorkingHours(date);
        assertThat(workingHour, is(false));

        date.setHours(20);
        workingHour = gestionSenalesService.isWorkingHours(date);
        assertThat(workingHour, is(false));

        date.setHours(13);
        workingHour = gestionSenalesService.isWorkingHours(date);
        assertThat(workingHour, is(true));

        date.setHours(7);
        date.setMinutes(59);
        workingHour = gestionSenalesService.isWorkingHours(date);
        assertThat(workingHour, is(false));

        date.setHours(18);
        date.setMinutes(31);
        workingHour = gestionSenalesService.isWorkingHours(date);
        assertThat(workingHour, is(false));

        date.setHours(18);
        date.setMinutes(29);
        workingHour = gestionSenalesService.isWorkingHours(date);
        assertThat(workingHour, is(true));

    }

    @Test
    public void testDiscarOldMessage() {
        long DAY = 24*60*60*1000;
        Message m = new Message();
        m.setEntryDate(new Date());
        boolean discard=false;
         discard = gestionSenalesService.isExpired(m);
        assertThat(discard, is(false));

        m.setEntryDate(new Date(System.currentTimeMillis() - 1 * DAY));
        discard = gestionSenalesService.isExpired(m);
        assertThat(discard, is(false));


        m.setEntryDate(new Date(System.currentTimeMillis() - 3*DAY));
        discard = gestionSenalesService.isExpired(m);
        assertThat(discard, is(false));

        m.setEntryDate(new Date(System.currentTimeMillis() - 5 * DAY));
        discard = gestionSenalesService.isExpired(m);
        assertThat(discard, is(true));
    }

    @Test
    public void testLogFormat() {
        LOGGER.info("example");
    }


    /**
     * Para probar que los WSDL estén bien, si hay errores de XML es porque las dos funciones en los WSDL de consulta de
     * instalación se llaman igual y tienen distintos parametros, hay que editarlos a mano
     */
    @Test
    public void getInstallation() throws DataServiceFault {
        GestionSenalesService.MixedInstallationData mixedInstallationData = gestionSenalesService.getInstallationData(1732839);
        assertThat(mixedInstallationData,notNullValue());
        assertThat(mixedInstallationData.installationDataResultInstallation,notNullValue());
        assertThat(mixedInstallationData.installationDataResultTareas,notNullValue());
        LOGGER.info("installationData: {}",mixedInstallationData);
    }

}
