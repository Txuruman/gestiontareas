<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="comunes.jsp"/>
<div class="row">
    <app:input id="maintenanceNumber" label="eti.visortarea.form.label.maintenanceNumber" value="${tarea.maintenanceNumber}" cells="6" readonly="true"/>
    <app:input id="technician" label="eti.visortarea.form.label.technician" value="${tarea.technician}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:input id="manager" label="eti.visortarea.form.label.manager" value="${tarea.manager}" cells="6" readonly="true"/>
    <app:input id="centroCoste" label="eti.visortarea.form.label.centroCoste" value="${tarea.centroCoste}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:input id="razonClaveValoracion" label="eti.visortarea.form.label.razonClaveValoracion" value="${tarea.razonClaveValoracion}" cells="6" readonly="true"/>
    <app:input id="compromiso" label="eti.visortarea.form.label.compromiso" value="${tarea.compromiso}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:input id="solucion" label="eti.visortarea.form.label.solucion" value="${tarea.solucion}" cells="6" readonly="true"/>

</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:input id="departamentoDestino" label="eti.visortarea.form.label.departamentoDestino" value="${tarea.departamentoDestino}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>

<!--

InstallationData : InstallationData{
numeroInstalacion=111111,
titular='ARATHERMIK S.L.',
panel='SD 2000C', version='null',
personaContacto='JOSE RAMON ARAGON ARRONTES'
}
Tarea : TareaEncuestaMantenimiento{
maintenanceNumber=0,
technician='null',
manager='null',
centroCoste='null',
razonClaveValoracion='null',
solucion='null',
compromiso='null',
departamentoDestino='null',
motivosCierre=null,
compensacion='null'
}

-->