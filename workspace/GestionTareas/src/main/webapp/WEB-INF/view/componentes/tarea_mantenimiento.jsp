<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <form:label path="tarea" cssClass="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">
            <spring:message code="eti.visortarea.mantenimiento.form.label.key1"/>
        </form:label>
        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
            <form:select id="desplegableKey1" path="tarea.key1" items="${desplegableKey1}" cssClass="form-control"/>
        </div>
    </div>


    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <form:label path="tarea" cssClass="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">
            <spring:message code="eti.visortarea.mantenimiento.form.label.key2"/>
        </form:label>
        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
            <form:select id="desplegableKey2" path="tarea.key1" items="${desplegableKey2}" cssClass="form-control"/>
        </div>
    </div>
</div>
<div class="spacer_t2"></div>
<div class="row">
    <app:input id="numeroFactura" label="eti.visortarea.mantenimiento.form.label.contrato" value="${tarea.contrato}" cells="6" readonly="true"/>
    <app:input id="direccion" label="eti.visortarea.mantenimiento.form.label.contrato" value="${tarea.direccion}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="ciudad" label="eti.visortarea.mantenimiento.form.label.ciudad" value="${tarea.ciudad}" cells="6" readonly="true"/>
    <app:input id="fechaEvento" label="eti.visortarea.mantenimiento.form.label.fechaEvento" value="${tarea.fechaEvento}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="agenteCierre" label="eti.visortarea.mantenimiento.form.label.agenteCierre" value="${tarea.agenteCierre}" cells="6" readonly="true"/>
    <app:input id="agenteAsignado" label="eti.visortarea.mantenimiento.form.label.agenteAsignado" value="${tarea.agenteAsignado}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:inputCombo id="tipificacion" label="eti.visortarea.mantenimiento.form.label.tipificacion" value="${tarea.tipificacion}" cells="6" readonly="false"/>
    <app:textArea id="descripcionTipificacion" label="eti.visortarea.mantenimiento.form.label.descripciontipificacion" value="${tarea.tipificacion}" cells="6"/>
</div>

<!--
InstallationData : InstallationData{
numeroInstalacion=111111,
titular='ARATHERMIK S.L.',
panel='SD 2000C', version='null',
personaContacto='JOSE RAMON ARAGON ARRONTES'
}
Tarea : TareaMantenimiento{
contrato='null',
direccion='null',
ciudad='null',
fechaEvento=Mon Jun 22 10:44:57 CEST 2015,
tipificacion='null',
agenteAsignado='null',
agenteCierre='null',
opcionTipificacion=0,
key1=0,
key2=0
}
-->