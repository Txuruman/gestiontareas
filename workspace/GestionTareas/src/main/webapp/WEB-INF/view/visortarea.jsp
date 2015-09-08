<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //TODO QUITAR??
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Expires", "-1");
    response.addHeader("Pragma", "no-cache");
%>

<html data-ng-app="myApp">
<head>
    <title><spring:message code="${titulo}"/></title>
    <app:commonImports/>
</head>


<body>


<div class="visoTareaContainer" ng-init="tareaId='${tareaId}';callingList='${callingList}';outContactInfo='${outContactInfo}';outCampaignName='${outCampaignName}';outClName='${outClName}';outRecordHandle='${outRecordHandle}';outAgentPlace='${outAgentPlace}';">

    ng-init="tareaId='${tareaId}';callingList='${callingList}';outContactInfo='${outContactInfo}';outCampaignName='${outCampaignName}';outClName='${outClName}';outRecordHandle='${outRecordHandle}';outAgentPlace='${outAgentPlace}';"

    ${params}

    <app:messages/>

    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
            <h2><spring:message code="${titulo}"/></h2>
        </div>
    </div>
    <div class="spacer_t2"></div>
    <form class="form-horizontal" role="form" name="formVisorTarea">
        <!-- Include Tareas, dependiendo del tipo de tarea -->
        <div class="spacer_t3"></div>
        <%--TODO AQUI SE INCLUYE LA SECUNDARIA--%>
        <jsp:include page="${secundaria}"/>
        <!-- Include Tareas, end -->
    </form>
</div>
<!-- END ANGULARJS CONTROLLER DIV-->
<!-- Scripts de angularjs -->
${params}
</body>
</html>
