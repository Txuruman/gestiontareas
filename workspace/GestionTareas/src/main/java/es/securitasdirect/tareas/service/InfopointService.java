package es.securitasdirect.tareas.service;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import com.webservice.CCLIntegration;
import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.tickets.*;
import es.securitasdirect.tareas.model.tickets.operations.CreateTicket;
import es.securitasdirect.tareas.model.tickets.operations.OperateTicket;
import es.securitasdirect.tareas.model.tickets.responses.DATA;
import es.securitasdirect.tareas.support.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tempuri.CloseSesionResponse;
import org.tempuri.CreateSessionResponse;
import org.tempuri.FunSoap;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.RowErrorAA;
import org.wso2.ws.dataservice.RowErrorCA;
import org.wso2.ws.dataservice.SPAVISOSOPERACIONESPortType;
import wsticketsv2.WsTicketsSoap;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
@Named(value = "infopointService")
@Singleton
public class InfopointService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfopointService.class);

    @Resource
    private String processCodeCreateMaintenanceInfopoint;

    @Inject
    protected FunSoap wsInfopoint;

    public String createSession(String matricula) {
        return createSession(matricula, null);
    }

    public void closeSession(String sessionCode) {
        closeSession(sessionCode, null);
    }

    public void closeSession(Agent agent) {
        closeSession(agent.getInfopointSession(),agent.getAuth_ipAddress());
        agent.setInfopointSession(null);
    }

    public boolean validateSession(String sessionCode) {
        return validateSession(sessionCode, null);
    }

    public boolean validarProceso(String sessionCode, String matricula, String proceso) {
        return validarProceso(sessionCode, matricula, proceso, null);
    }

    public boolean createSession(Agent agent) {
        String session =  createSession(agent.getAgentIBS(), agent.getAuth_ipAddress());
        agent.setInfopointSession(session);
        return session!=null;
    }

    public boolean validarProceso(String sessionCode, Agent agent,String proceso) {
        return validarProceso(sessionCode, agent.getAgentIBS(), proceso, agent.getAuth_ipAddress());
    }

    public boolean isAllowedCreateMaintenance( Agent agent) {
        if (agent.getInfopointSession()!=null) {
            return validarProceso(agent.getInfopointSession(), agent.getAgentIBS(), processCodeCreateMaintenanceInfopoint, agent.getAuth_ipAddress());
        } else {
            return false;
        }
    }

    /**
     * Creates a session in Infopoint
     *
     * @param matricula
     * @param ip
     * @return
     */
    public String createSession(String matricula, String ip) {
        //La IP es parametro requerido al WS
        if (ip == null || ip.isEmpty()) ip = "0.0.0.0";

        CreateSessionResponse.CreateSessionResult session = wsInfopoint.createSession(matricula, "", ip);
        if (session != null && !session.getContent().isEmpty()) {
            try {
                String errorCode = getErrorCode(((ElementNSImpl) session.getContent().get(0)));
                String errorDescription = getErrorDescription(((ElementNSImpl) session.getContent().get(0)));
                String sessionCode = getSessionCode(((ElementNSImpl) session.getContent().get(0)));
                if ("0".equals(errorCode)) {
                    LOGGER.error("Error returned from create infopoint session {}-{}", errorCode, errorDescription);
                    throw new BusinessException(BusinessException.ErrorCode.ERROR_CREATE_SESSION_INFOPONT);
                } else {
                    LOGGER.debug("Successfully created session in infopoint for user {}", matricula);
                    return sessionCode;
                }
            } catch (Exception e) {
                LOGGER.error("Error reading result of CreateSession in Infopoint", e);
                throw new BusinessException(BusinessException.ErrorCode.ERROR_CREATE_SESSION_INFOPONT);
            }
        } else {
            throw new BusinessException(BusinessException.ErrorCode.ERROR_CREATE_SESSION_INFOPONT);
        }
    }

    public void closeSession(String sessionCode, String ip) {
        //La IP es parametro requerido al WS
        if (ip == null || ip.isEmpty()) ip = "0.0.0.0";

        CloseSesionResponse.CloseSesionResult closeSesionResult = wsInfopoint.closeSesion(sessionCode, ip);
        if (closeSesionResult != null && !closeSesionResult.getContent().isEmpty()) {
            String errorCode = getErrorCode(((ElementNSImpl) closeSesionResult.getContent().get(0)));
            String errorDescription = getErrorDescription(((ElementNSImpl) closeSesionResult.getContent().get(0)));
            String resultDescription = getResultDescription(((ElementNSImpl) closeSesionResult.getContent().get(0)));
            if (!"code=\"0\"".equals(errorCode)) {
                LOGGER.error("Error closing infopoint Session {}-{}-{}", errorCode, errorDescription, resultDescription);
            } else {
                LOGGER.debug("Successfully closed session in infopoint");
            }
        } else {
            LOGGER.error("Error reading result of CloseSession in Infopoint");
        }
    }

    /**
     * Valida una session.
     * Como este WS devuelve un XML en vez de datos estructurados simplemente vamos a comprobar que no contenga el texto de error
     * <p/>
     * <p/>
     * <p/>
     * <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
     * <soap:Body>
     * <ValidarSesionResponse xmlns="http://tempuri.org/">
     * <ValidarSesionResult>&lt;DATA>&lt;ERROR number='-3' description='Acceso Denegado' />&lt;/DATA></ValidarSesionResult>
     * </ValidarSesionResponse>
     * </soap:Body>
     * </soap:Envelope>
     * <p/>
     * <p/>
     * <p/>
     * <p/>
     * <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
     * <soap:Body>
     * <ValidarSesionResponse xmlns="http://tempuri.org/">
     * <ValidarSesionResult>&lt;DATA>&lt;USER GBL_ID_USR='M0OOS' GBL_ID_PAIS='1' GBL_ID_CC='92' GBL_DES_USR='Oscar Sanchez Martinez' GBL_ID_LANG='es' GBL_DS_DPTO='ATENCION_CLI(1)' GBL_ID_DPTO='23' />&lt;/DATA></ValidarSesionResult>
     * </ValidarSesionResponse>
     * </soap:Body>
     * </soap:Envelope>
     *
     * @param sessionCode
     * @return
     */
    public boolean validateSession(String sessionCode, String ip) {
        //La IP es parametro requerido al WS
        if (ip == null || ip.isEmpty()) ip = "0.0.0.0";

        String textoError = "ERROR number='-3'";
        String resultadoValidation = wsInfopoint.validarSesion(sessionCode, ip);

        if (resultadoValidation != null && !resultadoValidation.isEmpty() && !resultadoValidation.contains(textoError)) {
            LOGGER.debug("Validate infopoint session: {}", textoError);
            return true;
        } else {
            LOGGER.debug("The Infopoint session is invalid : {}", resultadoValidation);
            return false;
        }
    }


    /**
     * Valida el permiso de acceso a un proceso
     *
     * @param sessionCode
     * @param ip
     * @return Correcto
     * <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
     * <soap:Body>
     * <ValidarProcesoResponse xmlns="http://tempuri.org/">
     * <ValidarProcesoResult>&lt;DATA>&lt;USER GBL_MTR_PERMISOS='99999' />&lt;/DATA></ValidarProcesoResult>
     * </ValidarProcesoResponse>
     * </soap:Body>
     * </soap:Envelope>
     * <p/>
     * Incorrecto:
     * <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
     * <soap:Body>
     * <ValidarProcesoResponse xmlns="http://tempuri.org/">
     * <ValidarProcesoResult>&lt;DATA>&lt;ERROR number='-4' description='EL USUARIO DF1512 NO TIENE PERMISOS PARA ACCEDER A ESTE PROCESO.' />&lt;/DATA></ValidarProcesoResult>
     * </ValidarProcesoResponse>
     * </soap:Body>
     * </soap:Envelope>
     */
    public boolean validarProceso(String sessionCode, String matricula, String proceso, String ip) {
        //La IP es parametro requerido al WS
        if (ip == null || ip.isEmpty()) ip = "0.0.0.0";

        String textoError = "ERROR number";
        String resultadoValidation = wsInfopoint.validarProceso(sessionCode, ip, proceso, matricula);

        if (resultadoValidation != null && !resultadoValidation.isEmpty() && !resultadoValidation.contains(textoError)) {
            LOGGER.debug("User {} has access to process {} : {}", matricula, proceso, resultadoValidation);
            return true;
        } else {
            LOGGER.debug("User {} don't  have access to process {} : {} ", matricula, proceso, resultadoValidation);
            return false;
        }
    }


    /**
     * Extrae de un resultado el codigo de error
     * <createSessionResult>
     * <DAT xmlns="">
     * <ERR code="0" desc=""/>
     * <SES t="E51A103032B07B6BE01A586AD23A317C9F2AA606B8CF7156D1285C597274A54EA48DA2575390BF6B0769909DADB6143851297193C5F3A49F28ABA2FE42270477F8C54EDF503D3088CDA5899B908D4D68BD55342ED6B8E3F070667FCC575CC51A"/>
     * </DAT>
     * </createSessionResult
     *
     * @return
     */
    private String getErrorCode(ElementNSImpl elementNS) {
        return String.valueOf(elementNS.getElementsByTagName("ERR").item(0).getAttributes().getNamedItem("code"));
    }

    /**
     * Extrae de un resultado la descripción del error
     * <createSessionResult>
     * <DAT xmlns="">
     * <ERR code="0" desc=""/>
     * <SES t="E51A103032B07B6BE01A586AD23A317C9F2AA606B8CF7156D1285C597274A54EA48DA2575390BF6B0769909DADB6143851297193C5F3A49F28ABA2FE42270477F8C54EDF503D3088CDA5899B908D4D68BD55342ED6B8E3F070667FCC575CC51A"/>
     * </DAT>
     * </createSessionResult
     *
     * @return
     */
    private String getErrorDescription(ElementNSImpl elementNS) {
        return String.valueOf(elementNS.getElementsByTagName("ERR").item(0).getAttributes().getNamedItem("desc"));
    }

    /**
     * Extrae de un resultado  el codigo de la session
     * <createSessionResult>
     * <DAT xmlns="">
     * <ERR code="0" desc=""/>
     * <SES t="E51A103032B07B6BE01A586AD23A317C9F2AA606B8CF7156D1285C597274A54EA48DA2575390BF6B0769909DADB6143851297193C5F3A49F28ABA2FE42270477F8C54EDF503D3088CDA5899B908D4D68BD55342ED6B8E3F070667FCC575CC51A"/>
     * </DAT>
     * </createSessionResult
     *
     * @return
     */
    private String getSessionCode(ElementNSImpl elementNS) {
        return String.valueOf(elementNS.getElementsByTagName("SES").item(0).getAttributes().getNamedItem("t").getNodeValue());
    }


    private String getResultDescription(ElementNSImpl elementNS) {
        return String.valueOf(elementNS.getElementsByTagName("RES").item(0).getAttributes().getNamedItem("desc"));
    }

    /*
    Ejemplo cerrar session con error:

    <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
       <soap:Body>
          <closeSesionResponse xmlns="http://tempuri.org/">
             <closeSesionResult>
                <DAT xmlns="">
                   <ERR code="-1" desc="Ha Caducado la Sesion"/>
                   <RES desc="ERROR AL CERRAR SESION"/>
                </DAT>
             </closeSesionResult>
          </closeSesionResponse>
       </soap:Body>
    </soap:Envelope>

    Ejemplo cerrar session sin error:

    <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
       <soap:Body>
          <closeSesionResponse xmlns="http://tempuri.org/">
             <closeSesionResult>
                <DAT xmlns="">
                   <ERR code="0" desc=""/>
                   <RES desc="SESION CERRADA"/>
                </DAT>
             </closeSesionResult>
          </closeSesionResponse>
       </soap:Body>
    </soap:Envelope>

     */

}
