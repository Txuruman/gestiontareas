<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div ng-controller="notificationtask" ng-init="getTarea()">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="spacer_t2"></div>
            <div class="row">
                <app:inputTextNG id="aviso" label="tareaAviso.identificativoAvisoTarea" value="tarea.idAviso" cells="6" readonly="false"/>
                <app:inputTextNG id="fechaCreacion" label="tareaAviso.fechaCreacion" value="tarea.fechaCreacion" cells="6" readonly="false"/>
               <!-- <input type="date" data-ng-model="tarea.fechaCreacion" json-date/> -->
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputTextNG id="requeridoPor" label="tareaAviso.requeridoPor" value="tarea.requeridoPor" cells="6" readonly="false"/>
                <app:inputTextNG id="horarioDesde" label="tareaAviso.horarioDesde" value="tarea.horarioDesde" cells="3" readonly="false"/>
                <app:inputTextNG id="horarioHasta" label="tareaAviso.horarioHasta" value="tarea.horarioHasta" cells="3" readonly="false"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputCombo id="tipoAviso1" label="eti.visortarea.form.label.tipo" value="${tarea.tipoAviso1}" cells="6"
                                readonly="false"/>
                <app:inputCombo id="motivo1" label="eti.visortarea.form.label.reason" value="${tarea.motivo1}" cells="6"
                                readonly="false"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputCombo id="tipoAviso2" value="${tarea.tipoAviso2}" cells="6" readonly="false"/>
                <app:inputCombo id="motivo2" value="${tarea.motivo2}" cells="6" readonly="false"/>
            </div>
            <div class="spacer_t1"></div>
            <div class="row">
                <app:inputCombo id="tipoAviso3" value="${tarea.tipoAviso3}" cells="6" readonly="false"/>
                <app:inputCombo id="motivo3" value="${tarea.motivo3}" cells="6" readonly="false"/>
            </div>
            <!-- row -->
            <div class="spacer_t1"></div>
            <div class="row">
                <app:input id="closing" label="notificationtask.closing" cells="6" readonly="false">
                    <select data-ng-init="getClosing()" ng-model="tarea.closing" convert-to-number class="form-control">
                        <!-- ng-model="model.id" convert-to-number -->
                        <option data-ng-repeat="k in closingList" value="{{k.id}}" ng-selected="k.id==tarea.closing">
                            {{k.value}}
                        </option>
                    </select>
                </app:input>
                <app:inputCombo id="adicionalesCierre" label="eti.visortarea.tareaavisos.form.label.adicionalesCierre"
                                value="${tarea.motivo3}" cells="6" readonly="false"/>
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
    <jsp:include page="btn_avisos.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/resources/app/component/notificationTask-ctrl.js"></script>



    <!-- ANGULARJS NOTIFICATION TASK CONTROLLER END -->

