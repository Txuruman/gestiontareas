package es.securitasdirect.tareas.web.controller.task.exceltask;

import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.service.ExternalDataService;
import es.securitasdirect.tareas.service.QueryTareaService;
import es.securitasdirect.tareas.web.controller.BaseController;
import es.securitasdirect.tareas.web.controller.dto.support.DummyResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.wso2.ws.dataservice.DataServiceFault;

import javax.inject.Inject;
import java.util.List;

/**
 * @author jel
 */

@Controller
@RequestMapping("/exceltaskcommon")
public class CommonExcelTaskController extends BaseController {

    @Inject
    private QueryTareaService queryTareaService;
    @Inject
    private DummyResponseGenerator dummyResponseGenerator;
    @Inject
    private ExternalDataService externalDataService;


    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExcelTaskController.class);

    @RequestMapping(value = "/getClosingReason", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public
    @ResponseBody
    List<Pair> getClosingReason() throws DataServiceFault {
        LOGGER.debug("Calling external data service: getClosingReason");
        List<Pair> closingReasonList = externalDataService.getClosingReason();
        LOGGER.debug("Closing reason list: {}", closingReasonList);
        return closingReasonList;
    }


}
