<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="comunes.jsp"/>
<div class="row">
    <app:inputText id="contrato" label="limpiezacuota.contrato" value="${tarea.contrato}" cells="6" readonly="true"/>
    <app:inputText id="departamentoAsignado" label="limpiezacuota.departamentoasignado" value="${tarea.departamentoAsignado}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:inputText id="descripcionIncidencia" label="limpiezacuota.descripcionincidencia" value="${tarea.descripcionIncidencia}" cells="6" readonly="true"/>
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

closingReason=null,
compensation='null'
}
-->