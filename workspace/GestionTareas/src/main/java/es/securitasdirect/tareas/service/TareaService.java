package es.securitasdirect.tareas.service;

import es.securitasdirect.tareas.model.Tarea;
import es.securitasdirect.tareas.model.TareaAviso;
import es.securitasdirect.tareas.model.TareaExcel;
import es.securitasdirect.tareas.model.TareaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.TareaEncuestaMantenimiento;
import es.securitasdirect.tareas.model.tareaexcel.TareaEncuestaMarketing;
import es.securitasdirect.tareas.model.tareaexcel.TareaListadoAssistant;
import es.securitasdirect.tareas.web.controller.params.ExternalParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.*;

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
        List<GetAvisobyIdResult> avisobyId = spAioTareas2.getAvisobyId(idAviso);
        if (avisobyId != null && !avisobyId.isEmpty()) {
            tarea = mapTareaAvisoFromWS(avisobyId.get(0)); //TODO Devuelve una lista aunque se supone que solo es uno, check
        }
        LOGGER.debug("Consultado TareaAviso ({}): {}", idAviso, tarea);
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
        tarea.setDatosContacto(avisobyIdResult.getContacto() + "??" + avisobyIdResult.getFormaContacto());

        return tarea;
    }


    /**
     * Consulta de los valores para el combo Key1 de tareas de mantenimiento
     */
    public Map<Integer, String> getDesplegableKey1() throws DataServiceFault {
        LOGGER.debug("Consultando listado Desplegable KEY1");
        Map<Integer, String> result = new HashMap<Integer, String>();
        List<GetKey1DIYResult> listaKey1 = spAioTareas2.getKey1DIY();
        if (listaKey1 != null) {
            for (GetKey1DIYResult getKey1DIYResult : listaKey1) {
                result.put(getKey1DIYResult.getSKey().intValue(), getKey1DIYResult.getText());
            }
        }
        return result;
    }

    /**
     * Consulta de los valores para el combo Key2 de tareas de mantenimiento
     */
    public Map<Integer, String> getDesplegableKey2(Integer skey1) throws DataServiceFault {
        LOGGER.debug("Consultando listado Desplegable KEY2 {}", skey1);
        assert skey1 != null : "skey1 es parametro requerido";
        Map<Integer, String> result = new HashMap<Integer, String>();
        List<GetKey2DIYResult> listaKey2 = spAioTareas2.getKey2DIY(skey1);
        if (listaKey2 != null) {
            for (GetKey2DIYResult getKey2DIYResult : listaKey2) {
                //Viene un sublistado de valores, parece que siempre viene solo uno, así que cogemos el primero
                if (!getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().isEmpty()) {
                    result.put(getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().get(0).getSKey().intValue(),
                            getKey2DIYResult.getGetKeyDataResults().getGetKeyDataResult().get(0).getText());
                }
            }
        }
        return result;
    }


    public List<Tarea> findByTelefono(String telefono) {
        List<Tarea> tareas = createDummy();
        return tareas;
    }

    public List<Tarea> findByInstalacion(String instalacion) {
        List<Tarea> tareas = createDummy();
        return tareas;
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
     * @param mapa
     * @return
     * @throws DataServiceFault
     */
    public Tarea loadTareaFromParameters(Map<String,String> mapa) throws DataServiceFault {
        Tarea tarea = null;

        if (mapa.get(ExternalParams.ID_AVISO) != null) {
            //TODO MEJORAR TRATAR INTERGER
            tarea = getTareaByIdAviso(Integer.parseInt(mapa.get(ExternalParams.ID_AVISO)));
        } else {
            //TODO Distinguir entre las distintos tipos de Tareas depeniendo de los parametros
            String tipoTarea = mapa.get(ExternalParams.TIPO_TAREA);
            if ("TareaEncuestaMarketing".equalsIgnoreCase(tipoTarea)) {
                return createTareaEncuestaMarketingFromParameters(mapa);
            } else if ("TareaListadoAssistant"){
                return createTareaListadoAssistantFromParameters(mapa);
            }

            //TODO JESUS
        }

        return tarea;
    }

    private Tarea createTareaEncuestaMarketingFromParameters(Map<String, String> parameters) {
        TareaEncuestaMarketing tarea = new TareaEncuestaMarketing();
        loadTareaCommons(tarea, parameters);
        loadTareaExcelCommons(tarea, parameters);
        //TODO JESUS
        tarea.setFecha(toDateFromParam(parameters.get(ExternalParams.ENCUESTAMARKETING_FECHA)));
        tarea.setMotivo(parameters.get(ExternalParams.ENCUESTAMARKETING_MOTIVO));
        return tarea;
    }

    private Tarea createTareaListadoAssistantFromParameters(Map<String,String> parameters){
        TareaListadoAssistant tarea = new TareaListadoAssistant();
        loadTareaCommons(tarea,parameters);
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
        tarea.setMotivosCierre(toListFromParam(parameters.get(ExternalParams.ASSISTANT_MOTIVO_CIERRE)));
        tarea.setCompensacion(parameters.get(ExternalParams.ASSISTANT_COMPENSACION));
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
        tarea.setMotivosCierre(toListFromParam(parameters.get(ExternalParams.ENCUESTAMNTOS_MOTIVO_CIERRE)));
        tarea.setCompensacion(parameters.get(ExternalParams.ENCUESTAMNTOS_COMPENSACION));

        return tarea;
    }


    /**
     * Carga los datos comunes de Tarea pasados por parametro
     * @return
     */
    private Tarea loadTareaCommons(Tarea tarea, Map<String,String> parameters) {
        assert tarea!=null && parameters!=null;
        tarea.setNumeroInstalacion(parameters.get(ExternalParams.NUMERO_INSTALACION));
        //TODO JESUS
        return tarea;
    }


    /**
     * Conversores , indicar parametro (String)
     * @param value
     * @return
     */
    private Date toDateFromParam(String value) {
        return new Date(); //TODO JAVIER
    }
    private Integer toIntegerFromParam(String value) {
        return new Integer(); //TODO JAVIER
    }
    private Float toFloatFromParam(String value){
        return new Float();
    }
    private List toListFromParam(String value){
        return new ArrayList();
    }


    /**
     * Carga los datos comunes de Tarea Excel pasados por parametro
     * @return
     */
    private TareaExcel loadTareaExcelCommons(TareaExcel tarea, Map<String,String> parameters) {
        assert tarea!=null && parameters!=null;
        //TODO Pendiente saber formato lista tarea.setMotivosCierre(parameters.get(ExternalParams.MOTIVO_CIERRE));
        tarea.setCompensacion(parameters.get(ExternalParams.COMPENSACION));
        return tarea;
    }

    private List<Tarea> createDummy() {
        List<Tarea> tareas = new ArrayList<Tarea>();
        Tarea ejemploAviso = new TareaAviso();
        ejemploAviso.setEstado("estado1");
        ejemploAviso.setTelefono("652696869");
        ejemploAviso.setCallingList("CC_CA_IL_500");
        ejemploAviso.setNumeroContrato("526369");
        ejemploAviso.setCodigoCliente(500);
        ejemploAviso.setFechaReprogramacion(new Date());
        tareas.add(ejemploAviso);

        Tarea ejemploTareaMantenimiento = new TareaMantenimiento();
        ejemploTareaMantenimiento.setEstado("estado2");
        ejemploTareaMantenimiento.setTelefono("652696789");
        ejemploTareaMantenimiento.setCallingList("CC_CA_IL_502");
        ejemploTareaMantenimiento.setNumeroContrato("526370");
        ejemploTareaMantenimiento.setCodigoCliente(501);
        ejemploTareaMantenimiento.setFechaReprogramacion(new Date());
        tareas.add(ejemploTareaMantenimiento);

        Tarea ejemploTareaEncuestaMarketing = new TareaEncuestaMarketing();
        ejemploTareaEncuestaMarketing.setEstado("estado3");
        ejemploTareaEncuestaMarketing.setTelefono("652696478");
        ejemploTareaEncuestaMarketing.setCallingList("CC_CA_IL_510");
        ejemploTareaEncuestaMarketing.setNumeroContrato("526371");
        ejemploTareaEncuestaMarketing.setCodigoCliente(502);
        ejemploTareaEncuestaMarketing.setFechaReprogramacion(new Date());
        tareas.add(ejemploTareaEncuestaMarketing);

        Tarea ejemploTareaListadoAssistant = new TareaListadoAssistant();
        ejemploTareaListadoAssistant.setEstado("estado4");
        ejemploTareaListadoAssistant.setTelefono("652696785");
        ejemploTareaListadoAssistant.setCallingList("CC_CA_IL_512");
        ejemploTareaListadoAssistant.setNumeroContrato("526372");
        ejemploTareaListadoAssistant.setCodigoCliente(503);
        ejemploTareaListadoAssistant.setFechaReprogramacion(new Date());
        tareas.add(ejemploTareaListadoAssistant);

        return tareas;

    }
}
