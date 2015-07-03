package es.securitasdirect.tareas.service;

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
@Named(value = "tareaService")
@Singleton
public class TareaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TareaService.class);

    //Web Services para hacer pruebas directamente
    @Inject
    protected SPAVISOSOPERACIONESPortType spAvisosOperaciones;
    @Inject
    protected SPAIOTAREAS2PortType spAioTareas2;



    @Resource(name="datosCierreTareaAviso")
    protected Map<String,Map<Integer, String>> datosCierreTareaAviso;

    /**
     * Aplazar: muestra un diálogo en modo modal para introducir la fecha y hora de la reprogramación,
     * indicando también si es para el propio agente o para el grupo de la Campaña.
     * Si confirma, se invocará un web service para marcar la Tarea como tratada (mark done) y
     * se invocará un web service para actualizar el Aviso.
     */
    public boolean aplazar(Integer idAviso) {
        LOGGER.info("Aplazando idAviso:{}", idAviso);

        //TODO se invocará un web service para marcar la Tarea como tratada (mark done) y

        // Invocará un web service para actualizar el Aviso.
        Integer naviso = idAviso;
        String gblidusr = null; //TODO PENDIENTES
        String idaplaza = null; //TODO PENDIENTES
        String fhasta = null; //TODO PENDIENTES
        String cnota = null; //TODO PENDIENTES

        try {
            List<RowErrorAA> rowErrorAAs = spAvisosOperaciones.aplazarAviso(naviso, gblidusr, idaplaza, fhasta, cnota);
            //TODO Debug para ver que devuelve y controlar si hay errores devolver
            if (rowErrorAAs != null && !rowErrorAAs.isEmpty()) {
                LOGGER.error("Error aplazando aviso {}", idAviso);
                return false;
            }
        } catch (DataServiceFault e) {
            LOGGER.error("Error aplazando aviso", e);
            return false;
        }
        return true;
    }

    /* Descartar: se invocará un web service para marcar la Tarea como tratada (mark done) y se invocará un web service para actualizar el Aviso. */
    public boolean descartar(String idAviso) {
        LOGGER.info("Descartar idAviso:{}", idAviso);
        return true;
    }

    /* Finalizar: se validará que haya rellenado la información del Cierre, se invocará un web service para marcar la Tarea como tratada (mark done) y se invocará un web service para finalizar el Aviso. */
    public boolean finalizar(String idAviso) {
        LOGGER.info("Finalizando idAviso:{}", idAviso);
        return true;
    }

    /* Llamar: realiza una llamada telefónica al número indicado en el campo de teléfono de contacto. Para ello, ejecutará un web service disponible en CCL. */
    public boolean llamar(String idAviso) {
        LOGGER.info("Llamando idAviso:{}", idAviso);

        return true;
    }

    /**
     * Obtiene una Tarea by Id de Aviso
     *
     * @param idAviso
     * @return
     */
    public Tarea getTareaByIdAviso(Integer idAviso) throws DataServiceFault {
        TareaAviso tarea = null;
        List<GetAvisobyIdResult> avisobyId = null;
        avisobyId = spAioTareas2.getAvisobyId(idAviso);
        if (avisobyId != null && !avisobyId.isEmpty()) {
            tarea = mapTareaAvisoFromWS(avisobyId.get(0)); //TODO Devuelve una lista aunque se supone que solo es uno, check
        }
        LOGGER.debug("Consultado TareaAviso ({}): {}", idAviso, tarea);
        return tarea;
    }


    /**
     * Crea un objeto de tipo TareaAviso consultando el WS
     * TODO BORRAR ESTA FUNCION
     * @param avisobyIdResult
     * @return
     */
    private TareaAviso mapTareaAvisoFromWS(GetAvisobyIdResult avisobyIdResult) {
        TareaAviso tarea = new TareaAviso();
        tarea.setIdentificativoAvisoTarea(avisobyIdResult.getIdaviso().intValue());
        tarea.setObservaciones(avisobyIdResult.getObservaciones());
        tarea.setEstado(avisobyIdResult.getEstado().toString()); //TODO
        tarea.setTitular(avisobyIdResult.getTitular());

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






    public Tarea createDummyTareaAviso() {
        Tarea ejemploAviso = new TareaAviso();
        ejemploAviso.setEstado("estado1");
        ejemploAviso.setTelefono("652696869");
        ejemploAviso.setCallingList("CC_CA_IL_500");
        ejemploAviso.setNumeroContrato("526369");
        ejemploAviso.setCodigoCliente(500);
        ejemploAviso.setFechaReprogramacion(new Date());
        return ejemploAviso;
    }


    /**
     * Carga las tareas dependiendo de los valores que se envian como parámetros.
     *
     * @param mapa
     * @return
     * @throws DataServiceFault
     */
    public Tarea loadTareaFromParameters(Map<String, String> mapa) throws DataServiceFault {
        Tarea tarea = null;

        if (mapa.get(ExternalParams.ID_AVISO) != null) {
            //TODO MEJORAR TRATAR INTERGER
            tarea = getTareaByIdAviso(Integer.parseInt(mapa.get(ExternalParams.ID_AVISO)));
        } else {
            //TODO Distinguir entre las distintos tipos de Tareas depeniendo de los parametros
            String tipoTarea = mapa.get(ExternalParams.TIPO_TAREA);

            if ("TareaEncuestaMarketing".equalsIgnoreCase(tipoTarea)) {
                return createTareaEncuestaMarketingFromParameters(mapa);
            } else if ("TareaListadoAssistant".equalsIgnoreCase(tipoTarea)) {
                return createTareaListadoAssistantFromParameters(mapa);

            } else if ("TareaEncuestaMantenimiento".equalsIgnoreCase(tipoTarea)) {
                return createTareaEncuestaMantenimientoFromParameters(mapa);

            } else if ("TareaKeybox".equalsIgnoreCase(tipoTarea)) {
                return createTareaKeyboxFromParameters(mapa);

            } else if ("TareaOtrasCampanas".equalsIgnoreCase(tipoTarea)) {
                return createTareaOtrasCampanasFromParameters(mapa);

            } else if ("TareaLimpiezaCuota".equalsIgnoreCase(tipoTarea)) {
                return createTareaLimpiezaCuotaFromParameters(mapa);

            } else if ("TareaMantenimiento".equalsIgnoreCase(tipoTarea)) {
                return createTareaMantenimientoFromParameters(mapa);
            }

            //TODO JESUS
        }

        return tarea;
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

    private Tarea createTareaListadoAssistantFromParameters(Map<String, String> parameters) {
        TareaListadoAssistant tarea = new TareaListadoAssistant();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setNumeroInstalacion(parameters.get(ExternalParams.ASSISTANT_INSTALACION));
        tarea.setMaintenanceNumber(toIntegerFromParam(parameters.get(ExternalParams.ASSISTANT_MANTENIMIENTO)));
        tarea.setTechnician(parameters.get(ExternalParams.ASSISTANT_TECNICO));
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

    private Tarea createTareaEncuestaMantenimientoFromParameters(Map<String, String> parameters) {
        MaintenanceSurveyTask tarea = new MaintenanceSurveyTask();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setMaintenanceNumber(toIntegerFromParam(parameters.get(ExternalParams.ENCUESTAMNTOS_MANTENIMIENTO)));
        tarea.setTechnician(parameters.get(ExternalParams.ENCUESTAMNTOS_TECNICO));
        tarea.setManager(parameters.get(ExternalParams.ENCUESTAMNTOS_RESPONSABLE));
        tarea.setCostCenter(parameters.get(ExternalParams.ENCUESTAMNTOS_CENTROCOSTE));
        tarea.setValuationKeyReason(parameters.get(ExternalParams.ENCUESTAMNTOS_RAZON));
        tarea.setSolution(parameters.get(ExternalParams.ENCUESTAMNTOS_SOLUCION));
        tarea.setAgreement(parameters.get(ExternalParams.ENCUESTAMNTOS_COMPROMISO));
        tarea.setDestinationDepartment(parameters.get(ExternalParams.ENCUESTAMNTOS_DPTO_DESTINO));
        return tarea;
    }

    private Tarea createTareaEncuestaMarketingFromParameters(Map<String, String> parameters) {
        MarketingSurveyTask tarea = new MarketingSurveyTask();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setDate(toDateFromParam(parameters.get(ExternalParams.ENCUESTASMKT_FECHA)));
        tarea.setReason(parameters.get(ExternalParams.ENCUESTASMKT_MOTIVO));
        return tarea;
    }

    private Tarea createTareaKeyboxFromParameters(Map<String, String> parameters) {
        KeyboxTask tarea = new KeyboxTask();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);

        tarea.setContrato(parameters.get(ExternalParams.KEYBOX_CONTRATO));
        tarea.setInvoiceDate(toDateFromParam(parameters.get(ExternalParams.KEYBOX_FECHA_FACTURA)));
        tarea.setInvoiceNumber(parameters.get(ExternalParams.KEYBOX_NUMERO_FACTURA));
        tarea.setLineValue(toIntegerFromParam(parameters.get(ExternalParams.KEYBOX_IMPORTE_LINEA)));
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

    //TODO FINAL METHODS createXXXXFromParameters


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
        //TODO Pendiente saber formato lista tarea.setClosingReason(parameters.get(ExternalParams.MOTIVO_CIERRE));
        tarea.setCompensation(parameters.get(ExternalParams.COMPENSACION));
        return tarea;
    }




    public Map<Integer,String> getDatosCierreTareaAviso(TareaAviso tareaAviso){
        if (tareaAviso == null){
            return null;
        }else{
            if (tareaAviso.getTipoAviso1()==null) {
                return null;
            } else {
                Map cierrePorTipo = datosCierreTareaAviso.get(tareaAviso.getTipoAviso1());
                if (cierrePorTipo==null) {
                    LOGGER.error("No se ha encontrado tipos de cierre para una Tarea Aviso con tipo {}", tareaAviso.getTipoAviso1());
                }
                return cierrePorTipo;
            }
        }
    }

}
