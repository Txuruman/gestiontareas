<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="row">
    <div class="spacer_t2"></div>
    <app:input id="closingReason" label="excelcommonfields.closingreason" cells="6" >
        <select ng-model="tarea.closingReason" convert-to-number class="form-control" name="closingReason" ng-required="true">
            <!-- ng-model="model.id" convert-to-number -->
            <option data-ng-repeat="k in closingReasonList" value="{{k.id}}" ng-selected="k.id==tarea.closingReason">
                {{k.value}}
            </option>
        </select>
        <span class="error" ng-show="formVisorTarea.closingReason.$error.required"><spring:message code="closingReason.error"/></span>
    </app:input>
    <app:inputTextNG id="compensation" label="excelcommonfields.compensation" value="tarea.compensation" cells="6" required="true"/>
</div>
<div class="spacer_t1"></div>
