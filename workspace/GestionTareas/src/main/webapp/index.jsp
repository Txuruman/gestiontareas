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
            <li><a href="visortarea.htm?bp_out_chain_id=1&ins_no=111111&bp_agent=12187&bp_out_clname=CL_CCT_ATT_Averia_Test">Tarea Tipo Aviso (aviso:10267236)</a></li>
            <li><a href="visortarea.htm?ins_no=111111&bp_out_chain_id=1&bp_agent=12187&bp_out_clname=CL_TAREAS_DIY">Tarea Mantenimiento</a></li>
            <li>Tarea tipo Excel
                <ul>
                    <li><a href="visortarea.htm?ins_no=111111&bp_agent=12187&bp_out_chain_id=1&bp_out_clname=CL_CCT_XLS_ASSISTANT">Tarea Tipo Excel Listado Assistant</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&bp_agent=12187&bp_out_chain_id=1&bp_out_clname=CL_CCT_XLS_ENCUESTAS_MTOS">Tarea Tipo Excel Encuestas Mantenimientos</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&bp_agent=12187&bp_out_chain_id=1&bp_out_clname=CL_CCT_XLS_ENCUESTAS_MKT">Tarea Tipo Excel Encuestas Marketing</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&bp_agent=12187&bp_out_chain_id=1&bp_out_clname=CL_CCT_XLS_KEYBOX">Tarea Tipo Excel Keybox</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&bp_agent=12187&bp_out_chain_id=1&bp_out_clname=CL_CCT_XLS_LIMPIEZA_CUOTA">Tarea Tipo Excel Limpieza de cuota</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&bp_agent=12187&bp_out_chain_id=1&bp_out_clname=CL_CCT_XLS_ATC">Tarea Tipo Excel Otras campañas</a></li>
                </ul>
            </li>
        </ul>
    <li>
        Estando el agente sin interacción en curso, decide crear una Tarea o buscar una existente.<br/>
        <a href="searchtarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=&INSTALACION=">Buscar Tareas</a><br/>
        <a href="creartarea.htm">Crear Tarea</a><br/>
    </li>
    <li>
        Estando el agente con una interacción en curso, decide crear una Tarea o buscar una existente.<br/>
        <ul>
            <li><a href="visortarea/creartarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=69696969&INSTALACION=111111">Crear Tarea</a></li>
            <li><a href="searchtarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=69696969&INSTALACION=111111">Buscar Tareas (Instalación con Tareas)</a></li>
            <li><a href="searchtarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=69696969&INSTALACION=222222">Buscar Tareas (Instalación sin Tareas)</a></li>
        </ul>

    </li>
</ul>

<h2><a href="test.htm">test</a></h2>


</body>
</html>