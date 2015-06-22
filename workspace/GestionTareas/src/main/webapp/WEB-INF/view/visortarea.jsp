<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title><spring:message code="${titulo}"/></title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/custom.css"/>
</head>
<body>


<div class="container">


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
        <div class="row">


            <app:input id="ninstalacion" label="eti.visortarea.form.label.ninstalacion" value="${installationData.numeroInstalacion}" cells="6" readonly="true"/>

            <app:input id="titular" label="eti.visortarea.form.label.titular" value="${installationData.titular}" cells="6" readonly="true"/>

        </div>

        <div class="spacer_t1"></div>
        <div class="row">
            <%--<app:input id="telefono" label="eti.visortarea.form.label.3" value="${installationData.telefono}" cells="6" readonly="true"  />--%>

            <app:input id="personaContacto" label="eti.visortarea.form.label.personacontacto" value="${installationData.personaContacto}" cells="6" readonly="true"/>

        </div>

        <div class="spacer_t1"></div>
        <div class="row">
            <app:input id="panel" label="eti.visortarea.form.label.panel" value="${installationData.panel}" cells="6" readonly="true"/>

            <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true"/>
        </div>


        <!-- INCLUDES DE TAREAS -->
        <div class="spacer_t3"></div>
        <div class="panel panel-default">
            <div class="panel-body">
                <%--TODO AQUI SE INCLUYE LA SECUNDARIA--%>
                <jsp:include page="${secundaria}"/>
            </div>
        </div>

        <!-- Botones -->
        <div class="spacer_t2"></div>
        <div class="row">
            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8"></div>
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                <div class="row text-right">
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                        <input type="submit" class="btn btn-default" value="Aplazar"/>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                        <input type="submit" class="btn btn-default" value="Descartar"/>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                        <input type="submit" class="btn btn-default" value="LLamar"/>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                        <input type="submit" class="btn btn-primary" value="Finalizar"/>
                    </div>

                </div>

            </div>
        </div>
        <!-- row -->


    </form>

</div>
<!-- Container -->
InstallationData : ${installationData}<br/>
Tarea : ${tarea}

</body>
</html>