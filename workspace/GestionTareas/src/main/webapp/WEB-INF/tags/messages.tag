<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- http://www.onjava.com/pub/a/onjava/2004/05/12/jsp2part4.html -->
<%@ tag body-content="empty" %>

<%--<div class="form-messages errors {{extraStyles}}" ng-show="serverMessages.length > 0" ng-cloak>--%>
<%--&lt;%&ndash;<img class="error-icon" src="/resources/img/error-icon.png">&ndash;%&gt;--%>

<%--<div class="list-group">--%>
<%--<!-- list-group-item-X: bootstrap, color style--%>
<%--badge: bootstrap, rounded panel--%>
<%--bordel-gray: custom, border--%>
<%--list-group-item-message: custom, message style.--%>
<%---->--%>
<%--<div ng-repeat="msg in serverMessages" class="list-group-item-{{msg.level}} badge bordel-gray list-group-item-message" >--%>
<%--{{msg.forElement}} {{msg.value}}--%>
<%--</div>--%>

<%--</div>--%>
<%--</div>--%>


<div ng-controller="MessagesController">
    <alert ng-repeat="msg in vm.serverMessages" type="{{msg.level}}" close="closeMessage($index)">{{msg.value}}</alert>
</div>
<div class="please-wait-dialog" ng-hide="vm.appReady">
    <img class="please-wait-spinner" src="http://www.mytreedb.com/uploads/mytreedb/loader/ajax_loader_blue_512.gif">
</div>



