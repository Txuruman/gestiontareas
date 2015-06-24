<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html ng-app ">
<head>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/custom.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.23/angular.min.js"></script>
</head>
<body>

<div class="spacer_t3"></div>
<div class="container">
    <div class="spacer_t3"></div>
    <form action="test.htm" method="get">
        ¿Cómo te llamas? <input type="text" name="elnombre"  ng-init="nombre='${elnombre}'">

        <select name="despletest" class="">

            <option value="">valor1</option>

        </select>

        <input type="submit" class="bt- btn-default" value="click"/>
    </form>


    <h1>Hola {{${elnombre}}}</h1>

</div>

${todosparametros}
</body>
</html>