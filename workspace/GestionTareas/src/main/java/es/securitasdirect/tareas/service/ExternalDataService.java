package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import com.webservice.Item;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.TareaExcel;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.external.Pair;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;

/**
 *
 */
@Named(value = "externalDataService")
@Singleton
public class ExternalDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalDataService.class);

    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;
    @Resource(name="datosAdicionalesCierreTareaAviso")
    protected List<Pair> datosAdicionalesCierreTareaAviso;
    @Resource(name="datosAdicionalesCierreTareaExcel")
    protected List<Pair> datosCierreTareaExcel;

    /**
     * Consulta de los valores para el combo Key1 de tareas de mantenimiento
     */
    public List<Pair> getDesplegableKey1() throws DataServiceFault {
        LOGGER.debug("Calling for KEY1 query (for pull down combo)");
        List<Pair> result = new ArrayList<Pair>();
        List<GetKey1DIYResult> listaKey1 = spAioTareas2.getKey1DIY();
        if (listaKey1 != null) {
            for (GetKey1DIYResult getKey1DIYResult : listaKey1) {
                result.add(new Pair(getKey1DIYResult.getSKey().intValue(), getKey1DIYResult.getText()));
            }
        }
        return result;
    }

    /**
     * Call for closing reason query
     */
    public List<Pair> getClosingReason() throws DataServiceFault{
        LOGGER.debug("Calling for closing reason query (for pull down combo)");
        List<Pair> result = dummyPairList();
        return result;
    }



    /**
     * Call for closing reason query
     */
    public List<Pair> getClosing() throws DataServiceFault{
        LOGGER.debug("Calling for closing query (for pull down combo)");
        List<Pair> result = dummyPairList();
        return result;
    }

    /**
     * Creation of pair dummy list for unknown services
     * @return
     */
    private List<Pair> dummyPairList(){
        LOGGER.warn("Creating dummy list for mock a unknown service");
        List<Pair> dummyPairList = new ArrayList<Pair>();
        dummyPairList.add(new Pair(1, "dummy1"));
        dummyPairList.add(new Pair(2, "dummy2"));
        dummyPairList.add(new Pair(3, "dummy3"));
        dummyPairList.add(new Pair(4, "dummy4"));
        return dummyPairList;
    }


    /**
     * Consulta de los valores para el combo Key2 de tareas de mantenimiento
     */
    public List<Pair> getDesplegableKey2(Integer skey1) throws DataServiceFault {
        LOGGER.debug("Consultando listado Desplegable KEY2 {}", skey1);

        List<Pair> result = new ArrayList<Pair>();
        if (skey1 != null) {
            List<GetKey2DIYResult> listaKey2 = spAioTareas2.getKey2DIY(skey1);
            if (listaKey2 != null) {
                for (GetKey2DIYResult getKey2DIYResult : listaKey2) {
                    //Viene un sublistado de valores, parece que siempre viene solo uno, así que cogemos el primero
                    if (!getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().isEmpty()) {
                        result.add(new Pair(getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().get(0).getSKey().intValue(),
                                getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().get(0).getText()));
                    }
                }
            }
        }
        return result;
    }


    public List<Pair> getDatosAdicionalesCierreTareaAviso() {
        assert datosAdicionalesCierreTareaAviso!=null;
        return datosAdicionalesCierreTareaAviso;
    }

    public List<Pair> getDatosCierreTareaExcel() {
        return datosCierreTareaExcel;
    }

}
