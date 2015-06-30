<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/custom.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <jsp:include page="bloques/tabs1.jsp"/>
    </div>
    <div class="spacer_t2"></div>
    <form class="form-horizontal" role="form">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row text-center">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="col-lg-3 col-md-3 col-sm-4 col-xs-4">
                            <div class="bordel text-center">
                                <label class="checkbox-inline"> <input type="radio"
                                                                       id="checkboxEnLinea1" name="options" value="opcion_1">
                                    <spring:message code="eti.buscartarea.form.radio.telefono1"/>
                                </label>
                                <label class="checkbox-inline"> <input type="radio"
                                                                                id="checkboxEnLinea2" name="options"
                                                                                value="opcion_2">
                                    <spring:message code="eti.buscartarea.form.radio.cliente"/>
                                </label>
                            </div>
                        </div>
                        <label for="ejemplo_email_3"
                               class="col-lg-3 col-md-3 col-sm-3 col-xs-2 control-label labelcent">
                            <spring:message code="eti.buscartarea.form.label.filtro"/>:
                        </label>
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                            <input type="text" class="form-control" id="ejemplo_email_3"
                                   placeholder="Buscar">
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-left">
                            <input type="submit" class="btn btn-primary" value="Buscar"/>
                        </div>
                    </div>
                </div>
                <!-- row -->
                <div class="spacer_t3"></div>
                <div class="spacer_t2"></div>
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
                        <table class="table table-bordered">
                            <tr class="cabecillas">
                                <th>Cliente</th>
                                <th>Calling List</th>
                                <th>Teléfono</th>
                                <th>Estado</th>
                                <th>Fecha de reprogramación</th>
                                <th>Gestion</th>
                                <th>Aplazar</th>
                            </tr>
                            <%--Bucle de la tabla --%>
                            <c:forEach items="${listaTareas}" var="tarea">
                                <tr>
                                    <td><c:out value="${tarea.codigoCliente}"/></td>
                                    <td><c:out value="${tarea.callingList}"/></td>
                                    <td><c:out value="${tarea.telefono}"/></td>
                                    <td><c:out value="${tarea.estado}"/></td>
                                    <td><fmt:formatDate pattern="HH:mm dd-MM-yyyy" value="${tarea.fechaReprogramacion}" /></td>
                                    <td><a href="visortarea.htm?ins_no=<c:out value="${tarea.callingList}"/>&tipotarea=aviso" class="btn btn-default"><spring:message code="eti.buscartarea.btn.gestion"/></a></td>
                                    <td><a href="#" class="btn btn-default"><spring:message code="eti.buscartarea.btn.aplazar"/></a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>


<!-- <div class="row">
<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
</div>
</div>-->