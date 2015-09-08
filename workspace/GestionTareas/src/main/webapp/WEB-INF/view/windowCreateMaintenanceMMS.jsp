<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html data-ng-app="myApp">
<head>
    <title>Redirect Create Maintenance</title>
    <app:commonImports/>
</head>


<body>

<h1>MMS</h1>


PARAMS: ${params}

<FORM method='post' action='${externalCreateAppointmentUrl}'>
    t<INPUT type='text' name='t' value='${params.t}'><br/>
    NINSTALACION <INPUT type='text' name='NINSTALACION' value='${params.NINSTALACION}'><br/>
    TIPOPANEL <INPUT type='text' name='TIPOPANEL' value='${params.TIPOPANEL}'><br/>
    PAIS <INPUT type='text' name='PAIS' value='${params.PAIS}'><br/>
    IDIOMA <INPUT type='text' name='IDIOMA'value='${params.IDIOMA}'><br/>
    FINANCIACION  <INPUT type='text' name='FINANCIACION' value='${params.FINANCIACION}'><br/>
    AVISO <INPUT type='text' name='AVISO' value='${params.AVISO}'><br/>
    MATRICULA <INPUT type='text' name='MATRICULA' value='${params.MATRICULA}'><br/>
    TIPOCIERRE <INPUT type='text' name='TIPOCIERRE' value='${params.TIPOCIERRE}'><br/>
    NOTACIERRE <INPUT type='text' name='NOTACIERRE' value='${params.NOTACIERRE}'><br/>
    STATUSDESTINO <INPUT type='text' name='STATUSDESTINO' value='${params.STATUSDESTINO}'><br/>
    TIPODEUDA <INPUT type='text' name='TIPODEUDA' value='${params.TIPODEUDA}'><br/>
    DATOSADIC <INPUT type='text' name='DATOSADIC' value='${params.DATOSADIC}'><br/>
    REPNAME <INPUT type='text' name='REPNAME' value='${params.REPNAME}'><br/>
    NOMBRE <INPUT type='text' name='NOMBRE' value='${params.NOMBRE}'><br/>
    TELEFONO <INPUT type='text' name='TELEFONO' value='${params.TELEFONO}'><br/>
    CALLTYPEPROBLEM <INPUT type='text' name='CALLTYPEPROBLEM' value='${params.CALLTYPEPROBLEM}'><br/>
    TEXTO <INPUT type='text' name='TEXTO' value='${params.TEXTO}'><br/>
    <input type="submit"/>
</FORM>


<SCRIPT language='javascript'>
    //Descomentar para que se haga el envio automático del formulario
    setTimeout(function () {
        document.forms[0].submit();
    }, 600000);//Hay que hacerlo con timeout para que funciones el POST</SCRIPT>
</body>

</html>