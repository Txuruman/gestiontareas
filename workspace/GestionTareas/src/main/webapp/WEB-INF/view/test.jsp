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

    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/custom.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/ui-bootstrap-tpls-0.13.0.js"/>--%>

    <%--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular.js"></script>--%>
    <%--<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.6.0/moment.min.js"></script>--%>


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
            PanelTypeId: 'panelType',
            TicketNumber: '11350758',
            RequestedBy: 'HM',
            Operator: 'operator',
            ContactPerson: 'persona llamar',
            ContactPhone: '696252991',
            Text: 'text',
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
    // document.forms[0].submit();
</SCRIPT>


<%--<!-- Acordeon -start -->--%>
<%--<div ng-controller="AccordionDemoCtrl">--%>
<%--<p>--%>
<%--<button class="btn btn-default btn-sm" ng-click="status.open = !status.open">Toggle last panel</button>--%>
<%--<button class="btn btn-default btn-sm" ng-click="status.isFirstDisabled = ! status.isFirstDisabled">Enable / Disable first panel</button>--%>
<%--</p>--%>

<%--<label class="checkbox">--%>
<%--<input type="checkbox" ng-model="oneAtATime">--%>
<%--Open only one at a time--%>
<%--</label>--%>
<%--<accordion close-others="oneAtATime">--%>
<%--<accordion-group heading="Static Header, initially expanded" is-open="status.isFirstOpen" is-disabled="status.isFirstDisabled">--%>
<%--This content is straight in the template.--%>
<%--</accordion-group>--%>
<%--<accordion-group heading="{{group.title}}" ng-repeat="group in groups">--%>
<%--{{group.content}}--%>
<%--</accordion-group>--%>
<%--<accordion-group heading="Dynamic Body Content">--%>
<%--<p>The body of the accordion group grows to fit the contents</p>--%>
<%--<button class="btn btn-default btn-sm" ng-click="addItem()">Add Item</button>--%>
<%--<div ng-repeat="item in items">{{item}}</div>--%>
<%--</accordion-group>--%>
<%--<accordion-group is-open="status.open">--%>
<%--<accordion-heading>--%>
<%--I can have markup, too! <i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': status.open, 'glyphicon-chevron-right': !status.open}"></i>--%>
<%--</accordion-heading>--%>
<%--This is just some content to illustrate fancy headings.--%>
<%--</accordion-group>--%>
<%--</accordion>--%>
<%--</div>--%>
<%--<!-- Acordeon -end -->--%>


<%--<!-- Date -->--%>
<%--<div ng-controller="DatepickerDemoCtrl">--%>

<%--<div class="row">--%>
<%--<div class="col-md-6">--%>
<%--<p class="input-group">--%>
<%--<input type="text" class="form-control" datepicker-popup="{{format}}" ng-model="dt" is-open="opened" min-date="minDate" max-date="'2015-06-22'" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"/>--%>
<%--<span class="input-group-btn">--%>
<%--<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>--%>
<%--</span>--%>
<%--</p>--%>
<%--</div>--%>

<%--<div class="col-md-6">--%>
<%--<p class="input-group">--%>
<%--<input type="date" class="form-control" datepicker-popup ng-model="dt" is-open="opened" min-date="minDate" max-date="'2015-06-22'" datepicker-options="dateOptions" date-disabled="disabled(date, mode)" ng-required="true" close-text="Close"/>--%>
<%--<span class="input-group-btn">--%>
<%--<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>--%>
<%--</span>--%>
<%--</p>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="row">--%>
<%--<div class="col-md-6">--%>
<%--<p class="input-group">--%>
<%--<input type="date" class="form-control" datepicker-popup ng-model="dt"--%>
<%--ng-required="true"--%>
<%--/>--%>
<%--&lt;%&ndash;<span class="input-group-btn">&ndash;%&gt;--%>
<%--&lt;%&ndash;<button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>&ndash;%&gt;--%>
<%--&lt;%&ndash;</span>&ndash;%&gt;--%>
<%--</p>--%>
<%--</div>--%>

