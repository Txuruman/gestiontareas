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

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void queryTareaAvisoTest(){


        String ccUserId             = "12187";
        String filter               = "chain_id=1";

        List<String> callingListList = new ArrayList<String>();

         callingListList.add("CL_CCT_ATT_Averia_Test");
         callingListList.add("CL_CCT_ATC_CRA");
         callingListList.add("CL_CCT_ATC_Recla");
         callingListList.add("CL_CCT_ATT_Averia_Cam");
         callingListList.add("CL_CCT_ATT_Averia_FastI");
         callingListList.add("CL_CCT_ATT_Averia_FastII");
         callingListList.add("CL_CCT_ATT_Averia_Iridium");
         callingListList.add("CL_CCT_ATT_Averia_Oldclass");
         callingListList.add("CL_CCT_ATT_Averia_SDM");
         callingListList.add("CL_CCT_ATT_Camaras");
         callingListList.add("CL_CCT_BO");
         callingListList.add("CL_CCT_BO_Recla");
         callingListList.add("CL_CCT_GI_Robo1");
         callingListList.add("CL_CCT_GI_Robo2");
         callingListList.add("CL_CCT_Ingles_ATC");
         callingListList.add("CL_CCT_Ingles_ATT");
         callingListList.add("CL_CCT_ODC");

        LOGGER.debug("Probando las diferentes tareas de tipo aviso");
        HashMap<String, String> results = new HashMap<String, String>();
        HashMap<String, Boolean> assertions = new HashMap<String, Boolean>();
        for (String callingList : callingListList) {
            LOGGER.info("Realizando la llamada para " + callingList);
            Tarea tarea = queryTareaService.queryTarea(
                    ccUserId,
                    callingList,
                    filter);
            LOGGER.info("Tarea: {}", tarea);
            boolean res = tarea!=null;
            assertions.put(callingList, res);
            String descResultado;
            if(res == true){
                descResultado = "Resultado OK";
            }else{
                //TODO Envolver Tarea en Bean de resultado, con los mensajes en su caso.
                descResultado = "Resultado ERROR";
            }
            results.put(callingList, descResultado);
        }
        StringBuilder sb = new StringBuilder();
        for (String key : results.keySet()) {
            String value = results.get(key);
            sb.append("\n").append(key).append(": ").append(value);
        }
        LOGGER.info("RESULTADOS TAREAS AVISO:\n{}", sb.toString());
        for (String key : assertions.keySet()) {
            Boolean value = assertions.get(key);
            assertThat(value, is(true));
        }
    }

    @Test
    public void queryTareaMantenimientoTest(){
        String ccUserId             = "12187";
        String filter               = "1";


        String callingList = "CL_TAREAS_DIY";

        LOGGER.debug("Probando la tarea de tipo Mantenimiento");

        Tarea tarea = queryTareaService.queryTarea(
                ccUserId,
                callingList,
                filter);
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
    }

    @Test
    public void queryTareaEncuestaMantenimientoTest(){
        String ccUserId             = "12187";
        String filter               = "1";

        String callingList = "CL_CCT_XLS_ENCUESTAS_MTOS";

        LOGGER.debug("Probando la tarea de tipo Encuesta Mantenimiento");

        Tarea tarea = queryTareaService.queryTarea(
                ccUserId,
                callingList,
                filter);
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
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


        Tarea tarea = queryTareaService.queryTarea(
                ccUserId,
                callingList,
                 filter
        );
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
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


        Tarea tarea = queryTareaService.queryTarea(
                ccUserId,
                callingList,
                filter
                );
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
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

        Tarea tarea = queryTareaService.queryTarea(
                ccUserId,
                callingList,
                filter
                );
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
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

        Tarea tarea = queryTareaService.queryTarea(
                ccUserId,
                callingList,
                filter
        );
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
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

        Tarea tarea = queryTareaService.queryTarea(
                ccUserId,
                callingList,
                filter
                );
        LOGGER.info("Tarea: {}", tarea);
        assertThat(tarea, notNullValue());
    }



    @Test
    public void queryTareaAvisoDirecto() throws DataServiceFault {
        TareaAviso tareaByIdAviso = queryTareaService.getTareaByIdAviso(10267236);
        assertThat(tareaByIdAviso, notNullValue());
    }


}