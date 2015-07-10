<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- http://www.onjava.com/pub/a/onjava/2004/05/12/jsp2part4.html -->
<%@ tag body-content="empty" %>

Mensajes de Servidor:
<div class="form-messages errors {{extraStyles}}" ng-show="serverMessages.length > 0" ng-cloak>
    <%--<img class="error-icon" src="/resources/img/error-icon.png">--%>

    <div class="messages-group">
        <div ng-repeat="msg in serverMessages">
            <p class="bg-{{msg.level}}">{{msg.forElement}} {{msg.value}}</p></div>
    </div>
</div>