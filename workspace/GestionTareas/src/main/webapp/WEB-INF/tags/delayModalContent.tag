<%@ tag body-content="empty" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- El id se hace referencia en el controller -->
<div type="text/ng-template" data-cached-template="deplayModalContent.html">
    <div class="modal-header">
        <h3 class="modal-title"> <spring:message code='titulo.deplay.modal'/> </h3>
    </div>
    <div class="modal-body">
        <div class="row">
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                <spring:message code='delay.recallType'/>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <select ng-model="delayInfo.recallType" ng-change="withoutChanges=false">
                    <option value="5" selected><spring:message code='delay.personal'/></option>
                    <option value="6"><spring:message code='delay.campaign'/></option>
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
                    <datepicker ng-model="delayInfo.delayDate" min-date="today" show-weeks="true" class="well well-sm" custom-class="getDayClass(date, mode)" ng-changed="withoutChanges=false"></datepicker>
                </div>

            </div>
            <div class="col-lg-5 col-md-5 col-sm-5 col-xs-5">
                <timepicker ng-model="delayInfo.delayTime"  hour-step="1" minute-step="5" show-meridian="true" ng-changed="withoutChanges=false"></timepicker>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" ng-click="ok()" ng-disabled="withoutChanges"><spring:message code='boton.ok'/></button>
        <button class="btn btn-warning" ng-click="cancel()"><spring:message code='boton.cancel'/></button>
    </div>
</div>