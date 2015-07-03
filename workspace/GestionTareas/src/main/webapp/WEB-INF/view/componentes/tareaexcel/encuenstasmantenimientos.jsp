<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="comunes.jsp"/>
<div class="row">
    <app:inputText id="maintenanceNumber" label="eti.visortarea.form.label.maintenanceNumber" value="${tarea.maintenanceNumber}" cells="6" readonly="true"/>
    <app:inputText id="technician" label="eti.visortarea.form.label.technician" value="${tarea.technician}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:inputText id="manager" label="eti.visortarea.form.label.manager" value="${tarea.manager}" cells="6" readonly="true"/>
    <app:inputText id="costCenter" label="eti.visortarea.form.label.costCenter" value="${tarea.costCenter}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:inputText id="valuationKeyReason" label="eti.visortarea.form.label.valuationKeyReason" value="${tarea.valuationKeyReason}" cells="6" readonly="true"/>
    <app:inputText id="agreement" label="eti.visortarea.form.label.agreement" value="${tarea.agreement}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:inputText id="solution" label="eti.visortarea.form.label.solution" value="${tarea.solution}" cells="6" readonly="true"/>

</div>
<div class="spacer_t1"></div>

<div class="row">
    <app:inputText id="destinationDepartment" label="eti.visortarea.form.label.destinationDepartment" value="${tarea.destinationDepartment}" cells="6" readonly="true"/>
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
costCenter='null',
valuationKeyReason='null',
solution='null',
agreement='null',
destinationDepartment='null',
closingReason=null,
compensation='null'
}

-->