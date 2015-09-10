package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.TareaMantenimiento;
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
public class SecurityServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceTest.class);

    @Inject
    protected SecurityService securityService;


    /**
     * Parametros entrada ejemplo:
     *
     * bp_auth_requestDate=201509021201
     * bp_callingListManagedDesktop=CL_CCT_ATT_Averia_FastI
     * bp_out_GSW_CAMPAIGN_NAME=C_CCT_ATT_Averia_FastI
     * bp_out_GSW_CHAIN_ID=1
     * bp_out_instalacion=1606430
     * bp_out_contact_info=609051985
     * bp_currentLanguage=es
     * bp_agentPlace=P17023
     * bp_out_ctr_no=1606430
     * bp_interactionType=PREVIEW
     * bp_connid=Id003
     * bp_desktopDepartment=ATC_SPN
     * bp_out_clname=CL_CCT_ATT_Averia_FastI
     * bp_interactionDirection=INTERNAL
     * bp_agent=12171
     * bp_auth_connid=Id003
     * bp_agentIBS=I24311
     * bp_agentGroupSD=SD_CALLBACK
     * bp_auth_signature=CGba8nhPxuM5a3+yzGOpEnZjtFE=
     * bp_auth_ipAddress=192.168.5.110
     * bp_agentCountryJob=SPAIN
     * bp_agentUserSD=I24311_2
     * bp_out_GSW_CHAIN_ID_CUSTOM=1
     * bp_agentGroupOutService=OUT_SERVICE_CC_Clas_Rec
     * bp_out_contact_info=1
     * bp_out_GSW_CAMPAIGN_NAME=1
     * bp_out_GSW_RECORD_HANDLE=6
     * bp_agentPlace=P17023
     *
     * @throws Exception
     */
    @Test
    public void validate() throws Exception {
        String bp_auth_requestDate="201509021201";
        String bp_auth_ipAddress="192.168.5.1";
        String bp_auth_signature="CGba8nhPxuM5a3+yzGOpEnZjtFE=";
        String bp_auth_connid="Id003";
        securityService.validateAuthenticationRequest(bp_auth_signature, bp_auth_requestDate, bp_auth_ipAddress, bp_auth_connid);
    }


    @Test
    public void backdoor() throws Exception {
        String bp_auth_requestDate="201509021201";
        String bp_auth_ipAddress="192.168.5.1";
        String bp_auth_signature="78926738F";
        String bp_auth_connid="Id003";
        securityService.validateAuthenticationRequest(bp_auth_signature, bp_auth_requestDate, bp_auth_ipAddress, bp_auth_connid);
    }


    @Test
    public void happy() throws Exception {
        securityService.check();
    }


}
