<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html data-ng-app="myApp">
<head>
    <title><spring:message code="titulo.creartarea"/></title>
    <app:commonImports/>
</head>
<body>

<div class="visoTareaContainer" ng-controller="taskCreation" ng-init="installationId='${installationId}';ccUserId='${ccUserId}';init()" >
    <div class="row">
        <jsp:include page="bloques/tabs1.jsp"/>
    </div>
    <app:messages/>

    <form class="form-horizontal" role="form" name="formCreateTask">
        <div class="spacer_t2"></div>
        <jsp:include page="instalacion.jsp"/>
        <div class="panel panel-default">
            <div class="panel-body">
                <!-- row -->
                <div class="row">
		            <app:inputTextNG id="personaContacto" label="visortarea.personacontacto"
		                             value="installationData.personaContacto" cells="6" />
		            <app:inputTextNG id="telefono" label="visortarea.telefono" value="installationData.telefono" cells="6"
		                             type="text"  only="true" />
		
		        </div>
                <div class="spacer_t1"></div>
                <div class="row">
                    <app:inputTextNG id="requiredBy" value="tarea.requeridoPor" form="formCreateTask" required="true" label="createtask.requiredby" cells="6" />
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <app:inputTextNG id="horarioDesde" label="tareaAviso.horarioDesde" form="formCreateTask" required="true" value="tarea.horarioDesde" cells="6" only="true" size="2" /><!--  ng_keypress="onlyNumber($event,true,tarea.horarioDesde.length)"/> -->
                		<app:inputTextNG id="horarioHasta" label="tareaAviso.horarioHasta" form="formCreateTask" required="true" value="tarea.horarioHasta" cells="6"  only="true" size="2"/>
                    </div>
                </div>
                <!-- row -->
                <div class="spacer_t1"></div>
                <div class="row">
                    <app:input id="tipoAviso1" label="createtask.type" cells="6">
                        <select  ng-model="tarea.tipoAviso1" convert-to-number class="form-control" ng-change="getTypeReasonList1(tarea.tipoAviso1)">
                             <option data-ng-repeat="itemTipoAviso1 in tipoAvisoList" value="{{itemTipoAviso1.id}}" ng-selected="itemTipoAviso1.id==tarea.tipoAviso1">{{itemTipoAviso1.id +" - "+ itemTipoAviso1.value}}</option>
                        </select>
                    </app:input>
					<app:input id="motivo1" label="eti.visortarea.form.label.reason" cells="6">
	                    <select  ng-model="tarea.motivo1" convert-to-number class="form-control" ng-change="refeshDisabled=false; getClosingList(tarea.tipoAviso1,tarea.tipoMotivo1,tarea.closing)">
	                        <option data-ng-repeat="itemMotivo1 in motivoList1" value="{{itemMotivo1.id}}" ng-selected="itemMotivo1.id==tarea.motivo1" >{{itemMotivo1.id +" - "+ itemMotivo1.value}}</option>
	                    </select>
                	</app:input>
                </div>
                <div class="spacer_t1"></div>
                <div class="row">
                    <app:input id="tipoAviso2" cells="6">
                    <select  ng-model="tarea.tipoAviso2" convert-to-number class="form-control" ng-change="refeshDisabled=false; getTypeReasonList2(tarea.tipoAviso2)">
                        <option data-ng-repeat="itemTipoAviso2 in tipoAvisoList" value="{{itemTipoAviso2.id}}" ng-selected="itemTipoAviso2.id==tarea.tipoAviso2" >{{itemTipoAviso2.id +" - "+ itemTipoAviso2.value}}</option>
                    </select>
	                </app:input>
	                <app:input id="motivo2" cells="6">
	                    <select  ng-model="tarea.motivo2" convert-to-number class="form-control" ng-change="refeshDisabled=false">
	                        <option data-ng-repeat="itemMotivo2 in motivoList2" value="{{itemMotivo2.id}}" ng-selected="itemMotivo2.id==tarea.motivo2" >{{itemMotivo2.id +" - "+ itemMotivo2.value}}</option>
	                    </select>
	                </app:input>
                </div>
                <div class="spacer_t1"></div>
                <div class="row">
                     <app:input id="tipoAviso3" cells="6">
                    <select  ng-model="tarea.tipoAviso3" convert-to-number class="form-control" ng-change="refeshDisabled=false; getTypeReasonList3(tarea.tipoAviso3)">
                        <option data-ng-repeat="k in tipoAvisoList" value="{{k.id}}" ng-selected="k.id==tarea.tipoAviso3" >{{k.id +" - "+ k.value}}</option>
                    </select>
	                </app:input>
	                <app:input id="motivo3" cells="6">
	                    <select  ng-model="tarea.motivo3" convert-to-number class="form-control" ng-change="refeshDisabled=false">
	                        <option data-ng-repeat="k in motivoList3" value="{{k.id}}" ng-selected="k.id==tarea.motivo3" >{{k.id +" - "+ k.value}}</option>
	                    </select>
	                </app:input>
                </div>
                <div class="spacer_t1"></div>
                <!-- row -->
                <app:textAreaNG id="comment" value="tarea.observaciones" label="createtask.comment" cell_label="2" cell_input="10"/>
            </div>
        </div>
        <!-- row -->
        <div class="spacer_t1"></div>
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row" align="right">
                    <div class="container-fluid">
                        <app:inputButtonNG value="boton.CrearMantenimiento" button_type="default" ng_click="createMaintenance()" fluid_wrapper="true"/>
                        <app:inputButtonNG button_type="primary" type="submit" value="boton.Crear" ng_click="formCreateTask.$valid ? createTask() : null" fluid_wrapper="true"/>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- ANGULARJS DIV END -->
<!-- Container -->

</body>
<script src="${pageContext.request.contextPath}/resources/app/maincontrollers/taskCreation-ctrl.js"></script>
</html>