<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div ng-controller="marketingsurveytask-ctrl" ng-init="getInstallationAndTask()">
<app:messageNoInstallation/>
<!--     <div class="row bordel"> -->
<!--         Tarea: {{tarea}}<br/> -->
<!--         Instalacion: {{installationData}} -->
<!--     </div> -->
    <jsp:include page="instalacion.jsp"/>
    <div class="panel panel-default">
        <div class="panel-body">
            <jsp:include page="comunes.jsp"/>
            <div class="row">
                <app:inputDate id="date" label="marketingsurvey.date" value="tarea.fecha" cells="6" readonly="true"/>
                <app:inputTextNG id="reason" label="marketingsurvey.reason" value="tarea.motivo" cells="6" readonly="true"/>
            </div>
        </div>
    </div>
    <jsp:include page="btn_excel.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/marketingSurvey-ctrl.js"></script>
