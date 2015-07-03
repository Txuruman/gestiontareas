<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">

  <app:inputCombo id="closingReason" label="eti.visortarea.form.label.closingReason" value="${tarea.closingReason}" cells="6" readonly="false"/>
  <app:input id="compensation" label="eti.visortarea.form.label.compensation" value="${tarea.compensation}" cells="6" readonly="false"/>

</div>
<div class="spacer_t1"></div>

<!--
closingReason=null,
compensation='null'
-->