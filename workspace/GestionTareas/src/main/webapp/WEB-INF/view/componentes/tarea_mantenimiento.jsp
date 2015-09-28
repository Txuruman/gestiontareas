<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div ng-controller="maintenancetask-ctrl" ng-init="init()">
	<app:messageNoInstallation/>
    <jsp:include page="instalacion.jsp"/>
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="spacer_t2"></div>
            <div class="row">
                        <app:inputTextNG id="numeroContrato" label="tareamantenimiento.numeroContrato" value="tarea.numeroContrato"
                                         readonly="true" cells="6"/>
<%--                         <app:inputTextNG id="tipoMantenimiento" label="tareamantenimiento.tipoMantenimiento" value="tarea.tipoMantenimiento" --%>
<%--                                          readonly="true" cells="6"/> --%>
						<app:inputDate id="fechaEvento" label="tareamantenimiento.fechaEvento" value="tarea.fechaEvento"
                                         readonly="true" cells="6"/>
						
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                        <app:inputTextNG id="direccion" label="tareamantenimiento.direccion" value="tarea.direccion"
                                         readonly="true" cells="6"/>
                        <app:inputTextNG id="agenteAsignado" label="tareamantenimiento.agenteAsignado"
                                         value="tarea.agenteAsignado" readonly="true" cells="6"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                    <div class="row">
                    	<app:inputTextNG id="ciudad" label="tareamantenimiento.ciudad" value="tarea.ciudad" readonly="true" cells="12"/>
                    </div>
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <app:input id="cancelationTypeCombo" label="tareamantenimiento.tipoCancelacion" cells="12">
                            <select ng-model="tarea.tipoCancelacion" convert-to-number class="form-control" ng-change="asignarTextoCancelacion()">
                                <option data-ng-repeat="k in cancelationTypeList" value="{{k.id}}" ng-selected="k.id==tarea.tipoCancelacion">{{k.value}}</option>
                            </select>
                        </app:input>
                    </div>
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                            <app:inputTextNG id="telefonoInstal" label="tareamantenimiento.telefonoInstalacion" value="tarea.telefono"
                                             readonly="true"/>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                            <button type="button" class="btn btn-default btn-sm botonAbsolute" title="<spring:message code="tareamantenimiento.llamartelefonoInst"/>" ng-click="lastCalledPhone=tarea.telefono; doCall(tarea.telefono);">
                        			<span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
               				 </button>
                        </div>
                    </div>
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                            <app:inputTextNG id="telefono1" label="tareamantenimiento.telefono1" value="tarea.telefono1"
                                             readonly="true"/>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                        	<button type="button" class="btn btn-default btn-sm botonAbsolute" title="<spring:message code="tareamantenimiento.llamartelefono1"/>" ng-click="lastCalledPhone=tarea.telefono1; doCall(tarea.telefono1);">
                        			<span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
               				 </button>
                        </div>
                    </div>
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                        </div>
                        <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                            <app:inputTextNG id="telefono2" label="tareamantenimiento.telefono2" value="tarea.telefono2"
                                             readonly="true"/>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                             <button type="button" class="btn btn-default btn-sm botonAbsolute" title="<spring:message code="tareamantenimiento.llamartelefono2"/>" ng-click="lastCalledPhone=tarea.telefono2; doCall(tarea.telefono2);">
                        			<span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
               				 </button>
                        </div>
                    </div>
                     <div class="spacer_t1"></div>
                    <div class="row">
	                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
	                        </div>
	                        <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
	                            <app:inputTextNG id="otroTelefono" label="tareamantenimiento.otrotelefono" value="otroTelefono" only="true"/>
	                        </div>
	                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
	                             <button type="button" class="btn btn-default btn-sm botonAbsolute" title="<spring:message code="tareamantenimiento.llamartelefono3"/>" ng-click="compruebaTfno()">
	                        			<span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
	               				 </button>
	                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                    <div class="row">
                           <app:textAreaNG id="decripcion" label="tareamantenimiento.descripcion" value="tarea.textoCancelacion" cells="12"/>
                    </div>
                     <div class="spacer_t1"></div>
                    <div class="row">
                    	<app:input id="desplegableKey1" label="tareamantenimiento.key1" required="true" cells="12">
                            <select  ng-model="tarea.key1" convert-to-number class="form-control" ng-change="getDesplegableKey2()" name="key1" required="true">
                                <option data-ng-repeat="k in key1List" value="{{k.id}}" ng-selected="k.id==tarea.key1" >{{k.value}}</option>
                            </select>
                            <span class="error" ng-show="formVisorTarea.key1.$error.required && verErrores==true"><spring:message code="error.notext"/></span>
                        </app:input>
                    </div>
                     <div class="spacer_t1"></div>
                    <div class="row">
                        <app:input id="desplegableKey2" label="tareamantenimiento.key2" required="true" cells="12">
                            <select  ng-model="tarea.key2" convert-to-number class="form-control" name="key2" required="true">
                                <option data-ng-repeat="k in key2List" value="{{k.id}}" ng-selected="k.id==tarea.key2" >{{k.value}}</option>
                            </select>
                            <span class="error" ng-show="formVisorTarea.key2.$error.required && verErrores==true"><spring:message code="error.notext"/></span>
                        </app:input>
                    </div>
                </div>
            </div>
            <div class="spacer_t1"></div>
        </div>
        <!-- End div angular -->
    </div>
    <jsp:include page="btn_mantenimiento.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/resources/app/component/maintenanceTask-ctrl.js"></script>