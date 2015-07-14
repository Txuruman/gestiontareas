<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div ng-controller="feecleaningtask-ctrl" ng-init="getTarea()">
    <div class="panel panel-default">
        <div class="panel-body">
            <jsp:include page="comunes.jsp"/>
            <div class="row">
                <app:inputTextNG id="contrato" label="limpiezacuota.contrato" value="tarea.contrato" cells="6"
                                 readonly="true"/>
                <app:inputTextNG id="departamentoAsignado" label="limpiezacuota.departamentoasignado"
                                 value="tarea.departamentoAsignado" cells="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="descripcionIncidencia" label="limpiezacuota.descripcionincidencia"
                                 value="tarea.descripcionIncidencia" cells="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
        </div>
        <!-- END ANGULARJS DIV -->
    </div>
    <jsp:include page="btn_excel.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/feeCleaning-ctrl.js"></script>

