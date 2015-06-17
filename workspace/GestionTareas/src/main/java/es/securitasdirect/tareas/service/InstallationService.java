package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.InstallationData;
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
    public InstallationData getInstallationData(String installationNumber) throws DataServiceFault {
        assert installationNumber!=null;
        Mainstallationdataresult result = null;

        List<Mainstallationdataresult> installationDataWS = spInstallationMonData.getInstallationData(installationNumber);

        if (installationDataWS!=null && installationDataWS.size()>0) {
            result = installationDataWS.get(0);
        }

        InstallationData installationData = createInstallationData(result);
        LOGGER.debug("getInstallationData({}) : {}",installationNumber, installationData);
        return installationData;
    }


    private InstallationData createInstallationData (Mainstallationdataresult mainstallationdataresult) {
        if (mainstallationdataresult==null) {
            return null;
        } else {
            InstallationData data = new InstallationData();
            //TODO Mapeo de atributos
            data.setPersonaContacto(mainstallationdataresult.getAliasName());
            data.setNumeroInstalacion(mainstallationdataresult.getInsNo());
            data.setPersonaContacto(mainstallationdataresult.getAliasName());
            data.setTitular(mainstallationdataresult.getName());
            data.setPanel(mainstallationdataresult.getPanel());
            return data;
        }
    }
}
