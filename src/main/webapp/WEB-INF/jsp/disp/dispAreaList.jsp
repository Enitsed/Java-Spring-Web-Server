<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>




전시영역 목록
<hr>



<%-- 검색 영역 시작 --%>
<form name="searchForm" action="?">
<table style="width:100%">
	<tr>
		<td width="150px;">전시유형</td>
		<td>
			<select name="dispType">
				<option value="">전체</option>
				<option value="ITEM" <c:if test="${param.dispType eq 'ITEM'}">selected</c:if>>상품</option>
				<option value="BANNER" <c:if test="${param.dispType eq 'BANNER'}">selected</c:if>>배너</option>
				<option value="CONTENTS" <c:if test="${param.dispType eq 'CONTENTS'}">selected</c:if>>컨텐츠</option>
			</select>
		</td>
		<td width="150px;">검색조건</td>
		<td>
			<select name="keyField">
				<option value="">선택</option>
				<option value="dispAreaNm" <c:if test="${param.keyField eq 'dispAreaNm'}">selected</c:if>>전시영역이름</option>
				<option value="dispCd" <c:if test="${param.keyField eq 'dispCd'}">selected</c:if>>전시영역코드</option>
			</select>
			<input type="text" name="keyWord" value="<c:out value="${param.keyWord}" />" />
		</td>
	</tr>
</table>
</form>




<div class="center" style="width:100%; padding:20px;">
	<button onclick="search()">검색</button>
	<button onclick="location.href='/disp/dispAreaForm.do'">등록</button>
</div>


<%-- 검색 코멘트 영역 시작 --%>
<c:if test="${not empty param.keyWord}">
	<div style="width:100%">
		"<c:out value="${param.keyWord}" />" 검색 결과입니다.
	</div>
</c:if>


<%-- 데이터 영역 시작 --%>
<table style="width:100%">
	<thead class="tableHeader">
		<tr>
			<td style="width:50px;">번호</td>
			<td style="width:100px;">전시영역코드</td>
			<td style="width:80px;">전시유형</td>
			<td>전시영역이름</td>
			<td style="width:80px;">전시개수</td>
			<td style="width:80px;">상세사용여부</td>
			<td style="width:100px;">작성자</td>
			<td style="width:100px;">작성일</td>
			<td style="width:100px;">수정자</td>
			<td style="width:100px;">수정일</td>
		</tr>
	</thead>
	<tbody>
		<%-- 데이터가 존재하는 경우 --%>
		<c:if test="${not empty result.list}">
			<c:forEach items="${result.list}" var="i" varStatus="s">
				<tr>
					<td class="center"><c:out value="${result.totalCount - (result.page - 1) * result.listSize - s.index}" /></td>
					<td class="center"><c:out value="${i.dispCd}" /></td>
					<td class="center"><c:out value="${i.dispType}" /></td>
					<td>
						<a href="/disp/dispAreaForm.do?<core:param skip="dispCd" addParamStr="mode=update&dispCd=${i.dispCd}" />">${i.dispAreaNm}</a>
					</td>
					<td class="center"><c:out value="${i.dispCnt}" /></td>
					<td class="center"><c:out value="${i.dtlUseYn}" /></td>

					<td class="center"><c:out value="${i.regNm}" /></td>
					<td class="center"><core:mask pattern="####.##.##">${i.regDt}</core:mask></td>
					<td class="center"><c:out value="${i.modNm}" /></td>
					<td class="center"><core:mask pattern="####.##.##">${i.modDt}</core:mask></td>
				</tr>
			</c:forEach>
		</c:if>
		
		<%-- 데이터가 존재하지 않는 경우 --%>
		<c:if test="${empty result.list}">
			<tr>
				<td colspan="11" style="height:200px;" class="center">
					데이터가 존재하지 않습니다.
				</td>
			</tr>
		</c:if>
	</tbody>
	
	<%-- 데이터가 존재하는 경우 페이징 처리 --%>
	<c:if test="${not empty result.list && result.totalPage ne 1}">
		<tfoot>
			<tr>
				<td colspan="11" class="center">
					<core:pagination listResultName="result" />
				</td>
			</tr>
		</tfoot>
	</c:if>
</table>


<script>

// 검색 버튼 클릭 이벤트 핸들
function search(){
	// 전송
	$("form[name=searchForm]").submit();
}


$(document).ready(function(evt){
});


</script>



