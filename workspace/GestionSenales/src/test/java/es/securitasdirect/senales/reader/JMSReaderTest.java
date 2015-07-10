package es.securitasdirect.senales.reader;

import es.securitasdirect.senales.support.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.naming.NamingException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Javier Naval on 23/06/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class JMSReaderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JMSReaderTest.class);

    @Inject
    JMSReader jmsReader;


    @Test
    public void connect() throws NamingException, JMSException {
        assertThat (jmsReader,notNullValue());
        jmsReader.connect();
    }
}
