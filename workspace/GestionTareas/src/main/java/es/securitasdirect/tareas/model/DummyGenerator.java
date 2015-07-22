package es.securitasdirect.tareas.model;

import es.securitasdirect.tareas.model.tareaexcel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.DataServiceFault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Javier Naval on 03/07/2015.
 */
public class DummyGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyGenerator.class);

    public static InstallationData getInstallationData() {
        InstallationData ins = new InstallationData();
        ins.setNumeroInstalacion("111111");
        ins.setPersonaContacto("DUMMY");
        return ins;
    }

    public static TareaOtrasCampanas dummyAnotherCampaingnsTask() {
        LOGGER.debug("Creating dummy AnoherCampaignsTask");
        TareaOtrasCampanas tareaOtrasCampanas = new TareaOtrasCampanas();
        tareaOtrasCampanas.setComentario("Dummy comentario");
        tareaOtrasCampanas.setTipoCampana("Dummy tipo campaña");
        tareaOtrasCampanas.setCampo1("Dummy campo 1");
        tareaOtrasCampanas.setCampo2("Dummy campo 2");
        tareaOtrasCampanas.setCampo3("Dummy campo 3");

        tareaOtrasCampanas = (TareaOtrasCampanas)addTareaValues(tareaOtrasCampanas);
        tareaOtrasCampanas = (TareaOtrasCampanas)addTareaExcelValues(tareaOtrasCampanas);
        return tareaOtrasCampanas;
    }

    public static TareaListadoAssistant dummyListAssistantTask() {
        LOGGER.debug("Creating dummy List Assistant");
        TareaListadoAssistant tareaListadoAssistant = new TareaListadoAssistant();
        tareaListadoAssistant.setBoComentarios("Dummy BO comentarios");
        tareaListadoAssistant.setBoDatosRecuperados("Dummy BO datos recuperados");
        tareaListadoAssistant.setBoFechaGestion(new Date());
        tareaListadoAssistant.setBoFechaRecepcion(new Date());
        tareaListadoAssistant.setBoMatricula("Dummy BO matrícula");
        tareaListadoAssistant.setBoTipo("Dummy BO tipo");
        tareaListadoAssistant.setCambiosIncidencia("Dummy cambios incidencia");
        tareaListadoAssistant.setDepartamento("Dummy departamento");
        tareaListadoAssistant.setFechaArchivo(new Date());
        tareaListadoAssistant.setFechaCierre(new Date());
        tareaListadoAssistant.setFechaIncidencia(new Date());
        tareaListadoAssistant.setFechaPago(new Date());
        tareaListadoAssistant.setGrupoPanel("Dummy grupo panel");
        tareaListadoAssistant.setIncidencia("Dummy incidencia");
        tareaListadoAssistant.setMaintenanceNumber(0);
        tareaListadoAssistant.setNombre("Dummy nombre");
        tareaListadoAssistant.setNumeroInstalacion("Dummy número instalación");
        tareaListadoAssistant.setNumeroParte("Dummy número parte");
        tareaListadoAssistant.setOperador("Dummy operador");
        tareaListadoAssistant.setSolicitudCliente("Dummy solicitud cliente");
        tareaListadoAssistant.setSubtipoIncidencia("Dummy subtipo incidencia");
        tareaListadoAssistant.setTechnician("Dummy technician");
        tareaListadoAssistant.setTelefono("Dummy teléfono");
        tareaListadoAssistant.setTipoIncicencia("Dummy tipo incidencia");
        tareaListadoAssistant.setTotalConIVA(new Float("0.1"));
        tareaListadoAssistant.setTotalSinIVA(new Float("0.1"));

        tareaListadoAssistant = (TareaListadoAssistant)addTareaValues(tareaListadoAssistant);
        tareaListadoAssistant = (TareaListadoAssistant)addTareaExcelValues(tareaListadoAssistant);
        return tareaListadoAssistant;
    }

    public static TareaLimpiezaCuota dummyFeeCleaningTask(){
        LOGGER.debug("Creating dummy TareaLimpiezaCuota");
        TareaLimpiezaCuota tareaLimpiezaCuota = new TareaLimpiezaCuota();
        tareaLimpiezaCuota.setContrato("Dummy contrato");
        tareaLimpiezaCuota.setDepartamentoAsignado("Dummy departamento asignado");
        tareaLimpiezaCuota.setDescripcionIncidencia("Dummy descripcion indicencia");
        tareaLimpiezaCuota = (TareaLimpiezaCuota)addTareaValues(tareaLimpiezaCuota);
        tareaLimpiezaCuota = (TareaLimpiezaCuota)addTareaExcelValues(tareaLimpiezaCuota);
        return tareaLimpiezaCuota;
    }

    public static TareaMantenimiento dummyMaintenanceTask() {
        TareaMantenimiento t = new TareaMantenimiento();
        t.setAgenteAsignado("Dummy AgenteAsignado");
        t.setAgenteCierre("Dummy AgenteCierre");
        t.setCiudad("Dummy Ciudad");
        t.setDireccion("Dummy Direccion");
        t.setFechaEvento(new Date());
        t.setKey1(0);
        t.setKey2(0);
        t.setNumeroContrato("Dummy NumeroContrato");
        t.setOpcionTipificacion(0);
        t.setTelefono1("Dummy Telefono1");
        t.setTelefono2("Dummy Telefono2");
        t.setTelefono3("Dummy Telefono3");
        t.setTextoCancelacion("Dummy TextoCancelacion");
        t.setTipoCancelacion("Dummy TipoCancelacion");
        t.setTipoMantenimiento("Dummy TipoMantenimiento");
        return t;
    }


    public static MaintenanceSurveyTask dummyMaintenanceSurveyTask(){
        LOGGER.debug("Creating dummy MaintenanceSurveyTask");
        //MaintenanceSurveyTask maintenanceSurveyTask = externalDataService.getMaintenanceSurveyTask();
        MaintenanceSurveyTask maintenanceSurveyTask =  new MaintenanceSurveyTask();
        maintenanceSurveyTask.setAgreement("Dummy agreement");
        maintenanceSurveyTask.setCostCenter("Dummy cost center");
        maintenanceSurveyTask.setDestinationDepartment("Dummy destination department");
        maintenanceSurveyTask.setMaintenanceNumber(0);
        maintenanceSurveyTask.setManager("Dummy manager");
        maintenanceSurveyTask.setSolution("Dummy solution");
        maintenanceSurveyTask.setTechnician("Dummy technician");
        maintenanceSurveyTask.setValuationKeyReason("Dummy valuation key reason");
        maintenanceSurveyTask.setClosingReason(3);
        maintenanceSurveyTask = (MaintenanceSurveyTask) addTareaValues(maintenanceSurveyTask);
        maintenanceSurveyTask = (MaintenanceSurveyTask) addTareaExcelValues(maintenanceSurveyTask);
        LOGGER.debug("Created dummy MaintenanceSurveyTask: {}", maintenanceSurveyTask);
        return maintenanceSurveyTask;
    }

    public static MarketingSurveyTask dummyMarketingSurveyTask(){
        MarketingSurveyTask marketingSurveyTask =  new MarketingSurveyTask();
            LOGGER.debug("Creating dummy MarketingSurveyTask");
            marketingSurveyTask.setFecha(new Date());
            marketingSurveyTask.setMotivo("Dummy reason");

            marketingSurveyTask = (MarketingSurveyTask)addTareaValues(marketingSurveyTask);
            marketingSurveyTask = (MarketingSurveyTask)addTareaExcelValues(marketingSurveyTask);

            LOGGER.debug("Created dummy MaintenanceSurveyTask: {}", marketingSurveyTask);
            return marketingSurveyTask;
    }

    public static KeyboxTask  dummyKeyboxTask(){
        LOGGER.debug("Creating dummy KeyboxTask");
        KeyboxTask keyboxTask = new KeyboxTask();

        keyboxTask.setInvoiceNumber("Dummy invoice number");
        keyboxTask.setInvoiceDate(new Date());
        keyboxTask.setLineValue(0);
        keyboxTask.setIdentificadorItem("Dummy identificador item");
        keyboxTask.setContrato("Dummy contrato");
        keyboxTask.setPanel("Dummy panel");

        keyboxTask = (KeyboxTask)addTareaValues(keyboxTask);
        keyboxTask = (KeyboxTask)addTareaExcelValues(keyboxTask);

        LOGGER.debug("Created dummy KeyboxTask: {}", keyboxTask);
        return keyboxTask;
    }


    public static TareaAviso  dummyNotificationTask() {
        LOGGER.debug("Creating dummy NotificationTask");
        TareaAviso tareaAviso = new TareaAviso();
        tareaAviso.setTelefono("DUMMY");
        tareaAviso.setIdAviso(1234);
        tareaAviso.setTipoAviso1("Dummy tipo aviso 1");
        tareaAviso.setTipoAviso2("Dummy tipo aviso 2");
        tareaAviso.setTipoAviso3("Dummy tipo aviso 3");
        tareaAviso.setMotivo1("Dummy motivo 1");
        tareaAviso.setMotivo2("Dummy motivo 2");
        tareaAviso.setMotivo3("Dummy motivo 3");

        tareaAviso.setObservaciones("Dummy observaciones");

        tareaAviso.setIdentificativoAvisoTarea(1);
        tareaAviso.setNumeroInstalacion("Dummy numero instalacion");
        tareaAviso.setTitular("Dummy titular");
        tareaAviso.setRequeridoPor("Dummy requerido por");
        tareaAviso.setDatosContacto("Dummy datos contacto");
        tareaAviso.setFechaCreacion(new Date());
        tareaAviso.setEstado("Dummy estado");
        tareaAviso.setHorarioDesde("Dummy horario desde");
        tareaAviso.setHorarioHasta("Dummy horario hasta");
        tareaAviso.setClosing("1");
        tareaAviso.setDatosAdicionalesCierre("Datos adicionales cierre");
        tareaAviso.setFechaCierre(new Date());
        tareaAviso.setNota("Dummy nota");
        tareaAviso.setResponsableCierre("Dummy responsable cierre");

        tareaAviso = (TareaAviso)addTareaValues(tareaAviso);
        return tareaAviso;
    }


    public static InstallationData  dummyInstallationData()  {
        LOGGER.debug("Creating dummy Installation Data");
        InstallationData installationData = new InstallationData();
        installationData.setNumeroInstalacion("Dummy numero instalacion");
        installationData.setTitular("Dummy titular");
        installationData.setPanel("Dummy panel");
        installationData.setVersion("Dummy version");
        installationData.setPersonaContacto("Dummy persona contacto");
        installationData.setTelefono("Dummy telefono");
        return installationData;
    }

    public static TareaExcel addTareaExcelValues(TareaExcel tarea){
        tarea.setCompensation("Dummy compensation");
        tarea.setClosingReason(3);
        return tarea;
    }

    public static Tarea addTareaValues(Tarea tarea){
        tarea.setNumeroInstalacion("Dummy numeroInstalacion");
        tarea.setEstado("Dummy status");
        tarea.setNumeroContrato("Dummy contract number");
        tarea.setCallingList("Dummy calling list");
        tarea.setTelefono("Dummy telephone");
        tarea.setFechaReprogramacion(new Date());
        tarea.setCodigoCliente(3);
        return tarea;
    }
}

