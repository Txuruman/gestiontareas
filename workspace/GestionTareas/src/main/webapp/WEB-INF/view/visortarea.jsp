<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Expires", "-1");
    response.addHeader("Pragma", "no-cache");
%>

<html data-ng-app="myApp">
<head>
    <title><spring:message code="${titulo}"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/custom.css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.6.0/moment.min.js"></script>
</head>


<body>
<!-- Angular JS Scripts -->
<script src="${pageContext.request.contextPath}/resources/app/gestiontarea-app.js"></script>
<script src="${pageContext.request.contextPath}/resources/app/maincontrollers/taskViewer-ctrl.js"></script>


<div class="container" ng-controller="taskviewer-ctrl" ng-init="tareaId='${tareaId}';installationId='${installationId}';callingList='${callingList}';ccUserId='${ccUserId}';getInstallationData(installationId)">
    <app:messages />
    
    <!-- DEBUG Start -->

    <div class="please-wait-dialog" ng-hide="vm.appReady">
        Apliacion NO LISTA
        <img class="please-wait-spinner" src="/resources/img/loading.gif">
    </div>
    <!-- DEBUG End -->

    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
            <h2><spring:message code="${titulo}"/></h2>
        </div>
    </div>
    <%--<div class="row">--%>
    <%--<jsp:include page="bloques/tabs1.jsp"/>--%>
    <%--</div>--%>

    <div class="spacer_t2"></div>

    <form class="form-horizontal" role="form">

        <!-- Datos de la Instalacion  -  Start -->
        <div class="row">
            <app:inputTextNG id="ninstalacion" label="visortarea.ninstalacion"
                             value="installationData.numeroInstalacion" cells="6" cell_label="4" cell_input="8"
                             readonly="true"/>
            <app:inputTextNG id="titular" label="visortarea.titular" value="installationData.titular" cells="6"
                             readonly="true"/>
        </div>

        <div class="spacer_t1"></div>
        <div class="row">
            <app:inputTextNG id="personaContacto" label="visortarea.personacontacto"
                             value="installationData.personaContacto" cells="6" readonly="true"/>
            <app:inputTextNG id="panel" label="visortarea.panel" value="installationData.panel" cells="6"
                             readonly="true"/>
        </div>

        <div class="spacer_t1"></div>

        <div class="row">
            <app:inputTextNG id="telefono" label="visortarea.telefono" value="installationData.telefono" cells="6"
                             readonly="true"/>
            <app:inputTextNG id="version" label="visortarea.version" value="installationData.version" cells="6"
                             readonly="true"/>
        </div>
        <!-- Datos de la Instalacion  - End -->


        <!-- Include Tareas, dependiendo del tipo de tarea -->
        <div class="spacer_t3"></div>
            <%--TODO AQUI SE INCLUYE LA SECUNDARIA--%>
            <jsp:include page="${secundaria}"/>
            </div>
        </div>
        <!-- Include Tareas, end -->

    </form>
</div><!-- END ANGULARJS CONTROLLER DIV-->
S
<!-- Scripts de angularjs -->

</body>
</html>
