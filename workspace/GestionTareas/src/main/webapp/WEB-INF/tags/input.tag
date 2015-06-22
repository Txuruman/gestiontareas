<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- http://www.onjava.com/pub/a/onjava/2004/05/12/jsp2part4.html -->
<%@ tag body-content="empty" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="label" required="false" type="java.lang.String" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="cells" required="false" description="Number of cells used from the 12 available" %>
<%@ attribute name="required" required="false" description="If the field is required true/false" %>
<%@ attribute name="readonly" required="false" description="If the field is readonly true/false" %>
<%@ attribute name="maxlength" required="false" description="The maximum number of characters allowed in the <input> element" %>


<div class="col-lg-${cells} col-md-${cells} col-sm-${cells} col-xs-${cells}">
    <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent ${required=='true'? 'label.required' : ''}">
        <c:if test="${label!=null && !label.isEmpty()}"> <spring:message code='${label}'/> ${required=='true'? '*' : ''}:</c:if>
    </label>

    <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
        <input type="text" class="form-control" name="${id}" id="${id}"
               value="${value}"
               maxlength="${maxlength}"
        ${readonly=='true'? 'disabled' : ''}
                />
    </div>

</div>