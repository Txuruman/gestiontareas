<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">


    <app:inputCombo id="tipoAviso1" label="eti.visortarea.form.label.tipo" value="${tarea.tipoAviso1}" cells="6" readonly="false"/>

    <app:inputCombo id="motivo1" label="eti.visortarea.form.label.motivo" value="${tarea.motivo1}" cells="6" readonly="false"/>

</div>

<div class="spacer_t1"></div>
<div class="row">

    <app:inputCombo id="tipoAviso2" value="${tarea.tipoAviso2}" cells="6" readonly="false"/>

    <app:inputCombo id="motivo2" value="${tarea.motivo2}" cells="6" readonly="false"/>

</div>

<div class="spacer_t1"></div>
<div class="row">

    <app:inputCombo id="tipoAviso3" value="${tarea.tipoAviso3}" cells="6" readonly="false"/>

    <app:inputCombo id="motivo3" value="${tarea.motivo3}" cells="6" readonly="false"/>

</div>
<!-- row -->
<div class="spacer_t1"></div>
<div class="row">

    <app:textArea id="observaciones" label="eti.visortarea.tareaavisos.form.label.observaciones" value="${tarea.motivo3}" cells="6"/>

    <app:inputCombo id="cierre" label="eti.visortarea.tareaavisos.form.label.cierre" cells="6" value="${tarea.motivo3}" readonly="false"/>

    <div class="spacer_t1"></div>
    <div class="spacer_t1"></div>
    <div class="spacer_t1"></div>
    <div class="spacer_t1"></div>
    <div class="spacer_t1"></div>
    <div class="spacer_t1"></div>
    <app:inputCombo id="adicionalesCierre" label="eti.visortarea.tareaavisos.form.label.adicionalesCierre" value="${tarea.motivo3}" cells="6" readonly="false"/>
        <!-- subrow -->

    <div class="spacer_t1"></div>


</div>
<div class="spacer_t2"></div>




