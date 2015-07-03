package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.DummyGenerator;
import es.securitasdirect.tareas.model.InstallationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.*;

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
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;

    /**
     * Obtiene los datos de una instalación por número de instalación, si no se encuentra devuelve null.
     * @param installationNumber
     * @return
     * @throws DataServiceFault
     */
    public InstallationData getInstallationData(String installationNumber) throws DataServiceFault {
        try {
            assert installationNumber != null;
            Mainstallationdataresult installationDataWS1 = null;
            GetInstallationDataResults installationDataWS2 = null;

            List<Mainstallationdataresult> installationDataWS1List = spInstallationMonData.getInstallationData(installationNumber);
            if (installationDataWS1List != null && !installationDataWS1List.isEmpty()) {
                installationDataWS1 = installationDataWS1List.get(0);
                // Obtener la versión del panel
                GetInstallationDataInput getInstallationDataInput = new GetInstallationDataInput();
                getInstallationDataInput.setSIns(installationDataWS1.getSIns().intValue());
                getInstallationDataInput.setSCtr(1); //TODO Numero contrato? no sirve para nada
                installationDataWS2 = spAioTareas2.getInstallationData(getInstallationDataInput);
            }

            InstallationData installationData = createInstallationData(installationDataWS1, installationDataWS2);
            LOGGER.debug("getInstallationData({}) : {}", installationNumber, installationData);
            return installationData;
        } catch (Exception e) {
            //TODO DUMMY
            return DummyGenerator.getInstallationData();
        }
    }


    private InstallationData createInstallationData (Mainstallationdataresult mainstallationdataresult,GetInstallationDataResults installationData2) {
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
            data.setTelefono(mainstallationdataresult.getPhone());
            if (installationData2!=null && !installationData2.getGetInstallationDataResult().isEmpty()) {
                data.setVersion(installationData2.getGetInstallationDataResult().get(0).getVersion());
            }
            return data;
        }
    }


}
