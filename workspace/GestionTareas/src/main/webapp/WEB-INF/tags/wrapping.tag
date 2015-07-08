<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- http://www.onjava.com/pub/a/onjava/2004/05/12/jsp2part4.html -->
<%@ attribute name="cells" required="false" description="Number of cells used from the 12 available" %>

<div class="col-lg-${cells} col-md-${cells} col-sm-${cells} col-xs-${cells}">
    <jsp:doBody/>
</div>