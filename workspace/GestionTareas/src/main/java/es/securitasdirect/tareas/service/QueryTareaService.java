package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import com.webservice.Item;
import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.web.controller.params.ServiceParams;
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
@Named(value = "queryTareaService")
@Singleton
public class QueryTareaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTareaService.class);

    @Inject
    protected CCLIntegration cclIntegration;
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;

    /**
     * Maps the name of the Calling List to the specific tipe of Tarea
     */
    @Resource(name = "callingListToModel")
    private Map<String, List<String>> callingListToModel;

    @Resource(name = "applicationUser")
    private String applicationUser;


    //checkCallingListContact fields
    public static final String TASK_TYPE = "CALLING_LIST"; // Calling List Name


    public Tarea queryTarea(String ccUserId,
                            String callingList,
                            String id) {

        //
        String ccIdentifier         = "ATC_SPN";
        String country              = "SPAIN";
        String filter               = "chain_id=" + id;

        LOGGER.debug("Calling to service CallingList with values: ccIdentifier: {} applicationUser: {} ccUserId: {} filter: {} callingList: {} country: {}", ccIdentifier, applicationUser, ccUserId, filter, callingList, country);

        Map<String, String> responseMap = checkCallingListContact(ccIdentifier,
                applicationUser,
                ccUserId,
                filter,
                callingList,
                country);

        if (responseMap != null) {
            String tipoTarea = getTaskTypeFromMap(responseMap);
            Tarea tarea = null;
            if (tipoTarea != null) {
                if (tipoTarea.equals(Constants.TAREA_AVISO)) {
                    tarea = createTareaAvisoFromParameters(responseMap);
                } else if (tipoTarea.equals(Constants.TAREA_LISTADOASSISTANT)) {
                    tarea = createTareaListadoAssistantFromParameters(responseMap);
                } else if (tipoTarea.equals(Constants.TAREA_ENCUESTAMANTENIMIENTO)) {
                    tarea = createMaintenanceSurveyTaskFromParameters(responseMap);
                } else if (tipoTarea.equals(Constants.TAREA_ENCUESTAMARKETING)) {
                    tarea = createTareaEncuestaMarketingFromParameters(responseMap);
                } else if (tipoTarea.equals(Constants.TAREA_KEYBOX)) {
                    tarea = createTareaKeyboxFromParameters(responseMap);
                } else if (tipoTarea.equals(Constants.TAREA_LIMPIEZACUOTA)) {
                    tarea = createTareaLimpiezaCuotaFromParameters(responseMap);
                } else if (tipoTarea.equals(Constants.TAREA_OTRASCAMPANAS)) {
                    tarea = createTareaOtrasCampanasFromParameters(responseMap);
                } else if (tipoTarea.equals(Constants.TAREA_MANTENIMIENTO)) {
                    tarea = createTareaMantenimientoFromParameters(responseMap);
                }

                LOGGER.debug("Tipo tarea:{}", tipoTarea);
                LOGGER.debug("Tarea: {}", tarea);

                return tarea;
            } else {
                LOGGER.warn("Tipo tarea not found in response map checkCallingListContact.");
                return null;
            }
        } else {
            LOGGER.warn("Response map not recovered from service call checkCallingListContact.");
            return null;
        }
    }

    private void tareaToLog(String tipoTarea, Tarea tarea) {
        if (tipoTarea != null) {
            if (tipoTarea.equals(Constants.TAREA_AVISO)) {
                TareaAviso castedTarea = (TareaAviso) tarea;
                LOGGER.debug("Tarea - TareaAviso: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_LISTADOASSISTANT)) {
                TareaListadoAssistant castedTarea = (TareaListadoAssistant) tarea;
                LOGGER.debug("Tarea - TareaListadoAssistant: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_ENCUESTAMANTENIMIENTO)) {
                MaintenanceSurveyTask castedTarea = (MaintenanceSurveyTask) tarea;
                LOGGER.debug("Tarea - MaintenanceSurveyTask: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_ENCUESTAMARKETING)) {
                MarketingSurveyTask castedTarea = (MarketingSurveyTask) tarea;
                LOGGER.debug("Tarea - MarketingSurveyTask: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_KEYBOX)) {
                KeyboxTask castedTarea = (KeyboxTask) tarea;
                LOGGER.debug("Tarea - KeyboxTask: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_LIMPIEZACUOTA)) {
                TareaLimpiezaCuota castedTarea = (TareaLimpiezaCuota) tarea;
                LOGGER.debug("Tarea - TareaLimpiezaCuota: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_OTRASCAMPANAS)) {
                TareaOtrasCampanas castedTarea = (TareaOtrasCampanas) tarea;
                LOGGER.debug("Tarea - TareaOtrasCampanas: {}", castedTarea);
            } else if (tipoTarea.equals(Constants.TAREA_MANTENIMIENTO)) {
                TareaMantenimiento castedTarea = (TareaMantenimiento) tarea;
                LOGGER.debug("Tarea - TareaMantenimiento: {}", castedTarea);
            }
        }
    }

    /**
     * Returns the specific Tarea type from the response, mapping with a map configured in Spring
     */
    private String getTaskTypeFromMap(Map<String, String> responseMap) {
        String callingList = responseMap.get(TASK_TYPE);
        return getTaskTypeFromCallingList(callingList);
    }

    /**
     * Returns the specific Tarea type from the calling list, mapping with a map configured in Spring
     * @param callingList
     * @return
     */
    public String getTaskTypeFromCallingList(String callingList) {
        for (String tipoTarea : callingListToModel.keySet()) {
            if (callingListToModel.get(tipoTarea) != null && callingListToModel.get(tipoTarea).contains(callingList)) {
                return tipoTarea;
            }
        }
        return null;
    }


    private boolean cclResponseHasError(CclResponse response) {
        boolean hasError = true;
        if (response != null && response.getOperationResult() != null) {
            if (response.getOperationResult().getResultCode() == 200) {
                hasError = false;
            }
        }
        return hasError;
    }

    private void printCclResponseError(CclResponse response) {
        StringBuilder sb = new StringBuilder();
        if (response != null && response.getOperationResult() != null) {
            sb.append("ERROR CODE: [");
            sb.append(response.getOperationResult().getResultCode());
            sb.append("]");
            sb.append(" - ERROR MESSAGE: [");
            if (response.getOperationResult().getResultMessage() != null) {
                sb.append(response.getOperationResult().getResultMessage());
            } else {
                sb.append("Not found");
            }
            sb.append("]");
        } else {
            sb.append("ERROR DATA NOT FOUND");
        }
        LOGGER.error(sb.toString());
    }

    /**
     * Crea un objeto de tipo TareaAviso consultando el WS
     *
     * @param avisobyIdResult
     * @return
     */
    private TareaAviso mapTareaAvisoFromWS(GetAvisobyIdResult avisobyIdResult) {
        TareaAviso tarea = new TareaAviso();

        tarea.setIdAviso(avisobyIdResult.getIdaviso().intValue());
        tarea.setIdentificativoAvisoTarea(avisobyIdResult.getIdaviso().intValue());
        tarea.setObservaciones(avisobyIdResult.getObservaciones());
        tarea.setEstado(avisobyIdResult.getEstado().toString()); //TODO
        tarea.setTitular(avisobyIdResult.getTitular());
        tarea.setFechaCreacion(toDateFromMap(avisobyIdResult.getFechaCreacion())); //TODO OJO PUEDE SER DISTINTO

        // Tipos de Aviso: el primero es el utilizado, el segundo y el tercero son sólo informativos.
        tarea.setTipoAviso1(avisobyIdResult.getTipo()); //TODO Pendiente sacar estos tipos
        tarea.setTipoAviso2(null);
        tarea.setTipoAviso3(null);

        //Motivos: el primero es el utilizado, el segundo y el tercero son sólo informativos.
        tarea.setMotivo1(avisobyIdResult.getMotivo()); //TODO Pendiente sacar estos motivos
        tarea.setMotivo2(null);
        tarea.setMotivo3(null);
        tarea.setRequeridoPor(avisobyIdResult.getRequeridoPor());
        tarea.setNumeroInstalacion(avisobyIdResult.getInsNo());
        tarea.setHorarioDesde(avisobyIdResult.getDesde().toString());//TODO FORMATO
        tarea.setHorarioHasta(avisobyIdResult.getHasta().toString());//TODO FORMATO
        tarea.setDatosContacto(avisobyIdResult.getContacto());

        return tarea;
    }



    private Tarea createTareaMantenimientoFromParameters(Map<String, String> parameters) {
        TareaMantenimiento tarea = new TareaMantenimiento();
        loadTareaCommons(tarea, parameters);

        tarea.setContrato(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_CONTRATO));
        tarea.setDireccion(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_DIRECCION));
        tarea.setCiudad(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_CIUDAD));
        tarea.setFechaEvento(toDateFromMap(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_FECHAEVENTO)));
        tarea.setTipificacion(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_TIPIFICACION));
        tarea.setAgenteAsignado(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_AGENTEASIGNADO));
        tarea.setAgenteCierre(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_AGENTECIERRE));
        tarea.setOpcionTipificacion(toIntegerFromMap(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_OPCIONTIPIFICACION)));
        tarea.setKey1(toIntegerFromMap(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_KEY1)));
        tarea.setKey2(toIntegerFromMap(parameters.get(ServiceParams.TAREA_MANTENIMIENTO_KEY2)));

        return tarea;
    }


    private Tarea createMaintenanceSurveyTaskFromParameters(Map<String, String> parameters) {
        MaintenanceSurveyTask task = new MaintenanceSurveyTask();
        loadTareaCommons(task, parameters);
        loadTareaExcelCommons(task, parameters);

        task.setMaintenanceNumber(toIntegerFromMap(parameters.get(ServiceParams.ENCUESTAMNTOS_MANTENIMIENTO)));
        task.setTechnician(parameters.get(ServiceParams.ENCUESTAMNTOS_TECNICO));
        task.setManager(parameters.get(ServiceParams.ENCUESTAMNTOS_RESPONSABLE));
        task.setCostCenter(parameters.get(ServiceParams.ENCUESTAMNTOS_CENTROCOSTE));
        task.setValuationKeyReason(parameters.get(ServiceParams.ENCUESTAMNTOS_RAZON));
        task.setSolution(parameters.get(ServiceParams.ENCUESTAMNTOS_SOLUCION));
        task.setAgreement(parameters.get(ServiceParams.ENCUESTAMNTOS_COMPROMISO));
        task.setDestinationDepartment(parameters.get(ServiceParams.ENCUESTAMNTOS_DPTO_DESTINO));
        return task;
    }

    private Tarea createTareaEncuestaMarketingFromParameters(Map<String, String> parameters) {
        MarketingSurveyTask tarea = new MarketingSurveyTask();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setDate(toDateFromMap(parameters.get(ServiceParams.ENCUESTASMKT_FECHA)));
        tarea.setReason(parameters.get(ServiceParams.ENCUESTASMKT_MOTIVO));
        return tarea;
    }

    private Tarea createTareaKeyboxFromParameters(Map<String, String> parameters) {
        KeyboxTask tarea = new KeyboxTask();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setContrato(parameters.get(ServiceParams.KEYBOX_CONTRATO));
        tarea.setInvoiceDate(toDateFromMap(parameters.get(ServiceParams.KEYBOX_FECHA_FACTURA)));
        tarea.setInvoiceNumber(parameters.get(ServiceParams.KEYBOX_NUMERO_FACTURA));
        tarea.setLineValue(toIntegerFromMap(parameters.get(ServiceParams.KEYBOX_IMPORTE_LINEA)));
        tarea.setIdentificadorItem(parameters.get(ServiceParams.KEYBOX_ID_ITEM));
        return tarea;
    }

    private Tarea createTareaOtrasCampanasFromParameters(Map<String, String> parameters) {
        TareaOtrasCampanas tarea = new TareaOtrasCampanas();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setCampo1(parameters.get(ServiceParams.OTRASCAMPANAS_CAMPO1));
        tarea.setCampo2(parameters.get(ServiceParams.OTRASCAMPANAS_CAMPO2));
        tarea.setCampo3(parameters.get(ServiceParams.OTRASCAMPANAS_CAMPO3));
        tarea.setComentario(parameters.get(ServiceParams.OTRASCAMPANAS_CAMPO3));
        tarea.setTipoCampana(parameters.get(ServiceParams.OTRASCAMPANAS_TIPOTAREA));

        return tarea;
    }

    private Tarea createTareaLimpiezaCuotaFromParameters(Map<String, String> parameters) {
        TareaLimpiezaCuota tarea = new TareaLimpiezaCuota();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setContrato(parameters.get(ServiceParams.LIMPIEZA_CUOTA_CONTRATO));
        tarea.setDepartamentoAsignado(parameters.get(ServiceParams.LIMPIEZA_CUOTA_DPT_ASIGNADO));
        tarea.setDescripcionIncidencia(parameters.get(ServiceParams.LIMPIEZA_CUOTA_DESCRIPTCION));

        return tarea;
    }

    /**
     * Las respuestas de CclResponse tiene un mapa de mapas pero la lista de items solo retorna un item siempre.
     * Retornamos un mapa <clave,valor> de los datos de la respuesta.
     *
     * @return
     */
    private Map<String, String> loadCclResponseMap(CclResponse reponse) {
        HashMap<String, String> responseMap = new HashMap<String, String>();
        for (Item item : reponse.getColumnReturn().get(0).getItem()) { //Recorre la lista, aunque no parezca una lista
            if (item != null) {
                if (item.getCampo() != null && item.getValor() != null) {
                    responseMap.put(item.getCampo(), item.getValor());
                } else if (item.getCampo() != null && item.getValor() == null) {
                    responseMap.put(item.getCampo(), ""); //TODO QUITAR
                }
            }
        }
        return responseMap;
    }

    /**
     * Web Service Calling and transformation of the results to Map
     *
     * @param ccIdentifier
     * @param applicationUser
     * @param ccUserId
     * @param filter
     * @param callingList
     * @param country
     * @return
     */
    private Map<String, String> checkCallingListContact(String ccIdentifier,
                                                        String applicationUser,
                                                        String ccUserId,
                                                        String filter,
                                                        String callingList,

                                                        String country) {
        //WS Call
        CclResponse response = null;
        try {
            response = cclIntegration.checkCallingListContact(ccIdentifier,
                    applicationUser,
                    ccUserId,
                    filter,
                    Arrays.asList(""), //Allways ask for all the parameters
                    Arrays.asList(callingList),
                    country);
        } catch (Exception e) {
            LOGGER.error("Error calling CCL checkCallingListContact.", e);
            return null;
        }

        //Results Map
        Map<String, String> responseMap = null;

        //Comprobar respuesta si tiene error:
        if (cclResponseHasError(response)) {
            printCclResponseError(response);
            //TODO Definir si realizar otras operaciones en caso de error
        } else {
            //Si la respuesta no tiene error
            //Obtener el tipo de respuesta
            responseMap = loadCclResponseMap(response);
            LOGGER.debug("checkCallingListContact result {}", responseMap);
        }
        return responseMap;
    }


    /**
     * Carga los datos comunes de Tarea pasados por parametro
     */
    private Tarea loadTareaCommons(Tarea tarea, Map<String, String> parameters) {
        assert tarea != null && parameters != null;
        tarea.setNumeroInstalacion(parameters.get(ServiceParams.NUMERO_INSTALACION));
        //TODO JESUS
        return tarea;
    }

    /**
     * Carga los datos comunes de Tarea Excel pasados por parametro
     *
     * @return
     */
    private TareaExcel loadTareaExcelCommons(TareaExcel tarea, Map<String, String> parameters) {
        assert tarea != null && parameters != null;
        //TODO Pendiente saber formato lista tarea.setClosingReason(parameters.get(ServiceParams.MOTIVO_CIERRE));
        tarea.setCompensation(parameters.get(ServiceParams.COMPENSACION));
        return tarea;
    }

    private Tarea createTareaListadoAssistantFromParameters(Map<String, String> parameters) {
        TareaListadoAssistant tarea = new TareaListadoAssistant();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setNumeroInstalacion(parameters.get(ServiceParams.ASSISTANT_INSTALACION));
        tarea.setMaintenanceNumber(toIntegerFromMap(parameters.get(ServiceParams.ASSISTANT_MANTENIMIENTO)));
        tarea.setTechnician(parameters.get(ServiceParams.ASSISTANT_TECNICO));
        tarea.setDepartamento(parameters.get(ServiceParams.ASSISTANT_DEPARTAMENTO));
        tarea.setGrupoPanel(parameters.get(ServiceParams.ASSISTANT_GRUPOPANEL));
        tarea.setTotalSinIVA(toFloatFromMap(parameters.get(ServiceParams.ASSISTANT_TOTALSINIVA)));
        tarea.setTotalConIVA(toFloatFromMap(parameters.get(ServiceParams.ASSISTANT_TOTALCONIVA)));
        tarea.setNumeroParte(parameters.get(ServiceParams.ASSISTANT_NPARTE));
        tarea.setFechaArchivo(toDateFromMap(parameters.get(ServiceParams.ASSISTANT_ARCHIVO_FECHA)));
        tarea.setSubtipoIncidencia(parameters.get(ServiceParams.ASSISTANT_SUBIDA_INC_FECHA));
        tarea.setFechaPago(toDateFromMap(parameters.get(ServiceParams.ASSISTANT_PAGO_FECHA)));
        tarea.setIncidencia(parameters.get(ServiceParams.ASSISTANT_INCIDENCIA));
        tarea.setSubtipoIncidencia(parameters.get(ServiceParams.ASSISTANT_SUBINCIDENCIA));
        tarea.setSolicitudCliente(parameters.get(ServiceParams.ASSISTANT_SOLICITUD));
        tarea.setCambiosIncidencia(parameters.get(ServiceParams.ASSISTANT_CAMBIOS));
        tarea.setBoFechaGestion(toDateFromMap(parameters.get(ServiceParams.ASSISTANT_BO_GESTION_FECHA)));
        tarea.setBoMatricula(parameters.get(ServiceParams.ASSISTANT_BO_MATRICULA));
        tarea.setBoFechaRecepcion(toDateFromMap(parameters.get(ServiceParams.ASSISTANT_BO_RECEPCION_FECHA)));
        tarea.setBoTipo(parameters.get(ServiceParams.ASSISTANT_BO_EMPRESA_PARTICULAR));
        tarea.setBoComentarios(parameters.get(ServiceParams.ASSISTANT_BO_COMENTARIOS));
        tarea.setTelefono(parameters.get(ServiceParams.ASSISTANT_CONTACTO_TELEFONO));
        return tarea;
    }


    /**
     * @param responseMap
     * @return
     */
    private TareaAviso createTareaAvisoFromParameters(Map<String, String> responseMap) {
        TareaAviso tarea = null;
        Integer idAviso = toIntegerFromMap(responseMap.get(ServiceParams.CALLING_LIST_RESPONSE_ID_AVISO));
        if (idAviso != null && idAviso != 0) {
            try {
                tarea = getTareaByIdAviso(idAviso);
            } catch (DataServiceFault dataServiceFault) {
                LOGGER.error("Error calling service for TareaAviso data or not valid ID_AVISO: {}", idAviso);
            }
        } else {
            LOGGER.warn("ID_AVISO (idaviso) not found in response map");
        }
        return tarea;
    }


    /**
     * Obtiene una Tarea by Id de Aviso mediante WS
     *
     * @param idAviso
     * @return
     */
    protected TareaAviso getTareaByIdAviso(Integer idAviso) throws DataServiceFault {
        TareaAviso tarea = null;
        if (idAviso != null && !idAviso.equals(0)) {
            List<GetAvisobyIdResult> avisobyId = spAioTareas2.getAvisobyId(idAviso);
            if (avisobyId != null && !avisobyId.isEmpty()) {
                Iterator<GetAvisobyIdResult> iterator = avisobyId.iterator();
                List<TareaAviso> bloquesTareasAviso = new ArrayList<TareaAviso>();
                while(iterator.hasNext()){
                    GetAvisobyIdResult avisobyIdResultItem = iterator.next();
                    bloquesTareasAviso.add(mapTareaAvisoFromWS(avisobyIdResultItem));
                }
                tarea = bloquesTareasAviso.get(0);
                if(bloquesTareasAviso.size()>=2){
                    tarea.setTipoAviso2(bloquesTareasAviso.get(1).getTipoAviso1());
                    tarea.setMotivo2(bloquesTareasAviso.get(1).getMotivo1());
                }
                if(bloquesTareasAviso.size()>=3){
                    tarea.setTipoAviso3(bloquesTareasAviso.get(2).getTipoAviso1());
                    tarea.setMotivo3(bloquesTareasAviso.get(2).getMotivo1());
                }
                if(bloquesTareasAviso.size()>=4){
                    LOGGER.debug("No se esperan más de 3 tipos y motivos de aviso en la respuesta");
                }
            }
            LOGGER.debug("Queried Aviso ({}): {}", idAviso, tarea);
        } else {
            LOGGER.warn("Can not query for an not informed idAviso.");
        }
        return tarea;
    }

    /**
     * Conversores , indicar parametro (String).
     * Ejemplo de lo que devuelve Aviso Service: 2014-08-22T10:08:26.000+02:00
     */
    private Date toDateFromMap(String value) {
        return new Date(); //TODO JAVIER
    }

    private Integer toIntegerFromMap(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return Integer.valueOf(value);
        }
    }

    private Float toFloatFromMap(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return Float.valueOf(value);
        }
    }

    private List toListFromMap(String value) {
        if (true) throw new RuntimeException("No implementado");
        return new ArrayList();
    }


}
