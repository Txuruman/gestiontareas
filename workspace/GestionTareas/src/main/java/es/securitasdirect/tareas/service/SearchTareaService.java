package es.securitasdirect.tareas.service;


import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.MarketingSurveyTask;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;

/**
 * Service to Search for Tareas
 */
@Named(value = "searchTareaService")
@Singleton
public class SearchTareaService {

    @Inject
    private CCLIntegration cclIntegration;
    @Inject
    private TareaServiceTools tareaServiceTools;
    @Resource(name = "applicationUser")
    private String applicationUser;

    /**
     * Maps the name of the Calling List to the specific tipe of Tarea
     */
    @Resource(name = "callingListToModel")
    private Map<String, List<String>> callingListToModel;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTareaService.class);

    public List<Tarea> findByPhone(String phone){
        LOGGER.debug("Searching Tarea by Phone {}", phone);
        List<Tarea> tareas = createDummy();
        LOGGER.debug("Found tareaList by Phone: {}", tareas);
        return tareas;
    }

    public List<Tarea> findByInstalacion (String instalacion){
        LOGGER.debug("Searching Tarea by Installation: {}", instalacion);
        List<Tarea> tareas = createDummy();
        LOGGER.debug("Found tareaList by Installation", tareas);
        return tareas;
    }

    public List<Tarea> findByPhone(String ccIdentifier,
                                   String ccUserId,
                                   String phone,
                                   String country){
        StringBuilder sbFilter = new StringBuilder();
        sbFilter.append("contact_info=").append(phone);
        return find(ccIdentifier,
                ccUserId,
                phone,
                country,sbFilter.toString());
    }

    public List<Tarea> find(String ccIdentifier,
                                   String ccUserId,
                                   String phone,
                                   String country, String filter){
        List<Tarea> response;
        if(ccIdentifier==null || ccIdentifier.isEmpty()
                || applicationUser==null || applicationUser.isEmpty()
                || ccUserId==null || ccUserId.isEmpty()
                || phone==null || phone.isEmpty()
                || country==null || country.isEmpty()
                ){
            response = null;
            LOGGER.warn("Parámetros para la búsqueda de tareas por teléfono no informados");
        }else {

            List<String> returnData = Arrays.asList("");

            List<String> callingListList = new ArrayList<String>();
            for (String callingListType : callingListToModel.keySet()) {
                List<String> callingListTypeCallingListList = callingListToModel.get(callingListType);
                callingListList.addAll(callingListTypeCallingListList);
            }

            CclResponse cclResponse = cclIntegration.checkCallingListContact(
                    ccIdentifier,
                    applicationUser,
                    ccUserId,
                    filter,
                    returnData,
                    callingListList,
                    country
            );

            LOGGER.debug("CCL RESPONSE, EXTRAER TAREAS");
            Map<String, String> map = tareaServiceTools.loadCclResponseMap(cclResponse);
            response = tareaServiceTools.createTareaListFromParameters(map);
        }
        return response;
    }

    public List<Tarea> findByClient(String client){
        LOGGER.debug("Searching Task by Client: {}", client);
        List<Tarea> tareas = createDummy();
        return tareas;
    }


    private List<Tarea> createDummy() {
        LOGGER.warn("Creating dummy Tarea list");
        List<Tarea> tareas = new ArrayList<Tarea>();
        Tarea ejemploAviso = new TareaAviso();
        ejemploAviso.setNumeroInstalacion("1234");
        ejemploAviso.setEstado(1);
        ejemploAviso.setTelefono("652696869");
        ejemploAviso.setCallingList("CC_CA_IL_500");
        ejemploAviso.setNumeroContrato("526369");
        ejemploAviso.setCodigoCliente(500);
        ejemploAviso.setFechaReprogramacion(new Date());
        tareas.add(ejemploAviso);

        Tarea ejemploTareaMantenimiento = new TareaMantenimiento();
        ejemploTareaMantenimiento.setNumeroInstalacion("12345");
        ejemploTareaMantenimiento.setEstado(2);
        ejemploTareaMantenimiento.setTelefono("652696789");
        ejemploTareaMantenimiento.setCallingList("CC_CA_IL_502");
        ejemploTareaMantenimiento.setNumeroContrato("526370");
        ejemploTareaMantenimiento.setCodigoCliente(501);
        ejemploTareaMantenimiento.setFechaReprogramacion(new Date());
        tareas.add(ejemploTareaMantenimiento);

        Tarea ejemploTareaEncuestaMarketing = new MarketingSurveyTask();
        ejemploTareaEncuestaMarketing.setNumeroInstalacion("123456");
        ejemploTareaEncuestaMarketing.setEstado(3);
        ejemploTareaEncuestaMarketing.setTelefono("652696478");
        ejemploTareaEncuestaMarketing.setCallingList("CC_CA_IL_510");
        ejemploTareaEncuestaMarketing.setNumeroContrato("526371");
        ejemploTareaEncuestaMarketing.setCodigoCliente(502);
        ejemploTareaEncuestaMarketing.setFechaReprogramacion(new Date());
        tareas.add(ejemploTareaEncuestaMarketing);

        Tarea ejemploTareaListadoAssistant = new TareaListadoAssistant();
        ejemploTareaListadoAssistant.setNumeroInstalacion("1234567");
        ejemploTareaListadoAssistant.setEstado(4);
        ejemploTareaListadoAssistant.setTelefono("652696785");
        ejemploTareaListadoAssistant.setCallingList("CC_CA_IL_512");
        ejemploTareaListadoAssistant.setNumeroContrato("526372");
        ejemploTareaListadoAssistant.setCodigoCliente(503);
        ejemploTareaListadoAssistant.setFechaReprogramacion(new Date());
        tareas.add(ejemploTareaListadoAssistant);

        return tareas;

    }

}
