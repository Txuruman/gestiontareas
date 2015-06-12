<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>

    <title>Welcome</title>
</head>
<body>

Enlaces
<ul>
    <li>
        Estando el agente disponible, le llega una interacción de tipo Tarea para gestionarla.
        <ul>
            <li><a href="/VisorTareaController">Tarea Tipo Aviso</a></li>
            <li><a href="/VisorTareaController">Tarea Mantenimiento</a></li>
            <li>Tarea tipo Excel
                <ul>
                    <li>Tarea Tipo Excel Listado Assistant</li>
                    <li>Tarea Tipo Excel Encuestas Mantenimientos</li>
                    <li>Tarea Tipo Excel Encuestas Marketing</li>
                    <li>Tarea Tipo Excel Keybox</li>
                    <li>Tarea Tipo Excel Limpieza de cuota</li>
                    <li>Tarea Tipo Excel Otras campañas</li>
                </ul>
            </li>
        </ul>
    <li>
        Estando el agente sin interacción en curso, decide crear una Tarea o buscar una existente.<br/>
        <a href="buscartarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=&INSTALACION=">Buscar Tareas</a><br/>
        <a href="buscartarea.htm">Crear Tarea</a><br/>
    </li>
    <li>
        Estando el agente con una interacción en curso, decide crear una Tarea o buscar una existente.<br/>
        <ul>
            <li><a href="/creartarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=69696969&INSTALACION=111111">Crear Tarea</a></li>
            <li><a href="buscartarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=69696969&INSTALACION=111111">Buscar Tareas (Instalación con Tareas)</a></li>
            <li><a href="buscartarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=69696969&INSTALACION=222222">Buscar Tareas (Instalación sin Tareas)</a></li>
        </ul>

    </li>
</ul>
<%--<% response.sendRedirect("inicio.htm"); %>--%>

</body>
</html>
