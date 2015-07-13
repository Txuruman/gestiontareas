package es.securitasdirect.senales.reader;

import es.securitasdirect.senales.model.JMSConnectionData;
import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.service.GestionSenalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Date;
import java.util.HashMap;
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

    //servidor
    //	t3://10.2.145.102:8011
    //	connection factory
    //	sd.reptef.reptefxacf
    //	queue
    //	sd.reptef.telef_convmng_01

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

    private String aliasName;

    private String bus;

    private String port;

    private String qfcName;

    private String queueName;

    private String user;

    private String pass;

    @Autowired
    protected GestionSenalesService gestionSenalesService;

    public JMSReader() {
    }

    @PostConstruct
    public void connect() {
        assert gestionSenalesService != null;
        LOGGER.info("{} JMS Reader connecting:\n\t - Alias name: {}\n\t - Bus: {}\n\t - Port: {}\n\t - QFC Name: {}\n\t - Queue name: {}\n\t - User: {}\n\t - Password: {}", aliasName, bus, port, qfcName, queueName,user, pass);
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            env.put(javax.naming.Context.PROVIDER_URL, "t3://" + bus + ":" + port);
            if(user!=null && !user.isEmpty() && pass!=null && !pass.isEmpty()) {
                env.put(javax.naming.Context.SECURITY_PRINCIPAL, user);
                env.put(javax.naming.Context.SECURITY_CREDENTIALS, pass);
            }else{
                LOGGER.info("JMS Reader configured with no user and password");
            }

            LOGGER.info("{} Getting initial Context", aliasName);
            javax.naming.Context ctx = new InitialContext(env);

            LOGGER.info("{} QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup(QCF_NAME);", aliasName);
            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup(qfcName);

            LOGGER.info("{} QueueConnection qc = qcf.createQueueConnection();", aliasName);
            QueueConnection qc = qcf.createQueueConnection();

            LOGGER.info("{} QueueSession qsess = qc.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);", aliasName);
            QueueSession qsess = qc.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);

            LOGGER.info("{} Destination dest = (Destination) ctx.lookup(QUEUE_NAME);", aliasName);
            Destination dest = (Destination) ctx.lookup(queueName);

            LOGGER.info("{} MessageConsumer consumer = qsess.createConsumer(dest);", aliasName);
            MessageConsumer consumer = qsess.createConsumer(dest);

            consumer.setMessageListener(new MessageListener() {
                public void onMessage(javax.jms.Message message) {
                    LOGGER.info("{} onMessage {}", aliasName, message.toString());
                }
            });

        } catch (Exception e) {
            LOGGER.error("{} {}", aliasName, e.getMessage());
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

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getQfcName() {
        return qfcName;
    }

    public void setQfcName(String qfcName) {
        this.qfcName = qfcName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
