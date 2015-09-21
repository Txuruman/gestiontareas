package es.securitasdirect.senales.service;

import es.securitasdirect.senales.model.HappyData;
import es.securitasdirect.senales.reader.JMSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;

/**
 *
 */

@Named(value = "happyService")
@Singleton
public class HappyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HappyService.class);

    public static final String EMPTY = "";

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
                happyData.addJmsReaderStatus(jmsReader.getAliasName(),jmsReader.isReaderStatusUp(),jmsReader.getReaderStatusDescription());
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
}
