<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <app:input id="tipoAviso1" label="eti.visortarea.form.label.tipo" value="${tarea.tipoAviso1}" cells="6" readonly="false"/>

    <app:input id="motivo1" label="eti.visortarea.form.label.motivo" value="${tarea.motivo1}" cells="6" readonly="false"/>

</div>

<div class="spacer_t1"></div>
<div class="row">

    <app:input id="tipoAviso2" value="${tarea.tipoAviso2}" cells="6" readonly="false"/>

    <app:input id="motivo2" value="${tarea.motivo2}" cells="6" readonly="false"/>

</div>

<div class="spacer_t1"></div>
<div class="row">

    <app:input id="tipoAviso3" value="${tarea.tipoAviso3}" cells="6" readonly="false"/>

    <app:input id="motivo3" value="${tarea.motivo3}" cells="6" readonly="false"/>

</div>
<!-- row -->
<div class="spacer_t2"></div>
<div class="row">

    <app:textArea id="observaciones" label="eti.visortarea.form.label.observaciones" value="${tarea.observaciones}" cells="6"/>
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
        <div class="row">
            <label for="ejemplo_email_3"
                   class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">Cierre</label>

            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                <select class="form-control">
                    <option>option1</option>
                    <option>option2</option>
                    <option>option3</option>
                    <option>option4</option>
                    <option>option5</option>
                </select>
            </div>
        </div>
        <!-- subrow -->
        <div class="spacer_t1"></div>
        <div class="row">
            <label for="ejemplo_email_3"
                   class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label labelcent">Datos adicionales de cierre</label>

            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                <select class="form-control">
                    <option>option1</option>
                    <option>option2</option>
                    <option>option3</option>
                    <option>option4</option>
                    <option>option5</option>
                </select>
            </div>
        </div>
        <!-- subrow -->
        <div class="spacer_t2"></div>
        <div class="spacer_t2"></div>

    </div>
</div>
<div class="spacer_t2"></div>




