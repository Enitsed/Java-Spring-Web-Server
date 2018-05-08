<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>

<table class="table table-bordered table-hover">
		<tr>
			<th scope="col">제목</th>
			<td>${data.title }</td>
			<th scope="col">조회수</th>
			<td>${data.viewCnt }</td>
			<th scope="col">게시물 번호</th>
			<td>${data.contentsSeq }</td>
		</tr>
		<tr>
			<th scope="col">게시물 유형</th>
			<td colspan="2">${data.contentsType }</td>
			<th scope="col">등록일</th>
			<td colspan="2">${data.regDt }</td>
		</tr>
		<tr>
			<th scope="row">내용</th>
			<td colspan="5">
				<c:set var="contents"><c:out value="${data.contents}" /></c:set>
				${core:nl2br(contents) }
			</td>
		</tr>
		
		<tr>
			<td colspan="6">
				<a class="btn btn-outline-success" href="/contents/bbsForm.do?<core:param addParamStr="mode=reply" />">답변</a>
				<c:if test="${currUser.userSeq eq data.regSeq}">
					<a class="btn btn-outline-warning" href="/contents/bbsForm.do?<core:param addParamStr="mode=update" />">수정</a>
					<a class="btn btn-outline-danger" href="javascript:removeContents()">삭제</a>
				</c:if>
				<a class="btn btn-outline-primary" href="/contents/bbsList.do?<core:param skip="contentsSeq" />">목록</a>
			</td>
		</tr>
</table>

		<div class="justify-content-center"><h3>이미지 미리보기</h3>
			<div id="image-wrap">
				<c:forEach items="${data.filesList}" var="i" varStatus="s">
					<a href="/contents/download.do?fileSeq=${i.fileSeq}&contentsSeq=${data.contentsSeq}"><img src="${imgBase}${i.filepath}" alt="${i.filename}" width="100" height="100" /></a>
				</c:forEach>
			</div>
		</div>

		
<script>
// 전송 처리
function removeContents(){
	// 데이터 구성
	var data = { contentsSeq : ${data.contentsSeq} }
	
	// 전송
	$.ajax({
		async		: true,
		url			: '/contents/bbsRemove.json',
		dataType	: 'json',
		type		: 'POST',
		data		: data,
		success		: function(data, textStatus, jqXHR){
			// 처리결과 확인
			if(data.resultCode == 500){
				alert("하위게시물이 있는 항목은 삭제할 수 없습니다.");
				return;				
			}
			
			if(data.resultCode != 1){
				alert("잘못된 요청입니다.");
				return;
			}
			
			// 정상 메세지 처리
			alert("삭제되었습니다.");
			location.href = "/contents/bbsList.do";
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}

</script>
