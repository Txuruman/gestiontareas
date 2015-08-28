<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html data-ng-app="myApp">
<head>
    <title><spring:message code="titulo.BuscarTarea"/></title>

    <app:commonImports/>

    <script src="${pageContext.request.contextPath}/resources/app/maincontrollers/taskViewer-ctrl.js"></script>

</head>


<body>

Enlace Infopoint:

<SCRIPT language='javascript'>
    var entityMap = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': '&quot;',
        "'": '&#39;',
        "/": '&#x2F;'
    };

    function escapeHtml(string) {
        return String(string).replace(/[&<>"'\/]/g, function (s) {
            return entityMap[s];
        });
    }

    $(document).ready(function () {
        var codifications =
                        "<CODIFICATIONS>                                " + "     " +
                        "    <CODIFICATION>                             " + "     " +
                        "         <IX>0</IX>                              " + "     " +
                        "         <CALLTYPE>ABC</CALLTYPE>                                " + "     " +
                        "         <PROBLEM>XYZ</PROBLEM>                             " + "     " +
                        "         <CAUSE></CAUSE>                               " + "     " +
                        "         <RESOLUTION></RESOLUTION>                              " + "     " +
                         "            <ITEM ID=\"0\"                                " + "     " +
                         "           COUNT=\"1\" />    " +
                        "    </CODIFICATION>                              " + "     " +
                        "</CODIFICATIONS>                               " + "     "
                ;

        codifications = escapeHtml(codifications);


        var jsString = {
            Codifications: codifications,
//            Codifications:'&lt;CODIFICATIONS&gt;&lt;/CODIFICATIONS&gt;',
            //Codifications:'<CODIFICATIONS></CODIFICATIONS>',
            PanelTypeId: '${params.PanelTypeId}', //installationData.panel
            TicketNumber: '${params.TicketNumber}', //tarea.idAviso
            RequestedBy: '${params.RequestedBy}', //tarea.requeridoPor
            Operator: '${params.Operator}', //agent.agentIBS
            ContactPerson: '${params.ContactPerson}', //installationData.personaContacto
            ContactPhone: '${params.ContactPhone}', //installationData.telefono
            Text: '${params.Text}', //tarea.observaciones
            SessionToken: 'DB4DA3EDF77965766C8700D373DF30EFDE5124EA42282DB07447AE4AEA58DBEC53A0F6459EBDDCCF26CB6D80E686EC5765DB38A0B42B9DF15ED14AAB27DF58D6285C354540805C045A1C17FB208F9EC4C8AA810406BA706306FCFA73DDF614EB'
        };


        //   var jsString = "{" & chr(34) & "SessionToken" & chr(34) & ":" & chr(34) & token & chr(34) & "," & _
        //    chr(34) & "InstallationNumber" & chr(34) & ":" & chr(34) & numInst & chr(34) & "," & _
        //    chr(34) & "PanelTypeId"  & chr(34) & ":" & chr(34) & panelType & chr(34) & "," & _
        //    chr(34) & "TicketNumber" & chr(34) & ":" & chr(34) & numTicket & chr(34) & "," & _
        //    chr(34) & "RequestedBy" & chr(34) & ":" & chr(34) & reqBy & chr(34) & "," & _
        //    chr(34) & "Operator" & chr(34) & ":" & chr(34) & operator & chr(34) & "," & _
        //    chr(34) & "ContactPerson" & chr(34) & ":" & chr(34) & contPerson & chr(34) & "," & _
        //    chr(34) & "ContactPhone" & chr(34) & ":" & chr(34) & contPhone & chr(34) & "," & _
        //    chr(34) & "Codifications" & chr(34) & ":" & chr(34) & codif & chr(34) & "," & _
        //    chr(34) & "Text" & chr(34) & ":" & chr(34) & text & chr(34) & "}"



        var jsonado = JSON.stringify(jsString);
        $("input[name='data']").val(jsonado);
        $("textarea[name='data2']").val(jsonado);
    })
</SCRIPT>

<BODY>


<FORM method='post' action='http://es1infotest01v/FSMAppointmentManager/CreateAppointmentModal.aspx' id='frmTOA' name='frmTOA' runat='server'>
    <INPUT type='text' name='data' value='' style="width: 1600px; margin:5px; padding: 3px; font-weight: bold">
    <textarea name='data2' rows="15" cols="80"></textarea>
    <input type="submit"/>
</FORM>
</BODY>
<SCRIPT language='javascript'>
     //document.forms[0].submit();
</SCRIPT>


</body>

</html>