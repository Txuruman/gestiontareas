package es.securitasdirect.senales.reader;

import es.securitasdirect.senales.model.*;
import es.securitasdirect.senales.model.params.PARAMSType;
import es.securitasdirect.senales.service.GestionSenalesService;
import es.securitasdirect.senales.support.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jms.*;
import javax.jms.Message;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.soap.Text;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Lector de mensajes de bus JMS.
 * <p/>
 * Created by Team Vision
 */
public class JMSReader implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JMSReader.class);

    private static String JNDI_FACTORY_WL = "weblogic.jndi.WLInitialContextFactory";
    private static String JNDI_FACTORY_JBOSS = "org.jboss.naming.remote.client.InitialContextFactory";
    private static String JNDI_FACTORY = JNDI_FACTORY_WL;


    private String aliasName;
    private String jndiUrl;
    /**
     * Queue Factory
     */
    private String qfcName;
    private String queueName;
    private String user;
    private String pass;

    private QueueConnection queueConnection;
    private QueueSession queueSession;
    private MessageConsumer consumer;

    @Autowired
    protected GestionSenalesService gestionSenalesService;
    @Autowired
    private XmlMarshaller xmlMarshaller;

    public JMSReader() {
    }


    private InitialContext getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, jndiUrl);
        if (user != null && !user.isEmpty() && pass != null && !pass.isEmpty()) {
            env.put(javax.naming.Context.SECURITY_PRINCIPAL, user);
            env.put(javax.naming.Context.SECURITY_CREDENTIALS, pass);
        }
        return new InitialContext(env);
    }

    @PostConstruct
    public void connect() {
        assert gestionSenalesService != null;
        LOGGER.info("JMSReader {} connecting :  \n\tJNDI Url: {}\n\t - QFC Name: {}\n\t - Queue name: {}\n\t - User: {}\n\t - Password: {}",
                aliasName, jndiUrl, qfcName, queueName, user, pass);
        try {

            LOGGER.info("JMSReader {} : Getting initial Context", aliasName);
            javax.naming.Context context = getInitialContext();

            LOGGER.info("JMSReader {} : Looking up Queue Connection Factory in Context", aliasName);
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup(qfcName);

            LOGGER.info("JMSReader {} : Creating Connection from Queue Connection Factory", aliasName);
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueConnection.start();

            LOGGER.info("JMSReader {} : Creating session from Queue Connection", aliasName);
            queueSession = queueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);

            LOGGER.info("JMSReader {} : Looking up Queue in Context", aliasName);
            Destination dest = (Destination) context.lookup(queueName);

            LOGGER.info("JMSReader {} : Creating consumer from session in the queue", aliasName);
            consumer = queueSession.createConsumer(dest);


//            //PRUEBA ESCRITURA
//            try {
//                MessageProducer producer = queueSession.createProducer(dest);
//                for (int i = 0; i < 10; i++) {
//                    TextMessage textMessage = queueSession.createTextMessage(paramsTypeExample1);
//                    producer.send(textMessage);
//                }
//                LOGGER.info("Escritos mensajes de prueba");
//            } catch (Exception e) {
//                LOGGER.error("Error escribiendo mensajes de prueba", e);
//            }
//            //FIN PRUEBA ESCRITURA


            consumer.setMessageListener(this);

            LOGGER.info("JMSReader {} : Successfully connected", aliasName);

        } catch (Exception e) {
            LOGGER.error("Error connecting to JMS Queue {}: {}", aliasName, e.getMessage(), e);
        }

    }


    @PreDestroy
    public void close() {
        try {
            if (queueConnection != null) queueConnection.close();
            if (queueSession != null) queueSession.close();
            if (consumer != null) consumer.close();
        } catch (Exception e) {
            LOGGER.error("JMSReader {} : Error closing JMS connections", aliasName, e);
        }
    }


    /**
     * On JMS Message, process JMS message content and call Gestion Senales Service
     */
    public void onMessage(Message jmsMessage) {
        if (jmsMessage instanceof TextMessage) {

            String jmsMessageText = null;
            try {
                jmsMessageText = ((TextMessage) jmsMessage).getText();
            } catch (JMSException e) {
                LOGGER.error("JMSReader {} : Can't read content of Message", aliasName, e);
            }
            if (jmsMessageText != null) {
                es.securitasdirect.senales.model.Message modelMessage = new es.securitasdirect.senales.model.Message();
                try {
                    //Extract JMS Info
                    PARAMSType paramsType = xmlMarshaller.unmarshallPARAMSType(jmsMessageText);
                    modelMessage = new es.securitasdirect.senales.model.Message();
                    modelMessage.setParamsType(paramsType);
                    modelMessage.setEntryDate(new Date());
                } catch (Exception e) {
                    LOGGER.error("JMSReader {} : Can't parse message content \n[{}]\n", aliasName, jmsMessageText, e);
                    modelMessage=null;
                }

                if (modelMessage!=null) {
                    //Call Gestion Senales Message Service
                    gestionSenalesService.onMessage(modelMessage);
                }
            }

        } else {
            LOGGER.error("JMSReader {} : Message received not from the correct type", aliasName);
        }

    }


    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getJndiUrl() {
        return jndiUrl;
    }

    public void setJndiUrl(String jndiUrl) {
        this.jndiUrl = jndiUrl;
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

    private String paramsTypeExample1 = "<PARAMS>\n" +
            "    <CIBB>\n" +
            "        <EVENTS MMV=\"020401\" Modelo=\"Modelo Desconocido\" Counter=\"00B\"\n" +
            "                Ack=\"1\" InsNumber=\"00000000\" InsNumber_e=\"1555667\" DataTime=\"1502091042\" TypeProtocol=\"E\">\n" +
            "            <EVENT id=\"UCB\">\n" +
            "                <type>2</type>\n" +
            "                <ArmType value='Panel disarm'>D</ArmType>\n" +
            "                <ArmForced value='Panel is not forced armed'>N\n" +
            "                </ArmForced>\n" +
            "                <Zone value='There is not  any  zone open'>N</Zone>\n" +
            "                <CancelPending value='There is not  any  cancel situation in panel'>N\n" +
            "                </CancelPending>\n" +
            "                <PanelInFault value='Panel is in Fault'>S</PanelInFault>\n" +
            "                <EventType value='Informative events'>I</EventType>\n" +
            "                <DevIdentification value='Central:Central'>CE</DevIdentification>\n" +
            "                <EventStatus value='Event status does not apply for this event.'>N</EventStatus>\n" +
            "                <ArmTime>067511</ArmTime>\n" +
            "                <DevManufacturer>0KYMGB</DevManufacturer>\n" +
            "            </EVENT>\n" +
            "        </EVENTS>\n" +
            "        <!-- TimeArrived Mon Feb 09 10:42:35 CET 2015 -->\n" +
            "        <PROPS tfno=\"0000000000000\"\n" +
            "               texto=\"SDES04702040100B1000000000001502091042E1555667#XDN067511NNSICE0KYMGBNUCB2!C517\"\n" +
            "               pais=\"ESP\" host=\"es1recveri04v\" op=\"MOVS\" centro=\"\"\n" +
            "               Numero=\"comfort\"\n" +
            "               tipo=\"SDI2\" user=\"1555667\" err=\"\"\n" +
            "               transId=\"es1recveri04v_rx_GPRS_es_mov7587_20150209104235556\"\n" +
            "               TimeIn=\"1423474955000\"\n" +
            "               RecepName=\"rx_GPRS_es_mov7587\" Medio=\"GPRS\"\n" +
            "               TansmisionType=\"EVENT\"\n" +
            "               SeviceType=\"comfort\" ProtocolType=\"POSESA\" InOrOut=\"INPUT\"\n" +
            "               DestinoType=\"MACHINE\" OrigenType=\"PANEL\" ModeloId=\"\"\n" +
            "               origen=\"panel\"\n" +
            "               Ok=\"true\" ServiceType=\"COM\"/>\n" +
            "    </CIBB>\n" +
            "</PARAMS>";
}
