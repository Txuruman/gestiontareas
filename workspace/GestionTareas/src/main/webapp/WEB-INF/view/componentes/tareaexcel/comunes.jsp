<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="row">
    <app:input id="closingReason" label="excelcommonfields.closingreason" cells="6" readonly="false">
        <select ng-model="tarea.closingReason" convert-to-number class="form-control">
            <!-- ng-model="model.id" convert-to-number -->
            <option data-ng-repeat="k in closingReasonList" value="{{k.id}}" ng-selected="k.id==tarea.closingReason">
                {{k.value}}
            </option>
        </select>
    </app:input>
    <app:inputTextNG id="compensation" label="excelcommonfields.compensation" value="tarea.compensation" cells="6" readonly="false"/>
</div>
<div class="spacer_t1"></div>
