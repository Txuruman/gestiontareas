package es.securitasdirect.senales.support;

import es.securitasdirect.senales.model.Message;

import es.securitasdirect.senales.model.cibb.CIBB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.io.*;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Javier Naval on 23/06/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//Evitamos cargar el applicationContext-jms para que funcionen los test
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-bean.xml","classpath*:spring/applicationContext-ws.xml"})
public class XmlMarsaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlMarsaller.class);
    @Inject
    XmlMarshaller xmlMarshaller;

    @Test
    public void testInjection() {
        assertThat (xmlMarshaller,notNullValue());
    }

    @Test
    public void toXml() {
        CIBB cibb = new CIBB();
        String xml = xmlMarshaller.marshall(cibb);
        LOGGER.info("XML Params: {}", xml);
    }

    @Test
    public void Xml_Message() throws IOException {
        String inputXml = readFile("params.xml");
        CIBB cibb = xmlMarshaller.unmarshallCIBB(inputXml);
        LOGGER.info("CIBB:\n {} \n Source Xml: \n {}", cibb, inputXml);

        Message message = new Message(cibb);
        message.setEntryDate(new Date());
        String xmlMessage = xmlMarshaller.marshallMessage(message);
        LOGGER.info("Message:\n {} \n Message Xml: \n {}", message, xmlMessage);

        Message message2 = xmlMarshaller.unmarshallMessage(xmlMessage);
        LOGGER.info("Unmarshalled Message:\n {} \n Original Message Xml: \n {}", message, xmlMessage);
        assertThat(message.getEntryDate(), equalTo(message2.getEntryDate()));

    }

    private String readFile(String fileName) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line).append("\n");
        }
        reader.close();
        return out.toString();
    }
}
