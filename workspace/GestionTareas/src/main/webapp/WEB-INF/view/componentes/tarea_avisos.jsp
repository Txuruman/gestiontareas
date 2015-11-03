<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div ng-controller="notificationtask" ng-init="tarea={};init()">
	<app:messageNoInstallation/>
    <div class="panel panel-default">
    <div class="panel-body">
        <div class="row">
            <app:inputTextNG id="ninstalacion" label="visortarea.ninstalacion"
                             value="tarea.numeroInstalacion" cells="6" cell_label="4" cell_input="8"
                             readonly="true"/>
            <app:inputTextNG id="panel" label="visortarea.panel" value="installationData.panel" cells="6"
                             readonly="true"/>
           
        </div>

        <div class="spacer_t1"></div>
        <div class="row">
             <app:inputTextNG id="titular" label="visortarea.titular" value="installationData.titular" cells="6"
                             readonly="true"/>
            <app:inputTextNG id="version" label="visortarea.version" value="installationData.version" cells="6"
                             readonly="true" />
        </div>

        <div class="spacer_t1"></div>
		<hr/>
       <div class="row">
            <app:inputTextNG id="aviso" label="tareaAviso.identificativoAvisoTarea" value="tarea.idAviso" cells="6" readonly="true"/>
            <app:inputDate id="fechaCreacion" label="tareaAviso.fechaCreacion" value="tarea.fechaCreacion"  cells="6" readonly="true"/>
       </div>
       <div class="spacer_t1"></div>
       <div class="row">
       		<app:inputTextNG id="requeridoPor" label="tareaAviso.requeridoPor" value="tarea.requeridoPor" cells="6" readonly="false"/>
       </div>
        <!-- Datos de la Instalacion  - End -->
    </div>
</div>

    <div class="panel panel-default">
        <div class="panel-body">
            <div class="spacer_t2"></div>
             <div class="row">
	            <app:inputTextNG id="personaContacto" label="visortarea.personacontacto"
	                             value="tarea.personaContacto" cells="6" required="true" form="formVisorTarea" avisoC="true"/>
	            <app:inputTextNG id="telefono" label="visortarea.telefono" value="tarea.telefonoAviso" cells="6"
	                              type="text"  only="true" required="true" form="formVisorTarea" avisoT="true"/>
	        </div>
            <div class="row">
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG pattern="^(?:(?:([01]?\d|2[0-3])))$" form="formVisorTarea" id="horarioDesde" label="tareaAviso.horarioDesde" value="tarea.horarioDesde" cells="6"  hour="true" size="2" only="true"/>
                <app:inputTextNG pattern="^(?:(?:([01]?\d|2[0-3])))$" form="formVisorTarea" id="horarioHasta" label="tareaAviso.horarioHasta" value="tarea.horarioHasta" cells="6" hour="true" size="2" only="true"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:input id="tipoAviso1" label="eti.visortarea.form.label.tipo" cells="6" required="true">
                    <select  ng-model="tarea.tipoAviso1" convert-to-number class="form-control" ng-change="refeshDisabled=false; getTypeReasonList1(tarea.tipoAviso1)">
                        <option data-ng-repeat="itemTipoAviso1 in tipoAvisoList" value="{{itemTipoAviso1.id}}" ng-selected="itemTipoAviso1.id==tarea.tipoAviso1">{{itemTipoAviso1.id +" - "+ itemTipoAviso1.value}}</option>
                    </select>
                    <span class="error" ng-show="formVisorTarea.tipoAviso1.$error.required"><spring:message code="error.notext"/></span>
                </app:input>
                <app:input id="motivo1" label="eti.visortarea.form.label.reason" cells="6" required="true">
                    <select  ng-model="tarea.motivo1" convert-to-number class="form-control" ng-change="refeshDisabled=false; getClosingList()">
                        <option data-ng-repeat="itemMotivo1 in motivoList1" value="{{itemMotivo1.id}}" ng-selected="itemMotivo1.id==tarea.motivo1" >{{itemMotivo1.id +" - "+ itemMotivo1.value}}</option>
                    </select>
                    <span class="error" ng-show="errorMotivo1 || formVisorTarea.motivo1.$error.required"><spring:message code="error.notext"/></span>
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
                    <select  ng-model="tarea.motivo2" convert-to-number class="form-control" ng-change="refeshDisabled=false; getClosingList()">
                        <option data-ng-repeat="itemMotivo2 in motivoList2" value="{{itemMotivo2.id}}" ng-selected="itemMotivo2.id==tarea.motivo2" >{{itemMotivo2.id +" - "+ itemMotivo2.value}}</option>
                    </select>
                    <span class="error" ng-show="errorMotivo2"><spring:message code="error.notext"/></span>
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
                    <select  ng-model="tarea.motivo3" convert-to-number class="form-control" ng-change="refeshDisabled=false; getClosingList()">
                        <option data-ng-repeat="k in motivoList3" value="{{k.id}}" ng-selected="k.id==tarea.motivo3" >{{k.id +" - "+ k.value}}</option>
                    </select>
                    <span class="error" ng-show="errorMotivo3"><spring:message code="error.notext"/></span>
                </app:input>
            </div>
            <!-- row -->
            <div class="spacer_t1"></div>
            <div class="row">
            	<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
	                <app:input id="closing" label="notificationtask.closing" cells="0" readonly="false" required="true">
	                    <select ng-model="tarea.closing" convert-to-number class="form-control marginBottom5" ng-change="refeshDisabled=false; closingAlert=false;">
	                        <option data-ng-repeat="k in closingList" value="{{k.id}}" ng-selected="k.id==tarea.closing" title="{{k.id +' - '+ k.value}}">{{k.id +" - "+ k.value}}</option>
	                    </select>
	                    <span class="error" ng-show="closingAlert"><spring:message code="error.notext"/></span>
	                </app:input>
	                <app:input id="closingAdditionalData" label="notificationTask.closingAdditionalData" cells="0" readonly="false">
	                    <select ng-model="tarea.datosAdicionalesCierre" convert-to-number class="form-control" ng-change="refeshDisabled=false; closingADAlert=false;">
	                        <option data-ng-repeat="k in datosAdicionalesList" value="{{k.id}}" ng-selected="k.id==tarea.datosAdicionalesCierre">{{k.id +" - "+ k.value}}</option>
	                    </select>
<%-- 	                    <span class="error" ng-show="closingADAlert"><spring:message code="error.notext"/></span> --%>
	                </app:input>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                	<app:textAreaNG form="formVisorTarea" id="observaciones" label="eti.visortarea.tareaavisos.form.label.observaciones" value="tarea.observaciones" cell_label="4" cell_input="8"/>
                </div>
                <!-- subrow -->
                <div class="spacer_t1"></div>
            </div>
            <div class="spacer_t1"></div>
            <div class="spacer_t2"></div>
        </div>
    </div>

    <!-- Botones Tarea Aviso -->
    <jsp:include page="btn_avisos.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/resources/app/component/notificationTask-ctrl.js"></script>



<!-- ANGULARJS NOTIFICATION TASK CONTROLLER END -->

