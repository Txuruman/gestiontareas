<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">

  <app:inputCombo id="motivosCierre" label="eti.visortarea.form.label.motivosCierre" value="${tarea.motivosCierre}" cells="6" readonly="false"/>
  <app:inputCombo id="compensacion" label="eti.visortarea.form.label.compensacion" value="${tarea.compensacion}" cells="6" readonly="false"/>

</div>
<div class="spacer_t2"></div>

<!--
motivosCierre=null,
compensacion='null'
-->