package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.Tarea;
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
import java.util.ArrayList;
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
public class QueryTareasServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTareasServiceTest.class);

    @Inject
    protected QueryTareaService queryTareaService;

    @Test
    public void testQueryTarea(){
/**
 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://webservice.com/">
 <soapenv:Header/>
 <soapenv:Body>
 <web:checkCallingListContact>
 <!--Optional:-->
 <ccIdentifier>ATC_SPN</ccIdentifier>
 <!--Optional:-->
 <applicationUser>Tareas</applicationUser>
 <!--Optional:-->
 <ccUserId>12187</ccUserId>
 <!--Optional:-->
 <filter>chain_id=1</filter>
 <!--Zero or more repetitions:-->
 <returnData></returnData>
 <!--Zero or more repetitions:-->
 <callingList>CL_CCT_ATT_Averia_Test</callingList>
 <!--Optional:-->
 <country>SPAIN</country>
 </web:checkCallingListContact>
 </soapenv:Body>
 </soapenv:Envelope>

 */

        String ccIdentifier         = "ATC_SPN";
        String applicationUser      = "Tareas";
        String ccUserId             = "12187";
        String filter               = "chain_id=1";
        List<String> returnData     = new ArrayList(); // Define los datos a recuperar en la respuesta del WS, queremos que devuelva todos.
        List<String> callingList    = new ArrayList();
        String country              = "SPAIN";

        returnData.add("");
        callingList.add("CL_CCT_ATT_Averia_Test");

        Tarea tarea = queryTareaService.queryTarea(ccIdentifier,
                applicationUser,
                ccUserId,
                filter,
                returnData,
                callingList,
                country);
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
    }

}
