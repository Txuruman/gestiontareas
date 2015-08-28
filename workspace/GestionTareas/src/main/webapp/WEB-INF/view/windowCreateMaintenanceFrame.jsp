<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html data-ng-app="myApp">
<head>
    <title>Frame with Redirect Create Maintenance</title>
    <app:commonImports/>
</head>

<frameset>
    <frame src="windowCreateMaintenace?InstallationNumber=${params.InstallationNumber}&PanelTypeId=${params.type}&TicketNumber=${params.TicketNumber}&RequestedBy=${params.RequestedBy}&Operator=${params.Operator}&ContactPerson=${params.ContactPerson}&ContactPhone=${params.ContactPhone}&Text=${params.Text}&SessionToken=${params.SessionToken}&type=${params.type}&motive=${params.motive}">
</frameset>

</html>