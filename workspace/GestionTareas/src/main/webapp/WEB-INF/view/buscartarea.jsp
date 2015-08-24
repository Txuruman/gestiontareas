
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html data-ng-app="myApp">
<head>
    <title><spring:message code="titulo.BuscarTarea"/></title>

    <app:commonImports/>
    <script src="${pageContext.request.contextPath}/resources/app/maincontrollers/taskSearch-ctrl.js"></script>

</head>
<body>
<div class="visoTareaContainer" ng-controller="taskSearch" ng-init="searchText='${lastSearchTareaRequest.searchText}';searchOption='${lastSearchTareaRequest.searchOption}'">

    <app:messages/>
    <div class="row filaAjustada">
        <jsp:include page="bloques/tabs1.jsp"/>
    </div>
    <div class="row panel filaAjustada">
        <form class="form-horizontal" role="form" name="searchForm">
            <div class="panel panel-default panel-body">
                <div class="row">
                    <!-- Caja de busqueda -->
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                        <label for="searchText" class=" control-label labelcent">
                            <spring:message code="eti.buscartarea.form.label.filtro"/>:
                        </label>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">

                        <input type="text" name="searchTextInput" class="form-control" id="searchText" ng-model="searchText"
                               ng-minlength="6" ng-required="true" title="6 caracteres mínimo" pattern=".{6,}" ng-keyup="($event.keyCode===13 && searchForm.$valid) ? searchTareaFromServer() : null">
                        <span class="error" ng-show="searchForm.searchTextInput.$error.required"><spring:message code="searchTarea.error.required"/></span>
                        <span class="error" ng-show="searchForm.searchTextInput.$error.minlength"><spring:message code="searchTarea.error.minlength"/></span>

                    </div>
                    <!-- Boton Busqueda -->
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-left">
                        <app:inputButtonNG button_type="primary" type="submit" value="boton.search" ng_click="searchForm.$valid ? searchTareaFromServer() : null"/>
                    </div>
                    <!-- Opciones busqueda -->
                    <app:wrapping cells="4">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <input id="phone" class="radio-inline" type="radio" name="options" ng-model="searchOption"
                                   value="phone" >
                            <label for="phone"><spring:message code="eti.buscartarea.form.radio.telefono1"/></label>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <input id="customer" class="radio-inline" type="radio" name="options" ng-model="searchOption"
                                   value="customer">
                            <label for="customer"><spring:message code="eti.buscartarea.form.radio.cliente"/></label>
                        </div>
                    </app:wrapping>
                </div>

            </div>

            <div class="spacer_t2"></div>
            <div class="row filaAjustada">
                <!-- Tabla de Tareas -->
                <table class="table table-bordered"  >
                    <tr class="cabecillas">
                        <th><spring:message code="searchTarea.table.installationId"/></th>
                        <th><spring:message code="searchTarea.table.taskType"/></th>
                        <th><spring:message code="searchTarea.table.status"/></th>
                        <th><spring:message code="searchTarea.table.description"/></th>
                        <th><spring:message code="searchTarea.table.reprogramationDate"/></th>
                        <th>Gestion</th>
                        <th>Aplazar</th>
                    </tr>
                    <tr ng-repeat="t in taskList | orderBy : 'codigoCliente'">
                        <td>{{ t.numeroInstalacion }}</td>
                        <td>{{ t.callingList }}</td>
                        <td>
                        	<div ng-show="t.estado===0">
                        		<spring:message code="searchTarea.table.status.status0"/>
                        	</div>
                        	<div ng-show="t.estado===1">
                        		<spring:message code="searchTarea.table.status.status1"/>
                        	</div>
                        	<div ng-show="t.estado===2">
                        		<spring:message code="searchTarea.table.status.status2"/>
                        	</div>
                        	<div ng-show="t.estado===3">
                        		<spring:message code="searchTarea.table.status.status3"/>
                        	</div>
                        	<div ng-show="t.estado===4">
                        		<spring:message code="searchTarea.table.status.status4"/>
                        	</div>
                        	<div ng-show="t.estado===5">
                        		<spring:message code="searchTarea.table.status.status5"/>
                        	</div>
                        	<div ng-show="t.estado===6">
                        		<spring:message code="searchTarea.table.status.status6"/>
                        	</div>
                        	<div ng-show="t.estado===8">
                        		<spring:message code="searchTarea.table.status.status8"/>
                        	</div>
                        </td>
                        <td>{{ t.observaciones }}</td>
                        <td>{{ t.fechaReprogramacion | date:'yyyy-MM-dd HH:mm:ss'}}</td>
                        <td><a ng-href="visortarea.htm?bp_out_GSW_CHAIN_ID_CUSTOM={{t.id}}&bp_out_clname={{t.callingList}}"
                               class="btn btn-default" ng-disabled="t.estado===2"><spring:message code="eti.buscartarea.btn.gestion" /></a></td>
                        <td><a href="#" class="btn btn-default" ng-click="openDelayModal(null,t)" ng-disabled="t.estado===2"><spring:message code="eti.buscartarea.btn.aplazar"/></a>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
    <!-- Dialogo Delay Modal -->
	<app:delayModalContent/>
</div>

${params}
</body>

</html>