<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>

<div>
	<form name="form" onsubmit="return false">
	<input type="hidden" name="mode" value="${param.mode}">
	<input type="hidden" name="contentsSeq" value="${data.contentsSeq}">
	
		<div class="form-group">
			<label for="title">제목</label>
			<c:set var="title" value="${data.title}" />
			<c:if test="${param.mode eq 'reply'}"><c:set var="title" value="Re : ${data.title}" /></c:if>
			<input type="text" class="form-control" name="title" value="${title }" maxlength="200" />
		</div>
		<div class="form-group">
			<label for="contents">내용</label>
				<c:set var="contents" value="${data.contents}" />
				<c:if test="${param.mode eq 'reply'}">
					<c:set var="contents">
					${contents }
					</c:set>
				</c:if>
			<textarea class="form-control" name="contents" rows="5" maxlength="150">${contents}</textarea>
		</div>
		
		<div class="form-group">
			<div class="justify-content-center"><h3>파일</h3></div>
			<div class="fileResultArea"></div>
			<div class="fileSendArea">
				<input type="file" name="file" accept="image/*" onchange="sendFile(this)" />
			</div>
		</div>
	</form>
	<div class="form-group">
		<button class="btn btn-outline-success" onclick="sendData()">등록</button>
	</div>

</div>
<div class="right">
	<button class="btn btn-outline-success" onclick="location.href='/contents/bbsList.do?<core:param skip='contentsSeq,mode' />'">목록</button>
	<c:if test="${data.contentsSeq ne 0}">
		<button class="btn btn-outline-success" onclick="location.href='/contents/bbsView.do?<core:param skip='mode' />'">상세</button>
	</c:if>
</div>


<script>
var fileInfoList = ${fileInfoListJsonStr};

//파일 영역 초기화 - 수정인 경우 기존에 등록된 정보 처리용
function initFile(){
	// 파일 전송 결과 노출 처리 및 결과 저장
	for(var i=0, d; d = fileInfoList[i]; i++){ 
		var html = [];
		html.push("<div class='imageArea" + d.key + "'>");
		html.push("	<img src='${imgBase}" + d.tempFile + "' width='100' height='100' />");
		html.push("	<a href=\"javascript:removeFile('" + d.key + "')\">[삭제]</a>");
		html.push("</div>");
		$(".fileResultArea").append(html.join(""));
	}	
}

//파일 전송 처리 - 파일을 전송하고 전송결과를 기반으로 이미지를 노출함.
function sendFile(obj){
	$.crewFileLib.uploadCommon({
		input	: obj, 
		success	: function(data, xhr, status){
			// 취소버튼 선택시 처리
			if(data.result.length == 0){ return; }

			// 파일 전송 결과 노출 처리 및 결과 저장
			for(var i=0, d ; d = data.result[i] ; i++){ 
				var html = [];
				html.push("<div class='imageArea" + d.key + "'>");
				html.push("	<img src='/common/file/imageView.do?tempFile=" + d.tempFile + "' width='100' height='100' />");
				html.push("	<a href=\"javascript:removeFile('" + d.key + "')\">[삭제]</a>");
				html.push("</div>");
				$(".fileResultArea").append(html.join(""));
				
				fileInfoList.push(d); 
			}
			
			// 파일전송 영역 재구성
			$(".fileSendArea").html("<input type='file' name='file' accept='image/*' onchange='sendFile(this)' />");
		}
	});
}

//첨부파일 삭제 처리
function removeFile(key){
	// 영역 삭제
	$(".imageArea" + key).remove();
	
	// 데이터 삭제 마킹
	for(var i=0, d ; d = fileInfoList[i] ; i++){
		if(d.key == key){
			d.useYn = "N";
			break;
		}
	}
}

//전송 처리
function sendData(){
	// 데이터 구성
	var data = { mode : '', contentsSeq : 0, title : '', contents : '' }
	
	for(var key in data){ 
		if($("input[name=" + key + "]").length == 1){
			data[key] = $.trim($("input[name=" + key + "]").val());
		}else{
			data[key] = $.trim($("textarea[name=" + key + "]").val());
		}
	}
	
	// 첨부파일 정보 구성
	data.fileInfoStr = JSON.stringify(fileInfoList);
	

	// 필수값 체크
	if(data.title == ""){		alert("제목을 입력하십시오.");		$("input[name=title]").trigger("focus"); 		return; }
	if(data.contents == ""){	alert("내용을 입력하십시오.");		$("textarea[name=contents]").trigger("focus"); 	return; }
	
	
	// 전송
	$.ajax({
		async		: true,
		url			: '/contents/bbsSave.json',
		dataType	: 'json',
		type		: 'POST',
		data		: data,
		success		: function(data, textStatus, jqXHR){
			// 처리결과 확인
			if(data.resultCode != 1){
				alert("잘못된 요청입니다.");
				return;
			}

			// 정상 메세지 처리 - 등록/수정에 따라서 분기 처리
			var mode = $("input[name=mode]").val();
			if(mode == "" || mode == "create" || mode == "reply"){
				alert("등록되었습니다.");
				location.href = "/contents/bbsList.do";
				return;
			}
			
			if(mode == "update"){
				alert("수정되었습니다.");
				location.href = "/contents/bbsView.do?<core:param skip='mode' />";
			}
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}


$(document).ready(function(evt){
	// 파일 첨부 영역 초기화
	initFile();	
});

</script>
