<%@ tag body-content="empty" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- El id se hace referencia en el controller -->
<div type="text/ng-template" data-cached-template="ContentModal.html" >
    <div class="contentModal">
	    <div class="modal-header">
	        <h3 class="modal-title"> <spring:message code='contentmodal.titulo'/> </h3>
	    </div>
	    <div class="modal-body">
	        <div class="row">
	            <div class="col-lg-12 col-md-12col-sm-12 col-xs-12">
	                <h1><spring:message code='contentmodal.msg1'/></h1>
	            </div>
	         </div> 
	         <div class="spacer_t1"></div>
	          <div class="row">
	            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	             	<h2><spring:message code='contentmodal.msg2'/></h2>
	            </div>
	        </div>
			<div class="spacer_t1"></div>
	    </div>
	    <div class="modal-footer">
	        <button class="btn btn-primary" ng-click="ok()" ng-disabled="withoutChanges"><spring:message code='contentmodal.yes'/></button>
	        <button class="btn btn-warning" ng-click="cancel()"><spring:message code='contentmodal.no'/></button>
	    </div>
    </div>
</div>