<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
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
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
            <h2><spring:message code="${titulo}"/></h2>
        </div>
    </div>
    <%--<div class="row">--%>
    <%--<jsp:include page="bloques/tabs1.jsp"/>--%>
    <%--</div>--%>

    <div class="spacer_t2"></div>


    <form class="form-horizontal" role="form">
InstallationData : ${installationData}
Tarea : ${tarea}


        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label
                        class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.1"/>:
                </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="titular" id="titular"
                           value="${installationData.numeroInstalacion}" disabled/>

                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="titular"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.2"/>
                </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="titular" id="titular"
                           value="${installationData.titular}" disabled
                           placeholder="<spring:message code="eti.visortarea.form.label.2"/>">
                </div>
            </div>

        </div>

        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="telefono"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.3"/>: </label>


                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="telefono" id="telefono"
                    <%--value="${installationData.titular}" disabled--%>
                           placeholder="<spring:message code="eti.visortarea.form.label.3"/>">
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="panel"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.4"/>: </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="panel" id="panel"
                           value="${installationData.panel}" disabled
                           placeholder="<spring:message code="eti.visortarea.form.label.4"/>">
                </div>
            </div>

        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="pcontacto"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.5"/>: </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="pcontacto" id="pcontacto"
                           value="${installationData.titular}" disabled
                           placeholder="<spring:message code="eti.visortarea.form.label.5"/>">
                </div>
            </div>

            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="version"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.6"/> : </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="version" id="version"
                           value="${installationData.version}" disabled
                           placeholder="<spring:message code="eti.visortarea.form.label.6"/>">
                </div>
            </div>
        </div>
        <!-- row -->



        <!-- INCLUDES DE TAREAS -->
        <div class="spacer_t3"></div>
        <div class="panel panel-default">
            <div class="panel-body">
                <%--TODO AQUI SE INCLUYE LA SECUNDARIA--%>
                <jsp:include page="${secundaria}"/>
            </div>
        </div>

        <!-- INCLUDES DE TAREAS -->
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

</body>
</html>