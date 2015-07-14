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

    <input type="button" class="${button_type}" value="<spring:message code="${value}" />"
        ${ng_click!=null && !ng_click.isEmpty() ? 'ng-click=\"'.concat(ng_click).concat('\"') : ''}
        ${ng_controller!=null && !ng_controller.isEmpty() ? 'ng-controller=\"'.concat(ng_controller).concat('\"') : ''}
        />

<c:if test="${fluid_wrapper != 'true'}">
    </div>
</c:if>