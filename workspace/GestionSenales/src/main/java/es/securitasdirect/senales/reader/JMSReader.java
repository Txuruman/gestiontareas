package es.securitasdirect.senales.reader;

import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.service.GestionSenalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Date;
import java.util.Hashtable;

/**
 * Lector de mensajes de bus JMS.
 * <p/>
 * Created by Team Vision
 */
public class JMSReader {

    /*
    POZUELO
        Cola principal
        BUS:                       es1preosbprd01v-vip          PORT  8011
        Endpoint:               sd.prd.es1allinoneout
        Factoria:                 sd.prd.es1prdxacfout
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JMSReader.class);

    private static final String QCF_NAME = "sd.prd.es1prdxacfout";
    private static final String QUEUE_NAME = "sd.prd.es1allinoneout";
    private static final String BUS = "es1preosbprd01v-vip";
    private static final String PORT = "8011";


//    private static final String QCF_NAME = "sd.prd.es1prdxacfout";
//    private static final String QUEUE_NAME = "sd.prd.es1allinoneout";
//
//    // Set up all the default values
//    private static final String DEFAULT_MESSAGE = "Hello, World!";
//    private static final String DEFAULT_CONNECTION_FACTORY = "reader/RemoteConnectionFactory";
//    private static final String DEFAULT_DESTINATION = "reader/queue/test";
//    private static final String DEFAULT_MESSAGE_COUNT = "1";
//    private static final String DEFAULT_USERNAME = "admin";
//    private static final String DEFAULT_PASSWORD = "admin!";
//    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
//    private static final String PROVIDER_URL = "remote://localhost:4447";

    @Autowired
    protected GestionSenalesService gestionSenalesService;

    public JMSReader() {
    }

    @PostConstruct
    public void connect() {
        assert gestionSenalesService != null;
        LOGGER.info("JMS Reader connecting..."); //TODO Imprimir config

        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            env.put(javax.naming.Context.PROVIDER_URL, "t3://" + BUS + ":" + PORT);
            if (true) { //TODO Username y password !null !empty
                env.put(javax.naming.Context.SECURITY_PRINCIPAL, "username1");
                env.put(javax.naming.Context.SECURITY_CREDENTIALS, "password1");
            }

            LOGGER.info("javax.naming.Context ctx = new InitialContext(env);");
            javax.naming.Context ctx = new InitialContext(env);

            LOGGER.info("QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup(QCF_NAME);");
            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup(QCF_NAME);

            LOGGER.info("QueueConnection qc = qcf.createQueueConnection();");
            QueueConnection qc = qcf.createQueueConnection();

            LOGGER.info("QueueSession qsess = qc.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);");
            QueueSession qsess = qc.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);

            LOGGER.info("Destination dest = (Destination) ctx.lookup(QUEUE_NAME);");
            Destination dest = (Destination) ctx.lookup(QUEUE_NAME);

            LOGGER.info("MessageConsumer consumer = qsess.createConsumer(dest);");
            MessageConsumer consumer = qsess.createConsumer(dest);

            consumer.setMessageListener(new MessageListener() {
                public void onMessage(javax.jms.Message message) {
                    LOGGER.info("onMessage" + message.toString());
                }
            });

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
//        for (int i=0;i<10;i++) {
//            gestionSenalesService.onMessage(new Message(new Date().toString()));
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//            }
//        }

//        LOGGER.debug("Starting JMS Reader");
//        ConnectionFactory connectionFactory = null;
//        Connection connection = null;
//        Session session = null;
//        MessageProducer producer = null;
//        MessageConsumer consumer = null;
//        Destination destination = null;
//        TextMessage message = null;
//        Context context = null;
//
//        try {
//            // Set up the context for the JNDI lookup
//            final Properties env = new Properties();
//            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
//            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
//            env.put(Context.SECURITY_PRINCIPAL, System.getProperty("username", DEFAULT_USERNAME));
//            env.put(Context.SECURITY_CREDENTIALS, System.getProperty("password", DEFAULT_PASSWORD));
//            context = new InitialContext(env);
//
//            // Perform the JNDI lookups
//            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
//            LOGGER.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
//            connectionFactory = (ConnectionFactory) context.lookup(connectionFactoryString);
//            LOGGER.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");
//
//            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
//            LOGGER.info("Attempting to acquire destination \"" + destinationString + "\"");
//            destination = (Destination) context.lookup(destinationString);
//            LOGGER.info("Found destination \"" + destinationString + "\" in JNDI");
//
//            // Create the JMS connection, session, producer, and consumer
//            connection = connectionFactory.createConnection(System.getProperty("username", DEFAULT_USERNAME), System.getProperty("password", DEFAULT_PASSWORD));
//            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            producer = session.createProducer(destination);
//            consumer = session.createConsumer(destination);
//            connection.start();
//
//            int count = Integer.parseInt(System.getProperty("message.count", DEFAULT_MESSAGE_COUNT));
//            String content = System.getProperty("message.content", DEFAULT_MESSAGE);
//
//            LOGGER.info("Sending " + count + " messages with content: " + content);
//
//            // Send the specified number of messages
//            for (int i = 0; i < count; i++) {
//                message = session.createTextMessage(content);
//                producer.send(message);
//            }
//
//            // Then receive the same number of messages that were sent
//            for (int i = 0; i < count; i++) {
//                message = (TextMessage) consumer.receive(5000);
//                LOGGER.info("Received message with content " + message.getText());
//            }
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (context != null) {
//                try {
//                    context.close();
//                } catch (NamingException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            // closing the connection takes care of the session, producer, and consumer
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

//
//    public void connectExampleWebLOGGERic() throws JMSException, NamingException {
//        Hashtable<String, String> env = new Hashtable<String, String>();
//        env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
//                "weblogic.jndi.WLInitialContextFactory");
//        env.put( javax.naming.Context.PROVIDER_URL,  "t3://10.2.145.103:8011");
//
//        javax.naming.Context ctx = new InitialContext(env);
//        QueueConnectionFactory qcf =
//                (QueueConnectionFactory) ctx.lookup(QCF_NAME);
//        QueueConnection qc = qcf.createQueueConnection();
//        QueueSession qsess = qc.createQueueSession(false,
//                Session.CLIENT_ACKNOWLEDGE);
//        Destination dest = (Destination) ctx.lookup(QUEUE_NAME);
//        MessageConsumer consumer = qsess.createConsumer(dest);
////        TextoListener listener = new TextoListener();
////        consumer.setMessageListener(listener);
//
//    }

}
