<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div ng-controller="maintenancetask-ctrl" ng-init="getTarea()">
    <div class="spacer_t2"></div>
    <div class="row bordel">
        <h4> TAREA </h4>
        {{tarea}}
    </div>
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <app:inputTextNG id="contrato" label="tareamantenimiento.contrato" value="tarea.contrato"
                               readonly="true"/>
            </div>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <app:inputTextNG id="tipificacion" label="tareamantenimiento.tipificacion" value="tarea.tipificacion"
                               readonly="true"/>
            </div>
        </div>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <app:inputTextNG id="direccion" label="tareamantenimiento.direccion" value="tarea.direccion"
                               readonly="true"/>
            </div>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <app:inputTextNG id="fechaEvento" label="tareamantenimiento.fechaEvento" value="tarea.fechaEvento"
                               readonly="true"/>
            </div>
        </div>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <app:inputTextNG id="ciudad" label="tareamantenimiento.ciudad" value="tarea.ciudad" readonly="true"/>
            </div>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <app:inputTextNG id="agenteAsignado" label="tareamantenimiento.agenteAsignado"
                               value="tarea.agenteAsignado" readonly="true"/>
            </div>
        </div>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <app:input id="cancelationTypeCombo" label="tareamantenimiento.opcionTipificacion">
                    <select data-ng-init="getCancelationType()" ng-model="tarea.opcionTipificacion" convert-to-number class="form-control"><!-- ng-model="model.id" convert-to-number -->
                        <option data-ng-repeat="k in cancelationTypeList" value="{{k.id}}" ng-selected="k.id==tarea.opcionTipificacion" >{{k.value}}</option>
                    </select>
                </app:input>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                </div>
                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <app:inputTextNG id="telefono1" label="tareamantenimiento.telefono1" value="tarea.telephone1"
                                   readonly="false"/>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <input type="button" class="btn btn-xs"
                           value="<spring:message code="tareamantenimiento.llamartelefono1"/>"/>
                </div>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                </div>
                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <app:inputTextNG id="telefono2" label="tareamantenimiento.telefono2" value="tarea.telephone2"
                                   readonly="false"/>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <input type="button" class="btn btn-xs"
                           value="<spring:message code="tareamantenimiento.llamartelefono2"/>"/>
                </div>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                </div>
                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <app:inputTextNG id="telefono3" label="tareamantenimiento.telefono3" value="tarea.telephone3"
                                   readonly="false"/>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                    <input type="button" class="btn btn-xs"
                           value="<spring:message code="tareamantenimiento.llamartelefono3"/>"/>
                </div>
            </div>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                </div>
                <div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
                    <app:textAreaNG id="decripcion" label="tareamantenimiento.descripcion" value="tarea.cancelationText"/>
                </div>
            </div>
        </div>
    </div>
    <div class="spacer_t1"></div>
    <div class="row">
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">

                <app:input id="desplegableKey1" label="tareamantenimiento.key1">
                        <!-- ng-model="model.id" convert-to-number -->
                        <!--<option data-ng-repeat="item in key1List" value="{{item.id}}">{{item.value}}</option>
                        <!-- data-ng-repeat.... realiza una iteración de key1List donde item es el valor en cada iteración
                          -- item in key1List define la iteración y el campo donde almacenar el ítem de cada iteración
                          -- value="{{item.id}}" obtiene del campo obtenido en la iteración el valor de id, poniéndolo en el value de un combo
                          -- ...{{item.value}}...  almacena en la <option/> el valor a mostrar.
                        -->
                        <!--<option data-ng-repeat="item in key1List" value="item.id" ng-selected="item.id=tarea.key1">{{item.value}}</option>-->
                    <select data-ng-init="getDesplegableKey1()" ng-model="tarea.key1" convert-to-number class="form-control"><!-- ng-model="model.id" convert-to-number -->
                        <option data-ng-repeat="k in key1List" value="{{k.id}}" ng-selected="k.id==tarea.key1" >{{k.value}}</option>
                    </select>
                </app:input>

                <%--<app:inputCombo id="desplegableKey1" label="tareamantenimiento.key1" value="${tarea.key1}" readonly="false"/>--%>
            </div>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
            <div class="row">
                <app:input id="desplegableKey2" label="tareamantenimiento.key2">
                    <select data-ng-init="getDesplegableKey1()" ng-model="tarea.key2" convert-to-number class="form-control"><!-- ng-model="model.id" convert-to-number -->
                        <option data-ng-repeat="k in key1List" value="{{k.id}}" ng-selected="k.id==tarea.key2" >{{k.value}}</option>
                    </select>
                    Provisionalmente el segundo combo lo rellenamos con lo mismo que el 1º sabiendo que tenemos que rellenarlo al seleccionar el 1º, llamando a otro servicio
                </app:input>
            </div>
        </div>
    </div>
</div>
<!-- End div angular -->


<script src="${pageContext.request.contextPath}/resources/app/component/maintenanceTask-ctrl.js"></script>