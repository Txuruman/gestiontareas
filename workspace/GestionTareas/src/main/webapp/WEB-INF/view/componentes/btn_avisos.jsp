<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="panel panel-default">
    <div class="panel-body">
        <div class="row" align="right">
            <!-- Panel de botones - Seleccion de controlador -->
            <div class="container-fluid">
            	<button type="button" class="btn btn-default[disabled]" title="<spring:message code="boton.Refrescar" />" ng-disabled="refeshDisabled" ng_click="refrescar()">
			    	<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
           		</button>
                <app:inputButtonNG value="boton.CrearMantenimiento" type="submit" button_type="default" ng_click="(formVisorTarea.$valid && tarea.datosAdicionalesCierre!=null && tarea.closing!=null)? crearmantenimiento() : null" fluid_wrapper="true" /><!-- ng_disabled="(tarea===undefined || tarea===null) ? true : false"/>  -->
                <app:inputButtonNG value="boton.Aplazar" button_type="default" ng_click="openDelayModal()" fluid_wrapper="true" ng_disabled="(tarea===undefined || tarea===null) ? true : false"/>
                <app:inputButtonNG value="boton.Descartar" button_type="default" ng_click="openContentModal()" fluid_wrapper="true"/>
                <!-- Añadir condición cuando haya motivos de cierre :  && tarea.closing!=null -->
                <app:inputButtonNG value="boton.Finalizar" type="submit" button_type="primary" ng_click="(formVisorTarea.$valid && tarea.datosAdicionalesCierre!=null && tarea.closing!=null)? finalizar() : muestraFinalizarRequired()" fluid_wrapper="true" ng_disabled="(tarea===undefined || tarea===null) ? true : false"/>
            </div>
        </div>
    </div>
</div>
<!-- Fin Botones -->


<!-- Dialogo Delay Modal -->
<app:delayModalContent/>
<!-- Modal de contención de descartar -->
<app:contentModal/>


<!-- Ejemplos Ventana Modal Aplazar
    <button class="btn btn-default" ng-click="openDelayModal()">Open me!</button>
    <button class="btn btn-default" ng-click="open('lg')">Large modal</button>
    <button class="btn btn-default" ng-click="open('sm')">Small modal</button>
    <button class="btn btn-default" ng-click="toggleAnimation()">Toggle Animation ({{ animationsEnabled }})</button>
-->


