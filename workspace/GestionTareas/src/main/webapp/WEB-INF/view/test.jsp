<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html data-ng-app="myApp">
<head>
    <title><spring:message code="titulo.BuscarTarea"/></title>

    <app:commonImports/>

    <script src="${pageContext.request.contextPath}/resources/app/maincontrollers/taskViewer-ctrl.js"></script>

    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/custom.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/ui-bootstrap-tpls-0.13.0.js"/>--%>

    <%--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.js"></script>--%>
    <%--<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.6.0/moment.min.js"></script>--%>


</head>


<body>


<!-- Acordeon -start -->
<div ng-controller="AccordionDemoCtrl">
    <p>
        <button class="btn btn-default btn-sm" ng-click="status.open = !status.open">Toggle last panel</button>
        <button class="btn btn-default btn-sm" ng-click="status.isFirstDisabled = ! status.isFirstDisabled">Enable / Disable first panel</button>
    </p>

    <label class="checkbox">
        <input type="checkbox" ng-model="oneAtATime">
        Open only one at a time
    </label>
    <accordion close-others="oneAtATime">
        <accordion-group heading="Static Header, initially expanded" is-open="status.isFirstOpen" is-disabled="status.isFirstDisabled">
            This content is straight in the template.
        </accordion-group>
        <accordion-group heading="{{group.title}}" ng-repeat="group in groups">
            {{group.content}}
        </accordion-group>
        <accordion-group heading="Dynamic Body Content">
            <p>The body of the accordion group grows to fit the contents</p>
            <button class="btn btn-default btn-sm" ng-click="addItem()">Add Item</button>
            <div ng-repeat="item in items">{{item}}</div>
        </accordion-group>
        <accordion-group is-open="status.open">
            <accordion-heading>
                I can have markup, too! <i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': status.open, 'glyphicon-chevron-right': !status.open}"></i>
            </accordion-heading>
            This is just some content to illustrate fancy headings.
        </accordion-group>
    </accordion>
</div>
<!-- Acordeon -end -->


<!-- Date -->
<div ng-controller="DatepickerDemoCtrl">

    <div class="row">
        <div class="col-md-6">
            <p class="input-group">
                <input type="text" class="form-control" datepicker-popup="{{format}}" ng-model="dt" is-open="opened" min-date="minDate" max-date="'2015-06-22'" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"/>
              <span class="input-group-btn">
                <button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
              </span>
            </p>
        </div>

        <div class="col-md-6">
            <p class="input-group">
                <input type="date" class="form-control" datepicker-popup ng-model="dt" is-open="opened" min-date="minDate" max-date="'2015-06-22'" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"/>
              <span class="input-group-btn">
                <button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
              </span>
            </p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <p class="input-group">
                <input type="date" class="form-control" datepicker-popup ng-model="dt"
                       ng-required="true"
                       />
              <%--<span class="input-group-btn">--%>
                <%--<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>--%>
              <%--</span>--%>
            </p>
        </div>

        <div class="col-md-6">

        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <label>Format:</label> <select class="form-control" ng-model="format" ng-options="f for f in formats">
            <option></option>
        </select>
        </div>
    </div>
</div>
<!-- Date -->


<div class="container" ng-controller="taskviewer-ctrl">


    <h2>Pagina de pruebas</h2>

    Total Data : {{allData}}<br/>
    Server Messages : {{serverMessages}}<br/>
    SmisMensajes : {{misMensajes}}<br/>
    CommonService Messages : {{$CommonService.serverMessages}}<br/>
    Root Messages : {{$rootScope.serverMessages}}<br/>


    <app:messages/>

    <app:inputButtonNG value="boton.Finalizar" button_type="primary" ng_click="getMessages()" fluid_wrapper="true"/>


    <%--<h3>Spring MVC AngularJS JSON Drop Down sample JNA </h3>--%>

    <%--<table style="margin: 0px auto;" align="left">--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<div data-ng-init="getPersonDataFromServer()">--%>
    <%--<b>Person Data:</b> <select id="personData">--%>
    <%--<option value="">-- Select Persons --</option>--%>
    <%--<option data-ng-repeat="personData in personDatas" value="{{personData.personId}}">{{personData.personName}}</option>--%>
    <%--</select><br>--%>
    <%--</div>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</table>--%>
    <%--<br/>--%>
    <%--getDesplegableKey1:{{key1}}--%>
    <%--<br/>--%>

    <%--<div data-ng-init="getDesplegableKey1()">--%>
    <%--<select ng-model="key1value"><!-- ng-model="model.id" convert-to-number -->--%>
    <%--<option data-ng-repeat="k in key1" value="{{k.id}}">{{k.value}}</option>--%>
    <%--</select>--%>
    <%--</div>--%>
    <%--<br/>--%>
    <%--value:{{key1value}}--%>
    <%--<br/>--%>
    <%--<br/>--%>
    <%--<br/>--%>

    <%--<form name="myForm" onsubmit="return false;">--%>

    <%--<div>--%>
    <%--<input type="text" placeholder="First name" name="firstName" ng-model="firstName" required="true"/>--%>
    <%--<span ng-show="myForm.firstName.$dirty && myForm.firstName.$error.required">You must enter a value here</span>--%>
    <%--<span ng-show="myForm.firstName.$error.serverMessage">{{myForm.firstName.$error.serverMessage}}</span>--%>
    <%--</div>--%>
    <%--<div>--%>
    <%--<input type="text" placeholder="Last name" name="lastName" ng-model="lastName"/>--%>
    <%--<span ng-show="myForm.lastName.$error.serverMessage">{{myForm.lastName.$error.serverMessage}}</span>--%>
    <%--</div>--%>
    <%--<button ng-click="submit()">Submit</button>--%>
    <%--</form>--%>

</div>




</body>

</html>