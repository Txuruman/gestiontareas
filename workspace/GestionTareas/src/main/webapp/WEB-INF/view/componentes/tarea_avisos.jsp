<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div ng-controller="notificationtask" ng-init="init()">
    <jsp:include page="instalacion.jsp"/>

    <div class="panel panel-default">
        <div class="panel-body">
            <div class="spacer_t2"></div>
            <div class="row">
                <app:inputTextNG id="aviso" label="tareaAviso.identificativoAvisoTarea" value="tarea.idAviso" cells="6" readonly="false"/>
                <app:inputDate id="fechaCreacion" label="tareaAviso.fechaCreacion" value="tarea.fechaCreacion"  cells="6" readonly="false"/>
            </div>
            <div class="row">
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="requeridoPor" label="tareaAviso.requeridoPor" value="tarea.requeridoPor" cells="6" readonly="false"/>
                <app:inputTextNG id="horarioDesde" label="tareaAviso.horarioDesde" value="tarea.horarioDesde" cells="3" readonly="false"/>
                <app:inputTextNG id="horarioHasta" label="tareaAviso.horarioHasta" value="tarea.horarioHasta" cells="3" readonly="false"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:input id="tipoAviso1" label="eti.visortarea.form.label.tipo" cells="6">
                    <select  ng-model="tarea.tipoAviso1" convert-to-number class="form-control">
                        <option data-ng-repeat="itemTipoAviso1 in tipoAvisoList" value="{{itemTipoAviso1.id}}" ng-selected="itemTipoAviso1.id==tarea.tipoAviso1" >{{itemTipoAviso1.value}}</option>
                    </select>
                </app:input>
                <app:input id="motivo1" label="eti.visortarea.form.label.reason" cells="6">
                    <select  ng-model="tarea.motivo1" convert-to-number class="form-control">
                        <option data-ng-repeat="itemMotivo1 in motivoList1" value="{{itemMotivo1.id}}" ng-selected="itemMotivo1.id==tarea.motivo1" >{{itemMotivo1.value}}</option>
                    </select>
                </app:input>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:input id="tipoAviso2" cells="6">
                    <select  ng-model="tarea.tipoAviso2" convert-to-number class="form-control">
                        <option data-ng-repeat="itemTipoAviso2 in tipoAvisoList" value="{{itemTipoAviso2.id}}" ng-selected="itemTipoAviso2.id==tarea.tipoAviso2" >{{itemTipoAviso2.value}}</option>
                    </select>
                </app:input>
                <app:input id="motivo2" cells="6">
                    <select  ng-model="tarea.motivo2" convert-to-number class="form-control">
                        <option data-ng-repeat="itemMotivo2 in motivoList2" value="{{itemMotivo2.id}}" ng-selected="itemMotivo2.id==tarea.motivo2" >{{itemMotivo2.value}}</option>
                    </select>
                </app:input>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:input id="tipoAviso3" cells="6">
                    <select  ng-model="tarea.tipoAviso3" convert-to-number class="form-control">
                        <option data-ng-repeat="k in tipoAvisoList" value="{{k.id}}" ng-selected="k.id==tarea.tipoAviso3" >{{k.value}}</option>
                    </select>
                </app:input>
                <app:input id="motivo3" cells="6">
                    <select  ng-model="tarea.motivo3" convert-to-number class="form-control">
                        <option data-ng-repeat="k in motivoList3" value="{{k.id}}" ng-selected="k.id==tarea.motivo3" >{{k.value}}</option>
                    </select>
                </app:input>
            </div>
            <!-- row -->
            <div class="spacer_t1"></div>
            <div class="row">
                <app:input id="closing" label="notificationtask.closing" cells="6" readonly="false">
                    <select ng-model="tarea.closing" convert-to-number class="form-control">
                        <option data-ng-repeat="k in closingList" value="{{k.id}}" ng-selected="k.id==tarea.closing">{{k.value}}</option>
                    </select>
                </app:input>
                <app:input id="closing" label="notificationTask.closingAdditionalData" cells="6" readonly="false">
                    <select ng-model="tarea.closing" convert-to-number class="form-control">
                        <option data-ng-repeat="k in datosAdicionalesList" value="{{k.id}}" ng-selected="k.id==tarea.datosAdicionalesCierre">{{k.value}}</option>
                    </select>
                </app:input>
                <!-- subrow -->
                <div class="spacer_t1"></div>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:textAreaNG id="observaciones" label="eti.visortarea.tareaavisos.form.label.observaciones" value="tarea.observaciones" cell_label="2" cell_input="10"/>
            </div>
            <div class="spacer_t2"></div>
        </div>
    </div>

    <div>
        Tipo Aviso1: {{tarea.tipoAviso1}}
        Motivo Aviso1: {{tarea.motivo1}}
    </div>

    <!-- Botones Tarea Aviso -->
    <jsp:include page="btn_avisos.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/resources/app/component/notificationTask-ctrl.js"></script>



<!-- ANGULARJS NOTIFICATION TASK CONTROLLER END -->

