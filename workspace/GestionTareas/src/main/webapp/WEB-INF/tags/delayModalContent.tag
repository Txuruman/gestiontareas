<%@ tag body-content="empty" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- El id se hace referencia en el controller -->
<script type="text/ng-template" id="deplayModalContent.html">
    <div class="modal-header">
        <h3 class="modal-title"> <spring:message code='titulo.deplay.modal'/> </h3>
    </div>
    <div class="modal-body">
        <div class="row">
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                <spring:message code='delay.recallType'/>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <select ng-model="delayInfo.recallType" >
                    <option value="P">Personal</option>
                    <option value="C">Campa&nacute;a</option>
                </select>
            </div>
        </div>

        <div class="spacer_t1"></div>

        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <spring:message code='deplay.dateTime'/>
            </div>
        </div>

        <div class="spacer_t1"></div>

        <div class="row">
            <div class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
                <div style="display:inline-block; min-height:290px;">
                    <datepicker ng-model="delayInfo.delayDate" min-date="today" show-weeks="true" class="well well-sm" custom-class="getDayClass(date, mode)"></datepicker>
                </div>

            </div>
            <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
                <timepicker ng-model="delayInfo.delayTime"  hour-step="1" minute-step="5" show-meridian="true"></timepicker>
            </div>
        </div>
        <!-- Test
        <ul>
            <li ng-repeat="item in items">
                <a ng-click="selected.item = item">{{ item }}</a>
            </li>
        </ul>
        Seleccionado: <b>{{ selected.item }}</b>-->
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" ng-click="ok()"><spring:message code='boton.ok'/></button>
        <button class="btn btn-warning" ng-click="cancel()"><spring:message code='boton.cancel'/></button>
    </div>
</script>