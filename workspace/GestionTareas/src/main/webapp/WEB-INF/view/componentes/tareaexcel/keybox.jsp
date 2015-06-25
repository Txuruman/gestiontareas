<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="spacer_t1"></div>
<jsp:include page="comunes.jsp"/>
<div class="spacer_t1"></div>
<div class="row">

    <app:input id="numeroFactura" label="eti.visortarea.form.label.numeroFactura" value="${tarea.numeroFactura}" cells="6" readonly="true"/>
    <app:input id="fechaFactura" label="eti.visortarea.form.label.fechaFactura" value="${tarea.fechaFactura}" cells="6" readonly="true"/>

</div>
<div class="spacer_t1"></div>
<div class="row">

    <app:input id="importeLinea" label="eti.visortarea.form.label.importeLinea" value="${tarea.importeLinea}" cells="6" readonly="true"/>
    <app:input id="identificadorItem" label="eti.visortarea.form.label.identificadorItem" value="${tarea.identificadorItem}" cells="6" readonly="true"/>

</div>
<div class="spacer_t1"></div>
<div class="row">

    <app:input id="contrato" label="eti.visortarea.form.label.contrato" value="${tarea.contrato}" cells="6" readonly="true"/>

</div>


<!--

InstallationData : InstallationData{
numeroInstalacion=111111,
titular='ARATHERMIK S.L.',
panel='SD 2000C',
version='null',
personaContacto='JOSE RAMON ARAGON ARRONTES'
}


Tarea : TareaKeybox{
numeroFactura='565656',
fechaFactura=Fri Jun 19 13:15:56 CEST 2015,
importeLinea=0,
identificadorItem='null',
contrato='null',
motivosCierre=null,
compensacion='null'
}

-->