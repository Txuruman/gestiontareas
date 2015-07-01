package es.securitasdirect.tareas.service;

import com.webservice.CCLIntegration;
import com.webservice.CclResponse;
import com.webservice.Fila;
import com.webservice.Item;
import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.TareaExcel;
import es.securitasdirect.tareas.model.TareaMantenimiento;
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
@Named(value = "queryTareaService")
@Singleton
public class QueryTareaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryTareaService.class);

    @Inject
    private CCLIntegration cclIntegration;

    @Resource(name = "callingListToModel")
    private Map<String,List<String>> callingListToModel;


    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;

    public static final  String EMPTY_STRING = "";

    //checkCallingListContact fields
    public static final String TASK_TYPE = "CALLING_LIST"; // Calling List Name


    public Tarea queryTarea(  String ccIdentifier,
                              String applicationUser,
                              String ccUserId,
                              String filter,
                              List<String> returnData,
                              List<String> callingList,
                              String country){
        LOGGER.debug("Calling to service CallingList with values:\n" +
                        "ccIdentifier: {}\n" +
                        "applicationUser: {}\n" +
                        "ccUserId: {}\n" +
                        "filter: {}\n" +
                        "returnData: {}\n" +
                        "callingList: {}\n" +
                        "country: {}", ccIdentifier, applicationUser,
                        ccUserId, filter, returnData, callingList,country);

        Map<String,String> responseMap = checkCallingListContact(ccIdentifier,
                applicationUser,
                ccUserId,
                filter,
                returnData,
                callingList,
                country);

        if(responseMap!=null){
            String tipoTarea = loadTipoTarea(responseMap);
            Tarea tarea = null;
            if(tipoTarea!=null){
                if (tipoTarea.equals("TareaAviso")) {
                    tarea = createTareaAvisoFromParameters(responseMap);
                } else if (tipoTarea.equals("TareaListadoAssistant")) {
                    tarea = createTareaListadoAssistantFromParameters(responseMap);
                }else if(tipoTarea.equals("TareaEncuestaMantenimiento")){
                    tarea = createTareaEncuestaMantenimientoFromParameters(responseMap);
                }else if(tipoTarea.equals("TareaEncuestaMarketing")){
                    tarea = createTareaEncuestaMarketingFromParameters(responseMap);
                }else if(tipoTarea.equals("TareaKeybox")){
                    tarea = createTareaKeyboxFromParameters(responseMap);
                }else if(tipoTarea.equals("TareaLimpiezaCuota")){
                    tarea =createTareaLimpiezaCuotaFromParameters(responseMap);
                }else if(tipoTarea.equals("TareaOtrasCampanas")){
                    tarea =  createTareaOtrasCampanasFromParameters(responseMap);
                }else if(tipoTarea.equals("TareaMantenimiento")){
                    tarea = createTareaMantenimientoFromParameters(responseMap);
                }
                return tarea;
            }else{
                LOGGER.warn("Tipo tarea not found in response map");
                return null;
            }
        } else {
            LOGGER.warn("Response map not recovered from service call");
            return null;
        }
    }

    private String loadTipoTarea(Map<String, String> responseMap){
        String callingList = responseMap.get(TASK_TYPE);
        for (String tipoTarea : callingListToModel.keySet()) {
            if (callingListToModel.get(tipoTarea)!=null && callingListToModel.get(tipoTarea).contains(callingList)) {
                return tipoTarea;
            }
        }
        return null;
    }


    private boolean cclResponseHasError(CclResponse response){
        boolean hasError = true;
        if(response!=null && response.getOperationResult()!=null){
            if(response.getOperationResult().getResultCode()==200){
                hasError = false;
            }
        }
        return hasError;
    }

    private void printCclResponseError(CclResponse response){
        StringBuilder sb = new StringBuilder();
        if(response!=null && response.getOperationResult()!=null){
            sb.append("ERROR CODE: [");
            sb.append(response.getOperationResult().getResultCode());
            sb.append("]");
            sb.append(" - ERROR MESSAGE: [");
            if(response.getOperationResult().getResultMessage()!=null){
                sb.append(response.getOperationResult().getResultMessage());
            }else{
                sb.append("Not found");
            }
            sb.append("]");
        }else{
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
        tarea.setIdentificativoAvisoTarea(avisobyIdResult.getIdaviso().intValue());
        tarea.setObservaciones(avisobyIdResult.getObservaciones());
        tarea.setEstado(avisobyIdResult.getEstado().toString()); //TODO
        tarea.setTitular(avisobyIdResult.getTitular());
        tarea.setFechaCreacion((avisobyIdResult.getFechaCreacion() == null ? null : avisobyIdResult.getFechaCreacion().toGregorianCalendar().getTime()));

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


    /**
     * Consulta de los valores para el combo Key1 de tareas de mantenimiento
     */
    public Map<Integer, String> getDesplegableKey1() throws DataServiceFault {

        return null;
    }

    private Tarea createTareaMantenimientoFromParameters(Map<String, String> parameters) {
        TareaMantenimiento tarea = new TareaMantenimiento();
        loadTareaCommons(tarea, parameters);

        tarea.setContrato(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_CONTRATO));
        tarea.setDireccion(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_DIRECCION));
        tarea.setCiudad(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_CIUDAD));
        tarea.setFechaEvento(toDateFromParam(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_FECHAEVENTO)));
        tarea.setTipificacion(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_TIPIFICACION));
        tarea.setAgenteAsignado(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_AGENTEASIGNADO));
        tarea.setAgenteCierre(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_AGENTECIERRE));
        tarea.setOpcionTipificacion(toIntegerFromParam(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_OPCIONTIPIFICACION)));
        tarea.setKey1(toIntegerFromParam(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_KEY1)));
        tarea.setKey2(toIntegerFromParam(parameters.get(ExternalParams.TAREA_MANTENIMIENTO_KEY2)));

        return tarea;
    }



    private Tarea createTareaEncuestaMantenimientoFromParameters(Map<String, String> parameters) {
        TareaEncuestaMantenimiento tarea = new TareaEncuestaMantenimiento();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setNumeroMantenimiento(toIntegerFromParam(parameters.get(ExternalParams.ENCUESTAMNTOS_MANTENIMIENTO)));
        tarea.setTecnico(parameters.get(ExternalParams.ENCUESTAMNTOS_TECNICO));
        tarea.setResponsable(parameters.get(ExternalParams.ENCUESTAMNTOS_RESPONSABLE));
        tarea.setCentroCoste(parameters.get(ExternalParams.ENCUESTAMNTOS_CENTROCOSTE));
        tarea.setRazonClaveValoracion(parameters.get(ExternalParams.ENCUESTAMNTOS_RAZON));
        tarea.setSolucion(parameters.get(ExternalParams.ENCUESTAMNTOS_SOLUCION));
        tarea.setCompromiso(parameters.get(ExternalParams.ENCUESTAMNTOS_COMPROMISO));
        tarea.setDepartamentoDestino(parameters.get(ExternalParams.ENCUESTAMNTOS_DPTO_DESTINO));
        return tarea;
    }

    private Tarea createTareaEncuestaMarketingFromParameters(Map<String, String> parameters) {
        TareaEncuestaMarketing tarea = new TareaEncuestaMarketing();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setFecha(toDateFromParam(parameters.get(ExternalParams.ENCUESTASMKT_FECHA)));
        tarea.setMotivo(parameters.get(ExternalParams.ENCUESTASMKT_MOTIVO));
        return tarea;
    }

    private Tarea createTareaKeyboxFromParameters(Map<String, String> parameters) {
        TareaKeybox tarea = new TareaKeybox();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setContrato(parameters.get(ExternalParams.KEYBOX_CONTRATO));
        tarea.setFechaFactura(toDateFromParam(parameters.get(ExternalParams.KEYBOX_FECHA_FACTURA)));
        tarea.setNumeroFactura(parameters.get(ExternalParams.KEYBOX_NUMERO_FACTURA));
        tarea.setImporteLinea(toIntegerFromParam(parameters.get(ExternalParams.KEYBOX_IMPORTE_LINEA)));
        tarea.setIdentificadorItem(parameters.get(ExternalParams.KEYBOX_ID_ITEM));
        return tarea;
    }

    private Tarea createTareaOtrasCampanasFromParameters(Map<String, String> parameters) {
        TareaOtrasCampanas tarea = new TareaOtrasCampanas();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setCampo1(parameters.get(ExternalParams.OTRASCAMPANAS_CAMPO1));
        tarea.setCampo2(parameters.get(ExternalParams.OTRASCAMPANAS_CAMPO2));
        tarea.setCampo3(parameters.get(ExternalParams.OTRASCAMPANAS_CAMPO3));
        tarea.setComentario(parameters.get(ExternalParams.OTRASCAMPANAS_CAMPO3));
        tarea.setTipoCampana(parameters.get(ExternalParams.OTRASCAMPANAS_TIPOTAREA));

        return tarea;
    }

    private Tarea createTareaLimpiezaCuotaFromParameters(Map<String, String> parameters) {
       TareaLimpiezaCuota tarea = new TareaLimpiezaCuota();
       loadTareaCommons(tarea, parameters);
       loadTareaExcelCommons(tarea, parameters);

       tarea.setContrato(parameters.get(ExternalParams.LIMPIEZA_CUOTA_CONTRATO));
       tarea.setDepartamentoAsignado(parameters.get(ExternalParams.LIMPIEZA_CUOTA_DPT_ASIGNADO));
       tarea.setDescripcionIncidencia(parameters.get(ExternalParams.LIMPIEZA_CUOTA_DESCRIPTCION));

       return tarea;
    }

    /**
     * Las respuestas de CclResponse tiene un mapa de mapas pero la lista de items solo retorna un item siempre.
     * Retornamos un mapa <clave,valor> de los datos de la respuesta
     * @return
     */
    private Map<String, String> loadCclResponseMap(CclResponse reponse){
        HashMap<String, String> responseMap = new HashMap<String, String>();
        Iterator<Item> iterator = reponse.getColumnReturn().get(0).getItem().iterator();
        while(iterator.hasNext()) {
            Item item = iterator.next();
            if(item!=null){
                if(item.getCampo()!=null && item.getValor()!=null){
                    responseMap.put(item.getCampo(), item.getValor());
                }else if(item.getCampo()!=null && item.getValor()==null){
                    responseMap.put(item.getCampo(), EMPTY_STRING);
                }
            }
        }
        LOGGER.debug("Loaded response map:\n{}", responseMap);
        return responseMap;
    }

    private Map<String, String> checkCallingListContact(String ccIdentifier,
                                                        String applicationUser,
                                                        String ccUserId,
                                                        String filter,
                                                        List<String> returnData,
                                                        List<String> callingList,
                                                        String country){

        CclResponse response = cclIntegration.checkCallingListContact(ccIdentifier,
                applicationUser,
                ccUserId,
                filter,
                returnData,
                callingList,
                country);
        Map<String, String> responseMap = null;

        //Comprobar respuesta si tiene error:
        if(cclResponseHasError(response)){
            printCclResponseError(response);
            //TODO Definir si realizar otras operaciones en caso de error
        }else{
            //Si la respuesta no tiene error
            //Obtener el tipo de respuesta
            responseMap = loadCclResponseMap(response);
        }
        return responseMap;
    }


    /**
     * Carga los datos comunes de Tarea pasados por parametro
     */
    private Tarea loadTareaCommons(Tarea tarea, Map<String, String> parameters) {
        assert tarea != null && parameters != null;
        tarea.setNumeroInstalacion(parameters.get(ExternalParams.NUMERO_INSTALACION));
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
        //TODO Pendiente saber formato lista tarea.setMotivosCierre(parameters.get(ExternalParams.MOTIVO_CIERRE));
        tarea.setCompensacion(parameters.get(ExternalParams.COMPENSACION));
        return tarea;
    }

    private Tarea createTareaListadoAssistantFromParameters(Map<String, String> parameters) {
        TareaListadoAssistant tarea = new TareaListadoAssistant();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setNumeroInstalacion(parameters.get(ExternalParams.ASSISTANT_INSTALACION));
        tarea.setNumeroMantenimiento(toIntegerFromParam(parameters.get(ExternalParams.ASSISTANT_MANTENIMIENTO)));
        tarea.setTecnico(parameters.get(ExternalParams.ASSISTANT_TECNICO));
        tarea.setDepartamento(parameters.get(ExternalParams.ASSISTANT_DEPARTAMENTO));
        tarea.setGrupoPanel(parameters.get(ExternalParams.ASSISTANT_GRUPOPANEL));
        tarea.setTotalSinIVA(toFloatFromParam(parameters.get(ExternalParams.ASSISTANT_TOTALSINIVA)));
        tarea.setTotalConIVA(toFloatFromParam(parameters.get(ExternalParams.ASSISTANT_TOTALCONIVA)));
        tarea.setNumeroParte(parameters.get(ExternalParams.ASSISTANT_NPARTE));
        tarea.setFechaArchivo(toDateFromParam(parameters.get(ExternalParams.ASSISTANT_ARCHIVO_FECHA)));
        tarea.setSubtipoIncidencia(parameters.get(ExternalParams.ASSISTANT_SUBIDA_INC_FECHA));
        tarea.setFechaPago(toDateFromParam(parameters.get(ExternalParams.ASSISTANT_PAGO_FECHA)));
        tarea.setIncidencia(parameters.get(ExternalParams.ASSISTANT_INCIDENCIA));
        tarea.setSubtipoIncidencia(parameters.get(ExternalParams.ASSISTANT_SUBINCIDENCIA));
        tarea.setSolicitudCliente(parameters.get(ExternalParams.ASSISTANT_SOLICITUD));
        tarea.setCambiosIncidencia(parameters.get(ExternalParams.ASSISTANT_CAMBIOS));
        tarea.setBoFechaGestion(toDateFromParam(parameters.get(ExternalParams.ASSISTANT_BO_GESTION_FECHA)));
        tarea.setBoMatricula(parameters.get(ExternalParams.ASSISTANT_BO_MATRICULA));
        tarea.setBoFechaRecepcion(toDateFromParam(parameters.get(ExternalParams.ASSISTANT_BO_RECEPCION_FECHA)));
        tarea.setBoTipo(parameters.get(ExternalParams.ASSISTANT_BO_EMPRESA_PARTICULAR));
        tarea.setBoComentarios(parameters.get(ExternalParams.ASSISTANT_BO_COMENTARIOS));
        tarea.setTelefono(parameters.get(ExternalParams.ASSISTANT_CONTACTO_TELEFONO));
        return tarea;
    }


    /**
     *
     * @param responseMap
     * @return
     */
    private TareaAviso createTareaAvisoFromParameters(Map<String, String> responseMap){
        TareaAviso tarea = null;
        String idAviso = responseMap.get(ExternalParams.ID_AVISO_UP);
        if(idAviso!=null){
            try{
                int idAvisoInt = Integer.valueOf(idAviso);
                if(idAvisoInt!=0){
                    tarea = (TareaAviso)getTareaByIdAviso(idAvisoInt);
                }else{
                    LOGGER.warn("Not a valid ID_AVISO: {}",idAviso);
                    tarea = null;
                }
            }catch(DataServiceFault dataServiceFault){
                LOGGER.error("Error calling service for TareaAviso data or not valid ID_AVISO: {}", idAviso);
            }
        }else{
            LOGGER.warn("ID_AVISO (idaviso) not found in response map");
        }
        return tarea;
    }


    /**
     * Obtiene una Tarea by Id de Aviso
     *
     * @param idAviso
     * @return
     */
    public Tarea getTareaByIdAviso(Integer idAviso) throws DataServiceFault {
        TareaAviso tarea = null;
        if(idAviso!=null && !idAviso.equals(0)){
            List<GetAvisobyIdResult> avisobyId = spAioTareas2.getAvisobyId(idAviso);
            if (avisobyId != null && !avisobyId.isEmpty()) {
                tarea = mapTareaAvisoFromWS(avisobyId.get(0)); //TODO Devuelve una lista aunque se supone que solo es uno, check
            }
            LOGGER.debug("Consultado TareaAviso ({}): {}", idAviso, tarea);
        }else{
            LOGGER.warn("Can not query for an not informed AVISO, returning tarea = null;");
        }
        return tarea;
    }

    /**
     * Conversores , indicar parametro (String)
     */
    private Date toDateFromParam(String value) {
        return new Date(); //TODO JAVIER
    }

    private Integer toIntegerFromParam(String value) {
        return new Integer(0); //TODO JAVIER
    }

    private Float toFloatFromParam(String value) {
        return new Float(0);
    }

    private List toListFromParam(String value) {
        return new ArrayList();
    }


}
