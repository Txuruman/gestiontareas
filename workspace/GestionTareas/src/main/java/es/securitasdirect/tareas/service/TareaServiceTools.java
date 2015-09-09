package es.securitasdirect.tareas.service;

import com.webservice.CclResponse;
import com.webservice.Item;
import es.securitasdirect.tareas.exceptions.BusinessException;
import es.securitasdirect.tareas.exceptions.FrameworkException;
import es.securitasdirect.tareas.model.*;
import es.securitasdirect.tareas.model.tareaexcel.*;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import es.securitasdirect.tareas.web.controller.params.TaskServiceParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.GetAvisobyIdResult;
import org.wso2.ws.dataservice.SPAIOTAREAS2PortType;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servicio con mapeos de Tareas para utilizarse en Serach y Query
 */
@Named(value = "tareaServiceTools")
@Singleton
public class TareaServiceTools {
    public static final String TASK_TYPE = "CALLING_LIST"; // Calling List Name

    private static final Logger LOGGER = LoggerFactory.getLogger(TareaServiceTools.class);

    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;

    @Resource(name = "callingListToModel")
    private Map<String, List<String>> callingListToModel;

    /**
     * Returns the specific Tarea type from the response, mapping with a map configured in Spring
     */
    protected String getTaskTypeFromMap(Map<String, String> responseMap) {
        if (responseMap == null) {
            return null;
        } else {
            String callingList = responseMap.get(TASK_TYPE);
            return getTaskTypeFromCallingList(callingList);
        }
    }

    /**
     * Returns the specific Tarea type from the calling list, mapping with a map configured in Spring
     *
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


    public Tarea createTareaFromParameters(Map<String, String> responseMap) {
        Tarea tarea = null;
        if (responseMap != null) {
            String tipoTarea = getTaskTypeFromMap(responseMap);
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

                if (tarea == null) {
                    LOGGER.debug("Couldn´t create Tarea for map");
                } else {
                    LOGGER.debug("Loaded Task {} : {}", tipoTarea, tarea);
                }
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

    /**
     * @param responseMap
     * @return
     */
    private TareaAviso createTareaAvisoFromParameters(Map<String, String> responseMap) {
        TareaAviso tarea = null;
        Integer idAviso = toIntegerFromMap(responseMap.get(TaskServiceParams.TAREA_AVISO_CALLING_LIST_RESPONSE_ID_AVISO));
        if (idAviso != null && idAviso != 0) {
            LOGGER.debug("Calling service for TareaAviso for ID: '{}'", idAviso);
            tarea = getTareaByIdAviso(idAviso);

            if (tarea != null) {
                //No utilizamos loadTareaCommons para tener cuidado de no sobreescribir datos de la consulta del AVISO con lo que tenemos en la TAREA
                tarea.setTelefono(responseMap.get(TaskServiceParams.TAREA_COMMONS_TELEFONO));
                tarea.setCallingList(responseMap.get(TaskServiceParams.TAREA_COMMONS_CALLING_LIST));
                tarea.setNumeroContrato(responseMap.get(TaskServiceParams.TAREA_COMMONS_N_CONTRATO));
                tarea.setId(toIntegerFromMap(responseMap.get(TaskServiceParams.TAREA_COMMONS_ID)));
                tarea.setCampana(responseMap.get(TaskServiceParams.TAREA_CAMPAIGN));
                //Fecha de Reprogramación viene en numerico formato EPOC, i.e:1440588300
                tarea.setFechaReprogramacion(toDateEpocFromMap(responseMap.get(TaskServiceParams.TAREA_COMMONS_FECHA_REPROGRAMACION)));

            } else {
                LOGGER.error("Ticket not found by  id {}", idAviso);
                //TODO Descomentar, para pruebas está comentado para poder hacer busqueda completa
                //throw new BusinessException(BusinessException.ErrorCode.ERROR_FIND_TICKET, idAviso.toString(),responseMap.get(TaskServiceParams.TAREA_COMMONS_CALLING_LIST),responseMap.get(TaskServiceParams.TAREA_COMMONS_ID));
                return null;
            }
        } else {
            LOGGER.warn("ID_AVISO (idaviso) or 0 not found in response map");
            throw new BusinessException(BusinessException.ErrorCode.ERROR_FIND_TICKET, "null");
        }
        return tarea;
    }

