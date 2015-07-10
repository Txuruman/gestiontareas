<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html data-ng-app="myApp">
<head>
    <title><spring:message code="titulo.BuscarTarea"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/custom.css"/>

    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular-messages/angular-messages.js"></script>
    <script src="${pageContext.request.contextPath}/resources/app/common.js"></script>
    <script src="${pageContext.request.contextPath}/resources/app/gestiontarea-app.js"></script>
    <script src="${pageContext.request.contextPath}/resources/app/maincontrollers/taskViewer-ctrl.js"></script>

    <script type="text/javascript">

        var app = angular.module('myApp', ['common']);
        app.controller('MyController', function ($scope, $http, $parse) {

            $scope.getPersonDataFromServer = function () {

                $http({method: 'GET', url: 'test/populatePersonDataFromServer.web'}).
                        success(function (data, status, headers, config) {
                            $scope.personDatas = data;
                        }).
                        error(function (data, status, headers, config) {
                            // called asynchronously if an error occurs
                            // or server returns response with an error status.
                        });
            };

            $scope.getDesplegableKey1 = function () {
                $http({method: 'GET', url: 'visortarea/getDesplegableKey1'}).
                        success(function (data, status, headers, config) {
                            $scope.key1 = data;
                        }).
                        error(function (data, status, headers, config) {
                            // called asynchronously if an error occurs
                            // or server returns response with an error status.
                        });
            };


            var pretendThisIsOnTheServerAndCalledViaAjax = function(){
                var fieldState = {firstName: 'VALID', lastName: 'VALID'};
                var allowedNames = ['Bob', 'Jill', 'Murray', 'Sally'];

                if (allowedNames.indexOf($scope.firstName) == -1) fieldState.firstName = 'Allowed values are: ' + allowedNames.join(',');
                if ($scope.lastName == $scope.firstName) fieldState.lastName = 'Your last name must be different from your first name';

                return fieldState;
            };



            var realCallAjax = function() {
                $http({method: 'GET', url: 'test/message'}).
                        success(function (data, status, headers, config) {
                            processBaseResponse(data,status,headers,config);
                        }).
                        error(function (data, status, headers, config) {
                            processBaseResponse(data,status,headers,config);
                        });
            };



            $scope.submit = function(){
                var serverResponse = pretendThisIsOnTheServerAndCalledViaAjax();
                var realAjax = realCallAjax();
                console.log("result:" + realAjax);

            };

        }); //End contoller

    </script>


</head>


<body data-ng-controller="MyController">
Real : {{serverMessages}}


<app:messages/>




<h3>Spring MVC AngularJS JSON Drop Down sample JNA </h3>

<table style="margin: 0px auto;" align="left">
    <tr>
        <td>
            <div data-ng-init="getPersonDataFromServer()">
                <b>Person Data:</b> <select id="personData">
                <option value="">-- Select Persons --</option>
                <option data-ng-repeat="personData in personDatas" value="{{personData.personId}}">{{personData.personName}}</option>
            </select><br>
            </div>
        </td>
    </tr>
</table>
<br/>
getDesplegableKey1:{{key1}}
<br/>

<div data-ng-init="getDesplegableKey1()">
    <select ng-model="key1value"><!-- ng-model="model.id" convert-to-number -->
        <option data-ng-repeat="k in key1" value="{{k.id}}">{{k.value}}</option>
    </select>
</div>
<br/>
value:{{key1value}}
<br/>
<br/>
<br/>

<form name="myForm" onsubmit="return false;">

    <div>
        <input type="text" placeholder="First name" name="firstName" ng-model="firstName" required="true"/>
        <span ng-show="myForm.firstName.$dirty && myForm.firstName.$error.required">You must enter a value here</span>
        <span ng-show="myForm.firstName.$error.serverMessage">{{myForm.firstName.$error.serverMessage}}</span>
    </div>
    <div>
        <input type="text" placeholder="Last name" name="lastName" ng-model="lastName"/>
        <span ng-show="myForm.lastName.$error.serverMessage">{{myForm.lastName.$error.serverMessage}}</span>
    </div>
    <button ng-click="submit()">Submit</button>
</form>

</body>

</html>