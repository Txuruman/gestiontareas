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

    <script type="text/javascript">

        var app = angular.module('myApp', []);
        app.controller('MyController', function ($scope, $http) {

            $scope.getPersonDataFromServer = function () {

                $http({method: 'GET', url: 'populatePersonDataFromServer.web'}).
                        success(function (data, status, headers, config) {
                            $scope.personDatas = data;
                        }).
                        error(function (data, status, headers, config) {
                            // called asynchronously if an error occurs
                            // or server returns response with an error status.
                        });
            };
        });

    </script>


</head>


<body data-ng-controller="MyController">
<h3>Spring MVC AngularJS JSON Drop Down sample JNA aaa </h3>

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

</html>