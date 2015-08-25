<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Datos de la Instalacion - Start -->
<div class="panel panel-default">
    <div class="panel-body">
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                <app:inputTextNG id="ninstalacion" label="createtask.installationnumber"
                                 value="tarea.numeroInstalacion"
                                 cells="0" ng_keypress="($event.keyCode===13 && tarea.numeroInstalacion!='') ? getIntallation() : null"/>
                <button type="button" class="btn btn-default btn-sm botonAbsolute" title="<spring:message code="boton.search"/>" ng-click="getIntallation()">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </button>
           </div>
            <app:inputTextNG id="titular" label="visortarea.titular" value="installationData.titular"  readonly="true" cells="6"/>
        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <app:inputTextNG id="panel" label="visortarea.panel" value="installationData.panel" cells="6"
                             readonly="true"/>
            <app:inputTextNG id="version" label="visortarea.version" value="installationData.version" cells="6"
                             readonly="true"/>
        </div>
              
    </div>
</div>
<!-- Datos de la Instalacion  - End -->