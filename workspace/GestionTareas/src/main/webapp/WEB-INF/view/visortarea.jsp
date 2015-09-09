<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //TODO QUITAR??
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Expires", "-1");
    response.addHeader("Pragma", "no-cache");
%>

<html data-ng-app="myApp">
<head>
    <title><spring:message code="${titulo}"/></title>
    <app:commonImports/>
</head>


<br>

<script type="text/javascript">
      //Inicialización de un mapa de parametros con todas las variables que se han pasado por post
        var mapParams = {
        <c:forEach var="entry" items="${params}">
             ${entry.key}  : "<c:out value="${entry.value}"/>" ,
        </c:forEach>
			debug : "0"
        };
      $(document).ready(function(){
    	  var newCallConnid = null;
    	  
    	  $("button[name='papallama']").click(function() {
  			var phone = "0999655738925";
  			alert(phone);
  			e = window.external.DoCall(phone, 'myDoCallHandler', provideMockupObject());
  			alert(JSON.stringify(e));
  		});
    	  
    	function myDoCallHandler(eventName, connid) {
    			newCallConnid = connid;
    			alert("alberto!:"+eventName+": "+newCallConnid+": ");
    	}

    	function provideMockupObject() {
    			var o = {idProspect:'12345', task:'TAKE_RDV', comments:'These are the comments of my prospect', otherelement:'tatata'};
    			params = JSON.stringify(o);
    			return JSON.stringify(o);
    	}
    	  
      })
</script>

<div class="visoTareaContainer" ng-init="tareaId='${tareaId}';callingList='${callingList}';outContactInfo='${outContactInfo}';outCampaignName='${outCampaignName}';outClName='${outClName}';outRecordHandle='${outRecordHandle}';outAgentPlace='${outAgentPlace}';">

    <app:messages/>

    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
            <h2><spring:message code="${titulo}"/></h2>
        </div>
    </div>
    <div class="spacer_t2"></div>
    <form class="form-horizontal" role="form" name="formVisorTarea">
        <!-- Include Tareas, dependiendo del tipo de tarea -->
        <div class="spacer_t3"></div>
        <%-- Aqui se incluye la pantalla secundaria de carga de Tarea--%>
        <jsp:include page="${secundaria}"/>
    </form>
</div>
<!-- END ANGULARJS CONTROLLER DIV-->
<!-- Scripts de angularjs -->

</body>
</html>
