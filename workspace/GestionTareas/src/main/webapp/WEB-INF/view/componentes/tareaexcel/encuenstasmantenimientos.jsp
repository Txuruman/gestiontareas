<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div ng-controller="maintenancesurvey-ctrl" ng-init="getInstallationAndTask()">
<app:messageNoInstallation/>
    <jsp:include page="instalacion.jsp"/>
    <div class="panel panel-default">
        <div class="panel-body">
            <jsp:include page="comunes.jsp"/>

            <div class="row">
                <app:inputTextNG id="maintenanceNumber" label="eti.visortarea.form.label.maintenanceNumber"
                                 value="tarea.maintenanceNumber" cells="6" readonly="true"/>
                <app:inputTextNG id="technician" label="eti.visortarea.form.label.technician" value="tarea.technician"
                                 cells="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>

            <div class="row">
                <app:inputTextNG id="manager" label="eti.visortarea.form.label.manager" value="tarea.manager" cells="6"
                                 readonly="true"/>
                <app:inputTextNG id="costCenter" label="eti.visortarea.form.label.costCenter" value="tarea.costCenter"
                                 cells="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>

            <div class="row">
                <app:inputTextNG id="valuationKeyReason" label="eti.visortarea.form.label.valuationKeyReason"
                                 value="tarea.valuationKeyReason" cells="6" readonly="true"/>
                <app:inputTextNG id="agreement" label="eti.visortarea.form.label.agreement" value="tarea.agreement" cells="6"
                                 readonly="true"/>
            </div>
            <div class="spacer_t1"></div>

            <div class="row">
                <app:inputTextNG id="solution" label="eti.visortarea.form.label.solution" value="tarea.solution" cells="6"
                                 readonly="true"/>

            </div>
            <div class="spacer_t1"></div>

            <div class="row">
                <app:inputTextNG id="destinationDepartment" label="eti.visortarea.form.label.destinationDepartment"
                                 value="tarea.destinationDepartment" cells="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
        </div>
    </div>
    <jsp:include page="btn_excel.jsp"/>
</div>
<!-- FIN DIV ANGULARJS -->

<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/maintenanceSurvey-ctrl.js"></script>

