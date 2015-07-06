<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <app:inputText id="aviso" label="tareaAviso.identificativoAvisoTarea" value="${tarea.identificativoAvisoTarea}" cells="6" readonly="false"/>

        <app:inputText id="fechaCreacion" label="tareaAviso.fechaCreacion" value="${tarea.fechaCreacion}" cells="6" readonly="false"/>
    </div>
    <div class="spacer_t1"></div>

    <div class="row">
        <app:inputText id="requeridoPor" label="tareaAviso.requeridoPor" value="${tarea.requeridoPor}" cells="6" readonly="false"/>

        <app:inputText id="horarioDesde" label="tareaAviso.horarioDesde" value="${tarea.horarioDesde}" cells="3" readonly="false"/>
        <app:inputText id="horarioHasta" label="tareaAviso.horarioHasta" value="${tarea.horarioHasta}" cells="3" readonly="false"/>
    </div>
    <div class="spacer_t1"></div>

    <div class="row">
        <app:inputCombo id="tipoAviso1" label="eti.visortarea.form.label.tipo" value="${tarea.tipoAviso1}" cells="6" readonly="false"/>
        <app:inputCombo id="motivo1" label="eti.visortarea.form.label.reason" value="${tarea.motivo1}" cells="6" readonly="false"/>
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
    <div class="row"  >

        <app:textArea id="observaciones" label="eti.visortarea.tareaavisos.form.label.observaciones" value="${tarea.motivo3}" cells="6"/>

        <app:input id="closing" label="notificationtask.closing"  cells="6" readonly="false" >
            <select data-ng-init="getClosing()" ng-model="closing" ng-value="${tarea.closing}" class="form-control" ><!-- ng-model="model.id" convert-to-number -->
                <!--<option data-ng-repeat="item in key1List" value="{{item.id}}">{{item.value}}</option>
                <!-- data-ng-repeat.... realiza una iteración de key1List donde item es el valor en cada iteración
                  -- item in key1List define la iteración y el campo donde almacenar el ítem de cada iteración
                  -- value="{{item.id}}" obtiene del campo obtenido en la iteración el valor de id, poniéndolo en el value de un combo
                  -- ...{{item.value}}...  almacena en la <option/> el valor a mostrar.
                -->
                <option data-ng-repeat="closing in closingList" value="{{closing.id}}">{{closingReason.value}}</option>
            </select>
        </app:input>

        <div class="spacer_t1"></div>
        <div class="spacer_t1"></div>
        <div class="spacer_t1"></div>
        <div class="spacer_t1"></div>
        <div class="spacer_t1"></div>
        <div class="spacer_t1"></div>
        <app:inputCombo id="adicionalesCierre" label="eti.visortarea.tareaavisos.form.label.adicionalesCierre" value="${tarea.motivo3}" cells="6" readonly="false"/>
            <!-- subrow -->

        <div class="spacer_t1"></div>


    </div>
    <div class="spacer_t2"></div>
</div>

<script src="${pageContext.request.contextPath}/resources/app/component/notificationTask-ctrl.js" />


