package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import com.webservice.Item;

import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.TareaExcel;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.external.*;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.ws.dataservice.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.ws.Holder;

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

    /**
     * Datos cierre tarea mantenimiento configurados en spring
     */
    @Resource(name = "datosCierreTareaMantenimiento")
    protected List<CloseMaintenancePair> datosCierreTareaMantenimiento;


    @Resource(name = "datosCierreTareaExcel")
    protected List<Pair> datosCierreTareaExcel;


    /**
     * Call for closing reason query
     */
    /*
    WS 20: Leer valores CIERRE de T.Excel
        Descripción: Obtener la lista de valores para mostrar en un desplegable.
        Web Service:
        	POR DESARROLLAR


        	Entradas: ninguna
        	Salidas:
        	Lista de tipos de Cierre, para mostrar en la lista de selección del campo “Cierre” de las Tareas de tipo Excel.
        	Observaciones:
        	Actualmente no hay ninguna Tabla que almacene los valores posibles. El motivo de desarrollar este web service es para facilitar su posible almacenamiento en un futuro en una tabla, aislando esta operativa del aplicativo de Tareas.
        	Por lo tanto, simplemente devolverá esta lista de valores:
        AVISO GESTIONADO
        ILOCALIZADO
        Compensación
        ACUERDO CON CLIENTE
        NO PROCEDE APERTURA
        DESMONTAJE
     */
    public List<Pair> getExcelClosingReason() throws DataServiceFault {
        return datosCierreTareaExcel;
    }


    /**
     * Lectura de los valores de tipo de cierre dependiendo de los valores de tipo de aviso y tipo de motivo, más el grupo del usuario.
     *
     * @param idTipo   Tipo=Código del Tipo1 de la Tarea
     * @param idMotivo Motivo=Código del Motivo1 de la Tarea
     * @param agentIBS código de IBS del agente  recibido en parámetro bp_agentIBS
     */
    /*
    	Observaciones:
        	Internamente, el web service debe realizar la siguiente operativa.
        	En la tabla OPER, listar los IDGRP cuyo IDMATRICULA coincida con el Usuario (normalmente sólo habrá uno, pero puede haber varios)
        	En la tabla TCIERRE_MOTIVO, listar los IDTIPOCIERRE cuyo IDTIPO sea Tipo, y (IDMOTIVO esté vacío ó IDMOTIVO sea Motivo) y (IDGRP=0 ó IDGRP sea uno de los IDGRP obtenidos de la tabla OPER)
    */
    public List<StringPair> getNotificationClosing(TareaAviso tarea, String agentIBS) throws DataServiceFault {
        LOGGER.debug("Calling for closing type list, params: tarea: {}, agentIBS: {}", tarea, agentIBS);
        List<StringPair> result = new ArrayList<StringPair>();
        if (tarea!=null && tarea.getTipoAviso1() != null && tarea.getMotivo1() != null && agentIBS != null) {
        	
        	//Si los tipos y motivos son null, los seteamos a vacío
        	if (tarea.getTipoAviso2()==null)
        		tarea.setTipoAviso2("");
        	if (tarea.getTipoAviso3()==null)
        		tarea.setTipoAviso3("");
        	if (tarea.getMotivo2()==null)
        		tarea.setMotivo2("");
        	if (tarea.getMotivo3()==null)
        		tarea.setMotivo3("");
        	
			List<GetMotivoCierreFiltradoResult> motivosCierre = spAioTareas2.getMotivoCierreFiltrado(agentIBS, tarea.getTipoAviso1(), tarea.getMotivo1(), tarea.getTipoAviso2(), tarea.getMotivo2(), tarea.getTipoAviso3(), tarea.getMotivo3());
			
			if (motivosCierre!=null && !motivosCierre.isEmpty()){
	            LOGGER.debug("WS closing type list reponse: {}", motivosCierre);
	            for (GetMotivoCierreFiltradoResult motivoCierre : motivosCierre) {
	                result.add(new StringPair(motivoCierre.getIdMotivoCierre(), motivoCierre.getDsMotivoCierre()));
	            }
			}
			else{
				LOGGER.error("Error getting motivosCierre");
                throw new BusinessException(BusinessException.ErrorCode.ERROR_NOTIFICATION_CLOSING);
			}
        } else {
            LOGGER.warn("Not informed parameters for closing type list query, params: tarea: {}, idGrp: {}", tarea, agentIBS);
            throw new BusinessException(BusinessException.ErrorCode.ERROR_NOTIFICATION_CLOSING);
        }

        return result;
    }

    /**
     * Combo de Tipo para las tareas de Tipo Aviso
     */
    public List<Pair> getNotificationType() throws DataServiceFault {
        //TODO Meter cache
        LOGGER.debug("Calling for notification type list");
        List<Pair> result = new ArrayList<Pair>();
        for (Tipoaviso tipoaviso : spAioTareas2.getTipoAviso()) {
            result.add(new Pair(tipoaviso.getTipo().intValue(), tipoaviso.getDescripcion()));
        }
        return result;
    }

    /**
     * Devuelve la descripción de un tipo de aviso dado el identificador
     *
     * @param idtipo
     * @return
     */
    public String getNotificationTypeDescription(String idTipo) {
        if (idTipo == null) return null;
        try {
            for (Pair pair : getNotificationType()) {
                if (pair.getId().equals(Integer.valueOf(idTipo))) {
                    return pair.getValue();
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * Combo de Motivo para las tareas de Tipo Aviso
     */
    public List<BigIntegerPair> getTypeReasonList(Integer typeId) throws DataServiceFault {
        LOGGER.debug("Calling for notification type reason list, params: typeId: {}", typeId);
        List<BigIntegerPair> result = new ArrayList<BigIntegerPair>();
        if (typeId != null) {
            List<Tipomotivo> wsResult = spAioTareas2.getTipoMotivo(typeId);
            for (Tipomotivo tipomotivo : wsResult) {
                result.add(new BigIntegerPair(tipomotivo.getId(), tipomotivo.getDescripcion()));
            }
            LOGGER.debug("Notification type reason list reponse: {}", result);
        } else {
            LOGGER.warn("Not informed parameters for  notification type reason list query, params: typeId: {}{}", typeId);
        }
        return result;
    }

    /**
     * Devuelve la descripción de un Motivo de aviso dado el identificador de tipo y Motivo
     *
     * @return
     */
    public String getNotificationTypeReasonDescription(String idTipo, String idMotivo) {
        if (idTipo == null || idMotivo == null) return null;
        try {
            for (BigIntegerPair pair : getTypeReasonList(Integer.valueOf(idTipo))) {
                if (pair.getId().toString().equals(idMotivo)) {
                    return pair.getValue();
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }




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
     * Obtiene todos los datos de un Key1 por id para devolver el keyid
     *
     * @param skey
     * @return
     */
    public String getKey1KeyId(Integer skey) throws DataServiceFault {
        if (skey != null) {
            List<GetKey1DIYResult> listaKey1 = spAioTareas2.getKey1DIY();
            for (GetKey1DIYResult getKey1DIYResult : listaKey1) {
                if (getKey1DIYResult.getSKey().intValue() == skey) {
                    return getKey1DIYResult.getKeyid();
                }
            }
        }
        return null;
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

    public String getKey2KeyId(Integer skey1,Integer skey2) throws DataServiceFault {
        if (skey1 != null && skey2!=null) {
            List<GetKey2DIYResult> listaKey2 = spAioTareas2.getKey2DIY(skey1);
            if (listaKey2 != null) {
                for (GetKey2DIYResult getKey2DIYResult : listaKey2) {
                    //Viene un sublistado de valores, parece que siempre viene solo uno, así que cogemos el primero
                    if (!getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().isEmpty() &&
                            getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().get(0).getSKey().intValue()==skey2) {
                        return getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().get(0).getKeyid();
                    }
                }
            }
        }
        return null;
    }


    /** Version antigua de getDatosAdicionalesCierreTareaAviso en la que si se utilizaba un parametro
     public List<Pair> getDatosAdicionalesCierreTareaAviso(Integer closingTypeId) throws DataServiceFault {
     LOGGER.debug("Calling for closing type aditional data list, params: closingTypeId: {}", closingTypeId);
     List<Pair> result = new ArrayList<Pair>();
     if(closingTypeId!=null){
     List<TipoDatosAdicionalesTipoCierre> tiposDatosAdicionalesTipoCierreList = spAioTareas2.getClosingTypeAditionalData(closingTypeId.toString());
     for (TipoDatosAdicionalesTipoCierre tipoDatosAdicionalesTipoCierre : tiposDatosAdicionalesTipoCierreList) {
     result.add(new Pair(Integer.parseInt(tipoDatosAdicionalesTipoCierre.getId()), tipoDatosAdicionalesTipoCierre.getValor()));
     //TODO ver tipos de retorno de datos (CONSULTA WS)
     }
     LOGGER.debug("Closing type aditional data list reponse: {}", result);
     }else {
     LOGGER.warn("Not informed parameters for  closing type aditional data list query, params: closingTypeId: {}", closingTypeId);
     }
     return result;
     } */

    /** */
    public List<Pair> getDatosAdicionalesCierreTareaAviso() throws DataServiceFault {
        LOGGER.debug("Calling for closing type aditional data list");
        List<Pair> result = new ArrayList<Pair>();
        List<Entry> tiposDatosAdicionalesTipoCierreList = spAioTareas2.getClosingTypeAditionalData();
        for (Entry tipoDatosAdicionalesTipoCierre : tiposDatosAdicionalesTipoCierreList) {
            result.add(new Pair(Integer.parseInt(tipoDatosAdicionalesTipoCierre.getId()), tipoDatosAdicionalesTipoCierre.getDescription()));
        }
        LOGGER.debug("Closing type aditional data list reponse: {}", result);
        return result;
    }


    /**
     * Tipos de cierre para una tarea tipo mantenimiento
     *
     * @return
     */
    public List<DescriptionPair> getCancelationTypeMaintenanceTask() {
        List<DescriptionPair> cancelationTypeList = new ArrayList<DescriptionPair>();
        for (DescriptionPair descriptionPair : datosCierreTareaMantenimiento) {
            cancelationTypeList.add(new DescriptionPair(descriptionPair));
        }
        return cancelationTypeList;
    }
}
