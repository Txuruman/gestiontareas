package es.securitasdirect.tareas.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.FSMDataServiceLightPortType;
import org.wso2.ws.dataservice.GetInstallationDataResults;
import org.wso2.ws.dataservice.GetInstallationResult;
import org.wso2.ws.dataservice.GetMemberResult;
import org.wso2.ws.dataservice.InstallationMember;
import org.wso2.ws.dataservice.Installationfulldataresult;
import org.wso2.ws.dataservice.Mainstallationdataresult;
import org.wso2.ws.dataservice.SPAIOTAREAS2PortType;
import org.wso2.ws.dataservice.SPInstallationMonDataPortType;

import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.exceptions.FrameworkException;
import es.securitasdirect.tareas.model.InstallationData;

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
    @Inject
    protected FSMDataServiceLightPortType fsmDataServiceLight;

    /**
     * Obtiene los datos de una instalación por número de instalación, si no se encuentra devuelve null.
     *
     * @param installationNumber
     * @return
     * @throws DataServiceFault
     */
    public InstallationData getInstallationData(String installationNumber)   {
        //Ejemplo simulacion instalacion no encontrado
        //installationNumber="0000011111";
        LOGGER.debug("Getting Installation Data by Service");
        assert installationNumber != null;
        Mainstallationdataresult installationDataWS1 = null;
        GetInstallationDataResults installationDataWS2 = null;
        InstallationMember installationDataWS3 = null;
        Installationfulldataresult installationDataWS4 = null;
        
        try {
            List<Mainstallationdataresult> installationDataWS1List = spInstallationMonData.getInstallationData(installationNumber);
            if (installationDataWS1List != null && !installationDataWS1List.isEmpty()) {
                installationDataWS1 = installationDataWS1List.get(0);
                // Obtener la versión del panel
//                GetInstallationDataInput getInstallationDataInput = new GetInstallationDataInput();
//                getInstallationDataInput.setSIns(installationDataWS1.getSIns().intValue());
//                getInstallationDataInput.setSCtr(installationDataWS1.getSIns().intValue()); //Como segundo parametro metemos también el número de instalación , parece que con eso devuelve datos de número de contrato
//                installationDataWS2 = spAioTareas2.getInstallationData(getInstallationDataInput);
            } else {
                LOGGER.error("Can't find installation in SPAioTareas2");
                throw new BusinessException(BusinessException.ErrorCode.ERROR_FIND_INSTALLATION);
            }

            List<GetMemberResult> memberList = fsmDataServiceLight.getMember(installationNumber);
            if (memberList != null && !memberList.isEmpty() && memberList.get(0).getInstallationMemberResults() != null && memberList.get(0).getInstallationMemberResults().getInstallationMember() != null && !memberList.get(0).getInstallationMemberResults().getInstallationMember().isEmpty()) {
                installationDataWS3 = memberList.get(0).getInstallationMemberResults().getInstallationMember().get(0);
            } else {
                LOGGER.error("Can't find installation in FsmDataServiceLight getMember");
                throw new BusinessException(BusinessException.ErrorCode.ERROR_FIND_INSTALLATION);
            }

            List<GetInstallationResult> installationFSMList = fsmDataServiceLight.getInstallation(installationNumber);
            if (installationFSMList != null && !installationFSMList.isEmpty() && !installationFSMList.get(0).getInstallationfulldataresults().getInstallationfulldataresult().isEmpty()) {
                installationDataWS4 = installationFSMList.get(0).getInstallationfulldataresults().getInstallationfulldataresult().get(0);
            } else {
                LOGGER.error("Can't find installation in FsmDataServiceLight getInstallation");
                throw new BusinessException(BusinessException.ErrorCode.ERROR_FIND_INSTALLATION);
            }

            InstallationData installationData = createInstallationData(installationDataWS1, installationDataWS2, installationDataWS3, installationDataWS4);
            LOGGER.debug("getInstallationData({}) : {}", installationNumber, installationData);
            return installationData;
        } catch (DataServiceFault e) {
            LOGGER.error("Error searching installation data" , e);
            throw new FrameworkException(e);
        }
    }


    private InstallationData createInstallationData(Mainstallationdataresult mainstallationdataresult, GetInstallationDataResults installationData2, InstallationMember installationData3, Installationfulldataresult installationDataWS4) {
        if (mainstallationdataresult == null) {
            return null;
        } else {
            InstallationData data = new InstallationData();

            data.setClazz (installationDataWS4.getClazz() );

            data.setMember(installationData3.getMember());

            data.setPersonaContacto(mainstallationdataresult.getAliasName());
            data.setNumeroInstalacion(mainstallationdataresult.getInsNo());
            data.setMonitoringStatus(mainstallationdataresult.getMonStat());
            data.setPersonaContacto(mainstallationdataresult.getAliasName());
            data.setTitular(mainstallationdataresult.getName());
            data.setPanel(mainstallationdataresult.getPanel());
            data.setTelefono(mainstallationdataresult.getPhone());
            data.setCodigoPostal(mainstallationdataresult.getZip());
            data.setContactoPlan(mainstallationdataresult.getInstallationcontactsresults().getInstallationcontactsresult().get(0).getNAM());
            data.setTelefonoPlan(mainstallationdataresult.getInstallationcontactsresults().getInstallationcontactsresult().get(0).getPH1());

            if (installationData2 != null && !installationData2.getGetInstallationDataResult().isEmpty()) {
                data.setVersion(installationData2.getGetInstallationDataResult().get(0).getVersion());
            }
            return data;
        }
    }
}