    private Tarea createTareaListadoAssistantFromParameters(Map<String, String> parameters) {
        TareaListadoAssistant tarea = new TareaListadoAssistant();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setNumeroInstalacion(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_INS_NUMERO_INSTALACION));
        tarea.setMaintenanceNumber(toIntegerFromMap(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_N_MANTENIMIENTO)));
        tarea.setTechnician(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_TECNICO));
        tarea.setDepartamento(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_DEPARTAMENTO));
        tarea.setGrupoPanel(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_GRUPO_PANEL));
        tarea.setTotalSinIVA(toFloatFromMap(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_TOTAL_SIN_IVA)));
        tarea.setTotalConIVA(toFloatFromMap(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_TOTAL_CON_IVA)));
        tarea.setNumeroParte(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_PARTE_N));
        tarea.setFechaArchivo(toDateFromMap(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_FECHA_ARCHIVO), TaskServiceParams.LIST_ASSISTANT_TASK_DATE_FORMAT));
        tarea.setFechaIncidencia(toDateFromMap(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_FECHA_INCIDENCIA), TaskServiceParams.LIST_ASSISTANT_TASK_DATE_FORMAT));
        //TODO FECHA DE PAGO ES FORMA DE PAGO EN REALIDAD
        tarea.setFechaPago(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_FECHA_PAGO));
        tarea.setOperador(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_OPERADOR));
        tarea.setFechaCierre(toDateFromMap(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_FECHA_CIERRE), TaskServiceParams.LIST_ASSISTANT_TASK_DATE_FORMAT));
        tarea.setTipoIncicencia(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_TIPO_INCIDENCIA));
        tarea.setSubtipoIncidencia(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_SUBTIPO_INCIDENCIA));
        tarea.setSolicitudCliente(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_SOLICITUD));
        tarea.setCambiosIncidencia(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_CAMBIOS));
        tarea.setBoFechaGestion(toDateFromMap(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_BO_FECHA_GESTION), TaskServiceParams.LIST_ASSISTANT_TASK_DATE_FORMAT));
        tarea.setBoMatricula(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_BO_MATRICULA));
        tarea.setBoFechaRecepcion(toDateFromMap(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_BO_FECHA_RECEPCION), TaskServiceParams.LIST_ASSISTANT_TASK_DATE_FORMAT));
        tarea.setBoTipo(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_BO_TIPO));
        tarea.setBoComentarios(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_BO_COMENTARIOS));
        tarea.setTelefono(parameters.get(TaskServiceParams.LIST_ASSISTANT_TASK_INS_TELEFONO));
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
        tarea.setCompensation(parameters.get(TaskServiceParams.COMPENSACION));
        return tarea;
    }

    /**
     * Obtiene una Tarea by Id de Aviso mediante WS
     *
     * @param idAviso
     * @return
     */
    protected TareaAviso getTareaByIdAviso(Integer idAviso) {
        TareaAviso tarea = null;
        if (idAviso != null && !idAviso.equals(0)) {
            List<GetAvisobyIdResult> avisobyId = null;
            try {
                //Viene una lista de 3 avisos o menos, son todos el mismo, solo cambia los tipoAviso y tipoMotivo
                avisobyId = spAioTareas2.getAvisobyId(idAviso);
            } catch (DataServiceFault dsf) {
                LOGGER.error("ERROR calling service for TareaAviso ID:'{}'", idAviso);
                throw new FrameworkException(dsf);
            }
            if (avisobyId != null && !avisobyId.isEmpty()) {
                Iterator<GetAvisobyIdResult> iterator = avisobyId.iterator();
                List<TareaAviso> bloquesTareasAviso = new ArrayList<TareaAviso>();
                while (iterator.hasNext()) {
                    GetAvisobyIdResult avisobyIdResultItem = iterator.next();
                    bloquesTareasAviso.add(mapTareaAvisoFromWS(avisobyIdResultItem));
                }
                tarea = bloquesTareasAviso.get(0);
                if (bloquesTareasAviso.size() >= 2) {
                    tarea.setTipoAviso2(bloquesTareasAviso.get(1).getTipoAviso1());
                    tarea.setMotivo2(bloquesTareasAviso.get(1).getMotivo1());
                }
                if (bloquesTareasAviso.size() >= 3) {
                    tarea.setTipoAviso3(bloquesTareasAviso.get(2).getTipoAviso1());
                    tarea.setMotivo3(bloquesTareasAviso.get(2).getMotivo1());
                }
                if (bloquesTareasAviso.size() >= 4) {
                    LOGGER.debug("No se esperan más de 3 tipos y motivos de aviso en la respuesta");
                }
                LOGGER.debug("Queried Aviso ({}): {}", idAviso, tarea);
            } else {
                LOGGER.debug("Queried Aviso not found ({}): {}", idAviso, tarea);
            }
        } else {
            LOGGER.warn("Can not query for an not informed idAviso.");
        }
        return tarea;
    }

    /**
     * Crea un objeto de tipo TareaAviso consultando el WS
     *
     * @param avisobyIdResult
     * @return
     */
    private TareaAviso mapTareaAvisoFromWS(GetAvisobyIdResult avisobyIdResult) {
        TareaAviso tarea = new TareaAviso();
        //Datos instalacion - Se obtiene de la llamada al servicio de avisoById (mediante librería para WS)
        tarea.setNumeroInstalacion(avisobyIdResult.getInsNo());
        tarea.setPersonaContacto(avisobyIdResult.getContacto());
        tarea.setTelefonoAviso(avisobyIdResult.getValorFormaContacto());

        tarea.setIdAviso(avisobyIdResult.getIdaviso().intValue());
        tarea.setIdentificativoAvisoTarea(avisobyIdResult.getIdaviso().intValue());
        tarea.setObservaciones(avisobyIdResult.getObservaciones());
        tarea.setEstado(1);
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


    /**
     * Las respuestas de CclResponse tiene un mapa de mapas pero la lista de items solo retorna un item siempre.
     * Retornamos un mapa <clave,valor> de los datos de la respuesta.
     *
     * @return
     */
    public Map<String, String> loadCclResponseMap(CclResponse reponse, int position) {
        HashMap<String, String> responseMap = new HashMap<String, String>();
        if (position > reponse.getColumnReturn().size()) {
            return null;
        } else {
            for (Item item : reponse.getColumnReturn().get(position).getItem()) { //Recorre la lista, aunque no parezca una lista
                if (item != null) {
                    if (item.getCampo() != null && item.getValor() != null) {
                        responseMap.put(item.getCampo(), item.getValor());
                    } else if (item.getCampo() != null) {
                        responseMap.put(item.getCampo(), null);
                    }
                }
            }
            return responseMap;
        }
    }

    /**
     * Para los mapas que solo deberían tener una tarea
     */
    public Map<String, String> loadCclUniqueResponseMap(CclResponse reponse) {
        return loadCclResponseMap(reponse, 0);
    }


    private Tarea createTareaMantenimientoFromParameters(Map<String, String> parameters) {
        /**
         * 1.1.1.1.2.	Mapeo tarea mantenimiento
         Campo pantalla	Datos WS
         ESTADO                 NOMBRE_CAMPO            NOMBRE_CAMPO_WS     COMENTARIOS
         --installationData--   numeroInstalacion	    INSTALACION
         --installationdata--   titular	                                    (se lee del WS de getInstalacion [ref])
         --installationData--   Persona de contacto     NOMBRE              (no existe en el modelo)
         --installationData--   panel 	                                    WS de getInstalacion[ref]   (no viene en el modelo)
         --installationdata--   telefono	            CONTACT_INFO
         --installationData--   version                                     (no viene en el modelo)	WS de getInstalacion[ref]
         --CONFIRMAR--          numeroContrato	        CTR_NO              (confirmar)
         --NO_MOSTRADO--        tipoMantenimiento       TIPO_MANTENIMIENTO  (no viene en el modelo)
         --X--                  direccion	            DIRECCION
         --CONFIRMAR--          fechaEvento	            F_CREACION_TAREA?
         --X--                  ciudad	                CIUDAD
         --CONFIRMAR--          agenteAsignado	        AGENT_ID            (CONFIRMAR, SI ES EL LOGIN_ID HAY QUE LLAMAR A UN WS PARA TRADUCI
         --CONFIRMAR--                                  Tipo cancelacion    (no viene en el modelo, o es tipificacion?)
         --MODIFICACION--       tipoCancelacion         Tipo cancelacion    (no viene en el modelo, o es tipificacion?)
         --CONFIRMAR--                                  Texto cancelacion   (no viene en el modelo, o es tipificacion?)
         --MODIFICACION--       textoCancelacion        Texto cancelacion   (no viene en el modelo, o es tipificacion?)
         --X--                  telefono1               TELEFONO1           (no viene en el modelo)
         --X--                  telefono2               TELEFONO2           (no viene en el modelo)
         */
        TareaMantenimiento tarea = new TareaMantenimiento();
        loadTareaCommons(tarea, parameters);
        //INSTALLATION DATA FROM TASK
        tarea.setNumeroInstalacion(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_INS_NUMERO_INSTALACION));
        tarea.setPersonaContacto(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_INS_PERSONA_CONTACTO));
        tarea.setTelefono(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_INS_TELEFONO));

        //TAREA DATA
        tarea.setAgenteAsignado(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_AGENTEASIGNADO));
        tarea.setAgenteCierre(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_AGENTECIERRE));
        tarea.setTextoCancelacion(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_TEXTO_CANCELACION));
        ;
        tarea.setCiudad(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_CIUDAD));
        tarea.setDireccion(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_DIRECCION));
        tarea.setFechaEvento(toDateFromMap(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_FECHAEVENTO), TaskServiceParams.TAREA_MANTENIMIENTO_FECHAEVENTO_DATE_FORMAT));

        tarea.setKey1(toIntegerFromMap(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_KEY1)));
        tarea.setKey2(toIntegerFromMap(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_KEY2)));
        tarea.setNumeroContrato(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_NUMERO_CONTRATO));
        tarea.setOpcionTipificacion(toIntegerFromMap(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_OPCIONTIPIFICACION)));
        tarea.setTelefono1(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_TELEFONO_1));
        tarea.setTelefono2(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_TELEFONO_2));
        tarea.setTelefono3(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_TELEFONO_3));
        tarea.setTextoCancelacion(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_TEXTO_CANCELACION));
        tarea.setTipoCancelacion(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_TIPO_CANCELACION));
        tarea.setTipoMantenimiento(parameters.get(TaskServiceParams.TAREA_MANTENIMIENTO_TIPO_MANTENIMIENTO));
        return tarea;
    }


    private Tarea createMaintenanceSurveyTaskFromParameters(Map<String, String> parameters) {
        MaintenanceSurveyTask task = new MaintenanceSurveyTask();
        loadTareaCommons(task, parameters);
        loadTareaExcelCommons(task, parameters);

        task.setMaintenanceNumber(toIntegerFromMap(parameters.get(TaskServiceParams.MAINTENANCE_SURVEY_TASK_NUMERO_MANTENIMIENTO)));
        task.setTechnician(parameters.get(TaskServiceParams.MAINTENANCE_SURVEY_TASK_TECNICO));
        task.setManager(parameters.get(TaskServiceParams.MAINTENANCE_SURVEY_TASK_RESPONSABLE));
        task.setCostCenter(parameters.get(TaskServiceParams.MAINTENANCE_SURVEY_TASK_CENTROCOSTE));
        task.setValuationKeyReason(parameters.get(TaskServiceParams.MAINTENANCE_SURVEY_TASK_RAZON));
        task.setSolution(parameters.get(TaskServiceParams.MAINTENANCE_SURVEY_TASK_SOLUCION));
        task.setAgreement(parameters.get(TaskServiceParams.MAINTENANCE_SURVEY_TASK_COMPROMISO));
        task.setDestinationDepartment(parameters.get(TaskServiceParams.MAINTENANCE_SURVEY_TASK_DPTO_DESTINO));
        return task;
    }

    private Tarea createTareaEncuestaMarketingFromParameters(Map<String, String> parameters) {
        MarketingSurveyTask tarea = new MarketingSurveyTask();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setFecha(toDateFromMap(parameters.get(TaskServiceParams.MARKETING_SURVEY_TASK_FECHA), TaskServiceParams.MARKETING_SURVEY_TASK_DATE_FORMAT));
        tarea.setMotivo(parameters.get(TaskServiceParams.MARKETING_SURVEY_TASK_MOTIVO));
        return tarea;
    }

    private Tarea createTareaKeyboxFromParameters(Map<String, String> parameters) {
        KeyboxTask tarea = new KeyboxTask();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);
        tarea.setInvoiceNumber(parameters.get(TaskServiceParams.KEYBOX_TASK_NUMERO_FACTURA));
        tarea.setInvoiceDate(toDateFromMap(parameters.get(TaskServiceParams.KEYBOX_TASK_FECHA_FACTURA), TaskServiceParams.KEYBOX_TASK_DATE_FORMAT));
        tarea.setLineValue(toIntegerFromMap(parameters.get(TaskServiceParams.KEYBOX_TASK_IMPORTE_LINEA)));
        tarea.setIdentificadorItem(parameters.get(TaskServiceParams.KEYBOX_TASK_ID_ITEM));
        tarea.setContrato(parameters.get(TaskServiceParams.KEYBOX_TASK_CONTRATO));
        tarea.setPanel(parameters.get(TaskServiceParams.KEYBOX_TASK_PANEL));
        return tarea;
    }

    private Tarea createTareaOtrasCampanasFromParameters(Map<String, String> parameters) {
        TareaOtrasCampanas tarea = new TareaOtrasCampanas();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setCampo1(parameters.get(TaskServiceParams.OTRASCAMPANAS_CAMPO1));
        tarea.setCampo2(parameters.get(TaskServiceParams.OTRASCAMPANAS_CAMPO2));
        tarea.setCampo3(parameters.get(TaskServiceParams.OTRASCAMPANAS_CAMPO3));
        tarea.setComentario(parameters.get(TaskServiceParams.OTRASCAMPANAS_COMENTARIO));
        tarea.setTipoCampana(parameters.get(TaskServiceParams.OTRASCAMPANAS_TIPO_CAMPANA));

        return tarea;
    }

    private Tarea createTareaLimpiezaCuotaFromParameters(Map<String, String> parameters) {
        TareaLimpiezaCuota tarea = new TareaLimpiezaCuota();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setContrato(parameters.get(TaskServiceParams.LIMPIEZA_CUOTA_CONTRATO));
        tarea.setDepartamentoAsignado(parameters.get(TaskServiceParams.LIMPIEZA_CUOTA_DPT_ASIGNADO));
        tarea.setDescripcionIncidencia(parameters.get(TaskServiceParams.LIMPIEZA_CUOTA_INCIDENCIA));

        return tarea;
    }


    /**
     * Carga los datos comunes de Tarea pasados por parametro
     */
    private Tarea loadTareaCommons(Tarea tarea, Map<String, String> parameters) {
        /**
         --installationData--   numeroInstalacion	    INSTALACION
         --installationdata--   titular	                                    (se lee del WS de getInstalacion [ref])
         --installationData--   Persona de contacto     NOMBRE              (no existe en el modelo)
         --installationData--   panel 	                                    WS de getInstalacion[ref]   (no viene en el modelo)
         --installationdata--   telefono	            CONTACT_INFO
         --installationData--   version                                     (no viene en el modelo)	WS de getInstalacion[ref]
         */
        assert tarea != null && parameters != null;
        tarea.setNumeroInstalacion(parameters.get(TaskServiceParams.TAREA_COMMONS_INSTALACION));
        tarea.setPersonaContacto(parameters.get(TaskServiceParams.TAREA_COMMONS_PERSONA_CONTACTO));
        tarea.setTelefono(parameters.get(TaskServiceParams.TAREA_COMMONS_TELEFONO));
        tarea.setEstado(toIntegerFromMap(parameters.get(TaskServiceParams.TAREA_COMMONS_ESTADO)));
        tarea.setCallingList(parameters.get(TaskServiceParams.TAREA_COMMONS_CALLING_LIST));
        tarea.setNumeroContrato(parameters.get(TaskServiceParams.TAREA_COMMONS_N_CONTRATO));
        tarea.setId(toIntegerFromMap(parameters.get(TaskServiceParams.TAREA_COMMONS_ID)));
        tarea.setCampana(parameters.get(TaskServiceParams.TAREA_CAMPAIGN));
        //Fecha de Reprogramación viene en numerico formato EPOC, i.e:1440588300 http://www.epochconverter.com/
        tarea.setFechaReprogramacion(toDateEpocFromMap(parameters.get(TaskServiceParams.TAREA_COMMONS_FECHA_REPROGRAMACION)));
        return tarea;
    }


    /**
     * Conversores , indicar parametro (String).
     * Ejemplo de lo que devuelve Aviso Service: 2014-08-22T10:08:26.000+02:00
     */
    private Date toDateFromMap(String value, String dateFormatPattern) {
        assert dateFormatPattern != null;
        Date fecha = null;
        if (value != null && !value.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
            try {
                fecha = sdf.parse(value);
            } catch (ParseException parseException) {
                LOGGER.error("El formato de fecha '{}' no se corresponde con la fecha dada '{}'", dateFormatPattern, value, parseException.getMessage(), parseException);
                fecha = null;
            } catch (IllegalArgumentException illegalArgumentException) {
                LOGGER.error("El formato de fecha '{}' no es un patrón válido.\nMessage: {}\nStackTrace: {}", dateFormatPattern, illegalArgumentException.getMessage(), illegalArgumentException);
                fecha = null;
            }
        }
        return fecha;
    }

    protected Date toDateFromMap(String value) {
        String dateFormatPattern = TaskServiceParams.DEFAULT_DATE_FORMAT;
        return toDateFromMap(value, dateFormatPattern);
    }

    /**
     * Fecha desde String cuando es un valor EPOC.
     * Le restamos dos horas porque lo que envian esta en UTC+2 Paris
     *
     * @param value
     * @return
     */
    protected Date toDateEpocFromMap(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            try {
                //Restamos dos horas
                return new Date((Long.valueOf(value) - (2*60*60) ) * 1000 );
            } catch (Exception e) {
                LOGGER.error("Can't parse EPOC date from value {}", value, e);
                return null;
            }
        }
    }

    protected Integer toIntegerFromMap(String value) {
        Integer result = null;
        if (value != null && !value.isEmpty()) {
            try {
                result = Integer.valueOf(value);
            } catch (NumberFormatException numberFormatException) {
                LOGGER.error("No se pudo parsear a Integer el valor: '{}'\nMessage:{}\nException:{}", value, numberFormatException.getMessage(), numberFormatException);
                result = null;
            }
        }
        return result;
    }

    private Float toFloatFromMap(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return Float.valueOf(value);
        }
    }

    /**
     * Carga parametros recibidos por POST en la tarea
     * @param tarea
     * @param parameters
     * @return
     */
    public Tarea loadPostParametersInTask(Tarea tarea, Map<String, String> parameters) {
        if (parameters!=null) {
            tarea.setOutAgentPlace(parameters.get(ExternalParams.AGENT_PLACE));
            tarea.setOutCampaignName(parameters.get(ExternalParams.CAMPAIGN_NAME));
            tarea.setOutClName(parameters.get(ExternalParams.CALLING_LIST));
            tarea.setOutRecordHandle(parameters.get(ExternalParams.RECORD_HANDLE)); //TODO PASAR A INTEGER
            tarea.setOutAgentPlace(parameters.get(ExternalParams.AGENT_PLACE)); //TODO HACER ALGO CON UNA "L"?????
        }
        return tarea;
    }
}
