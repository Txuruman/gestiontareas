package es.securitasdirect.senales.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.webservice.CCLIntegration;
import com.webservice.IclResponse;
import com.webservice.ReturnData;
import com.webservice.WsResponse;
import es.securitasdirect.senales.model.HappyData;
import es.securitasdirect.senales.model.Message;
import es.securitasdirect.senales.model.SignalMetadata;
import es.securitasdirect.senales.model.SmsMessageLocation;
import es.securitasdirect.senales.reader.JMSReader;
import es.securitasdirect.senales.support.FileService;
import net.java.dev.jaxb.array.StringArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.ws.dataservice.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        happyData.setInWorkingHoursMessages(gestionSenalesService.getInWorkingHoursMessages());
        happyData.setOutWorkingHousMessages(gestionSenalesService.getOutWorkingHousMessages());

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
