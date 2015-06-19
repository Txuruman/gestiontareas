<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="spacer_t1"></div>
<jsp:include page="comunes.jsp"/>

<div class="spacer_t1"></div>
<div class="row">
    <app:input id="numeroInstalacion" label="eti.visortarea.form.label.assistant.numeroInstalacion" value="${tarea.tecnico}" cells="4" readonly="true"/>
    <app:input id="numeroMantenimiento" label="eti.visortarea.form.label.assistant.numeroMantenimiento" value="${tarea.numeroMantenimiento}" cells="4" readonly="true"/>
    <app:input id="nombre" label="eti.visortarea.form.label.assistant.nombre" value="${tarea.nombre}" cells="4" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="telefono" label="eti.visortarea.form.label.assistant.telefono" value="${tarea.telefono}" cells="4" readonly="true"/>
    <app:input id="tecnico" label="eti.visortarea.form.label.assistant.tecnico" value="${tarea.tecnico}" cells="4" readonly="true"/>
    <app:input id="departamento" label="eti.visortarea.form.label.assistant.departamento" value="${tarea.departamento}" cells="4" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="grupoPanel" label="eti.visortarea.form.label.assistant.grupoPanel" value="${tarea.grupoPanel}" cells="4" readonly="true"/>
    <app:input id="totalSinIVA" label="eti.visortarea.form.label.assistant.totalSinIVA" value="${tarea.totalSinIVA}" cells="4" readonly="true"/>
    <app:input id="totalConIVA" label="eti.visortarea.form.label.assistant.totalConIVA" value="${tarea.totalConIVA}" cells="4" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="numeroParte" label="eti.visortarea.form.label.assistant.numeroParte" value="${tarea.numeroParte}" cells="4" readonly="true"/>
    <app:input id="fechaCierre" label="eti.visortarea.form.label.assistant.fechaCierre" value="${tarea.fechaCierre}" cells="4" readonly="true"/>
    <app:input id="fechaArchivo" label="eti.visortarea.form.label.assistant.fechaArchivo" value="${tarea.fechaArchivo}" cells="4" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="fechaIncidencia" label="eti.visortarea.form.label.assistant.fechaIncidencia" value="${tarea.fechaIncidencia}" cells="4" readonly="true"/>
    <app:input id="fechaPago" label="eti.visortarea.form.label.assistant.fechaPago" value="${tarea.fechaPago}" cells="4" readonly="true"/>
    <app:input id="operador" label="eti.visortarea.form.label.assistant.operador" value="${tarea.operador}" cells="4" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="tipoIncicencia" label="eti.visortarea.form.label.assistant.tipoIncicencia" value="${tarea.tipoIncicencia}" cells="4" readonly="true"/>
    <app:input id="subtipoIncidencia" label="eti.visortarea.form.label.assistant.subtipoIncidencia" value="${tarea.subtipoIncidencia}" cells="4" readonly="true"/>
    <app:input id="boFechaGestion" label="eti.visortarea.form.label.assistant.boFechaGestion" value="${tarea.boFechaGestion}" cells="4" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="boMatricula" label="eti.visortarea.form.label.assistant.boMatricula" value="${tarea.boMatricula}" cells="4" readonly="true"/>
    <app:input id="boDatosRecuperados" label="eti.visortarea.form.label.assistant.boDatosRecuperados" value="${tarea.boDatosRecuperados}" cells="4" readonly="true"/>
    <app:input id="boFechaRecepcion" label="eti.visortarea.form.label.assistant.boFechaRecepcion" value="${tarea.boFechaRecepcion}" cells="4" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="boTipo" label="eti.visortarea.form.label.assistant.boTipo" value="${tarea.boTipo}" cells="6" readonly="true"/>
    <app:input id="boComentarios" label="eti.visortarea.form.label.assistant.boComentarios" value="${tarea.boComentarios}" cells="6" readonly="true"/>
</div>

<!--
InstallationData : InstallationData{
numeroInstalacion=111111,
titular='ARATHERMIK S.L.',
panel='SD 2000C',
version='null',
personaContacto='JOSE RAMON ARAGON ARRONTES'
}
Tarea : TareaListadoAssistant{
numeroInstalacion='null',
numeroMantenimiento=0,
nombre='null',

telefono='null',
tecnico='null',
departamento='null',

grupoPanel='null',
totalSinIVA=0.0,
totalConIVA=0.0,

numeroParte='null',
fechaCierre=null,
fechaArchivo=Fri Jun 19 14:17:43 CEST 2015,

fechaIncidencia=null,
fechaPago=Fri Jun 19 14:17:43 CEST 2015,
operador='null',

tipoIncicencia='null',
subtipoIncidencia='null',
solicitudCliente='null',

incidencia='null',
cambiosIncidencia='null',
boFechaGestion=Fri Jun 19 14:17:43 CEST 2015,

boMatricula='null',
boDatosRecuperados='null',
boFechaRecepcion=Fri Jun 19 14:17:43 CEST 2015,

boTipo='null',
boComentarios='null',
motivosCierre=null,

compensacion='null'

}


-->