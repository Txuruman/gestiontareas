package es.securitasdirect.tareas.service;

import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.Mainstallationdataresult;
import org.wso2.ws.dataservice.SPInstallationMonData;
import org.wso2.ws.dataservice.SPInstallationMonDataPortType;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 *
 */
@Named(value = "instalacionService")
@Singleton
public class InstalacionService {

    @Inject
    protected SPInstallationMonDataPortType spInstallationMonData;

    public void get() throws DataServiceFault {
        List<Mainstallationdataresult> installationData = spInstallationMonData.getInstallationData("111111");
        System.out.println("installationData:" + installationData.toString());
    }

}
