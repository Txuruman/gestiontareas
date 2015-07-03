<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="comunes.jsp"/>
<div class="row">

    <app:inputText id="invoiceNumber" label="keybox.invoicenumber" value="${tarea.invoiceNumber}" cells="6" readonly="true"/>
    <app:inputText id="invoiceDate" label="keybox.invoicedate" value="${tarea.invoiceDate}" cells="6" readonly="true"/>

</div>
<div class="spacer_t1"></div>
<div class="row">

    <app:inputText id="lineValue" label="keybox.linevalue" value="${tarea.lineValue}" cells="6" readonly="true"/>
    <app:inputText id="identificadorItem" label="keybox.identificadoritem" value="${tarea.identificadorItem}" cells="6" readonly="true"/>

</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:inputText id="contrato" label="keybox.contrato" value="${tarea.contrato}" cells="6" readonly="true"/>
    <app:inputText id="panel" label="keybox.panel" value="${tarea.panel}" cells="6" readonly="true"/>
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
closingReason=null,
compensation='null'
}

-->