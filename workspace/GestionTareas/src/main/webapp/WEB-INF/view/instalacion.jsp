<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Datos de la Instalacion - Start -->
<div class="panel panel-default">
    <div class="panel-body">
        <div class="row">
            <app:wrapping cells="6">
                <app:inputTextNG id="ninstalacion" label="createtask.installationnumber"
                                 value="tarea.numeroInstalacion"
                                 readonly="true" cells="10"/>
                <app:inputButtonNG button_type="xs" value="createtask.button.searchinstallation" cells="2"/>
            </app:wrapping>
            <app:inputTextNG id="titular" label="visortarea.titular" value="installationData.titular"  readonly="true" cells="6"/>
        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <app:inputTextNG id="personaContacto" label="visortarea.personacontacto"
                             value="tarea.personaContacto" cells="6" readonly="true"/>
            <app:inputTextNG id="panel" label="visortarea.panel" value="installationData.panel" cells="6"
                             readonly="true"/>
        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <app:inputTextNG id="telefono" label="visortarea.telefono" value="tarea.telefono" cells="6"
                             readonly="true"/>
            <app:inputTextNG id="version" label="visortarea.version" value="installationData.version" cells="6"
                             readonly="true"/>
        </div>
        <!-- Datos de la Instalacion  - End -->
    </div>
</div>