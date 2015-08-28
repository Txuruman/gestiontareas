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
     //Un ejemplo que funciona
    // {"SessionToken":"BBB..sssss","
    // InstallationNumber":"1729318",
    // "PanelTypeId":"SDVFAST",
    // "TicketNumber":"11493612",
    // "RequestedBy":"Asistencia Tecnica",
    // "Operator":"DF1512",
    // "ContactPerson":"RUBEN",
    // "ContactPhone":"636365884",
    // "Codifications":"100|101|1|",
    // "Text":"Falsas alarmas en sismico zona 4. revisar ubicacion y sensibilidad. Gracias  ##CARGO EN CUENTA: NO ## ##COMENTARIOS CONTACTO:  ##."}

    $(document).ready(function () {

        var parameters = {
            InstallationNumber : 1729318,
            Codifications: '100|101|1|', //Tipo y motivo
            PanelTypeId: 'SDVFAST',  //Tipo de panel de la instalacion
            TicketNumber: '11350758',  //Numero de Aviso
            RequestedBy: 'Asistencia Tecnica', // Campo Requerido por???
            Operator: 'M0OOS',  //Matricula del Agente
            ContactPerson: 'Ruben',
            ContactPhone: '696252991',
            Text: 'text',
            SessionToken: '70C483C562B496038C79C4D9EEF84C5A33BD867B7E5D7A400F5A8920402E9080E2A77BDF7F215E38EB1BDE3B27485D011FA8C7F5DD605D0703E1094CC2706F887024354289022062705ADDBAD0DC8A5D5F89113A2563A18C14E40259E7EE7605'


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


        var jsonado = JSON.stringify(parameters);
        $("input[name='data']").val(jsonado);
    })
</SCRIPT>

<BODY>

<FORM method='post' action='${externalCreateAppointmentUrl}' id='frmTOA' name='frmTOA' runat='server'>
    <INPUT type='text' name='data' value='' style="width: 1600px; margin:5px; padding: 3px; font-weight: bold">
    <input type="submit"/>
</FORM>
</BODY>
<SCRIPT language='javascript'>
    //Descomentar para que se haga el envio automático del formulario
       //document.forms[0].submit();
    setTimeout(function(){document.forms[0].submit();},3000);
</SCRIPT>


</body>

</html>