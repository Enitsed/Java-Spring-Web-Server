<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>


<tiles:importAttribute name="item" />

<div style="border:1px solid black; padding:5px; width:200px;">
	<img src="/images/item${item.mstImgPath}" alt="${item.itemNm}" title="${item.itemNm}" width="200px" height="200px;" />
	<div style="text-align:left;">
		[${item.itemSeq}]
		${item.itemNm}
	</div>
	<div style="text-align:left;">
		${item.descShort}
	</div>
	<div style="text-align:left;">
		<fmt:formatNumber value="${item.price}" pattern="#,###" />원
		
		<c:if test="${item.price ne item.priceDc}">
			-> 
			<fmt:formatNumber value="${item.priceDc}" pattern="#,###" />원
			(${item.dcRate}%)
		</c:if>
	</div>
</div>
