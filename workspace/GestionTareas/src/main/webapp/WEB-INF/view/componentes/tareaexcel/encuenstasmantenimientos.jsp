<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="spacer_t1"></div>
<jsp:include page="comunes.jsp"/>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="numeroMantenimiento" label="eti.visortarea.form.label.numeroMantenimiento" value="${tarea.numeroMantenimiento}" cells="6" readonly="true"/>
    <app:input id="tecnico" label="eti.visortarea.form.label.tecnico" value="${tarea.tecnico}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:input id="responsable" label="eti.visortarea.form.label.responsable" value="${tarea.responsable}" cells="6" readonly="true"/>
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
numeroMantenimiento=0,
tecnico='null',
responsable='null',
centroCoste='null',
razonClaveValoracion='null',
solucion='null',
compromiso='null',
departamentoDestino='null',
motivosCierre=null,
compensacion='null'
}

-->