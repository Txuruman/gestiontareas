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
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 development">
                                <app:inputText id="ninstalacion" label="creartarea.ninstalacion" value="${creartarea.ninstalacion}" readonly="false" />
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
                                <input type="button" class="btn btn-xs" value="<spring:message code="creartarea.boton.buscarinstalacion"/>"/>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <label for="ejemplo_email_3"
                                class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                                Titular: </label>
                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                <input type="text" class="form-control" id="ejemplo_email2" readonly="true"
                                    placeholder="Titular">
                            </div>
                        </div>

                    </div>

                    <div class="spacer_t1"></div>
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <label for="telefono"
                                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                                    Teléfono: </label>
                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                <select class="form-control" id="telefono">
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
                                   class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                                Panel: </label>
                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                <input type="text" class="form-control" id="ejemplo_email_3" readonly="true"
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
                                <input type="text" class="form-control" id="ejemplo_email_4"
                                    placeholder="Persona de contacto">
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <label for="ejemplo_email_3"
                                class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                                Versión: </label>
                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                <input type="text" class="form-control" id="ejemplo_email_5" readonly="true"
                                    placeholder="Versión">
                            </div>
                        </div>
                    </div>
                    <!-- row -->


                    <div class="spacer_t1"></div>
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <label for="ejemplo_email_3"
                                   class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                                Requerido por: </label>
                            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                <input type="text" class="form-control" id="ejemplo_email_6"
                                       placeholder="Requerido">
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <div class="row text-center">
                                <label for="ejemplo_email_3"
                                       class="labelcent">
                                    Horario </label>
                            </div>
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                                    <label for="ejemplo_email_3"
                                           class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                                        Desde: </label>
                                    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                        <input type="text" class="form-control" id="ejemplo_email_7"
                                               placeholder="Desde">
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                                    <label for="ejemplo_email_3"
                                           class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                                        Hasta: </label>
                                    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                                        <input type="text" class="form-control" id="ejemplo_email_8"
                                               placeholder="Hasta">
                                    </div>
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
                                <textarea class="form-control custom-area" id="ejemplo_email_9"
                                    placeholder="Observaciones" rows="5" cols="20"></textarea>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
			<!-- row -->
            <div class="spacer_t2"></div>
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="row" align="right">
                        <div class="container-fluid">
                            <input type="submit" class="btn btn-default"
                                   value="Crear mantenimiento" />
                            <input type="submit" class="btn btn-primary" value="Crear" />
                        </div>
                    </div>
                </div>
            </div>
		</form>

	</div>
	<!-- Container -->

</body>
</html>