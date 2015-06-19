<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
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
				<h2>Tarea crear</h2>
			</div>
		</div>
		<div class="row">
			<jsp:include page="bloques/tabs1.jsp" />
		</div>
		<div class="spacer_t2"></div>

		<form class="form-horizontal" role="form" >

			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
						Nº instalación: </label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<input type="text" class="form-control" id="ejemplo_email_3"
							placeholder="Instalación">
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
						Titular: </label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<input type="text" class="form-control" id="ejemplo_email_3"
							placeholder="Titular">
					</div>
				</div>

			</div>

			<div class="spacer_t1"></div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
						Teléfono: </label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<input type="text" class="form-control" id="ejemplo_email_3"
							placeholder="Teléfono">
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
						Panel: </label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<input type="text" class="form-control" id="ejemplo_email_3"
							placeholder="Panel">
					</div>
				</div>

			</div>
			<div class="spacer_t1"></div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
						Persona de contacto: </label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<input type="text" class="form-control" id="ejemplo_email_3"
							placeholder="Persona de contacto">
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
						Versión: </label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<input type="text" class="form-control" id="ejemplo_email_3"
							placeholder="Versión">
					</div>
				</div>
			</div>
			<!-- row -->


			<div class="spacer_t1"></div>
			<div class="row">
				<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 customtriple">
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
						<label for="ejemplo_email_3"
							class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
							Requerido: </label>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
							<input type="text" class="form-control" id="ejemplo_email_3"
								placeholder="Requerido">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
						<label for="ejemplo_email_3"
							class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
							Horario desde: </label>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
							<input type="text" class="form-control" id="ejemplo_email_3"
								placeholder="Desde">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
						<label for="ejemplo_email_3"
							class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
							Hasta: </label>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
							<input type="text" class="form-control" id="ejemplo_email_3"
								placeholder="Hasta">
						</div>
					</div>
				</div>
			</div>
			<!-- row -->



			<div class="spacer_t3"></div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">Tipo</label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<select class="form-control">
							<option>option1</option>
							<option>option2</option>
							<option>option3</option>
							<option>option4</option>
							<option>option5</option>
						</select>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">Motivo</label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<select class="form-control">
							<option>option1</option>
							<option>option2</option>
							<option>option3</option>
							<option>option4</option>
							<option>option5</option>
						</select>
					</div>
				</div>

			</div>
			<!-- row -->
			<div class="spacer_t1"></div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent"></label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<select class="form-control">
							<option>option1</option>
							<option>option2</option>
							<option>option3</option>
							<option>option4</option>
							<option>option5</option>
						</select>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent"></label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<select class="form-control">
							<option>option1</option>
							<option>option2</option>
							<option>option3</option>
							<option>option4</option>
							<option>option5</option>
						</select>
					</div>
				</div>
			</div>
			<!-- row -->
			<div class="spacer_t1"></div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent"></label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<select class="form-control">
							<option>option1</option>
							<option>option2</option>
							<option>option3</option>
							<option>option4</option>
							<option>option5</option>
						</select>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent"></label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<select class="form-control">
							<option>option1</option>
							<option>option2</option>
							<option>option3</option>
							<option>option4</option>
							<option>option5</option>
						</select>
					</div>
				</div>
			</div>
			<!-- row -->
			<div class="spacer_t2"></div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<label for="ejemplo_email_3"
						class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">Observaciones</label>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
						<textarea class="form-control custom-area" id="ejemplo_email_3"
							placeholder="Observaciones" rows="5" cols="20"></textarea>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
					<div class="row">
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"></div>
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
							<input type="submit" class="btn btn-default"
								value="Crear mantenimiento" />
						</div>
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
							<input type="submit" class="btn btn-primary" value="Finalizar" />
						</div>

					</div>

				</div>
			</div>
			<!-- row -->



		</form>

	</div>
	<!-- Container -->

</body>
</html>