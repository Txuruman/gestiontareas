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
          href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/custom.css"/>
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

        <!-- Datos de la Instalacion  -  Start -->
        <div class="row">
            <app:input id="ninstalacion" label="visortarea.ninstalacion" value="${installationData.numeroInstalacion}" cells="6" cell_label="4" cell_input="8" readonly="true"/>
            <app:input id="titular" label="visortarea.titular" value="${installationData.titular}" cells="6" readonly="true"/>
        </div>

        <div class="spacer_t1"></div>
        <div class="row">
            <app:input id="personaContacto" label="visortarea.personacontacto" value="${installationData.personaContacto}" cells="6" readonly="true"/>
            <app:input id="panel" label="visortarea.panel" value="${installationData.panel}" cells="6" readonly="true"/>
        </div>

        <div class="spacer_t1"></div>

        <div class="row">
            <app:input id="telefono" label="visortarea.telefono" value="${installationData.telefono}" cells="6" readonly="true"/>
            <app:input id="version" label="visortarea.version" value="${installationData.version}" cells="6" readonly="true"/>
        </div>
        <!-- Datos de la Instalacion  - End -->

        <!-- INCLUDES DE TAREAS, dependiendo del tipo de tarea -->
        <div class="spacer_t3"></div>
        <div class="panel panel-default">
            <div class="panel-body">
                <%--TODO AQUI SE INCLUYE LA SECUNDARIA--%>
                <jsp:include page="${secundaria}"/>
            </div>
        </div>

        <!-- Botones -->
        <div class="spacer_t2"></div>
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row" align="right">
                    <div class="container-fluid">
                        <c:if test="${secundaria eq 'componentes/tarea_avisos.jsp'}">
                            <input type="button" class="btn btn-default[disabled]" value="<spring:message code="boton.Atras" />"/>
                            <input type="button" class="btn btn-default[disabled]" value="<spring:message code="boton.Modificar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.CrearMantenimiento" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar" />"/>
                        </c:if>
                        <!-- Botones -->
                        <c:if test="${secundaria eq 'componentes/tarea_mantenimiento.jsp'}">
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.CrearMantenimiento" />"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/encuenstasmantenimientos.jsp'}">
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/encuestasmarketing.jsp'}">
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/keybox.jsp'}">
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/limpiezacuota.jsp'}">
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/listadoassistant.jsp'}">
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/otrascampanias.jsp'}">
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>


                        <input type="submit" class="btn btn-primary" value="<spring:message code="boton.Finalizar"/>"/>
                    </div>
                </div>
            </div>
        </div>
        <!-- Fin Botones -->
        <!-- row -->


    </form>

</div>
<!-- Container -->
<%--InstallationData : ${installationData}<br/>--%>
<%--Tarea : ${tarea}<br/>--%>
<%--Parametros: ${todosparametros}--%>

</body>
</html>