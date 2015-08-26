<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="panel panel-default">
    <div class="panel-body">
        <div class="row" align="right">
            <!-- Panel de botones - Seleccion de controlador -->
            <div class="container-fluid">
                <app:inputButtonNG value="boton.Finalizar" type="submit" button_type="primary" ng_click="formVisorTarea.$valid ? finalizar() : null" fluid_wrapper="true" ng_disabled="(tarea===undefined || tarea===null) ? true : false"/>
            </div>
        </div>
    </div>
</div>
<!-- Fin Botones -->
