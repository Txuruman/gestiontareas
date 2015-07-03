<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="comunes.jsp"/>
<div class="row">

    <app:inputText id="date" label="marketingsurvey.date" value="${tarea.date}" cells="6" readonly="true"/>
    <app:inputText id="reason" label="eti.visortarea.form.label.reason" value="${tarea.reason}" cells="6" readonly="true"/>
</div>

<!-- InstallationData : InstallationData{
numeroInstalacion=111111,
titular='ARATHERMIK S.L.',
panel='SD 2000C',
version='null',
personaContacto='JOSE RAMON ARAGON ARRONTES'
}
Tarea : TareaEncuestaMarketing{
fecha=Fri Jun 26 10:51:51 CEST 2015,
motivo='null',
closingReason=null,
compensation='null'
}
Parametros: {ins_no=111111,
tipotarea=TareaEncuestaMarketing
} -->