<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" import="java.io.*" pageEncoding="UTF-8" %>


<html>
<head>
    <title><spring:message code="titulo.error"/></title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/custom.css"/>
</head>


<body>
<div class="container">

    <!-- Titulo-->
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
            <h2><spring:message code="titulo.error"/></h2>
        </div>
    </div>

    <div class="spacer_t2"></div>

    <!-- Message -->
    <div class="row">
        <%=exception.getMessage()%>
    </div>

    <div class="spacer_t2"></div>

    <!-- Stacktrace -->
    <div class="row">
        StackTrace:
        <%
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            exception.printStackTrace(printWriter);
            out.println(stringWriter);
            printWriter.close();
            stringWriter.close();
        %>
    </div>
</div>

</body>
</html>