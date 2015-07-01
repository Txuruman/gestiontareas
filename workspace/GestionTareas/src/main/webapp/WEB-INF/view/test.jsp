<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head lang="en">
    <meta charset="utf-8">
    <title>AngularJS Drop Down Spring MVC JSON</title>
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