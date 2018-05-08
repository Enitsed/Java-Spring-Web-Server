<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>


상품 상세
<hr>
<table style="width:100%" class="table table-bordered">
	<tr>
		<td style="width:150px" class="center">상품명</td>
		<td><c:out value="${data.itemNm}" /></td>
	</tr>
	<tr>
		<td class="center">검색어</td>
		<td><c:out value="${data.searchKeyword}" /></td>
	</tr>
	<tr>
		<td class="center">상품간략설명</td>
		<td><c:out value="${data.descShort}" />
	</tr>
	<tr>
		<td class="center">상품설명</td>
		<td>${data.descLong}</td>
	</tr>
	<tr>
		<td class="center">대표카테고리</td>
		<td>${data.ctgMst.ctgPathNm}</td>
	</tr>
	<tr>
		<td class="center">전시카테고리</td>
		<td>
			<c:forEach items="${data.ctgListDisp}" var="i" varStatus="s">
				<div class="ctgSelectInfo${i.ctgSeq}">${i.ctgPathNm}</div>	
			</c:forEach>			
		</td>
	</tr>
	<tr>
		<td class="center">상품상태</td>
		<td>
			<c:choose>
				<c:when test="${data.itemStat eq 'NORMAL'}">정상</c:when>
				<c:when test="${data.itemStat eq 'SOLDOUT'}">품절</c:when>
				<c:when test="${data.itemStat eq 'STOP'}">판매중지</c:when>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="center">전시상태</td>
		<td>
			<c:choose>
				<c:when test="${data.itemDispStat eq 'DISP'}">전시</c:when>
				<c:when test="${data.itemDispStat eq 'NODISP'}">미전시</c:when>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="center">전체재고수량</td>
		<td><fmt:formatNumber value="${data.stockCntTot}" pattern="#,###" /></td>
	</tr>
	<tr>
		<td class="center">전체판매수량</td>
		<td><fmt:formatNumber value="${data.saleCntTot}" pattern="#,###" /></td>
	</tr>
	<tr>
		<td class="center">최소판매수량</td>
		<td><fmt:formatNumber value="${data.sellCntMin}" pattern="#,###" /></td>
	</tr>
	<tr>
		<td class="center">최대판매수량</td>
		<td><fmt:formatNumber value="${data.sellCntMax}" pattern="#,###" /></td>
	</tr>
	<tr>
		<td class="center">판매가</td>
		<td><fmt:formatNumber value="${data.price}" pattern="#,###" /></td>
	</tr>
	<tr>
		<td class="center">할인가</td>
		<td><fmt:formatNumber value="${data.priceDc}" pattern="#,###" /></td>
	</tr>
	<tr>
		<td class="center">대표이미지</td>
		<td>
			<img src='/images/item${data.imgMst.filepath}' width='100' height='100' />
		</td>
	</tr>
	<tr>
		<td class="center">상세이미지</td>
		<td>
			<c:forEach items="${data.imgSubList}" var="i" varStatus="s">
				<img src='/images/item${i.filepath}' width='100' height='100' />
			</c:forEach>
		</td>
	</tr>
</table>


<div class="center" style="padding:10px;">
	<a href="/item/itemList.do?<core:param skip="itemSeq" />">[목록]</a>
	<a href="/item/itemForm.do?<core:param skip="mode" addParamStr="mode=update" />">[수정]</a>
</div>


<script>

$(document).ready(function(evt){
});


</script>
