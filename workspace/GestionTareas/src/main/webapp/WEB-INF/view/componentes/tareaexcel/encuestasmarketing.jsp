<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div ng-controller="marketingsurveytask-ctrl" ng-init="getTarea()">
    <div class="panel panel-default">
        <div class="panel-body">
            <jsp:include page="comunes.jsp"/>
            <div class="row">
                <app:inputTextNG id="date" label="marketingsurvey.date" value="tarea.date" cells="6" readonly="true"/>
                <app:inputTextNG id="reason" label="eti.visortarea.form.label.reason" value="tarea.reason" cells="6" readonly="true"/>
            </div>
        </div>
    </div>
    <jsp:include page="btn_excel.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/marketingSurvey-ctrl.js"></script>
