package es.securitasdirect.tareas.web.controller;

import es.securitasdirect.tareas.model.InstallationData;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.service.InstallationService;
import es.securitasdirect.tareas.service.SearchTareaService;
import es.securitasdirect.tareas.web.controller.dto.InstallationDataResponse;
import es.securitasdirect.tareas.web.controller.dto.support.BaseResponse;
import es.securitasdirect.tareas.web.controller.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/installation")
public class InstallationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstallationController.class);

    @Inject
    private InstallationService installationService;
    @Inject
    protected MessageUtil messageUtil;

    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    InstallationDataResponse query(@RequestParam(value = "installationId", required = true) String installationId) {
        LOGGER.debug("Get Installation data for installationId: {}", installationId);
        InstallationDataResponse installationDataResponse = new InstallationDataResponse();
        try{
            InstallationData installationData = installationService.getInstallationData(installationId);

            if(installationData!=null){
                installationDataResponse.setInstallationData(installationData);
                installationDataResponse.success(messageUtil.getProperty("installationData.success"));
                installationDataResponse.success("Prueba de varios mensajes en una respuesta");
            }else{
                installationDataResponse.danger(messageUtil.getProperty("installationData.notFound"));
            }
            return  installationDataResponse;
        }catch(Exception e){
            installationDataResponse.danger(messageUtil.getProperty("exception",e.getMessage(),e.toString()));
            return installationDataResponse;
        }
    }
}
