<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="panel panel-default">
    <div class="panel-body">
        <div class="row" align="right">
            <!-- Panel de botones - Seleccion de controlador -->
            <div class="container-fluid">
                <app:inputButtonNG value="boton.Crear" button_type="default" ng_click="interactionCreateMaintenance()" fluid_wrapper="true" />
                <app:inputButtonNG value="boton.Finalizar" button_type="primary" ng_click="finalizar()" fluid_wrapper="true"/>
            </div>
        </div>
    </div>
</div>
<!-- Fin Botones -->
