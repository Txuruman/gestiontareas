package es.securitasdirect.tareas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.Mainstallationdataresult;
import org.wso2.ws.dataservice.SPInstallationMonDataPortType;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 *
 */
@Named(value = "installationService")
@Singleton
public class InstallationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstallationService.class);

    @Inject
    protected SPInstallationMonDataPortType spInstallationMonData;

    /**
     * Obtiene los datos de una instalación por número de instalación, si no se encuentra devuelve null.
     * @param installationNumber
     * @return
     * @throws DataServiceFault
     */
    public Mainstallationdataresult getInstallationData(String installationNumber) throws DataServiceFault {
        assert installationNumber!=null;
        Mainstallationdataresult result = null;

        List<Mainstallationdataresult> installationData = spInstallationMonData.getInstallationData(installationNumber);

        if (installationData!=null && installationData.size()>0) {
            result = installationData.get(0);
        }
        LOGGER.debug("getInstallationData({}) : {}",installationNumber, result);
        return result;
    }

}
