package es.securitasdirect.senales;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;
//import weblogic.jndi.WLInitialContextFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-*.xml"})
public class PozueloJMSClient {
    private static final Logger log = Logger.getLogger(PozueloJMSClient.class.getName());

//
//    POZUELO
//    Cola principal
//    BUS:                       es1preosbprd01v-vip          PORT  8011
//    Endpoint:               sd.prd.es1allinoneout
//    Factoria:                 sd.prd.es1prdxacfout

    private static final String QCF_NAME = "sd.prd.es1prdxacfout";
    private static final String QUEUE_NAME = "sd.prd.es1allinoneout";
    private static final String BUS = "es1preosbprd01v-vip";
    private static final String PORT = "8011";

    @Test
    public void connectExampleWebLOGGERic() throws  Exception {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put( javax.naming.Context.PROVIDER_URL,  "t3://" + BUS + ":" + PORT);

        javax.naming.Context ctx = new InitialContext(env);
        QueueConnectionFactory qcf =  (QueueConnectionFactory) ctx.lookup(QCF_NAME);
        QueueConnection qc = qcf.createQueueConnection();
        QueueSession qsess = qc.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination dest = (Destination) ctx.lookup(QUEUE_NAME);
        MessageConsumer consumer = qsess.createConsumer(dest);
//        TextoListener listener = new TextoListener();
//        consumer.setMessageListener(listener);

    }
}

