<%@ tag body-content="empty" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="titulo" required="true"  description="only numbers inputText" %>
<%@ attribute name="msg1" required="true"  description="input ng-disabled" %>
<%@ attribute name="msg2" required="false"  description="pattern for the input" %>
<%@ attribute name="button" required="true"  description="class for the div" %>

<!-- El id se hace referencia en el controller -->
<div type="text/ng-template" data-cached-template="alertModal.html" >
    <div class="contentModal">
	    <div class="modal-header">
	        <h3 class="modal-title"> <spring:message code='${titulo}'/> </h3>
	    </div>
	    <div class="modal-body">
	        <div class="row">
	            <div class="col-lg-12 col-md-12col-sm-12 col-xs-12">
	                <h1><spring:message code='${msg1}'/></h1>
	            </div>
	         </div> 
			<div class="spacer_t1"></div>
	    </div>
	    <div class="modal-footer">
	        <button class="btn btn-primary" ng-click="ok()" ng-disabled="withoutChanges"><spring:message code='${button}'/></button>
	    </div>
    </div>
</div>