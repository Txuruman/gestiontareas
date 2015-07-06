<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row" ng-controller="excelcommon" >
    <app:input id="closingReason" label="excelcommonfields.closingreason"  cells="6" readonly="false" >
        <select data-ng-init="getClosingReason()" ng-model="closingReason" ng-value="${tarea.closingReason}" class="form-control" ><!-- ng-model="model.id" convert-to-number -->
            <!--<option data-ng-repeat="item in key1List" value="{{item.id}}">{{item.value}}</option>
            <!-- data-ng-repeat.... realiza una iteración de key1List donde item es el valor en cada iteración
              -- item in key1List define la iteración y el campo donde almacenar el ítem de cada iteración
              -- value="{{item.id}}" obtiene del campo obtenido en la iteración el valor de id, poniéndolo en el value de un combo
              -- ...{{item.value}}...  almacena en la <option/> el valor a mostrar.
            -->
            <option data-ng-repeat="closingReason in closingReasonList" value="{{closingReason.id}}">{{closingReason.value}}</option>
        </select>
    </app:input>

    <app:inputText id="compensation" label="excelcommonfields.compensation" value="${tarea.compensation}" cells="6" readonly="false"/>



</div>
<div class="spacer_t1"></div>

<script src="${pageContext.request.contextPath}/resources/app/component/exceltask/excelCommon-ctrl.js"></script>
<!--
closingReason=null,
compensation='null'
-->