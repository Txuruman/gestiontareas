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

    public List<Tarea> findByPhone(String desktopDepartment,
                                   String ccUserId,
                                   String country,
                                   String phone) {
        LOGGER.debug("Search Task By Phone {}", phone);
        return find(desktopDepartment,
                ccUserId,
                country,
                "CONTACT_INFO='" + phone + "'");
    }

    public List<Tarea> findByCustomer(String desktopDepartment,
                                      String ccUserId,
                                      String country,
                                      String customer
    ) {
        LOGGER.debug("Search Task By Customer {}", customer);
        return find(desktopDepartment,
                ccUserId,
                country,
                "CTR_NO='" + customer + "'");
    }

    public List<Tarea> find(String desktopDepartment,
                            String ccUserId,
                            String country,
                            String filter) {
        List<Tarea> taskList = null;
        if (desktopDepartment == null || desktopDepartment.isEmpty()
                || applicationUser == null || applicationUser.isEmpty()
                || ccUserId == null || ccUserId.isEmpty()
                || country == null || country.isEmpty()
                ) {

            LOGGER.warn("Parámetros para la búsqueda de tareas por teléfono no informados");
        } else {


            CclResponse cclResponse = cclIntegration.checkCallingListContact(
                    desktopDepartment,
                    applicationUser,
                    ccUserId,
                    filter,
                    Arrays.asList(""),
                    getConfiguredCallingList(),
                    country
            );


            LOGGER.debug("Search Tarea with filter {} returned {} result", filter,cclResponse.getOperationResult().getResultMessage());

             taskList = new ArrayList<Tarea>();
            for (int i=0; i<cclResponse.getColumnReturn().size();i++) {
                Map<String, String> map = tareaServiceTools.loadCclResponseMap(cclResponse,i);
                taskList.add(tareaServiceTools.createTareaFromParameters(map));
            }

        }
        return taskList;
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
