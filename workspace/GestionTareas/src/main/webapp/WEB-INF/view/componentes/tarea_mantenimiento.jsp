<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row development">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 development">
        <div class="row development">
            <app:input id="contrato" label="tareamantenimiento.contrato" value="${tareamantenimiento.contrato}"  readonly="true"/>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 development">
        <div class="row development">
            <app:input id="tipificacion" label="tareamantenimiento.tipificacion" value="${tareamantenimiento.tipificacion}"  readonly="true"/>
        </div>
    </div>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 development">
        <div class="row">
            <app:input id="direccion" label="tareamantenimiento.direccion" value="${tareamantenimiento.direccion}" readonly="true"/>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 development">
        <div class="row">
            <app:input id="fechaEvento" label="tareamantenimiento.fechaEvento" value="${tareamantenimiento.fechaEvento}"  readonly="true"/>
        </div>
    </div>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 development">
        <div class="row">
            <app:input id="ciudad" label="tareamantenimiento.ciudad" value="${tareamantenimiento.ciudad}" readonly="true"/>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 development">
        <div class="row">
            <app:input id="agenteAsignado" label="tareamantenimiento.agenteAsignado" value="${tareamantenimiento.agenteAsignado}" readonly="true"/>
        </div>
    </div>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 development">
        <div class="row">
            <app:inputCombo id="opcionTipificacion" label="tareamantenimiento.opcionTipificacion" value="${tareamantenimiento.opcionTipificacion}" readonly="false"/>
        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 development">
            </div>
            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 development">
                <app:input id="telefono1" label="tareamantenimiento.telefono1" value="${tareamantenimiento.telefono1}" readonly="false" />
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <input type="button" class="btn btn-xs" value="<spring:message code="tareamantenimiento.llamartelefono1"/>"/>
            </div>
        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 development">
            </div>
            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 development">
                <app:input id="telefono2" label="tareamantenimiento.telefono2" value="${tareamantenimiento.telefono2}" readonly="false" />
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <input type="button" class="btn btn-xs" value="<spring:message code="tareamantenimiento.llamartelefono2"/>"/>
            </div>
        </div>
        <div class="spacer_t1"></div>
        <div class="row">
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 development">
            </div>
            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 development">
                <app:input id="telefono3" label="tareamantenimiento.telefono3" value="${tareamantenimiento.telefono3}" readonly="false" />
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                <input type="button" class="btn btn-xs" value="<spring:message code="tareamantenimiento.llamartelefono3"/>"/>
            </div>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 development">
        <div class="row">
            <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 development">
            </div>
            <div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 development">
                <app:textArea id="decripcion" label="tareamantenimiento.descripcion" value="${tareamantenimiento.descripcion}" />
            </div>
        </div>
    </div>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <div class="row">
            <app:inputCombo id="desplegableKey1" label="tareamantenimiento.key1" value="${tareamantenimiento.key1}" readonly="false"/>
        </div>
    </div>
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <div class="row">
            <app:inputCombo id="desplegableKey2" label="tareamantenimiento.key2" value="${tareamantenimiento.key2}" readonly="false"/>
        </div>
    </div>
</div>


<!--
InstallationData : InstallationData{
numeroInstalacion=111111,
titular='ARATHERMIK S.L.',
panel='SD 2000C', version='null',
personaContacto='JOSE RAMON ARAGON ARRONTES'
}
Tarea : TareaMantenimiento{
contrato='null',
direccion='null',
ciudad='null',
fechaEvento=Mon Jun 22 10:44:57 CEST 2015,
tipificacion='null',
agenteAsignado='null',
agenteCierre='null',
opcionTipificacion=0,
key1=0,
key2=0
}
-->
<!--
tickets.tareamantenimiento.contrato = Contrato
tickets.tareamantenimiento.direccion = Direcci\u00f3n
tickets.tareamantenimiento.ciudad =
tickets.tareamantenimiento.fechaEvento =
tickets.tareamantenimiento.tipificacion = Tipo mantenimiento
tickets.tareamantenimiento.agenteAsignado =
tickets.tareamantenimiento.agenteCierre =
tickets.tareamantenimiento.opcionTipificacion =
tickets.tareamantenimiento.key1 =
tickets.tareamantenimiento.key2 =
-->