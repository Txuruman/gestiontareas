<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul class="nav nav-tabs nav-justified"> 
    <li class="${fn:contains(pageContext.request.servletPath,'buscar')?'activeTab':''}"><a href="${pageContext.request.contextPath}/search"><spring:message code="tab.buscartarea.title"/></a></li>
    <li class="${fn:contains(pageContext.request.servletPath,'crear')?'activeTab':''}"><a href="${pageContext.request.contextPath}/createtask"><spring:message code="tab.searchTarea.title"/></a></li>
</ul>
