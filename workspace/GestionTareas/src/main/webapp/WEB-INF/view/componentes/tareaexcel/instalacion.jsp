<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Datos de la Instalacion - Start -->
<div class="panel panel-default">
    <div class="panel-body">
        <div class="row">
            <app:inputTextNG id="ninstalacion" label="visortarea.ninstalacion"
                             value="tarea.numeroInstalacion" cells="6" cell_label="4" cell_input="8"
                             readonly="true"/>
            <app:inputTextNG id="titular" label="visortarea.titular" value="installationData.titular" cells="6"
                             readonly="true"/>
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