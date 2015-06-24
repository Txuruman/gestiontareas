package es.securitasdirect.senales.jms;

import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.service.GestionSenalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Lector de mensajes de bus JMS.
 *
 * Created by Team Vision
 */
public class JMSReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JMSReader.class);


    private static final String QCF_NAME = "sd.prd.es1prdxacfout";
    private static final String QUEUE_NAME = "sd.prd.es1allinoneout";

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Hello, World!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/test";
    private static final String DEFAULT_MESSAGE_COUNT = "1";
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin!";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "remote://localhost:4447";

    @Autowired
    protected GestionSenalesService gestionSenalesService;

    public JMSReader() {
    }

    @PostConstruct
    public void connect() {
        assert gestionSenalesService!=null;
        LOGGER.debug("JMS Reader starting"); //TODO Imprimir config

        gestionSenalesService.onMessage(new Message("HOLA1"));

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
//                "webLOGGERic.jndi.WLInitialContextFactory");
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
