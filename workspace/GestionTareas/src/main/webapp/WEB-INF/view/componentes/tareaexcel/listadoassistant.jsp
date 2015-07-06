<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="comunes.jsp"/>



<div ng-controller="listAssistant-ctrl" ng-init="getTarea()">

    <div class="row">
        <app:inputText id="maintenanceNumber" label="eti.visortarea.form.label.assistant.maintenanceNumber" value="${tarea.maintenanceNumber}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="technician" label="eti.visortarea.form.label.assistant.technician" value="${tarea.technician}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="departamento" label="eti.visortarea.form.label.assistant.departamento" value="${tarea.departamento}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <app:inputText id="totalSinIVA" label="eti.visortarea.form.label.assistant.totalSinIVA" value="${tarea.totalSinIVA}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="totalConIVA" label="eti.visortarea.form.label.assistant.totalConIVA" value="${tarea.totalConIVA}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="grupoPanel" label="eti.visortarea.form.label.assistant.grupoPanel" value="${tarea.grupoPanel}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <app:inputText id="tipoIncicencia" label="eti.visortarea.form.label.assistant.tipoIncicencia" value="${tarea.tipoIncicencia}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="subtipoIncidencia" label="eti.visortarea.form.label.assistant.subtipoIncidencia" value="${tarea.subtipoIncidencia}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="numeroParte" label="eti.visortarea.form.label.assistant.numeroParte" value="${tarea.numeroParte}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <app:inputText id="fechaIncidencia" label="eti.visortarea.form.label.assistant.fechaIncidencia" value="${tarea.fechaIncidencia}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="fechaPago" label="eti.visortarea.form.label.assistant.fechaPago" value="${tarea.fechaPago}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="operador" label="eti.visortarea.form.label.assistant.operador" value="${tarea.operador}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <app:inputText id="fechaCierre" label="eti.visortarea.form.label.assistant.fechaCierre" value="${tarea.fechaCierre}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="fechaArchivo" label="eti.visortarea.form.label.assistant.fechaArchivo" value="${tarea.fechaArchivo}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <app:inputText id="boFechaRecepcion" label="eti.visortarea.form.label.assistant.boFechaRecepcion" value="${tarea.boFechaRecepcion}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="boMatricula" label="eti.visortarea.form.label.assistant.boMatricula" value="${tarea.boMatricula}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="boDatosRecuperados" label="eti.visortarea.form.label.assistant.boDatosRecuperados" value="${tarea.boDatosRecuperados}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <app:inputText id="boFechaGestion" label="eti.visortarea.form.label.assistant.boFechaGestion" value="${tarea.boFechaGestion}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="boTipo" label="eti.visortarea.form.label.assistant.boTipo" value="${tarea.boTipo}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="boComentarios" label="eti.visortarea.form.label.assistant.boComentarios" value="${tarea.boComentarios}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <app:inputText id="solicitud" label="eti.visortarea.form.label.assistant.solicitud" value="${tarea.solicitudCliente}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
        <app:inputText id="cambios" label="eti.visortarea.form.label.assistant.cambios" value="${tarea.cambiosIncidencia}" cells="4" cell_label="6" cell_input="6" readonly="true"/>
    </div>


    {{tarea}}
</div>
<!-- End angular -->


<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/listAssistant-ctrl.js" ></script>