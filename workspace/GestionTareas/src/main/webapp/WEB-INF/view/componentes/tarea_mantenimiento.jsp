<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <form:select id="desplegableKey1" path="tarea.key1" items="${desplegableKey1}" class="form-control" />
    </div>
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <label for="key1Desplegable"
               class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">
            key1: </label>

        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
            <select class="form-control" id="key1Desplegable" name="desplegableKey1">

                <c:forEach items="${desplegableKey1}" var="desplegableKey1">
                    <option value="<c:out value="${desplegableKey1.key}"/>"><c:out value="${desplegableKey1.value}"/></option>
                </c:forEach>

            </select>

        </div>
    </div>

</div>


