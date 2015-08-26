package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.dto.response.PairListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * @author jel
 */

@Controller
@RequestMapping("/exceltaskcommon")
public class CommonExcelTaskController extends BaseController {

    @Inject
    private ExternalDataService externalDataService;


    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExcelTaskController.class);

    /**
     * Tipos de cierre para tareas tipo excel
     * @return
     */
    @RequestMapping(value = "/getClosingReason", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    PairListResponse getClosingReason() {
        String SERVICE_MESSAGE = "commonexcel.closingReasonList";
        LOGGER.debug("Calling external data service: getExcelClosingReason");
        PairListResponse response;
        try{
            List<Pair> closingReasonList = externalDataService.getExcelClosingReason();
            response = new PairListResponse(closingReasonList);
            LOGGER.debug("Closing reason list: {}", closingReasonList);
        }catch(Exception e){
            response = new PairListResponse(processException(e,SERVICE_MESSAGE));
        }
        return response;
    }
}
