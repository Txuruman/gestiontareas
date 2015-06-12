<!DOCTYPE html>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head>
    <title>Welcome</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/custom.css"/>


</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
            <h2><spring:message code="eti.visortarea.h2.titulo.1"/></h2>
        </div>
    </div>
    <div class="row">
        <jsp:include page="bloques/tabs1.jsp"/>
    </div>
    <div class="spacer_t2"></div>


    <form class="form-horizontal" role="form">

        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="n-instalacion"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.1"/>:
                </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="ninstalacion" id="n-instalacion"
                           placeholder="<spring:message code='eti.visortarea.form.label.1'/>">
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="titular"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.2"/> </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="titular" id="titular"
                           placeholder="<spring:message code="eti.visortarea.form.label.2"/>">
                </div>
            </div>

        </div>

        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="telefono"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.3"/>: </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="telefono" id="telefono"
                           placeholder="<spring:message code="eti.visortarea.form.label.3"/>">
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="panel"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.4"/>: </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="panel" id="panel"
                           placeholder="<spring:message code="eti.visortarea.form.label.4"/>">
                </div>
            </div>

        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="pcontacto"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.5"/>: </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="pcontacto" id="pcontacto"
                           placeholder="<spring:message code="eti.visortarea.form.label.5"/>">
                </div>
            </div>

            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="version"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.6"/> : </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="version" id="version"
                           placeholder="<spring:message code="eti.visortarea.form.label.6"/>">
                </div>
            </div>
        </div>
        <!-- row -->


        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 customtriple">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                    <label for="requerido"
                           class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                        <spring:message code="eti.visortarea.form.label.6"/>: </label>

                    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="requerido" id="requerido"
                               placeholder="<spring:message code="eti.visortarea.form.label.6"/>">
                    </div>
                </div>

                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                    <label for="horario1"
                           class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                        <spring:message code="eti.visortarea.form.label.8"/>: </label>

                    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="horario1" id="horario1"
                               placeholder="<spring:message code="eti.visortarea.form.label.8.placeholder"/>">
                    </div>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                    <label for="horario2"
                           class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                        <spring:message code="eti.visortarea.form.label.9"/>: </label>

                    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                        <input type="text" class="form-control" name="horario2" id="horario2"
                               placeholder="<spring:message code="eti.visortarea.form.label.9.placeholder"/>">
                    </div>
                </div>
            </div>
        </div>
        <!-- row -->


        <div class="spacer_t3"></div>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="ejemplo_email_3"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.10"/>:
                </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="tipo" id="ejemplo_email_3" placeholder="<spring:message code="eti.visortarea.form.label.10"/>">
                </div>

            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="ejemplo_email_3"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
                    <spring:message code="eti.visortarea.form.label.10"/>:
                </label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" name="motivo" id="ejemplo_email_3"
                           placeholder="<spring:message code="eti.visortarea.form.label.10"/>">
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
                    <input type="text" class="form-control" id="ejemplo_email_3"
                           placeholder="Tipo">
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="ejemplo_email_3"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent"></label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" id="ejemplo_email_3"
                           placeholder="Motivo">
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
                    <input type="text" class="form-control" id="ejemplo_email_3"
                           placeholder="Tipo">
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <label for="ejemplo_email_3"
                       class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent"></label>

                <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
                    <input type="text" class="form-control" id="ejemplo_email_3"
                           placeholder="Motivo">
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
                    <label for="ejemplo_email_3"
                           class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">Cierre</label>

                    <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
                        <select class="form-control">
                            <option>option1</option>
                            <option>option2</option>
                            <option>option3</option>
                            <option>option4</option>
                            <option>option5</option>
                        </select>
                    </div>
                </div>
                <!-- subrow -->
                <div class="spacer_t1"></div>
                <div class="row">
                    <label for="ejemplo_email_3"
                           class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">Datos
                        adicionales de cierre</label>

                    <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
                        <select class="form-control">
                            <option>option1</option>
                            <option>option2</option>
                            <option>option3</option>
                            <option>option4</option>
                            <option>option5</option>
                        </select>
                    </div>
                </div>
                <!-- subrow -->
                <div class="spacer_t2"></div>
                <div class="spacer_t2"></div>

            </div>
        </div>
        <!-- row -->
        <div class="spacer_t2"></div>
        <div class="row">
            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8"></div>
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                <div class="row text-right">
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input type="submit" class="btn btn-default" value="Aplazar"/>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input type="submit" class="btn btn-default" value="Descartar"/>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input type="submit" class="btn btn-primary" value="Finalizar"/>
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