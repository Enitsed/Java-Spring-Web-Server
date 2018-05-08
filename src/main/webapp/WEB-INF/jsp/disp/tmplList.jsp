<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>




템플릿 목록
<hr>



<%-- 검색 영역 시작 --%>
<form name="searchForm" action="?">
<table style="width:100%">
	<tr>
		<td width="150px;">검색조건</td>
		<td>
			<select name="keyField">
				<option value="">선택</option>
				<option value="tmplNm" <c:if test="${param.keyField eq 'tmplNm'}">selected</c:if>>템플릿이름</option>
				<option value="tmplPath" <c:if test="${param.keyField eq 'tmplPath'}">selected</c:if>>템플릿경로</option>
			</select>
			<input type="text" name="keyWord" value="<c:out value="${param.keyWord}" />" />
		</td>
	</tr>
</table>
</form>




<div class="center" style="width:100%; padding:20px;">
	<button onclick="search()">검색</button>
	<button onclick="location.href='/disp/tmplForm.do'">등록</button>
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
			<td>템플릿 이름</td>
			<td style="width:200px;">템플릿 경로</td>
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
					<td>
						<a href="/disp/tmplForm.do?<core:param skip="tmplSeq" addParamStr="mode=update&tmplSeq=${i.tmplSeq}" />">${i.tmplNm}</a>
					</td>
					<td><c:out value="${i.tmplPath}" /></td>

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

