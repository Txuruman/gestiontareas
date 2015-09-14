package es.securitasdirect.tareas.service;


import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.model.Agent;
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

    public List<Tarea> findAll(Agent agent) {
        return find(agent.getDesktopDepartment(), agent.getAgentIBS(), agent.getAgentCountryJob(), "chain_id>=0");
    }

    public List<Tarea> findByPhone(Agent agent, String phone) {
        return findByPhone(agent.getDesktopDepartment(), agent.getAgentIBS(), agent.getAgentCountryJob(), phone);
    }

    public List<Tarea> findByContractNumber(Agent agent, String ctrNo) {
        return findByContractNumber(agent.getDesktopDepartment(), agent.getAgentIBS(), agent.getAgentCountryJob(), ctrNo);
    }

    public List<Tarea> findByInstallationNumber(Agent agent, String installationNumber) {
        return findByInstallationNumber(agent.getDesktopDepartment(), agent.getAgentIBS(), agent.getAgentCountryJob(), installationNumber);
    }

    protected List<Tarea> findByPhone(String desktopDepartment,
                                      String ccUserId,
                                      String country,
                                      String phone) {
        LOGGER.debug("Search Task By Phone {}", phone);
        return find(desktopDepartment,
                ccUserId,
                country,
                "CONTACT_INFO='" + phone + "'");
    }

    public List<Tarea> findByContractNumber(String desktopDepartment,
                                            String ccUserId,
                                            String country,
                                            String ctrNo
    ) {
        LOGGER.debug("Search Task By Contract Number {}", ctrNo);
        return find(desktopDepartment,
                ccUserId,
                country,
                "CTR_NO='" + ctrNo + "'");
    }

    protected List<Tarea> findByInstallationNumber(String desktopDepartment,
                                                   String ccUserId,
                                                   String country,
                                                   String installation
    ) {
        LOGGER.debug("Search Task By Installation Number {}", installation);
        return find(desktopDepartment,
                ccUserId,
                country,
                "INSTALACION='" + installation + "'");
    }

    /**
     * Find Task by filter and agent info
     *
     * @param desktopDepartment
     * @param ccUserId
     * @param country
     * @param filter
     * @return
     */
    protected List<Tarea> find(String desktopDepartment,
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


            LOGGER.debug("Search Tarea with filter {} returned {} result", filter, cclResponse.getOperationResult().getResultMessage());

            taskList = new ArrayList<Tarea>();
            for (int i = 0; i < cclResponse.getColumnReturn().size(); i++) {
                Map<String, String> map = tareaServiceTools.loadCclResponseMap(cclResponse, i);
                try {
                    taskList.add(tareaServiceTools.createTareaFromParameters(map));
                } catch (BusinessException e) {
                    //Vamos a capturar esta exception porque las tareas de tipo Aviso cuando no se encuentra el aviso dan error
                    LOGGER.warn("Ignoring error in search " + e.getErrorCode());
                }
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
