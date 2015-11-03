<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<br data-ng-app="myApp">
<head>
    <title>Redirect Create Maintenance</title>
    <app:commonImports/>
</head>

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
            ContactPerson: '${params.ContactPerson}',  //Persona de Contacto
            ContactPhone: '${params.ContactPhone}',
            Text: '${params.Text}',
            SessionToken: '${params.SessionToken}'
        };


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

        var jsonado = JSON.stringify(parameters,null, 4);
        $("input[name='data']").val(jsonado);
    })
</SCRIPT>

<FORM method="POST" action="${externalCreateAppointmentUrl}" id='frmTOA' name='frmTOA' runat='server'>
    <INPUT type='hidden' name='data' value='' style="width: 1600px; margin:5px; padding: 3px; font-weight: bold">
    <!-- <input type="submit"/> -->
</FORM>
<!--
<br/>----------------<br/>
${params}
<br/>----------------<br/>

InstallationNumber : '${params.InstallationNumber}',<br/>
dos : '${params.dos}',<br/>
Codifications: '${params.type}|${params.motive}|1|', //Tipo y motivo, si hay que meter varios es separado por #</html>br>
PanelTypeId: '${params.PanelTypeId}',  //Tipo de panel de la instalacion<br/>
TicketNumber: '${params.TicketNumber}',  //Numero de Aviso<br/>
RequestedBy: '${params.RequestedBy}', // Campo Requerido por???<br/>
Operator: '${params.Operator}',  //Matricula del Agente<br/>
ContactPerson: '${params.ContactPerson}',  //Persona de Contacto<br/>
ContactPhone: '${params.ContactPhone}',<br/>
Text: '${params.Text}',<br/>
SessionToken: '${params.SessionToken}'<br/>
-->



<SCRIPT language='javascript'>
    //Descomentar para que se haga el envio automático del formulario
    setTimeout(function(){document.forms[0].submit();},0);//Hay que hacerlo con timeout para que funciones el POST</SCRIPT>
</body>

</html>