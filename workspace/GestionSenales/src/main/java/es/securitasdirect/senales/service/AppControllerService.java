package es.securitasdirect.senales.service;

import es.securitasdirect.senales.model.HappyData;
import es.securitasdirect.senales.reader.JMSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;

/**
 *
 */

@Named(value = "appControllerService")
@Singleton
public class AppControllerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppControllerService.class);


    @Autowired
    protected GestionSenalesService gestionSenalesService;

    protected List<JMSReader> monitoredJmsReaders;

    public HappyData getHappyData() {
        HappyData happyData = new HappyData();
        happyData.setUpSince(gestionSenalesService.getUpTime());
        happyData.setErrorMessages(gestionSenalesService.getErrorMessages());
        happyData.setSuccessfulMessages(gestionSenalesService.getSuccessfulMessages());
        happyData.setWithLoggedInAgentsMessages(gestionSenalesService.getInWorkingHoursMessages());
        happyData.setWithoutAgentsMessages(gestionSenalesService.getOutWorkingHousMessages());

        if (monitoredJmsReaders!=null) {
            for (JMSReader jmsReader : monitoredJmsReaders) {
                happyData.addJmsReaderStatus(jmsReader.getAliasName(),jmsReader.isReaderStatusUp(),jmsReader.getReaderStatusDescription(),
                jmsReader.getTestSentMessages(),
                        jmsReader.getTestReceivedMesssages());
            }
        }
        return happyData;
    }

    public List<JMSReader> getMonitoredJmsReaders() {
        return monitoredJmsReaders;
    }

    public void setMonitoredJmsReaders(List<JMSReader> monitoredJmsReaders) {
        this.monitoredJmsReaders = monitoredJmsReaders;
    }


    public void testMessages() {
        LOGGER.debug("Starting test messages process for queues");
        if (monitoredJmsReaders!=null) {
            for (JMSReader monitoredJmsReader : monitoredJmsReaders) {
                monitoredJmsReader.injectTestMessage();
            }
        }
    }


    public void stopJMSReaders() {
        LOGGER.debug("Stopping all JMS Readers");
        if (monitoredJmsReaders!=null) {
            for (JMSReader monitoredJmsReader : monitoredJmsReaders) {
                monitoredJmsReader.close();
            }
        }
    }
    public void startJMSReaders() {
        LOGGER.debug("Starting all JMS Readers");
        if (monitoredJmsReaders!=null) {
            for (JMSReader monitoredJmsReader : monitoredJmsReaders) {
                monitoredJmsReader.connect();
            }
        }
    }
}
