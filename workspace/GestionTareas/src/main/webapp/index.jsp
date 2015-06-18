<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<html>
<head>

    <title>Welcome</title>
</head>
<body>
<my:input question="hola" dsfsd=""/>
Enlaces
<ul>
    <li>
        Estando el agente disponible, le llega una interacción de tipo Tarea para gestionarla.
        <ul>
            <li><a href="visortarea.htm?ins_no=111111&idaviso=10267236">Tarea Tipo Aviso (aviso:10267236)</a></li>
            <li><a href="visortarea.htm?ins_no=111111&tipotarea=mantenimiento">Tarea Mantenimiento</a></li>
            <li>Tarea tipo Excel
                <ul>
                    <li><a href="visortarea.htm?ins_no=111111&tipotarea=listadoassistant">Tarea Tipo Excel Listado Assistant</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&tipotarea=encuestasmantenimientos">Tarea Tipo Excel Encuestas Mantenimientos</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&tipotarea=encuestasmarketing">Tarea Tipo Excel Encuestas Marketing</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&tipotarea=keybox">Tarea Tipo Excel Keybox</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&tipotarea=limpiezacuota">Tarea Tipo Excel Limpieza de cuota</a></li>
                    <li><a href="visortarea.htm?ins_no=111111&tipotarea=otrascampanias">Tarea Tipo Excel Otras campañas</a></li>
                </ul>
            </li>
        </ul>
    <li>
        Estando el agente sin interacción en curso, decide crear una Tarea o buscar una existente.<br/>
        <a href="buscartarea.htm?AGENTELOGADO=7777&NUMEROTELEFONO=&INSTALACION=">Buscar Tareas</a><br/>
        <a href="creartarea.htm">Crear Tarea</a><br/>
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


<p>
    <my:input question="Will you start using tag files?" />
</p>

</body>
</html>