<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div ng-controller="anotherCampaigns" ng-init="getTarea()">
    <jsp:include page="instalacion.jsp"/>
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="spacer_t1"></div>
            <jsp:include page="comunes.jsp"/>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="tipoCampana" label="eti.visortarea.form.label.otras.tipoCampana" value="tarea.tipoCampana"
                                 cells="6" readonly="true"/>
                <app:inputTextNG id="campo1" label="" value="tarea.campo1" cells="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="comentario" label="eti.visortarea.form.label.otras.comentario" value="tarea.comentario"
                                 cells="6" readonly="true"/>
                <app:inputTextNG id="campo2" label="" value="tarea.campo2" cells="6" readonly="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6"></div>
                <app:inputTextNG id="campo3" label="" value="tarea.campo3" cells="6" readonly="true"/>
            </div>
        </div>
    </div>
    <jsp:include page="btn_excel.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/anotherCampaigns-ctrl.js"></script>