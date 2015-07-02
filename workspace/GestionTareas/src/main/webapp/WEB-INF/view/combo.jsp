<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html data-ng-app="myApp">
<head>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/custom.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.23/angular.min.js"></script>


    <script type="text/javascript">

        var app = angular.module('myApp', []);
        app.controller('MyController', function($scope, $http) {

            $scope.getPersonDataFromServer = function() {

                $http({method: 'GET', url: 'populatePersonDataFromServer.web'}).
                        success(function(data, status, headers, config) {
                            $scope.personDatas = data;
                        }).
                        error(function(data, status, headers, config) {
                            // called asynchronously if an error occurs
                            // or server returns response with an error status.
                        });
            };
        });

    </script>

</head>



<body data-ng-controller="MyController">
<h3>Spring MVC AngularJS JSON Drop Down sample</h3>
aaaaa
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

</body>


<%--<body>--%>

<%--<div class="spacer_t3"></div>--%>
<%--<div class="container">--%>
<%--<div class="spacer_t3"></div>--%>
<%--<form action="test.htm" method="get">--%>
<%--¿Cómo te llamas? <input type="text" name="elnombre"  ng-init="nombre='${elnombre}'">--%>

<%--<select name="despletest" class="">--%>

<%--<option value="">valor1</option>--%>

<%--</select>--%>

<%--<input type="submit" class="bt- btn-default" value="click"/>--%>
<%--</form>--%>


<%--<h1>Hola {{${elnombre}}}</h1>--%>

<%--</div>--%>


<%--<div ng-app="myApp" ng-controller="myCtrl">--%>

<%--First Name: <input type="text" ng-model="firstName"><br>--%>
<%--Last Name: <input type="text" ng-model="lastName"><br>--%>
<%--<br>--%>
<%--Full Name: {{firstName + " " + lastName}}--%>

<%--</div>--%>

<%--<script>--%>
<%--var app = angular.module('myApp', []);--%>
<%--app.controller('myCtrl', function($scope) {--%>
<%--$scope.firstName= "John";--%>
<%--$scope.lastName= "Doe";--%>
<%--});--%>
<%--</script>--%>

<%--${todosparametros}--%>
<%--</body>--%>
</html>