<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<html ng-app>
<head>
    <title>Welcome</title>
</head>
<body>


Enlaces
<ul>
    <li>
        Estando el agente disponible, le llega una interacción de tipo Tarea para gestionarla.
        <ul>
            <li><a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_GSW_CHAIN_ID_CUSTOM=0&bp_out_clname=CL_CCT_ATC_CRA&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Tipo Aviso</a></li>
            <li><a href="entry?bp_auth_requestDate=201509021201&bp_callingListManagedDesktop=CL_CCT_ATT_Averia_FastI&bp_out_GSW_CAMPAIGN_NAME=C_CCT_ATT_Averia_FastI&bp_out_GSW_CHAIN_ID=1&bp_out_instalacion=1606430&bp_out_contact_info=609051985&bp_currentLanguage=es&bp_agentPlace=P17023&bp_out_ctr_no=1606430&bp_interactionType=PREVIEW&bp_connid=Id003&bp_desktopDepartment=ATC_SPN&bp_out_clname=CL_CCT_ATT_Averia_FastI&bp_interactionDirection=INTERNAL&bp_agent=12171&bp_auth_connid=Id003&bp_agentIBS=I24311&bp_agentGroupSD=SD_CALLBACK&bp_auth_signature=CGba8nhPxuM5a3+yzGOpEnZjtFE=&bp_auth_ipAddress=192.168.5.110&bp_agentCountryJob=SPAIN&bp_agentUserSD=I24311_2&bp_out_GSW_CHAIN_ID_CUSTOM=1&bp_agentGroupOutService=OUT_SERVICE_CC_Clas_Rec&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Tipo Aviso 2</a></li>
            <li><a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_GSW_CHAIN_ID_CUSTOM=1&bp_out_clname=CL_TAREAS_DIY&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Mantenimiento</a></li>
            <li>Tarea tipo Excel
                <ul>
                    <li><a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_GSW_CHAIN_ID_CUSTOM=1&bp_out_clname=CL_CCT_XLS_ASSISTANT&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Tipo Excel Listado Assistant</a></li>
                    <li><a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_GSW_CHAIN_ID_CUSTOM=1&bp_out_clname=CL_CCT_XLS_ENCUESTAS_MTOS&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Tipo Excel Encuestas Mantenimientos</a></li>
                    <li><a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_GSW_CHAIN_ID_CUSTOM=2&bp_out_clname=CL_CCT_XLS_ENCUESTAS_MKT&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Tipo Excel Encuestas Marketing</a></li>
                    <li><a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_GSW_CHAIN_ID_CUSTOM=1&bp_out_clname=CL_CCT_XLS_KEYBOX&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Tipo Excel Keybox</a></li>
                    <li><a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_GSW_CHAIN_ID_CUSTOM=2&bp_out_clname=CL_CCT_XLS_LIMPIEZA_CUOTA&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Tipo Excel Limpieza de cuota</a></li>
                    <li><a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_GSW_CHAIN_ID_CUSTOM=1&bp_out_clname=CL_CCT_XLS_ATC&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Tarea Tipo Excel Otras campañas</a></li>
                </ul>
            </li>
        </ul>
    <li>
        Estando el agente con una interacción en curso.<br/>
        <ul>
            <li><a href="entry?bp_agent=12187&bp_agentIBS=I24311&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_instalacion=1599716&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Entrar (Instalación con Tareas)</a></li>
            <li><a href="entry?bp_agent=12187&bp_agentIBS=I24311&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_instalacion=111111&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Entrar (Instalación sin Tareas)</a></li>
        </ul>
    </li>
    <li>
        Estando el agente sin interacción en curso.<br/>
        <a href="entry?bp_agent=12187&bp_agentIBS=M0OOS&bp_agentCountryJob=SPAIN&bp_desktopDepartment=ATC&bp_out_contact_info=1&bp_out_GSW_CAMPAIGN_NAME=1&bp_out_GSW_RECORD_HANDLE=6&AGENT_PLACE=PL123456">Entrar</a><br/>
    </li>
    <li>
        Consulta del estado de la aplicaci&oacute;n.<br/>
        <a href="happy">Status</a><br/>
    </li>
</ul>

<h2><a href="test.htm">test</a></h2>


</body>
</html>