<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html data-ng-app="myApp">
<head>
    <title><spring:message code="titulo.creartarea"/></title>
    <app:commonImports/>
    <script src="${pageContext.request.contextPath}/resources/app/maincontrollers/taskCreation-ctrl.js"></script>

</head>
<body>

<div class="container" ng-controller="taskCreation" ng-init="loadInstallation();" >
    <div class="row">
        <jsp:include page="bloques/tabs1.jsp"/>
    </div>
    <app:messages/>
    <div class="please-wait-dialog" ng-hide="vm.appReady">
        Apliacion NO LISTA
        <img class="please-wait-spinner" src="/resources/img/loading.gif">
    </div>
    <div class="form-horizontal" role="form">
        <div class="spacer_t2"></div>
        <jsp:include page="instalacion.jsp"/>
        <div class="panel panel-default">
            <div class="panel-body">
                <!-- row -->
                <div class="spacer_t1"></div>
                <div class="row">
                    <app:inputTextNG id="requiredBy" value="createTaskModel.requiredBy" label="createtask.requiredby" cells="6"/>
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <app:inputTextNG id="hourfrom" value="createTaskModel.hourFrom" cells="6" label="createtask.hour.from"/>
                        <app:inputTextNG id="hourfrom" value="createTaskModel.hourTo" cells="6" label="createtask.hour.to"/>
                    </div>
                </div>
                <!-- row -->
                <div class="spacer_t1"></div>
                <div class="row">
                    <app:inputCombo id="type1" value="createTaskModel.type1" label="createtask.type" cells="6"/>
                    <app:inputCombo id="reason1" value="createTaskModel.reason1" label="createtask.reason" cells="6"/>
                </div>
                <div class="spacer_t1"></div>
                <div class="row">
                    <app:inputCombo id="type2" value="createTaskModel.type2" cells="6"/>
                    <app:inputCombo id="reason2" value="createTaskModel.reason2" cells="6"/>
                </div>
                <div class="spacer_t1"></div>
                <div class="row">
                    <app:inputCombo id="type3" value="createTaskModel.type3" cells="6"/>
                    <app:inputCombo id="reason3" value="createTaskModel.reason3" cells="6"/>
                </div>
                <div class="spacer_t1"></div>
                <!-- row -->
                <app:textAreaNG id="comment" value="createTaskModel.comment" label="createtask.comment" cell_label="2" cell_input="10"/>
            </div>
        </div>
        <!-- row -->
        <div class="spacer_t1"></div>
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row" align="right">
                    <div class="container-fluid">
                        <app:inputButtonNG value="boton.CrearMantenimiento" button_type="default" ng_click="createMaintenance()" fluid_wrapper="true"/>
                        <app:inputButtonNG button_type="primary" value="boton.Crear" ng_click="createTask()" fluid_wrapper="true"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ANGULARJS DIV END -->
<!-- Container -->

</body>
</html>