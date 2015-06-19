<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <app:input id="tipoCampana" label="eti.visortarea.form.label.otras.tipoCampana" value="${tarea.tipoCampana}" cells="6" readonly="true"/>
    <app:input id="comentario" label="eti.visortarea.form.label.otras.comentario" value="${tarea.comentario}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="campo1" label="eti.visortarea.form.label.otras.campo1" value="${tarea.campo1}" cells="6" readonly="true"/>
    <app:input id="campo2" label="eti.visortarea.form.label.otras.campo2" value="${tarea.campo2}" cells="6" readonly="true"/>
</div>
<div class="spacer_t1"></div>
<div class="row">
    <app:input id="campo3" label="eti.visortarea.form.label.otras.campo3" value="${tarea.campo3}" cells="6" readonly="true"/>
</div>



<!-- InstallationData : InstallationData{
numeroInstalacion=111111,
titular='ARATHERMIK S.L.',
panel='SD 2000C', version='null',
personaContacto='JOSE RAMON ARAGON ARRONTES'
}
Tarea : TareaOtrasCampanas{
tipoCampana='null',
comentario='null',
campo1='null',
campo2='null',
campo3='null',

motivosCierre=null,
compensacion='null'} -->