<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row bordel">
    <h3> DEBUG DIV </h3>
    <app:wrapping cells="6">
        <h4> JSP VARIABLES</h4>
        Secundaria:${secundaria}<br/>
        Installation:${installationId}<br/>
        callingList:${callingList}<br/>
        ccUserId:${ccUserId}<br/>
        idTarea:${tareaId}<br/>
    </app:wrapping>
    <app:wrapping cells="6">
        <h4> ANGULAR VARIABLES</h4>
        Installation:{{installationId}}<br/>
        callingList:{{callingList}}<br/>
        ccUserId:{{ccUserId}}<br/>
        idTarea:{{tareaId}}<br/>
    </app:wrapping>
</div>