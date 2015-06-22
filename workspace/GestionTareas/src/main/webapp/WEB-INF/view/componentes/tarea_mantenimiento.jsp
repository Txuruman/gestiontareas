<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <form:label path="tarea" cssClass="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">
            <spring:message code="eti.visortarea.mantenimiento.form.label.key1"/>
        </form:label>
        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
            <form:select id="desplegableKey1" path="tarea.key1" items="${desplegableKey1}" cssClass="form-control"/>
        </div>
    </div>


    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <form:label path="tarea" cssClass="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">
            <spring:message code="eti.visortarea.mantenimiento.form.label.key2"/>
        </form:label>
        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
            <form:select id="desplegableKey2" path="tarea.key1" items="${desplegableKey2}" cssClass="form-control"/>
        </div>
    </div>
</div>

<div class="row">
    <app:input id="" value=""
</div>


