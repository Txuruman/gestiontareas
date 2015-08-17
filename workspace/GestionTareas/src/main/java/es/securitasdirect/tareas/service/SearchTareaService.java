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
     * Lista de las Calling list configuradas en la aplicación
     */
    private List<String> callingListList;

    /**
     * Maps the name of the Calling List to the specific tipe of Tarea
     */
    @Resource(name = "callingListToModel")
    private Map<String, List<String>> callingListToModel;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTareaService.class);

    public List<Tarea> findByPhone(String ccIdentifier,
                                   String ccUserId,
                                   String country,
                                   String phone) {
        LOGGER.debug("Search Task By Phone {}", phone);
        return find(ccIdentifier,
                ccUserId,
                country,
                "CONTACT_INFO='" + phone + "'");
    }

    public List<Tarea> findByCustomer(String ccIdentifier,
                                      String ccUserId,
                                      String country,
                                      String customer
    ) {
        LOGGER.debug("Search Task By Customer {}", customer);
        return find(ccIdentifier,
                ccUserId,
                country,
                "CTR_NO='" + customer + "'");
    }

    public List<Tarea> find(String ccIdentifier,
                            String ccUserId,
                            String country,
                            String filter) {
        List<Tarea> taskList = null;
        if (ccIdentifier == null || ccIdentifier.isEmpty()
                || applicationUser == null || applicationUser.isEmpty()
                || ccUserId == null || ccUserId.isEmpty()
                || country == null || country.isEmpty()
                ) {

            LOGGER.warn("Parámetros para la búsqueda de tareas por teléfono no informados");
        } else {


            CclResponse cclResponse = cclIntegration.checkCallingListContact(
                    ccIdentifier,
                    applicationUser,
                    ccUserId,
                    filter,
                    new ArrayList<String>(0),
                    getConfiguredCallingList(),
                    country
            );

            LOGGER.debug("Search Tarea with filter {} returned {} results", filter);

             taskList = new ArrayList<Tarea>();
            for (int i=0; i<cclResponse.getColumnReturn().size();i++) {
                Map<String, String> map = tareaServiceTools.loadCclResponseMap(cclResponse,i);
                taskList.add(tareaServiceTools.createTareaFromParameters(map));
            }

        }
        return taskList;
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


    private List<String> getConfiguredCallingList() {
        if (callingListList == null) {
            callingListList = new ArrayList<String>();
            for (String callingListType : callingListToModel.keySet()) {
                List<String> callingListTypeCallingListList = callingListToModel.get(callingListType);
                callingListList.addAll(callingListTypeCallingListList);
            }
        }
        return callingListList;
    }


}
