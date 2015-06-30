<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="spacer_t1"></div>
    <jsp:include page="comunes.jsp"/>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="contrato" label="eti.visortarea.form.label.limpieza.contrato" value="${tarea.contrato}" cells="6" readonly="true"/>
    <app:input id="departamentoAsignado" label="eti.visortarea.form.label.limpieza.departamentoAsignado" value="${tarea.departamentoAsignado}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="descripcionIncidencia" label="eti.visortarea.form.label.limpieza.descripcionIncidencia" value="${tarea.descripcionIncidencia}" cells="6" readonly="true"/>
</div>


<!--
InstallationData : InstallationData{
numeroInstalacion=111111,
titular='ARATHERMIK S.L.',
panel='SD 2000C', version='null',
personaContacto='JOSE RAMON ARAGON ARRONTES'
}

Tarea : TareaLimpiezaCuota{
contrato='null',
departamentoAsignado='null',
descripcionIncidencia='null',

motivosCierre=null,
compensacion='null'
}
-->