<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- http://www.onjava.com/pub/a/onjava/2004/05/12/jsp2part4.html -->
<%@ tag body-content="empty" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="label" required="false" type="java.lang.String" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="cells" required="false" description="Number of cells used from the 12 available" %>
<%@ attribute name="required" required="false" description="If the field is required true/false" %>
<%@ attribute name="readonly" required="false" description="If the field is readonly true/false" %>
<%@ attribute name="maxlength" required="false" description="The maximum number of characters allowed in the <input> element" %>
<%@ attribute name="cell_label" required="false"  description="The maximun cells label in parent space" type="java.lang.Integer" %>
<%@ attribute name="cell_input" required="false"  description="The maximun cells label in parent space" %>
<%@ attribute name="type" required="false"  description="The maximun cells label in parent space" %>
<%@ attribute name="ng_keypress" required="false"  description="KeyPress event" %>
<%@ attribute name="form" required="false"  description="form of the input" %>
<%@ attribute name="only" required="false"  description="only numbers inputText" %>
<%@ attribute name="size" required="false"  description="only numbers inputText" %>
<%@ attribute name="hour" required="false"  description="only numbers inputText" %>
<%@ attribute name="ng_disabled" required="false"  description="input ng-disabled" %>
<%@ attribute name="pattern" required="false"  description="pattern for the input" %>

<c:if test="${cell_label == null}">
    <c:set var="cell_label" value="4"/>
</c:if>

<c:if test="${cell_input == null}">
    <c:set var="cell_input" value="8"/>
</c:if>

<c:if test="${cell_label + cell_input > 12}">
    <c:set var="cell_label" value="4"/>
    <c:set var="cell_input" value="8"/>
    <c:out value="Por favor introducir valores que sumen entre el label y el input un total de maximpo 12 "/>
</c:if>

<c:if test="${empty type}">
    <c:set var="type" value="text" />
</c:if>

<c:if test="${type=='number' && empty pattern}">
    <c:set var="pattern" value="[0-9]+([\.,][0-9]+)*" />
</c:if>



<div class="col-lg-${cells} col-md-${cells} col-sm-${cells} col-xs-${cells}">
    <label class="col-lg-${cell_label} col-md-${cell_label} col-sm-${cell_label} col-xs-${cell_label} control-label labelcent ${required=="true" ? "*" : ""}">
        <c:if test="${not empty label}"> <spring:message code="${label}"/> ${required=="true" ? "*" : ""}:</c:if>
    </label>

    
    <div class="col-lg-${cell_input} col-md-${cell_input} col-sm-${cell_input} col-xs-${cell_input}">
        <input type="${type}"
               id="${id}"
               name="${id}"
               class="form-control input-custom-global"
               ng-model="${value}" 
               <c:if test="${not empty maxlength}">
               		maxlength="${maxlength}"
               </c:if>
               <c:if test="${not empty pattern}">
              		pattern="${pattern}"
               </c:if>
               <c:if test="${not empty ng_keypress}">
               		ng-keypress="<c:out value="${ng_keypress}"/>"
           		</c:if>
           		<c:if test="${not empty readonly}">
               		readonly="<c:out value="${readonly}"/>"
           		</c:if>
           		<c:if test="${not empty required}">
           			ng-required="<c:out value="${required}"/>"
           		</c:if>	
           		<c:if test="${not empty only}">
           			only-number
           		</c:if>
           		<c:if test="${not empty size}">
           			number-size="<c:out value="${size}"/>"
           		</c:if>
           		<c:if test="${not empty hour}">
           			hour="<c:out value="${hour}"/>"
           		</c:if>
           		<c:if test="${not empty ng_disabled}">
           			ng-disabled="<c:out value="${ng_disabled}"/>"
           		</c:if>	
           		<c:if test="${not empty pattern}">
           			pattern="<c:out value="${pattern}"/>"
           		</c:if>		/>
           		  
           		<c:if test="${not empty required}">
           			<span class="error" ng-show="${form}.${id}.$error.required && mostrarAvisos!=false"><spring:message code="error.notext"/></span>
           		</c:if><c:if test="${not empty hour}">
           			<span class="error" ng-show="${form}.${id}.$error.pattern && mostrarAvisos!=false"> - <spring:message code="error.pattern.hour"/></span>
           		</c:if> 	 
           		  
    </div>
</div>