<%--<div class="col-md-6">--%>

<%--</div>--%>
<%--</div>--%>
<%--<div class="row">--%>
<%--<div class="col-md-6">--%>
<%--<label>Format:</label> <select class="form-control" ng-model="format" ng-options="f for f in formats">--%>
<%--<option></option>--%>
<%--</select>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<!-- Date -->--%>


<%--<div class="container" ng-controller="taskviewer-ctrl">--%>


<%--<h2>Pagina de pruebas</h2>--%>

<%--Total Data : {{allData}}<br/>--%>
<%--Server Messages : {{serverMessages}}<br/>--%>
<%--SmisMensajes : {{misMensajes}}<br/>--%>
<%--CommonService Messages : {{$CommonService.serverMessages}}<br/>--%>
<%--Root Messages : {{$rootScope.serverMessages}}<br/>--%>


<%--<app:messages/>--%>

<%--<app:inputButtonNG value="boton.Finalizar" button_type="primary" ng_click="getMessages()" fluid_wrapper="true"/>--%>


<%--&lt;%&ndash;<h3>Spring MVC AngularJS JSON Drop Down sample JNA </h3>&ndash;%&gt;--%>

<%--&lt;%&ndash;<table style="margin: 0px auto;" align="left">&ndash;%&gt;--%>
<%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
<%--&lt;%&ndash;<td>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div data-ng-init="getPersonDataFromServer()">&ndash;%&gt;--%>
<%--&lt;%&ndash;<b>Person Data:</b> <select id="personData">&ndash;%&gt;--%>
<%--&lt;%&ndash;<option value="">-- Select Persons --</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;<option data-ng-repeat="personData in personDatas" value="{{personData.personId}}">{{personData.personName}}</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;</select><br>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</td>&ndash;%&gt;--%>
<%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
<%--&lt;%&ndash;</table>&ndash;%&gt;--%>
<%--&lt;%&ndash;<br/>&ndash;%&gt;--%>
<%--&lt;%&ndash;getDesplegableKey1:{{key1}}&ndash;%&gt;--%>
<%--&lt;%&ndash;<br/>&ndash;%&gt;--%>

<%--&lt;%&ndash;<div data-ng-init="getDesplegableKey1()">&ndash;%&gt;--%>
<%--&lt;%&ndash;<select ng-model="key1value"><!-- ng-model="model.id" convert-to-number -->&ndash;%&gt;--%>
<%--&lt;%&ndash;<option data-ng-repeat="k in key1" value="{{k.id}}">{{k.value}}</option>&ndash;%&gt;--%>
<%--&lt;%&ndash;</select>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<br/>&ndash;%&gt;--%>
<%--&lt;%&ndash;value:{{key1value}}&ndash;%&gt;--%>
<%--&lt;%&ndash;<br/>&ndash;%&gt;--%>
<%--&lt;%&ndash;<br/>&ndash;%&gt;--%>
<%--&lt;%&ndash;<br/>&ndash;%&gt;--%>

<%--&lt;%&ndash;<form name="myForm" onsubmit="return false;">&ndash;%&gt;--%>

<%--&lt;%&ndash;<div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<input type="text" placeholder="First name" name="firstName" ng-model="firstName" required="true"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;<span ng-show="myForm.firstName.$dirty && myForm.firstName.$error.required">You must enter a value here</span>&ndash;%&gt;--%>
<%--&lt;%&ndash;<span ng-show="myForm.firstName.$error.serverMessage">{{myForm.firstName.$error.serverMessage}}</span>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<input type="text" placeholder="Last name" name="lastName" ng-model="lastName"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;<span ng-show="myForm.lastName.$error.serverMessage">{{myForm.lastName.$error.serverMessage}}</span>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<button ng-click="submit()">Submit</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;</form>&ndash;%&gt;--%>

<%--</div>--%>


</body>

</html>