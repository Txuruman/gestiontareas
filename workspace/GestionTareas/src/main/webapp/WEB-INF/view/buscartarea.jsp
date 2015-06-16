<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<head>
<title>Welcome</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/custom.css" />

<!--ECLIPSE  -->
<link rel="stylesheet" type="text/css"
	href="../../static/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="../../static/css/custom.css" />
<!--ECLIPSE  -->

</head>

<body>


	<div class="container">

		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
				<h2><spring:message code="eti.buscartarea.h2.titulo.1"/> </h2>
			</div>
		</div>
		<div class="row">
			<jsp:include page="bloques/tabs1.jsp" />
		</div>

		<div class="spacer_t3"></div>
		<div class="spacer_t2"></div>

		<form class="form-horizontal" role="form">

			<div class="row text-center">

				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-2 control-label labelcent">
						<spring:message code="eti.buscartarea.form.label.1"/>: </label>
					<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
						<input type="text" class="form-control" id="ejemplo_email_3"
							placeholder="Buscar">
					</div>

					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-left">
						<input type="submit" class="btn btn-primary" value="Buscar" />
					</div>

					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-4">
						<div class="bordel text-center">
							<label class="checkbox-inline"> <input type="radio"
								id="checkboxEnLinea1" name="options" value="opcion_1">
								<spring:message code="eti.buscartarea.form.radio.1"/>
							</label> <label class="checkbox-inline"> <input type="radio"
								id="checkboxEnLinea2" name="options" value="opcion_2">
							    <spring:message code="eti.buscartarea.form.radio.2"/>
							</label>
						</div>
					</div>


				</div>

			</div>
			<!-- row -->

			<div class="spacer_t3"></div>
			<div class="spacer_t2"></div>
		</form>


		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">

				<table class="table table-bordered">
					<tr class="cabecillas">
						<th>Cliente</th>
						<th>Calling List</th>
						<th>Teléfono</th>
						<th>Estado</th>
						<th>Fecha de reprogramación</th>
						<th>Gestion</th>
						<th>Aplazar</th>
					</tr>


					<tr>
						<td>458963</td>
						<td>CB_CL_CC_ODC</td>
						<td>681515458</td>
						<td>Rellamada Personal</td>
						<td>19/03/2014 12:20:00</td>
						<td><a href="visortarea.htm?ins_no=111111&tipotarea=aviso" class="btn btn-default"><spring:message code="eti.buscartarea.btn.1"/></a></td>
						<td><a href="#"><spring:message code="eti.buscartarea.btn.2"/></a> </td>
					</tr>

					<tr>
						<td>458963</td>
						<td>CB_CL_CC_ODC</td>
						<td>654896598</td>
						<td>En memoria</td>
						<td></td>
						<td><a href="visortarea.htm?ins_no=111111&tipotarea=aviso" class="btn btn-default"><spring:message code="eti.buscartarea.btn.1"/></a></td>
						<td><a href="#"><spring:message code="eti.buscartarea.btn.2"/></a> </td>

					</tr>

					<tr>
						<td>123456</td>
						<td>CB_CL_CC_GI</td>
						<td>65245963</td>
						<td>Abierto</td>
						<td></td>
						<td><a href="visortarea.htm?ins_no=111111&tipotarea=aviso" class="btn btn-default"><spring:message code="eti.buscartarea.btn.1"/></a></td>
						<td><a href="#"><spring:message code="eti.buscartarea.btn.2"/> </a> </td>

					</tr>



				</table>

			</div>
		</div>

	</div>

</body>
</html>


<!--  <div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
			</div>
		</div>-->