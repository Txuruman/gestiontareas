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
 * Test del Servicio de InstallationMonDataService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class QueryTareasServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTareasServiceTest.class);

    @Inject
    protected QueryTareaService queryTareaService;

    @Inject
    private CCLIntegration cclIntegration;
    @Inject
    private TareaServiceTools tareaServiceTools;
    @Resource(name = "applicationUser")
    private String applicationUser;

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
    public void queryTareaAvisoTest(){


        String ccUserId             = "12187";
        String id               = "1";
        String callingList = "CL_CCT_ATT_Averia_Test";

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

        try{
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    id);
            LOGGER.info("Tarea: {}", tarea);
        }catch(Exception e){

        }



    }

    @Test
    public void queryTareaMantenimientoTest(){
        String ccUserId             = "12187";
        String filter               = "1";


        String callingList = "CL_TAREAS_DIY";

        LOGGER.debug("Probando la tarea de tipo Mantenimiento");

        try{
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    filter);
            LOGGER.info("Tarea: {}", tarea);
            assertThat(tarea, notNullValue());
        }catch(Exception e){

        }
    }

    @Test
    public void queryTareaEncuestaMantenimientoTest(){
        String ccUserId             = "12187";
        String filter               = "1";

        String callingList = "CL_CCT_XLS_ENCUESTAS_MTOS";

        LOGGER.debug("Probando la tarea de tipo Encuesta Mantenimiento");

        try{
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    filter);
            LOGGER.info("Tarea: {}", tarea);
            assertThat(tarea, notNullValue());
        }catch(Exception e){

        }

    }

    /*<entry key="TareaEncuestaMarketing">
    <util:list list-class="java.util.ArrayList" value-type="java.lang.String">
    <value>CL_CCT_XLS_ENCUESTAS_MKT</value>
    </util:list>
    </entry>*/
    @Test
    public void queryTareaEncuestaMarketingTest(){
        String ccUserId             = "12187";
        String filter               = "1";
        String callingList = "CL_CCT_XLS_ENCUESTAS_MKT";

        LOGGER.debug("Probando la tarea de tipo Encuesta Marketing");


        try{
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    filter
            );
            LOGGER.info("Tarea: {}", tarea);
            assertThat(tarea, notNullValue());
        }catch(Exception e){

        }

    }

    /*
    <entry key="TareaKeybox">
    <util:list list-class="java.util.ArrayList" value-type="java.lang.String">
    <value>CL_CCT_XLS_KEYBOX</value>
    </util:list>
    </entry>
    */
    @Test
    public void queryTareaKeyboxTest(){
        String ccUserId             = "12187";
        String filter               = "1";
        String callingList = "CL_CCT_XLS_KEYBOX";

        LOGGER.debug("Probando la tarea de tipo Keybox");


        try{
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    filter
            );
            LOGGER.info("Tarea: {}", tarea);
            assertThat(tarea, notNullValue());
        }catch(Exception e){

        }

    }

    /*
    <entry key="TareaLimpiezaCuota">
    <util:list list-class="java.util.ArrayList" value-type="java.lang.String">
    <value>CL_CCT_XLS_LIMPIEZA_CUOTA</value>
    </util:list>
    </entry>
    */
    @Test
    public void queryTareaLimpiezaCuotaTest(){
        String ccUserId             = "12187";
        String filter               = "1";
        String callingList = "CL_CCT_XLS_LIMPIEZA_CUOTA";

        LOGGER.debug("Probando la tarea de tipo Limpieza de Couta");

        try{
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    filter
            );
            LOGGER.info("Tarea: {}", tarea);
            assertThat(tarea, notNullValue());
        }catch(Exception e){

        }

    }
    /*
    <entry key="TareaListadoAssistant">
    <util:list list-class="java.util.ArrayList" value-type="java.lang.String">
    <value>CL_CCT_XLS_ASSISTANT</value>
    </util:list>
    </entry>
    */
    @Test
    public void queryTareaListadoAssistantTest(){
        String ccUserId             = "12187";
        String filter               = "1";
        String callingList = "CL_CCT_XLS_ASSISTANT";
        LOGGER.debug("Probando la tarea de tipo Listado Assistant");

        try{
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    filter
            );
            LOGGER.info("Tarea: {}", tarea);
            assertThat(tarea, notNullValue());
        }catch(Exception e){

        }

    }
    /*
    <entry key="TareaOtrasCampanas">
    <util:list list-class="java.util.ArrayList" value-type="java.lang.String">
    <value>CL_CCT_XLS_OTRAS_CAM</value>
    </util:list>
    </entry>
*/
    @Test
    public void queryTareaOtrasCampanasTest(){
        String ccUserId             = "12187";
        String filter               = "1";
        String callingList = "CL_CCT_XLS_ATC";

        LOGGER.debug("Probando la tarea de tipo Otras campañas");

        try{
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    filter
            );
            LOGGER.info("Tarea: {}", tarea);
            assertThat(tarea, notNullValue());
        }catch(Exception e){

        }

    }


    @Resource(name = "callingListToModel")
    private Map<String, List<String>> callingListToModel;




}