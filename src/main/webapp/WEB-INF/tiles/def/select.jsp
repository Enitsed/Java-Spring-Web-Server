<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>

<%-- 파라미터 정보 획득 --%>
<c:set var="name"><tiles:getAsString name="name" /></c:set>

<c:set var="values"><tiles:getAsString name="values" ignore="true" /></c:set>
<c:if test="${empty values}"><c:set var="values" value="20,40,60,80,100" /></c:if>
<c:set var="values" value="${fn:split(values, ',')}" />

<c:set var="names"><tiles:getAsString name="names" ignore="true" /></c:set>
<c:if test="${empty names}"><c:set var="names" value="20,40,60,80,100" /></c:if>
<c:set var="names" value="${fn:split(names, ',')}" />

<c:set var="nameSuffix"><tiles:getAsString name="nameSuffix" ignore="true" /></c:set>

<c:set var="onchange"><tiles:getAsString name="onchange" ignore="true" /></c:set>
<c:if test="${not empty onchange}"><c:set var="onchange">onchange="${onchange}(this)"</c:set></c:if>

<c:set var="selectedValue"><tiles:getAsString name="selectedValue" ignore="true" /></c:set>
<c:if test="${empty selectedValue}"><c:set var="selectedValue" value="${param[name]}" /></c:if>




<%-- 인터페이스 구성 --%>
<select name="${name}" ${onchange}>
	<c:forEach begin="0" end="${fn:length(values) - 1}" var="i">
		<option value="${values[i]}" <c:if test="${values[i] eq selectedValue}">selected</c:if>>${names[i]}${nameSuffix}</option>
	</c:forEach>
</select>