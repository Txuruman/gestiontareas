package es.securitasdirect.tareas.service;

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

@Inject
    protected FunSoap wsInfopoint;

    public String createSession(String matricula, String ip) {
        CreateSessionResponse.CreateSessionResult session = wsInfopoint.createSession(matricula, "", ip);
        if (session!=null) {
            LOGGER.debug("Created Infopoint Session for {}" ,  matricula);
            return session.toString();
        } else {
          throw new BusinessException(BusinessException.ErrorCode.ERROR_CREATE_SESSION_INFOPONT);
        }
    }

}
