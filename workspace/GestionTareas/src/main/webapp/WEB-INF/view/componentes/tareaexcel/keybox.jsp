<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div ng-controller="keyboxtask-ctrl" ng-init="getInstallationAndTask()">
    <jsp:include page="instalacion.jsp"/>
    <div class="panel panel-default">
        <div class="panel-body">
            <jsp:include page="comunes.jsp"/>
            <div class="row">
                <app:inputTextNG id="invoiceNumber" label="keybox.invoicenumber" value="tarea.invoiceNumber" cells="6"
                                 readonly="true"/>
                <app:inputTextNG id="invoiceDate" label="keybox.invoicedate" value="tarea.invoiceDate" cells="6"
                                 readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="lineValue" label="keybox.linevalue" value="tarea.lineValue" cells="6" readonly="true"/>
                <app:inputTextNG id="identificadorItem" label="keybox.identificadoritem" value="tarea.identificadorItem"
                                 cells="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="contrato" label="keybox.contrato" value="tarea.contrato" cells="6" readonly="true"/>
                <app:inputTextNG id="panel" label="keybox.panel" value="tarea.panel" cells="6" readonly="true"/>
            </div>
        </div>
    </div>
    <jsp:include page="btn_excel.jsp"/>
</div>
<!-- END ANGULARJS DIV -->
<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/keybox-ctrl.js"></script>