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
     <USER idUser="A13003" idUsidCountry="01" idLanguage="ES" t="NOSESSION"/>
     <TICKET numInst="731483" observ="Texto Aviso" codZIP="28033" closeTicket="1" dataAditional="" noteClose="" morDebt="0" typePanel="SDMF">
     <REQ idReq="GPD" reqName="" reqLname1="" reqLname2="" reqCif="" reqEmpl="A13003"/>
     <ASGTO idAsg="GPD" idUser=""/>
     <COMM name="" lName1="" lName2=""/>
     <OPCOD codKey1="200" codKey2="210"/>
     <CLCOD codKey3="" codKey4="MANTEN"/>
     </TICKET>
     <SVRQ makeSVRQ="1" idTec="CUSTVER" insBoli="1">
     <LISTADE>
     <item idType="200" idProblem="210" count="1" idItemIBS=""/>
     <item idType="300" idProblem="500" count="1" idItemIBS=""/>
     </LISTADE>
     </SVRQ>
     </DATA>     */
    @Test
    public void createTicketTest() throws Exception{

        String idUser = "1";
        String idCountry = "2";
        String idLanguage = "3";




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