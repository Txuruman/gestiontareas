package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import es.securitasdirect.tareas.model.happy.HappyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.ws.dataservice.SPAIOTAREAS2PortType;
import org.wso2.ws.dataservice.SPInstallationMonDataPortType;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Date;
import java.util.List;

/**
 *
 */

@Named(value = "happyService")
@Singleton
public class HappyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HappyService.class);
    private static final String INFOPOINT_SERVICE = "Infopoint";
    private static final String AIOTAREAS2_SERVICE = "AioTareas2";
    private static final String INSTALLATIONMONDATA_SERVICE = "spInstallationMonData";
    private static final String CCL_SERVICE = "CCLIntegration";
    private static final String OK = "Ok";
    private static final String ERROR = "Error";

    @Resource
    protected String applicationUser ;


    protected Date upTime;

    @Inject
    protected InfopointService infopointService;
    @Inject
    protected InstallationService installationService;
    @Inject
    protected SPInstallationMonDataPortType spInstallationMonData;
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;
    @Inject
    protected CCLIntegration cclIntegration;


    @PostConstruct
    protected void init() {
        upTime = new Date();
    }

    public HappyData getHappyData() {
        HappyData happyData = new HappyData();
        //Up Time
        happyData.setUpSince(upTime);


        //Web Services
        try {
            infopointService.validateSession("check infopoint service");
            happyData.addService(INFOPOINT_SERVICE,OK);
        } catch (Exception e) {
            happyData.addService(INFOPOINT_SERVICE,ERROR, e.getMessage());
        }

        try {
            spAioTareas2.getKey1DIY();
            happyData.addService(AIOTAREAS2_SERVICE, OK);
        } catch (Exception e) {
            happyData.addService(AIOTAREAS2_SERVICE,ERROR, e.getMessage());
        }

        try {
            spInstallationMonData.getPanel(111111);
            happyData.addService(INSTALLATIONMONDATA_SERVICE, OK);
        } catch (Exception e) {
            happyData.addService(INSTALLATIONMONDATA_SERVICE,ERROR, e.getMessage());
        }


        try {
            cclIntegration.getCCLVersion("M0OOS",applicationUser,"","SPAIN");
            happyData.addService(CCL_SERVICE, OK);
        } catch (Exception e) {
            happyData.addService(CCL_SERVICE,ERROR, e.getMessage());
        }


        return happyData;
    }

}
