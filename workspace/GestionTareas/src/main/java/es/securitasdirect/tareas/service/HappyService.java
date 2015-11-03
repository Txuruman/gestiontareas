package es.securitasdirect.tareas.service;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.ws.dataservice.FSMDataServiceLightPortType;
import org.wso2.ws.dataservice.SPAIOTAREAS2PortType;
import org.wso2.ws.dataservice.SPAVISOSOPERACIONESPortType;
import org.wso2.ws.dataservice.SPInstallationMonDataPortType;

import com.webservice.CCLIntegration;

import es.securitasdirect.tareas.model.happy.HappyData;

/**
 *
 */

@Named(value = "happyService")
@Singleton
public class HappyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HappyService.class);
    private static final String INFOPOINT_SERVICE = "Infopoint";
    private static final String FSM_LIGHT = "FSMDataServiceLight";
    private static final String AIOTAREAS2_SERVICE = "AioTareas2";
    private static final String INSTALLATIONMONDATA_SERVICE = "spInstallationMonData";
    private static final String CCL_SERVICE = "CCLIntegration";
    private static final String SECURITY = "Security";
    private static final String OK = "Ok";
    private static final String ERROR = "Error";
	private static final String APP_VERSION = "Version TAREAS";
    private static final String APP_VERSION_VALUE = "2.13";

    @Resource
    protected String applicationUser;
    @Autowired
    protected SecurityService securityService;

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
    @Inject
    protected FSMDataServiceLightPortType fsmDataServiceLight;
    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;

    @PostConstruct
    protected void init() {
        upTime = new Date();
    }

    public HappyData getHappyData() {
        HappyData happyData = new HappyData();
		//verion de la aplicacion
        happyData.addService(APP_VERSION, APP_VERSION_VALUE);
        //Up Time
        happyData.setUpSince(upTime);

        //Security
        try {
            securityService.check();
            happyData.addService(SECURITY, OK);
        } catch (Throwable e) {
            happyData.addService(SECURITY,ERROR, e.getMessage());
        }


        //Web Services
        try {
            fsmDataServiceLight.getMember("1760731");
            happyData.addService(FSM_LIGHT,OK);
        } catch (Exception e) {
            happyData.addService(FSM_LIGHT,ERROR, e.getMessage());
        }

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
        
        try {
            cclIntegration.getCCLVersion("M0OOS",applicationUser,"","SPAIN");
            happyData.addService(CCL_SERVICE, OK);
        } catch (Exception e) {
            happyData.addService(CCL_SERVICE,ERROR, e.getMessage());
        }


        return happyData;
    }

}
