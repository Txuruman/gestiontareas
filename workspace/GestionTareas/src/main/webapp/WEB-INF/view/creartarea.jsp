<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><spring:message code="titulo.creartarea"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/custom.css" />
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
</head>
<body>

	<div class="container">
		<div class="row">
			<jsp:include page="bloques/tabs1.jsp" />
		</div>
		<div class="spacer_t2"></div>

		<form class="form-horizontal" role="form" >
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="row">
                        <app:wrapping cells="6">
                            <app:inputTextNG id="installationnumber" label="createtask.installationnumber" value="createTaskModel.installationNumber" readonly="false" cells="9" />
                            <app:inputButtonNG button_type="xs" value="createtask.button.searchinstallation" ng_click="createTaskSearchInstallation()" cells="3" />
                        </app:wrapping>
                        <app:inputTextNG id="holder" label="createtask.holder" value="createTaskModel.holder" readonly="false" cells="6"/>
                    </div>

                    <div class="spacer_t1"></div>
                    <div class="row">
                        <app:inputCombo id="telephone" value="createTaskModel.telephone" label="createtask.telephone" cells="6" />
                        <app:inputTextNG id="panel" value="createTaskModel.panel" label="createtask.panel" cells="6" />
                    </div>
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <app:inputTextNG id="contact" value="createTaskModel.contact" label="createtask.contact" cells="6" />
                        <app:inputTextNG id="contact" value="createTaskModel.version" label="createtask.version" cells="6" />
                    </div>
                    <!-- row -->
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <app:inputTextNG id="requiredBy" value="createTaskModel.requiredBy" label="createtask.requiredby" cells="6" />
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <app:inputTextNG id="hourfrom" value="createTaskModel.hourFrom" cells="6" label="createtask.hour.from" />
                            <app:inputTextNG id="hourfrom" value="createTaskModel.hourTo" cells="6" label="createtask.hour.to" />
                        </div>
                    </div>
                    <!-- row -->
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <app:inputCombo id="type1" value="createTaskModel.type1" label="createtask.type" cells="6" />
                        <app:inputCombo id="reason1" value="createTaskModel.reason1" label="createtask.reason" cells="6" />
                    </div>
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <app:inputCombo id="type2" value="createTaskModel.type2"  cells="6" />
                        <app:inputCombo id="reason2" value="createTaskModel.reason2" cells="6" />
                    </div>
                    <div class="spacer_t1"></div>
                    <div class="row">
                        <app:inputCombo id="type3" value="createTaskModel.type3"  cells="6" />
                        <app:inputCombo id="reason3" value="createTaskModel.reason3" cells="6" />
                    </div>
                    <div class="spacer_t1"></div>
                    <!-- row -->
                    <app:textAreaNG id="comment" value="createTaskModel.comment" label="createtask.comment" cell_label="2" cell_input="10"/>
                </div>
            </div>
			<!-- row -->
            <div class="spacer_t1"></div>
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="row" align="right">
                        <div class="container-fluid">
                            <app:inputButtonNG value="boton.CrearMantenimiento" button_type="default" ng_click="crearTareaCrearMantenimiento()" fluid_wrapper="true" />
                            <app:inputButtonNG button_type="primary" value="boton.Crear" ng_click="crearTareaCrear()" fluid_wrapper="true"  />
                        </div>
                    </div>
                </div>
            </div>
		</form>

	</div>
	<!-- Container -->

</body>
</html>