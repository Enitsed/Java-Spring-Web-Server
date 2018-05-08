<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>

<%--  처음 이동 버튼. --%>
<%-- <c:if test="${listResult.page ne 1}"> --%>
<%-- 	<a class="btn-page-prev" href="?${paramPage}=1${paramListSize}${paramPageSize}${paramStr}"><span>처음</span></a> --%>
<%-- </c:if> --%>

<%--  이전 N개 페이지 이동 버튼. --%>
<%-- <c:if test="${listResult.page > listResult.pageSize}"> --%>
<%-- 	<a class="btn-page-prev" href="?${paramPage}=${listResult.startPage-listResult.pageSize}${paramListSize}${paramPageSize}${paramStr}"><span>이전 ${listResult.pageSize}개</span></a> --%>
<%-- </c:if> --%>

<%--  이전 이동 버튼. --%>
<c:if test="${listResult.page ne 1}">
	<li class="page-item">
		<a class="page-link btn-page-prev" href="?${paramPage}=${listResult.page-1}${paramListSize}${paramPageSize}${paramStr}"><span>이전</span></a>
	</li>
</c:if>

<%--  페이지 이동 버튼. --%>
<c:if test="${listResult.totalPage != 1 && listResult.totalPage != 0}">
	<c:forEach begin="${listResult.startPage}" end="${listResult.endPage}" var="i" varStatus="s">
		<li class="page-item">
			<a class="page-link btn-page-num ${listResult.page == i ? 'here' : ''}" href="?${paramPage}=${i}${paramListSize}${paramPageSize}${paramStr}"><span>${i}</span></a>
		</li>
	</c:forEach>
</c:if>

<%--  다음 이동 버튼. --%>
<c:if test="${listResult.page < listResult.totalPage}">
	<li class="page-item">
		<a class="page-link btn-page-next" href="?${paramPage}=${listResult.page+1}${paramListSize}${paramPageSize}${paramStr}"><span>다음</span></a>
	</li>
</c:if>

<%--  다음 N개 페이지 이동 버튼. --%>
<%-- <c:if test="${listResult.endPage < listResult.totalPage}"> --%>
<%-- 	<a class="btn-page-next" href="?${paramPage}=${listResult.startPage+listResult.pageSize}${paramListSize}${paramPageSize}${paramStr}"><span>다음 ${listResult.pageSize}개</span></a> --%>
<%-- </c:if> --%>

<%--  마지막 이동 버튼. --%>
<%-- <c:if test="${listResult.page ne listResult.totalPage and listResult.totalPage > 1}"> --%>
<%-- 	<a class="btn-page-next" href="?${paramPage}=${listResult.totalPage}${paramListSize}${paramPageSize}${paramStr}"><span>마지막</span></a> --%>
<%-- </c:if> --%>

