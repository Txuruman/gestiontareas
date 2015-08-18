package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import es.securitasdirect.tareas.model.Tarea;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de InstallationMonDataService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class SearchTareasServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTareasServiceTest.class);

    @Inject
    protected SearchTareaService searchTareaService;

    @Resource(name = "applicationUser")
    private String applicationUser;


    @Test
    public void searchByPhone(){
        String ccIdentifier="ATC_SPN";
        String ccUserId="12187";
        String phone="699590908";
        String country="SPAIN";
        List<Tarea> tareaList = searchTareaService.findByPhone(ccIdentifier, ccUserId, country, phone);
        assertThat(tareaList,notNullValue());
        for (Tarea tarea : tareaList) {
            LOGGER.info(tarea.toString());
        }
    }

    @Test
    public void searchByPhone2(){
        String ccIdentifier="ATC_SPN";
        String ccUserId="12187";
        String phone="999999";
        String country="SPAIN";
        List<Tarea> tareaList = searchTareaService.findByPhone(ccIdentifier, ccUserId, country, phone);
        assertThat(tareaList,notNullValue());
        for (Tarea tarea : tareaList) {
            LOGGER.info(tarea.toString());
        }
    }


    @Test
    public void searchByCustomer(){
        String ccIdentifier="ATC_SPN";
        String ccUserId="12187";
        String customer="1234";
        String country="SPAIN";
        List<Tarea> tareaList = searchTareaService.findByCustomer(ccIdentifier, ccUserId,  country,customer);
        assertThat(tareaList,notNullValue());
        for (Tarea tarea : tareaList) {
            LOGGER.info(tarea.toString());
        }
    }

}