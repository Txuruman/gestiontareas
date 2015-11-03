<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="panel panel-default">
    <div class="panel-body">
        <div class="row" align="right">
            <!-- Panel de botones - Seleccion de controlador -->
            <div class="container-fluid">
                <app:inputButtonNG value="boton.Aplazar" button_type="default" ng_click="openDelayModal()" fluid_wrapper="true" ng_disabled="(tarea===undefined || tarea===null || installationData===null || installationData===undefined) ? true : false"/>
                <app:inputButtonNG value="boton.Descartar" button_type="default" ng_click="descartarTarea()" fluid_wrapper="true"/>
                <app:inputButtonNG value="boton.Finalizar" type="submit" button_type="primary" ng_click="formVisorTarea.$valid ? finalizar() : verErrores=true" fluid_wrapper="true" ng_disabled="(tarea===undefined || tarea===null || installationData===null || installationData===undefined) ? true : false"/>
            </div>
        </div>
    </div>
</div>


<!-- Dialogo Delay Modal -->
<app:delayModalContent/>
<!-- Modal de alerta de descartar -->
<app:alertModal titulo="alertmodal.titulo" msg1="alertmodal.msg3" button="alertmodal.return"/>

<!-- Modal de alerta de la tarea desde buscador y en retrieved -->
<app:alertModalTareaRetrieved titulo="alertmodaltarearetrieved.titulo" msg1="alertmodaltarearetrieved.msg1" button="alertmodaltarearetrieved.return"/>


<!-- Fin Botones -->
