package es.securitasdirect.tareas.service;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.exceptions.FrameworkException;
import es.securitasdirect.tareas.model.Agent;
import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.TareaAviso;
import fsm.split.CodificationsType;
import fsm.split.FSMSplitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tempuri.CloseSesionResponse;
import org.tempuri.CreateSessionResponse;
import org.tempuri.FunSoap;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Random;


/**
 * Para distinguir en que aplicación externa se debe de crear el mantenimiento.
 * Si es MMS vamos a MMS y en otro caso a TOA
 * El que tenemos hecho es el de TOA
 */
@Named(value = "splitService")
@Singleton
public class SplitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplitService.class);

    public static final String TOA = "TOA";
    public static final String MMS = "MMS";


//    @Inject
//    protected FSMSplitService wsFSMSplitService;
    @Resource(name = "applicationUser")
    private String applicationUser;
    @Inject
    private AvisoService avisoService;


    /**
     * Hay un cambio fundamental en la creación de Mantenimientos: no se van a contemplar los Mantenimientos de tipo MMS, sólo los de tipo TOA.
     * Es decir, ya no hay que realizar el Split, directamente se van a los de tipo SOA.
     *
     * @param agent
     * @param tareaAviso
     * @param installationData
     * @return
     */
    public String getMaintenanceApplication(Agent agent, TareaAviso tareaAviso, InstallationData installationData) {
        String result = TOA;
        if (result.equals(TOA)) {
            //Si es tipo TOA hay que insertar el aviso mediante ws
            avisoService.wsInsertDatosAvisoTOA(agent, tareaAviso,installationData);
        }
        return result;
    }



    /* Ejemplo llamada split service
    public String getMaintenanceApplication(Agent agent, TareaAviso tareaAviso, InstallationData installationData) {
        assert agent.getCurrentLanguage()!=null: "El campo currentLanguage del Agente es requerido";
        String country = "es" ; ///agent.getCurrentLanguage();//Ejemplo, debe venir 'es', no 'SPAIN'
        String app = applicationUser;
        String zipCode = installationData.getCodigoPostal();
        String monitoringStatus = installationData.getMonitoringStatus();
        String member = installationData.getMember();
        String splitclass = installationData.getClazz();
        //De Gestion de pedidos o no, será un cero constante
        Integer gpd = 0;
        java.util.List<fsm.split.CodificationsType> codifications = new ArrayList<CodificationsType>();
        //Meter los tipo y motivo, //Calltipe problem como el del TOA, 100|101|1|#Segundo#Tercero ?????
        if (tareaAviso.getTipoAviso1() != null && tareaAviso.getMotivo1() != null) {
            CodificationsType ct1 = new CodificationsType();
            ct1.setCallType(tareaAviso.getTipoAviso1());
            ct1.setProblem(tareaAviso.getMotivo1());
            codifications.add(ct1);
        }
        if (tareaAviso.getTipoAviso2() != null && tareaAviso.getMotivo2() != null) {
            CodificationsType ct2 = new CodificationsType();
            ct2.setCallType(tareaAviso.getTipoAviso1());
            ct2.setProblem(tareaAviso.getMotivo1());
            codifications.add(ct2);
        }
        if (tareaAviso.getTipoAviso3() != null && tareaAviso.getMotivo3() != null) {
            CodificationsType ct3 = new CodificationsType();
            ct3.setCallType(tareaAviso.getTipoAviso1());
            ct3.setProblem(tareaAviso.getMotivo1());
            codifications.add(ct3);
        }


        //Respuestas
        javax.xml.ws.Holder<String> result = new javax.xml.ws.Holder<String>();
        javax.xml.ws.Holder<String> description = new javax.xml.ws.Holder<String>();
        javax.xml.ws.Holder<String> split = new javax.xml.ws.Holder<String>();

        try {
            wsFSMSplitService.checkSplit(country, app, zipCode, monitoringStatus, member, splitclass, gpd, codifications, result, description, split);
        } catch (Exception e) {
            LOGGER.error("Error calling checkSplit", e);
            throw new FrameworkException(e);
        }


        if (result.value == null || result.value.equalsIgnoreCase("NACK") || split.value == null || split.value.equalsIgnoreCase("ERROR")) {
            LOGGER.error("Error callong Split Service {} {} {}", result.value, split.value, description.value);
            throw new BusinessException(BusinessException.ErrorCode.ERROR_SPLIT, description.value == null ? "''" : description.value);
        } else {
            return split.value;
        }

    }
*/

}
