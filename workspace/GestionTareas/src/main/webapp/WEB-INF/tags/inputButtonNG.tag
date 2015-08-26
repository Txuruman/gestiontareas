<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- http://www.onjava.com/pub/a/onjava/2004/05/12/jsp2part4.html -->
<%@ tag body-content="empty" %>
<%@ attribute name="value" required="false" type="java.lang.String" %>
<%@ attribute name="button_type" required="true" description="Choose between button types: {primary, default, default_disabled, xs. You can set aswell css properties directly" %>
<%@ attribute name="ng_click" required="false" description="Set up the AngularJS action" %>
<%@ attribute name="ng_controller" required="false" description="Set up the AngularJS controller" %>
<%@ attribute name="cells" required="false" description="Number of cells used from the 12 available" %>
<%@ attribute name="fluid_wrapper" required="false" description="Defines if the button its in a fluid wrapper for not set width" %>
<%@ attribute name="type" required="false" description="Type of input, button or submit" %>
<%@ attribute name="ng_disabled" required="false" description="Button ng-disabled" %>

<c:if test="${button_type == 'primary'}">
    <c:set var="button_type" value="btn btn-primary"/>
</c:if>

<c:if test="${button_type == 'default'}">
    <c:set var="button_type" value="btn btn-default"/>
</c:if>

<c:if test="${button_type == 'default_disabled'}">
    <c:set var="button_type" value="btn btn-default[disabled]"/>
</c:if>

<c:if test="${button_type == 'xs'}">
    <c:set var="button_type" value="btn btn-xs"/>
</c:if>

<c:if test="${fluid_wrapper != 'true'}">
    <div class="col-lg-${cells} col-md-${cells} col-sm-${cells} col-xs-${cells}">
</c:if>
<c:if test="${type != 'submit'}">
	<c:set var="type" value="button"/>
</c:if>
    <input type="${type}" class="${button_type}" value="<spring:message code="${value}" />"
           <c:if test="${not empty ng_click}">
               ng-click="<c:out value="${ng_click}" />"
           </c:if>
            <c:if test="${not empty ng_controller}">
                ng-click="<c:out value="${ng_controller}" />"
            </c:if>
            <c:if test="${not empty ng_disabled}">
                ng-disabled="<c:out value="${ng_disabled}" />"
            </c:if>
        />
<c:if test="${fluid_wrapper != 'true'}">
    </div>
</c:if>