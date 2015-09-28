<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div ng-controller="listAssistant-ctrl" ng-init="getInstallationAndTask()">
<app:messageNoInstallation/>
    <jsp:include page="instalacion.jsp"/>
    <div class="panel panel-default">
        <div class="panel-body">
            <jsp:include page="comunes.jsp"/>
            <div class="row">
                <app:inputTextNG id="maintenanceNumber" label="listAssistantTask.maintenanceNumber" value="tarea.maintenanceNumber" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="technician" label="listAssistantTask.technician" value="tarea.technician" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="departamento" label="listAssistantTask.departamento" value="tarea.departamento" cells="4" cell_label="6" cell_input="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="totalSinIVA" label="listAssistantTask.totalSinIVA" value="tarea.totalSinIVA" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="totalConIVA" label="listAssistantTask.totalConIVA" value="tarea.totalConIVA" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="grupoPanel" label="listAssistantTask.grupoPanel" value="tarea.grupoPanel" cells="4" cell_label="6" cell_input="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="tipoIncicencia" label="listAssistantTask.tipoIncicencia" value="tarea.tipoIncicencia" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="subtipoIncidencia" label="listAssistantTask.subtipoIncidencia" value="tarea.subtipoIncidencia" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="numeroParte" label="listAssistantTask.numeroParte" value="tarea.numeroParte" cells="4" cell_label="6" cell_input="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputDate id="fechaIncidencia" label="listAssistantTask.fechaIncidencia" value="tarea.fechaIncidencia" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="fechaPago" label="listAssistantTask.fechaPago" value="tarea.fechaPago" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="operador" label="listAssistantTask.operador" value="tarea.operador" cells="4" cell_label="6" cell_input="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputDate id="fechaCierre" label="listAssistantTask.fechaCierre" value="tarea.fechaCierre" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputDate id="fechaArchivo" label="listAssistantTask.fechaArchivo" value="tarea.fechaArchivo" cells="4" cell_label="6" cell_input="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputDate id="boFechaRecepcion" label="listAssistantTask.boFechaRecepcion" value="tarea.boFechaRecepcion" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="boMatricula" label="listAssistantTask.boMatricula" value="tarea.boMatricula" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="boDatosRecuperados" label="listAssistantTask.boDatosRecuperados" value="tarea.boDatosRecuperados" cells="4" cell_label="6" cell_input="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputDate id="boFechaGestion" label="listAssistantTask.boFechaGestion" value="tarea.boFechaGestion" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="boTipo" label="listAssistantTask.boTipo" value="tarea.boTipo" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="boComentarios" label="listAssistantTask.boComentarios" value="tarea.boComentarios" cells="4" cell_label="6" cell_input="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="solicitud" label="listAssistantTask.solicitud" value="tarea.solicitudCliente" cells="4" cell_label="6" cell_input="6" readonly="true"/>
                <app:inputTextNG id="cambios" label="listAssistantTask.cambios" value="tarea.cambiosIncidencia" cells="4" cell_label="6" cell_input="6" readonly="true"/>
            </div>
        </div>
    </div>
    <jsp:include page="btn_excel.jsp"/>
</div>
<!-- End angular -->
<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/listAssistant-ctrl.js"></script>