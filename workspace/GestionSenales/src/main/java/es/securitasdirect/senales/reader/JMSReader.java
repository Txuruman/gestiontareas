package es.securitasdirect.senales.reader;

import es.securitasdirect.senales.model.*;
import es.securitasdirect.senales.model.cibb.CIBB;
import es.securitasdirect.senales.service.GestionSenalesService;
import es.securitasdirect.senales.support.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MarkerIgnoringBase;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

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
    private static long RECONNECTION_DELAY = 5 * 60 * 1000;
    private static String TEST_MESSAGE_CONTENT = "GESTIONSENALESTESTMESSAGE";

    /**
     * Indica si el Reader se ha conectado a la cola correctametne
     */
    private boolean readerStatusUp = false;
    /**
     * Numero de mensajes de prueba recibidos
     */
    private AtomicInteger testReceivedMesssages = new AtomicInteger();
    /**
     * Numero de mensajes de prueba enviados
     */
    private AtomicInteger testSentMessages = new AtomicInteger();
    /**
     * Indica si se ha solicitado la destrucción de este Reader, para controlar el hilo de reconexión.
     */
    private boolean readerDestroyed = false;
    private String readerStatusDescription;

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
    private javax.naming.Context context;

    @Autowired
    protected GestionSenalesService gestionSenalesService;
    @Autowired
    private XmlMarshaller xmlMarshaller;

    public JMSReader() {
    }

    @PostConstruct
    protected void init() {
        connect();
    }

    @PreDestroy
    public void shutdown() {
        readerDestroyed = true;
        close();
    }


    /**
     * Conectar a las colas
     */
    public void connect() {
        if (!readerStatusUp) {

            assert gestionSenalesService != null;
            if (user != null && pass != null) {
                LOGGER.info("JMSReader {} ({})connecting :  \n\t - JNDI Url: {}\n\t - QFC Name: {}\n\t - Queue name: {}\n\t - User: {}\n\t - Password: {}", aliasName, this.hashCode(), jndiUrl, qfcName, queueName, user, pass);
            } else {
                LOGGER.info("JMSReader {} ({})connecting :  \n\t - JNDI Url: {}\n\t - QFC Name: {}\n\t - Queue name: {}\n", aliasName, this.hashCode(), jndiUrl, qfcName, queueName);
            }

            try {

                LOGGER.info("JMSReader {} ({}): Getting initial Context", aliasName, this.hashCode());
                context = getInitialContext();

                LOGGER.info("JMSReader {} ({}): Looking up Queue Connection Factory in Context", aliasName, this.hashCode());
                QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup(qfcName);

                LOGGER.info("JMSReader {} ({}): Creating Connection from Queue Connection Factory", aliasName, this.hashCode());
                queueConnection = queueConnectionFactory.createQueueConnection();
                queueConnection.start();

                LOGGER.info("JMSReader {} ({}): Creating session from Queue Connection", aliasName, this.hashCode());
                queueSession = queueConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);

                LOGGER.info("JMSReader {} ({}): Looking up Queue in Context", aliasName, this.hashCode());
                Destination dest = (Destination) context.lookup(queueName);

                LOGGER.info("JMSReader {} ({}): Creating consumer from session in the queue", aliasName, this.hashCode());
                consumer = queueSession.createConsumer(dest);

                consumer.setMessageListener(this);

                LOGGER.info("JMSReader {} ({}): Successfully connected", aliasName, this.hashCode());
                readerStatusUp = true;
                readerStatusDescription = "";

                //Cerramos el contexto tras obtener todo lo necesario
                context.close();

            } catch (Exception e) {
                LOGGER.error("JMSReader {} ({}): Error connecting to JMS {}", aliasName, this.hashCode(), e.getMessage(), e);
                //Try to close everything that could be open
                close();
                //Set this status after the close function
                readerStatusUp = false;
                readerStatusDescription = e.toString();
            }
        }
    }


    /**
     * Desconectar de las colas
     */
    public void close() {
        if (readerStatusUp) {
            LOGGER.debug("JMSReader {} ({}) closing....", aliasName, this.hashCode());
            readerDestroyed = true;
            readerStatusUp = false;
            readerStatusDescription = "Destroyed";

            //Pedimos una sesion JNDI de nuevo porque sino da errores de seguridad
            try {
                LOGGER.info("JMSReader {} ({}): Getting initial Context to avoid security problems", aliasName, this.hashCode());
                context = getInitialContext();
            } catch (NamingException e) {
                LOGGER.error("JMSReader {} ({}): Error getting initial context to close all JMS objects");
            }

            try {
                if (consumer != null) {
                    consumer.close();
                    LOGGER.debug("JMSReader {} ({}): Successfully close JMS consumer", aliasName, this.hashCode());
                }
            } catch (Exception e) {
                LOGGER.error("JMSReader {} ({}): Error closing JMS consumer", aliasName, this.hashCode(), e);
            }

            try {
                if (queueConnection != null) {
                    queueConnection.close();
                    LOGGER.debug("JMSReader {} ({}): Successfully close JMS connection", aliasName, this.hashCode());
                }
            } catch (Exception e) {
                LOGGER.error("JMSReader {} ({}): Error closing JMS connection", aliasName, this.hashCode(), e);
            }

            try {
                if (queueSession != null) {
                    queueSession.close();
                    LOGGER.debug("JMSReader {} ({}): Successfully close JMS session", aliasName, this.hashCode());
                }
            } catch (Exception e) {
                LOGGER.error("JMSReader {} ({}): Error closing JMS session", aliasName, this.hashCode(), e);
            }

            try {
                if (context != null) {
                    context.close();
                    LOGGER.debug("JMSReader {} ({}): Successfully close JNDI context", aliasName, this.hashCode());
                }
            } catch (Exception e) {
                LOGGER.error("JMSReader {} ({}): Error closing JNDI context", aliasName, this.hashCode(), e);
            }

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
                LOGGER.error("JMSReader {} ({}): Can't read content of Message", aliasName, this.hashCode(), e);
            }
            if (jmsMessageText != null) {
                if (!jmsMessageText.startsWith(TEST_MESSAGE_CONTENT)) {
                    es.securitasdirect.senales.model.Message modelMessage = new es.securitasdirect.senales.model.Message();
                    try {
                        //Extract JMS Info
                        CIBB cibb = xmlMarshaller.unmarshallCIBB(jmsMessageText);
                        modelMessage = new es.securitasdirect.senales.model.Message();
                        modelMessage.setCibb(cibb);
                        modelMessage.setEntryDate(new Date());

                        LOGGER.debug("JMSReader {} ({}): Message CIBB received {} ", aliasName, this.hashCode(), jmsMessageText);

                    } catch (Exception e) {
                        LOGGER.error("JMSReader {} ({}): Can't parse message content  [{}] ", aliasName, this.hashCode(), jmsMessageText, e);
                        modelMessage = null;
                    }

                    if (modelMessage != null) {
                        //Call Gestion Senales Message Service
                        gestionSenalesService.onMessage(modelMessage);
                    }
                } else {
                    LOGGER.debug("JMSReader {} ({}): Received test message '{}'", aliasName, this.hashCode(), jmsMessageText);
                    testReceivedMesssages.incrementAndGet();
                }
            }

        } else {
            LOGGER.error("JMSReader {} ({}): Message received not from the correct type", aliasName, this.hashCode());
        }

    }

    private InitialContext getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, jndiUrl);
        if (user != null && pass != null) {
            env.put(javax.naming.Context.SECURITY_PRINCIPAL, user);
            env.put(javax.naming.Context.SECURITY_CREDENTIALS, pass);
        }

        return new InitialContext(env);
    }

    /**
     * Conecta a la cola y envía un mensaje de pruebas
     */
    public void injectTestMessage() {
        try {
            javax.naming.Context context = getInitialContext();
            Destination dest = (Destination) context.lookup(queueName);
            MessageProducer producer = queueSession.createProducer(dest);
            for (int i = 0; i < 10; i++) {
                String testMessageContent = TEST_MESSAGE_CONTENT + " - " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                LOGGER.debug("JMSReader {} ({}):Sending test message '{}'", aliasName, this.hashCode(), testMessageContent);
                TextMessage textMessage = queueSession.createTextMessage(testMessageContent);
                producer.send(textMessage);
                testSentMessages.incrementAndGet();
            }
            producer.close();
        } catch (Exception e) {
            LOGGER.error("JMSReader {} ({}): Error writing test message to  queue", aliasName, this.hashCode(), e);
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

    public boolean isReaderStatusUp() {
        return readerStatusUp;
    }

    public String getReaderStatusDescription() {
        return readerStatusDescription;
    }

    public int getTestReceivedMesssages() {
        return testReceivedMesssages.get();
    }

    public int getTestSentMessages() {
        return testSentMessages.get();
    }


}
