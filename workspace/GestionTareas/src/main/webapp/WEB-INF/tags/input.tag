<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- http://www.onjava.com/pub/a/onjava/2004/05/12/jsp2part4.html -->
<%@ tag body-content="empty" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="name_id" required="true" %>
<%@ attribute name="cell" required="false" description="Numero de celdas que ocupa en el espacio max 12" %>


<div class="col-lg-${cell} col-md-${cell} col-sm-${cell} col-xs-${cell}">
    <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label labelcent">
        <spring:message code='${label}'/>:
    </label>
    <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9">
        <input type="text" class="form-control" name="${name_id}" id="${name_id}"
               value="${value}" disabled/>
    </div>
</div>