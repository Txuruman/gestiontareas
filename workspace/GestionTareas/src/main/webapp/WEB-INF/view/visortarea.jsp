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
            <app:input id="ninstalacion" label="eti.visortarea.form.label.ninstalacion" value="${installationData.numeroInstalacion}" cells="6" cell_label="4" cell_input="8" readonly="true"/>
            <app:input id="titular" label="eti.visortarea.form.label.titular" value="${installationData.titular}" cells="6" readonly="true"/>
        </div>

        <div class="spacer_t1"></div>
        <div class="row">
            <app:input id="personaContacto" label="eti.visortarea.form.label.personacontacto" value="${installationData.personaContacto}" cells="6" readonly="true"/>
            <app:input id="panel" label="eti.visortarea.form.label.panel" value="${installationData.panel}" cells="6" readonly="true"/>
        </div>

        <div class="spacer_t1"></div>


        <c:if test="${secundaria eq 'componentes/tarea_avisos.jsp'}" >
            <div class="row">
                <app:inputCombo id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true" />
            </div>
        </c:if>

        <c:if test="${secundaria eq 'componentes/tarea_mantenimiento.jsp'}" >
            <div class="row">
                <app:inputCombo id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <input type="submit" class="btn btn-xs" value="<spring:message code="boton.Llamar" />" cells="6" />
                <div class="spacer_t1"></div>
            </div>
            <div class="row">
                <div class="col col-lg-6"></div>
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true"/>
            </div>
        </c:if>

        <c:if test="${secundaria eq 'componentes/tareaexcel/comunes.jsp'}" >
            <div class="row">
                <app:input id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true" />
            </div>
        </c:if>
        <c:if test="${secundaria eq 'componentes/tareaexcel/encuestasmarketing.jsp'}" >
            <div class="row">
                <app:input id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true" />
            </div>
        </c:if>
        <c:if test="${secundaria eq 'componentes/tareaexcel/keybox.jsp'}" >
            <div class="row">
                <app:input id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true" />
            </div>
        </c:if>
        <c:if test="${secundaria eq 'componentes/tareaexcel/limpiezacuota.jsp'}" >
            <div class="row">
                <app:input id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true" />
            </div>
        </c:if>
        <c:if test="${secundaria eq 'componentes/tareaexcel/listadoassistant.jsp'}" >
            <div class="row">
                <app:input id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true" />
            </div>
        </c:if>
        <c:if test="${secundaria eq 'componentes/tareaexcel/otrascampanias.jsp'}" >
            <div class="row">
                <app:input id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true" />
            </div>
        </c:if>
        <c:if test="${secundaria eq 'componentes/tareaexcel/encuenstasmantenimientos.jsp'}" >
            <div class="row">
                <app:input id="telefono" label="eti.visortarea.form.label.telefono" value="${installationData.telefono}" cells="6" readonly="true"  />
                <input type="submit" class="btn btn-xs" value="<spring:message code="boton.Llamar" />" cells="6" />
                <div class="spacer_t1"></div>
            </div>
            <div class="row">
                <div class="col col-lg-6"></div>
                <app:input id="version" label="eti.visortarea.form.label.version" value="${installationData.version}" cells="6" readonly="true"/>
            </div>

        </c:if>

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
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row" align="right">
                    <div class="container-fluid">
                        <c:if test="${secundaria eq 'componentes/tarea_avisos.jsp'}" >
                            <input type="button" class="btn btn-default[disabled]" value="<spring:message code="boton.Atras" />"/>
                            <input type="button" class="btn btn-default[disabled]" value="<spring:message code="boton.Modificar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.CrearMantenimiento" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar" />"/>
                        </c:if>
                        <!-- Botones -->
                        <c:if test="${secundaria eq 'componentes/tarea_mantenimiento.jsp'}" >
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.CrearMantenimiento" />"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/encuenstasmantenimientos.jsp'}" >
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.CrearMantenimiento" />"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/encuestasmarketing.jsp'}" >
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/keybox.jsp'}" >
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/limpiezacuota.jsp'}" >
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/listadoassistant.jsp'}" >
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Aplazar" />"/>
                            <input type="submit" class="btn btn-default" value="<spring:message code="boton.Descartar"/>"/>
                        </c:if>
                        <c:if test="${secundaria eq 'componentes/tareaexcel/otrascampanias.jsp'}" >
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