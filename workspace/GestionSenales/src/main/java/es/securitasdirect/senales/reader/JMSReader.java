package es.securitasdirect.senales.reader;

import es.securitasdirect.senales.model.*;
import es.securitasdirect.senales.model.cibb.CIBB;
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
    private static long RECONNECTION_DELAY = 5 * 60 * 1000;

    /**
     * Indica si el Reader se ha conectado a la cola correctametne
     */
    private boolean readerStatusUp = false;
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
    protected void end() {
        close();
    }


    public void connect() {
        assert gestionSenalesService != null;
        if (user != null && pass != null) {
            LOGGER.info("JMSReader {} connecting :  \n\t - JNDI Url: {}\n\t - QFC Name: {}\n\t - Queue name: {}\n\t - User: {}\n\t - Password: {}", aliasName, jndiUrl, qfcName, queueName, user, pass);
        } else {
            LOGGER.info("JMSReader {} connecting :  \n\t - JNDI Url: {}\n\t - QFC Name: {}\n\t - Queue name: {}\n", aliasName, jndiUrl, qfcName, queueName);
        }

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
            readerStatusUp = true;
            readerStatusDescription = "";
        } catch (Exception e) {
            LOGGER.error("Error connecting to JMS Queue {}: {}", aliasName, e.getMessage(), e);
            //Try to close everything that could be open
            close();
            //Set this status after the close function
            readerStatusUp = false;
            readerStatusDescription = e.toString() + ". Will try to reconnect at " + new Date(System.currentTimeMillis() + RECONNECTION_DELAY).toString();

            //Try to recconnect later
            new Thread() {
                @Override
                public void run() {
                    LOGGER.debug("Preparing reconnection to queue {}", aliasName);
                    try {
                        Thread.sleep(RECONNECTION_DELAY);
                    } catch (InterruptedException e1) {
                        LOGGER.debug("Interrupted exception will try reconnection now");
                    }
                    LOGGER.info("Trying reconnection to queue {} because of previous fail", aliasName);
                    //Evitamos dejar el hilo colgado cuando se ha cerrado el Reader
                    if (!readerDestroyed) connect();
                }
            }.start();
        }
    }


    public void close() {
        LOGGER.debug("JMSReader {} closing.");
        readerDestroyed = true;
        readerStatusUp = false;
        readerStatusDescription = "Destroyed";

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
                    CIBB cibb = xmlMarshaller.unmarshallCIBB(jmsMessageText);
                    modelMessage = new es.securitasdirect.senales.model.Message();
                    modelMessage.setCibb(cibb);
                    modelMessage.setEntryDate(new Date());

                    //TODO BORRAR ESTE LOG; solo es para coger mensajes de ejemplo
                    LOGGER.info("JMSReader {} : Mensaje CIBB recivido\n\n\n\n\n\n{}\n\n\n\n\n\n\n\n", aliasName, jmsMessageText);

                } catch (Exception e) {
                    LOGGER.error("JMSReader {} : Can't parse message content \n[{}]\n", aliasName, jmsMessageText, e);
                    modelMessage = null;
                }

                if (modelMessage != null) {
                    //Call Gestion Senales Message Service
                    gestionSenalesService.onMessage(modelMessage);
                }
            }

        } else {
            LOGGER.error("JMSReader {} : Message received not from the correct type", aliasName);
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
}
