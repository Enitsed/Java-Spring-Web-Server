<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>


<core:param addParamStr="contentsSeq=5" skip="oekdio" />


<h3>게시판</h3>
<div class="row">
	<div class="form-control right m-2">
		<select class="form-control" name="keyField">
			<option value="title" <c:if test="${param.keyField eq 'title'}">selected</c:if>>제목</option>
			<option value="contents"  <c:if test="${param.keyField eq 'contents'}">selected</c:if>>내용</option>
		</select>
		<input class="form-control" type="text" name="keyWord" value="<c:out value="${param.keyWord }" />" />
		<button class="btn btn-outline-dark" onclick="search()">검색</button>
	</div>
	
	<%-- 검색 코멘트 영역 시작 --%>
	<c:if test="${not empty param.keyWord}">
		<div style="width:100%">
			"<c:out value="${param.keyWord}" />" 검색 결과입니다.
		</div>
	</c:if>
	
	<%-- 데이터 영역 시작 --%>
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th scope="col" style="width: 10%">게시물 번호</th>
				<th scope="col" style="width: 15%">게시물 유형</th>
				<th scope="col" style="width: 50%">제목</th>
				<th scope="col" style="width: 10%">조회수</th>
				<th scope="col" style="width: 10%">작성자</th>
				<th scope="col" style="width: 5%">등록일</th>
			</tr>
		</thead>
		<tbody>
		<%-- 데이터가 존재하지 않는 경우 --%>
		<c:if test="${empty result.list }">
			<tr>
				<td colspan="6">
					<h4>작성된 글이 없습니다.</h4>
				</td>
			</tr>
		</c:if>
		<%-- 데이터가 존재하는 경우 --%>
		<c:forEach items="${result.list }" var="contents" varStatus="status">
			<tr>
				<th scope="row"><c:out value="${result.totalCount - (result.page - 1) * result.listSize - status.index}" /></th>
				<td>${contents.contentsType }</td>
				<td>
					<c:forEach begin="0" end="${contents.replyStep}">&nbsp;&nbsp;&nbsp;</c:forEach>
					<a href="/contents/bbsView.do?<core:param addParamStr="contentsSeq=${contents.contentsSeq}" />">
						<c:if test="${contents.replyStep ne 0 }"><span class="oi oi-caret-right"></span></c:if>
						<c:out value="${contents.title }" />
					</a>
				</td>
				<td><fmt:formatNumber pattern="#,###" value="${contents.viewCnt }" /></td>
				<td>${contents.regName }</td>
				<td><core:mask pattern="####.##.##">${contents.regDt }</core:mask></td>
			</tr>
		</c:forEach>
			<%-- 데이터가 존재하는 경우 페이징 처리 --%>
			<c:if test="${not empty result.list}">
				<tr>
					<td colspan="6">
						<ul class="pagination justify-content-center">
							<core:pagination listResultName="result" />
						</ul>
					</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>

<div class="right">
	<button class="btn btn-outline-primary" onclick="location.href='/contents/bbsForm.do'">작성</button>
</div>

<script>

//검색 버튼 클릭 이벤트 핸들
function search(){
	// 데이터 획득
	var keyField	= $("select[name=keyField]").val();
	var keyWord		= $.trim($("input[name=keyWord]").val());
	
	// 필수값 체크
	if(keyWord == ""){ alert("검색어를 입력해 주십시오."); return; }
	
	// 검색처리
	location.href = "?<core:param skip="keyField,keyWord,page" />&keyField=" + keyField + "&keyWord=" + keyWord;
}


</script>
