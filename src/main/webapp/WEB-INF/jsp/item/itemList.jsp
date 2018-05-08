<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>


<p>상품 목록</p>
<hr />
<form name="searchForm" action="?">
	<table class="table table-bordered">
		<tr>
			<td>전시카테고리</td>
			<td colspan="3">
				<input type="hidden" name="ctgPath" value="<c:out value="${param.ctgPath}" />" />
				<span class="ctgSelectArea"></span>
			</td>
		</tr>
		<tr>
			<td>상품상태</td>
			<td>
				<select name="itemStat">
					<option value="">전체</option>
				<option value="NORMAL" <c:if test="${param.itemStat eq 'NORMAL'}">selected</c:if>>정상</option>
				<option value="SOLDOUT" <c:if test="${param.itemStat eq 'SOLDOUT'}">selected</c:if>>품절</option>
				<option value="STOP" <c:if test="${param.itemStat eq 'STOP'}">selected</c:if>>판매중지</option>
				</select>
			</td>
			<td>전시상태</td>
			<td>
				<select name="itemDispStat">
					<option value="">전체</option>
				<option value="DISP" <c:if test="${param.itemDispStat eq 'DISP'}">selected</c:if>>전시</option>
				<option value="NODISP" <c:if test="${param.itemDispStat eq 'NODISP'}">selected</c:if>>미전시</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>상품번호</td>
			<td>
				<input type="text" name="itemSeq" value="<c:if test="${param.itemSeq ne 0}"><c:out value="${param.itemSeq}" /></c:if>" />
			</td>
			<td>검색조건</td>
			<td>
				<select name="searchKeyword">
					<option value="">선택</option>
					<option value="itemNm" <c:if test="${param.keyField eq 'itemNm'}">selected</c:if>>상품명</option>
					<option value="descShort" <c:if test="${param.keyField eq 'descShort'}">selected</c:if>>간략설명</option>
				</select>
			<input type="text" name="keyWord" value="<c:out value="${param.keyWord}" />" />
			</td>
		</tr>
	</table>
</form>
<div class="center m-5">
	<button class="btn" onclick="search()">검색</button>
	<button class="btn" onclick="location.href='/item/itemForm.do'">등록</button>
</div>
<table class="table table-bordered">
	<tr>
		<th> 번호 </th>
		<th> 상품번호 </th>
		<th> 상품명 </th>
		<th> 상품상태 </th>
		<th> 전시상태 </th>
		<th> 판매가 </th>
		<th> 할인가 </th>
		<th> 작성자 </th>
		<th> 작성일 </th>
		<th> 수정자 </th>
		<th> 수정일 </th>
	</tr>
	<c:forEach items="${result.list }" var="item" varStatus="s">
		<tr>
			<td>
				<c:out value="${result.totalCount - (result.page - 1) * result.listSize - s.index}" />
			</td>
			<td>
				<c:out value="${item.itemSeq }"></c:out>
			</td>
			<td>
				<a href="/item/itemView.do?<core:param skip="itemSeq" addParamStr="itemSeq=${item.itemSeq}" />">${item.itemNm}</a>
			</td>
			<td>
				<c:out value="${item.itemStat }"></c:out>
			</td>
			<td>
				<c:out value="${item.itemDispStat }"></c:out>
			</td>
			<td>
				<fmt:formatNumber><c:out value="${item.price }"></c:out></fmt:formatNumber>
			</td>
			<td>
				<fmt:formatNumber><c:out value="${item.priceDc }"></c:out></fmt:formatNumber>
			</td>
			<td>
				<c:out value="${item.regNm }"></c:out>
			</td>
			<td>
				<core:mask pattern="####.##.##">${item.regDt }</core:mask>
			</td>
			<td>
				<c:out value="${item.modNm} "></c:out>
			</td>
			<td>
				<core:mask pattern="####.##.##">${item.modDt }</core:mask>
			</td>
		</tr>
	</c:forEach>
	
	<c:if test="${not empty result.list && result.totalPage ne 1}">
		<tr>
			<td colspan="12">
				<ul class="pagination justify-content-center">
					<core:pagination listResultName="result" />
				</ul>
			</td>
		</tr>
	</c:if>
</table>

<script>
// 검색 버튼 클릭 이벤트 핸들
function search(){
	var data = $("form[name=searchForm]").serializeObject();
	
	// 선택한 카테고리 정보 구성
	var info = $(".ctgSelectArea").getCtgSelectInfo();
	var ctgPath = info.value.length != 0 ? info.ctgPath : "";
	$("input[name=ctgPath]").val(ctgPath);

	// 상품번호 정보 구성
	if(data.itemSeq == ""){ $("input[name=itemSeq]").val(0); }
	
	
	// 전송
	$("form[name=searchForm]").submit();
}


$(document).ready(function(evt){
	// 카테고리 선택 영역 초기화
	$(".ctgSelectArea").ctgSelect({
		ctgPath : "<c:out value="${param.ctgPath}" />", 
		data : { dispYn : "Y" }
	});
});

	

</script>