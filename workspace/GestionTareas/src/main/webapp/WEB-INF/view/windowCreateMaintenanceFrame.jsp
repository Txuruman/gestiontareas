<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html data-ng-app="myApp">
<head>
    <title><spring:message code="titulo.crearteExternalMaintenence"/></title>
    <app:commonImports/>
</head>

<frameset>
    <frame src="${windowCreateMaintenace}?InstallationNumber=${params.InstallationNumber}&dos=${params.InstallationNumber}&type=${params.type}&motive=${params.motive}&PanelTypeId=${params.PanelTypeId}&TicketNumber=${params.TicketNumber}&RequestedBy=${params.RequestedBy}&Operator=${params.Operator}&ContactPerson=${params.ContactPerson}&ContactPhone=${params.ContactPhone}&Text=${params.Text}&SessionToken=${params.SessionToken}">

    <!--
    Estos son los parametros que necesita MMS pero no se va ha desarrollar
    &t=${params.t}&NINSTALACION=${params.NINSTALACION}&TIPOPANEL=${params.TIPOPANEL}&PAIS=${params.PAIS}&IDIOMA=${params.IDIOMA}&FINANCIACION=${params.FINANCIACION}&AVISO=${params.AVISO}&MATRICULA=${params.MATRICULA}&TIPOCIERRE=${params.TIPOCIERRE}&NOTACIERRE=${params.NOTACIERRE}&STATUSDESTINO=${params.STATUSDESTINO}&TIPODEUDA=${params.TIPODEUDA}&DATOSADIC=${params.DATOSADIC}&REPNAME=${params.REPNAME}&NOMBRE=${params.NOMBRE}&TELEFONO=${params.TELEFONO}&CALLTYPEPROBLEM=${params.CALLTYPEPROBLEM}&TEXTO=${params.TEXTO}
    -->
</frameset>

</html>