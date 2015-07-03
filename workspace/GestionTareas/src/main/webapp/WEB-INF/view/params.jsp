<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!-- PARA PRUEBAS DE CHECKEO DE PARAMETROS DE ENTRADA -->
<html>
<head>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/custom.css"/>
</head>
<body>


<div class="container">

    Parametros: ${todosparametros}

</div>


</body>
</html>