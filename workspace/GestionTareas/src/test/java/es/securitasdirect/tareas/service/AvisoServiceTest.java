package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
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

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test del Servicio de Aviso
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class AvisoServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTareasServiceTest.class);

    @Inject
    protected AvisoService avisoService;

    //@Inject
    //private CCLIntegration cclIntegration;
    @Inject
    private TareaServiceTools tareaServiceTools;
    @Resource(name = "applicationUser")
    private String applicationUser;


    /**
     *
     <DATA>
     <USER idUser='I24311' idCountry='1' idLanguage='ES' t='NOSESSION'/>
     <TICKET numInst='1606430' observ="Texto Aviso"  codZIP="28030" closeTicket="0" dataAditional="" noteClose="" morDebt="0" typePanel="SDVFAST">
     <REQ idReq="ATC" reqName="" reqLname1="" reqLname2="" reqCif="" reqEmpl="I24311" />
     <ASGTO idAsg="" idUser=""/><COMM name="" lName1="" lName2="" inChannel="AUTO" value="" coment="" outChannel="" from="11" to="18" />
     <OPCOD codKey1="200" codKey2="210" />
     <CLCOD codKey3="" codKey4="" /></TICKET>
     <SVRQ makeSVRQ="0" idTec="" insBoli="0">
     <ITEMS><item idType="200" idProblem="210" count="1" idItemIBS="" />
     <item idType="200" idProblem="212" count="1" idItemIBS="" />
     </ITEMS>
     </SVRQ>
     </DATA>     */
    @Test
    public void createTicketTest() throws Exception{

        String idUser = "I24311";
        String idCountry = "1";
        String idLanguage = "ES";




//         String callingList = "CL_CCT_ATT_Averia_Test";
//         String callingList = "CL_CCT_ATC_CRA";
//         String callingList = "CL_CCT_ATC_Recla";
//         String callingList = "CL_CCT_ATT_Averia_Cam";
//         String callingList = "CL_CCT_ATT_Averia_FastI";
//         String callingList = "CL_CCT_ATT_Averia_FastII";
//         String callingList = "CL_CCT_ATT_Averia_Iridium";
//         String callingList = "CL_CCT_ATT_Averia_Oldclass";
//         String callingList = "CL_CCT_ATT_Averia_SDM";
//         String callingList = "CL_CCT_ATT_Camaras";
//         String callingList = "CL_CCT_BO";
//         String callingList = "CL_CCT_BO_Recla";
//         String callingList = "CL_CCT_GI_Robo1";
//         String callingList = "CL_CCT_GI_Robo2";
//         String callingList = "CL_CCT_Ingles_ATC";
//         String callingList = "CL_CCT_Ingles_ATT";
//         String callingList = "CL_CCT_ODC";

            avisoService.createTicket(
                    idUser,
                    idCountry,
                    idLanguage);
            //LOGGER.info("Tarea: {}", tarea);




    }




    @Resource(name = "callingListToModel")
    private Map<String, List<String>> callingListToModel;

}
