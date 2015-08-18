package es.securitasdirect.tareas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Test del Servicio de Aviso
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class AvisoServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTareasServiceTest.class);

    @Inject
    protected AvisoService avisoService;

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

        avisoService.createTicket(
                idUser,
                idCountry,
                idLanguage);
    }

}
