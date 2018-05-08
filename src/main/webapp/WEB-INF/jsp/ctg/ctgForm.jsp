<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>

<h4>카테고리 폼</h4>

<form name="form" onsubmit="return false">
<input type="hidden" name="mode" value="<c:out value='${param.mode}' />" />
<input type="hidden" name="ctgSeq" value="${data.ctgSeq}" />
	<table class="table table-bordered">
		<tr>
			<th>상위카테고리</th>
			<td>
				<%-- 등록/수정에따라 UI가 다름. --%>
				<c:if test="${param.mode eq 'update' }">${data.ctgPathNmParent}</c:if>
				
				<c:if test="${param.mode ne 'update'}">
					<div class="ctgSelectArea"></div>
				</c:if>
			</td>
		</tr>
		
		<tr>
			<th>카테고리명</th>
			<td>
				<input type="text" name="ctgNm" value='<c:out value="${data.ctgNm }"></c:out>' maxlength="15"/>
			</td>
		</tr>
		
		<tr>
			<th>전시순서</th>
			<td>
				<input type="text" name="dispNo" value='<c:out value="${data.dispNo }"></c:out>' maxlength="10" />
			</td>
		</tr>
		
		<tr>
			<th>전시여부</th>
			<td>
				<select name="dispYn">
					<option value="Y" <c:if test="${data.dispYn eq 'Y'}">selected</c:if>>예</option>
					<option value="N" <c:if test="${data.dispYn eq 'N'}">selected</c:if>>아니오</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" class="center">
				<button class="btn btn-primary" onclick="location.href='/category/ctgList.do'">목록</button>
				<c:choose>
					<c:when test="${param.mode eq 'update' }">
						<button class="btn btn-warning" onclick="sendData()">수정</button>
						<button class="btn btn-danger" onclick="removeData()">삭제</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-success" onclick="sendData()">저장</button>
					</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
	</table>
</form>


<script>
// 전송 처리
function sendData(){
	// 데이터 구성
	var data = { mode : '', ctgSeqParent : 0, ctgNm : '', dispNo : 0, dispYn : '', ctgSeq : 0 }
	for(var key in data){
		if($("input[name=" + key + "]").length == 1){
			data[key] = $.trim($("input[name=" + key + "]").val());
		}else{
			data[key] = $("select[name=" + key + "]").val();
		}
	}
	data.ctgSeqParent = data.mode != "update" ? $(".ctgSelectArea").getCtgSelectValue() : 0;
	
	// 필수값 체크
	if(data.ctgNm == ""){ alert("카테고리 이름을 입력하십시오."); $("input[name=ctgNm]").trigger("focus"); return; }
	if(data.dispNo == "" || data.dispNo == 0){ alert("전시순서를 입력하십시오."); $("input[name=dispNo]").trigger("focus"); return; }
	if(isNaN(data.dispNo)){ alert("전시순서는 숫자만 입력이 가능합니다."); $("input[name=dispNo]").trigger("focus"); return; }

	// 전송
	$.ajax({
		async		: true,
		url			: '/category/ctgSave.json',
		dataType	: 'json',
		type		: 'POST',
		data		: data,
		success		: function(data, textStatus, jqXHR){
			console.log(data);
			
			// 처리결과 확인
			if(data.resultCode != 1){
				alert("잘못된 요청입니다.");
				return;
			}
			
			// 정상 처리
			alert("적용 되었습니다.");
			location.href = "/category/ctgList.do";
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}

// 삭제 처리
function removeData(){
	// 전송
	$.ajax({
		async		: true,
		url			: '/category/ctgRemove.json',
		dataType	: 'json',
		type		: 'POST',
		data		: { ctgSeq : "${data.ctgSeq}" },
		success		: function(data, textStatus, jqXHR){
			console.log(data);
			
			// 처리결과 확인
			if(data.resultCode != 1){ alert("잘못된 요청입니다."); return; }
			
			// 정상 처리
			alert("삭제 되었습니다.");
			location.href = "/category/ctgList.do";
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}

$(document).ready(function(evt){
	
	if(${param.mode != "update"}){
		// 카테고리 선택 인터페이스 구성
		$(".ctgSelectArea").ctgSelect();
	}
	
	
});

</script>