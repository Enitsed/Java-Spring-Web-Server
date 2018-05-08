<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>


템플릿폼
<hr>
<form name="form" onsubmit="return false">
<input type="hidden" name="mode" value="<c:out value='${param.mode}' />" />
<input type="hidden" name="tmplSeq" value="${data.tmplSeq}" />
<table style="width:100%">
	<tr>
		<td style="width:150px;" class="center">템플릿 이름</td>
		<td><input type="text" name="tmplNm" value="${data.tmplNm}" class="required" title="템플릿 이름" maxlength="60" /></td>
	</tr>
	<tr>
		<td class="center">템플릿대상</td>
		<td>
			<c:choose>
				<c:when test="${param.mode eq 'update'}">
					<c:if test="${data.tmplTgt eq 'PC'}">PC</c:if>
					<c:if test="${data.tmplTgt eq 'MO'}">MOBILE</c:if>
				</c:when>
				<c:otherwise>
					<select name="tmplTgt" class="required" title="템플릿대상">
						<option value="PC" <c:if test="${data.tmplTgt eq 'PC'}">selected</c:if>>PC</option>
						<option value="MO" <c:if test="${data.tmplTgt eq 'MO'}">selected</c:if>>MOBILE</option>
					</select>
				</c:otherwise>
			</c:choose>		
		</td>
	</tr>
	<tr>
		<td class="center">템플릿 경로</td>
		<td><input type="text" name="tmplPath" value="${data.tmplPath}" class="required" title="템플릿 경로" maxlength="100" /></td>
	</tr>
	<tr>
		<td class="center">전시영역</td>
		<td>
			<select name="dispArea" onchange="selectDispArea()">
				<option value="">선택</option>
				<c:forEach items="${dispAreaList}" var="i" varStatus="s">
					<option value="${i.dispCd}">${i.dispAreaNm}</option>
				</c:forEach>
			</select>
			
			<div class="dispAreaInfoArea">
			</div>
		</td>
	</tr>
</table>
</form>


<div class="center" style="padding:10px;">
	<button onclick="location.href='/disp/tmplList.do?<core:param skip="tmplSeq,mode" />'">목록</button>
	<button onclick="sendData()">저장</button>
</div>


<script src="/script/crew/enitsed.form.js"></script>
<script>
var dispAreaList = ${dispAreaListStr};


// 전시영역 초기화
function initDispArea(){
	for(var i=0, d ; d = dispAreaList[i] ; i++){
		createDispArea(d);
	}
}

// 전시영역 선택 이벤트 핸들
function selectDispArea(){
	var option = $("select[name=dispArea]").find("option:selected");
	
	// 선택한 값이 없으면 그냥 리턴
	if(option.val() == ""){ return; }
	
	// 선택한 값이 있으면 중복체크
	for(var i=0, d ; d = dispAreaList[i] ; i++){
		if(d.dispCd == option.val()){
			alert("이미 등록되어 있는 항목입니다.");
			$("select[name=dispArea]").find("option:first").prop("selected", true);
			return;
		}
	}
	
	// 등록 처리
	var data = { dispCd : option.val(), dispAreaNm : option.text() };
	dispAreaList.push(data);
	$("select[name=dispArea]").find("option:first").prop("selected", true);
	createDispArea(data);
}

// 전시영역 생성
function createDispArea(d){
	var html = [];
	html.push("<div>");
	html.push(d.dispAreaNm);
	html.push(" <span onclick=\"removeDispArea(this, '" + d.dispCd + "')\" style='cursor:pointer;'>[삭제]</span>");
	html.push("</div>");
	$(".dispAreaInfoArea").append(html.join(""));
}

// 전시영역 삭제
function removeDispArea(obj, dispCd){
	// 데이터 변경 처리
	var arr = [];
	
	for(var i=0, d ; d = dispAreaList[i] ; i++){
		if(d.dispCd != dispCd){
			arr.push(d);
		}
	}
	
	dispAreaList = arr;
	
	// ui 삭제 처리
	$(obj).closest("div").remove();
}


//----------------------------------------------------------------------------------------------------------
// 전송 처리
function sendData(){
	// 입력폼 기본값 체크
	var form = $("form[name=form]");
	if(!form.validateForm()){ return; }
	
	// 데이터 획득
	var data = form.serializeObject();
	

	// 상세영역 최종 정보 구성
	data.dispAreaListStr = JSON.stringify(dispAreaList);
	

	// 전송
	$.ajax({
		async		: true,
		url			: '/disp/tmplSave.json',
		dataType	: 'json',
		type		: 'POST',
		data		: data,
		success		: function(data, textStatus, jqXHR){
			console.log(data);
			
			// 처리결과 확인
			if(data.resultCode != 1){ alert("잘못된 요청입니다."); return; }
			
			
			// 정상 처리
			alert("적용되었습니다.");

			if($("input[name=mode]").val() == "update"){
				location.href = "/disp/tmplForm.do?<core:param />";
			}else{
				location.href = "/disp/tmplList.do";
			}
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}




$(document).ready(function(evt){
	// 전시영역 초기화
	initDispArea();
});


</script>