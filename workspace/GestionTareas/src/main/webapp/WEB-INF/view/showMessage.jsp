<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="showmessage.titulo"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/custom.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.js"></script>
</head>
<body>

<!-- Angular JS Scripts -->
<script src="${pageContext.request.contextPath}/resources/app/gestiontarea-app.js"></script>
<script src="${pageContext.request.contextPath}/resources/app/maincontrollers/showMessage-ctrl.js"></script>

<div class="container" ng-controller="showMessage">
    <app:messages/>
    <div class="row">

        <div class="col-lg-12 center">
            <h2>Tarea avisos</h2>

        </div>
        <div class="col-lg-12 center">
            <ul class="nav nav-tabs nav-justified">
                <li class="active"><a href="#">Tarea</a></li>
                <li><a href="#">Buscar</a></li>
                <li><a href="#">Crear</a></li>
            </ul>
        </div>
    </div>

    <form action="#" method="post" class="form-horizontal" role="form">
        <br> <br>


        <div class="form-group">
            <label for="ejemplo_email_3" class="col-lg-2 col-md-2 control-label">Email</label>

            <div class="col-lg-4 col-md-4 col-sm-4">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>
            <label for="ejemplo_email_3" class="col-lg-2 col-md-2 control-label">Email</label>

            <div class="col-lg-4 col-md-4 col-sm-4">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>

        </div>

        <div class="form-group">
            <label for="ejemplo_email_3" class="col-lg-2 col-md-2 control-label">Email</label>

            <div class="col-lg-4 col-md-4 col-sm-4">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>
            <label for="ejemplo_email_3"
                   class="col-lg-2  col-md-2 control-label">Email</label>

            <div class="col-lg-4 col-md-4 col-sm-4">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>
        </div>
        <div class="form-group">
            <label for="ejemplo_email_3" class="col-lg-2 col-md-2 control-label">Email</label>

            <div class="col-lg-4 col-md-4 col-sm-4">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>
        </div>
        <div class="form-group">
            <label for="ejemplo_email_3" class="col-lg-2 col-md-2 control-label">Email</label>

            <div class="col-lg-4 col-md-4 col-sm-4">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>
        </div>
        <div class="form-group">
            <label for="ejemplo_email_3" class="col-lg-2 col-md-2 control-label">Email</label>

            <div class="col-lg-4 col-md-4 col-sm-4">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>
            <label for="ejemplo_email_3" class="col-lg-1 col-md-1 control-label">Email</label>

            <div class="col-lg-2 col-md-4 col-sm-4">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>
            <label for="ejemplo_email_3" class="col-lg-1 col-md-1 control-label">Email</label>

            <div class="col-lg-2 col-md-2 col-sm-2">
                <input type="email" class="form-control" id="ejemplo_email_3"
                       placeholder="Email">
            </div>
        </div>


        <!--  		<div class="col-lg-3">
					<lablel> <spring:message code="eti.form.label.1" /></lablel>
					<input type="text" name="instalacion" class="form-control" />
				</div> -->


    </form>
</div><!-- ANGULARSJ CONTROLLER DIV END -->
<!-- Container -->

</body>
</html>