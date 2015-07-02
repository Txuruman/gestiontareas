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

    /**
     * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://webservice.com/">
     <soapenv:Header/>
         <soapenv:Body>
         <web:checkCallingListContact>
             <ccIdentifier>ATC_SPN</ccIdentifier>
             <applicationUser>Tareas</applicationUser>
             <ccUserId>12187</ccUserId>
             <filter>chain_id=1</filter>
             <returnData></returnData>
             <callingList>CL_CCT_ATT_Averia_Test</callingList>
             <country>SPAIN</country>
         </web:checkCallingListContact>
         </soapenv:Body>
     </soapenv:Envelope>
     */
    @Test
    public void QueryTareaAviso(){


        String ccIdentifier         = "ATC_SPN";
        String applicationUser      = "Tareas";
        String ccUserId             = "12187";
        String filter               = "chain_id=1";
        String callingList    = "CL_CCT_ATT_Averia_Test";
        String country              = "SPAIN";

        Tarea tarea = queryTareaService.queryTarea(ccIdentifier,
                applicationUser,
                ccUserId,
                filter,
                callingList,
                country);
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
    }

}
