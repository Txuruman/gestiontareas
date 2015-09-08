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

<SCRIPT language='javascript'>

        $(document).ready(function () {

            <%--var parameters = {--%>
                <%--InstallationNumber:'1729318',--%>
                <%--PanelTypeId:'SDVFAST',--%>
                <%--TicketNumber:'11493612',--%>
                <%--RequestedBy:'Asistencia Tecnica',--%>
                <%--Operator:'DF1512',--%>
                <%--ContactPerson:'RUBEN',--%>
                <%--ContactPhone:'636365884',--%>
                <%--Codifications:'100|101|1|',--%>
                <%--Text:'Falsas alarmas en sismico zona 4. revisar ubicacion y sensibilidad. Gracias  ##CARGO EN CUENTA: NO ## ##COMENTARIOS CONTACTO:  ##.',--%>
                <%--SessionToken: '${params.SessionToken}'--%>
            <%--};--%>


        var parameters = {
            InstallationNumber : '${params.InstallationNumber}',
            Codifications: '${params.type}|${params.motive}|1|', //Tipo y motivo, si hay que meter varios es separado por #
            PanelTypeId: '${params.PanelTypeId}',  //Tipo de panel de la instalacion
            TicketNumber: '${params.TicketNumber}',  //Numero de Aviso
            RequestedBy: '${params.RequestedBy}', // Campo Requerido por???
            Operator: '${params.Operator}',  //Matricula del Agente
            ContactPerson: '${params.ContactPerson}',
            ContactPhone: '${params.ContactPhone}',
            Text: '${params.Text}',
            SessionToken: '${params.SessionToken}'
        };

        //El que nos da problemas
        //{    "InstallationNumber": "401808",
        //   "Codifications": "100|199|1|",
        //  "PanelTypeId": "SDMF",
        //  "TicketNumber": "11351038",
        // "RequestedBy": "MCEX - L57808",
        //  "Operator": "M0OOS",
        // "ContactPerson": "RUBEN123123",
        //   "ContactPhone": "33333333",
        // "Text": "",
        // "SessionToken": ""}

        //Como abrirlo por url
        //http://localhost:8080/gestiontareas/windowCreateMaintenanceFrame?InstallationNumber=401808&PanelTypeId=SDMF&TicketNumber=11351038&RequestedBy=MCEX%20-%20L57808&Operator=M0OOS&ContactPerson=asdf&ContactPhone=123&Text=asdf&SessionToken=3746E15A037D310E4D1F8C0A0056B95A7C3376F48AD25012C3EEDFC7F713CC781D06BA433A9F86E0F62836CCDEF9139F8225441A1BCF606516852CFB2873FB3D13FB1088EA3DA19E8135BE12DD148AAC30C2AF4CB74B55D869FA7CEAEC5C9D41&type=100&motive=199

        //Prueba
        <%--parameters = {--%>
        <%--InstallationNumber : '1729318',--%>
        <%--Codifications: '100|101|1|', //Tipo y motivo--%>
        <%--PanelTypeId: 'SDVFAST',  //Tipo de panel de la instalacion--%>
        <%--TicketNumber: '11493612',  //Numero de Aviso--%>
        <%--RequestedBy: 'Asistencia Tecnica', // Campo Requerido por???--%>
        <%--Operator: 'M0OOS',  //Matricula del Agente--%>
        <%--ContactPerson: 'RUBEN',--%>
        <%--ContactPhone: '636365884',--%>
        <%--Text: 'falsa alarma',--%>
        <%--SessionToken: '${params.SessionToken}'--%>
        <%--};--%>


        <%--var jsString = {--%>
        <%--Codifications: codifications,--%>
        <%--//            Codifications:'&lt;CODIFICATIONS&gt;&lt;/CODIFICATIONS&gt;',--%>
        <%--//Codifications:'<CODIFICATIONS></CODIFICATIONS>',--%>
        <%--PanelTypeId: '${params.PanelTypeId}', //installationData.panel--%>
        <%--TicketNumber: '${params.TicketNumber}', //tarea.idAviso--%>
        <%--RequestedBy: '${params.RequestedBy}', //tarea.requeridoPor--%>
        <%--Operator: '${params.Operator}', //agent.agentIBS--%>
        <%--ContactPerson: '${params.ContactPerson}', //installationData.personaContacto--%>
        <%--ContactPhone: '${params.ContactPhone}', //installationData.telefono--%>
        <%--Text: '${params.Text}', //tarea.observaciones--%>
        <%--SessionToken: 'DB4DA3EDF77965766C8700D373DF30EFDE5124EA42282DB07447AE4AEA58DBEC53A0F6459EBDDCCF26CB6D80E686EC5765DB38A0B42B9DF15ED14AAB27DF58D6285C354540805C045A1C17FB208F9EC4C8AA810406BA706306FCFA73DDF614EB'--%>
        <%--};--%>


        var jsonado = JSON.stringify(parameters,null, 4);
        $("input[name='data']").val(jsonado);
    })
</SCRIPT>

<h1>TOA</h1>


<FORM method="POST" action="${externalCreateAppointmentUrl}" id='frmTOA' name='frmTOA' runat='server'>
    <INPUT type='text' name='data' value='' style="width: 1600px; margin:5px; padding: 3px; font-weight: bold">
    <input type="submit"/>
</FORM>

<SCRIPT language='javascript'>
    //Descomentar para que se haga el envio automático del formulario
    setTimeout(function(){document.forms[0].submit();},60000);//Hay que hacerlo con timeout para que funciones el POST</SCRIPT>


</body>

</html>