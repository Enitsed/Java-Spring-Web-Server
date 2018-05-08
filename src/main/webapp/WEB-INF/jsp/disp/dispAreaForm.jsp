<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>


전시영역폼
<hr>
<form name="form" onsubmit="return false">
<input type="hidden" name="mode" value="<c:out value='${param.mode}' />" />
<table style="width:100%">
	<tr>
		<td style="width:150px" class="center">전시영역코드</td>
		<td>
			<c:choose>
				<c:when test="${param.mode eq 'update'}">
					<input type="hidden" name="dispCd" value="${data.dispCd}" class="required" title="전시영역코드" readonly />
					${data.dispCd}
				</c:when>
				<c:otherwise>
					<input type="text" name="dispCd" value="${data.dispCd}" class="required" title="전시영역코드" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="center">전시영역이름</td>
		<td><input type="text" name="dispAreaNm" value="${data.dispAreaNm}" class="required" title="전시영역이름" maxlength="60" /></td>
	</tr>
	<tr>
		<td class="center">전시유형</td>
		<td>
			<c:choose>
				<c:when test="${param.mode eq 'update'}">
					<c:if test="${data.dispType eq 'ITEM'}">상품</c:if>
					<c:if test="${data.dispType eq 'BANNER'}">배너</c:if>
					<c:if test="${data.dispType eq 'CONTENTS'}">컨텐츠</c:if>
				</c:when>
				<c:otherwise>
					<select name="dispType" class="required" title="전시유형">
						<option value="ITEM" <c:if test="${data.dispType eq 'ITEM'}">selected</c:if>>상품</option>
						<option value="BANNER" <c:if test="${data.dispType eq 'BANNER'}">selected</c:if>>배너</option>
						<option value="CONTENTS" <c:if test="${data.dispType eq 'CONTENTS'}">selected</c:if>>컨텐츠</option>
					</select>
				</c:otherwise>
			</c:choose>		
		</td>
	</tr>
	<tr>
		<td class="center">전시개수</td>
		<td><input type="text" name="dispCnt" value="${data.dispCnt}" class="required onlynum zeroCheck" title="전시개수" maxlength="5" ></td>
	</tr>
	<tr>
		<td class="center">영역상세사용여부</td>
		<td>
			<select name="dtlUseYn" onchange="dtlUseYnChange(this)">
				<option value="Y" <c:if test="${data.dtlUseYn eq 'Y'}">selected</c:if>>사용</option>
				<option value="N" <c:if test="${data.dtlUseYn eq 'N'}">selected</c:if>>미사용</option>
			</select>
		</td>
	</tr>
</table>
</form>


<div class="dispAreaDtlArea" style="margin-top:20px;<c:if test="${data.dtlUseYn eq 'N'}">display:none;</c:if>">
	<table class="dispAreaDtlTableArea">
		<tr>
			<td style="width:150px" class="center">전시순서</td>
			<td style="width:200px;" class="center">영역이름</td>
			<td style="width:150px" class="center">기능</td>
		</tr>
		<tr class="dispAreaDtlInputArea">
			<td class="center">
				<input type="hidden" name="dispAreaDtlSeq" value="0" />
				<input type="hidden" name="useYn" value="Y" />
				<input type="text" name="dispNo" style="width:140px;" />
			</td>
			<td class="center"><input type="text" name="dispAreaDtlNm" style="width:190px;" maxlength="60" /></td>
			<td class="center fnArea"><a href="javascript:addDispAreaDtl()">추가</a></td>
		</tr>
	</table>

</div>



<div class="center" style="padding:10px;">
	<button onclick="location.href='/disp/dispAreaList.do?<core:param skip="dispCd,mode" />'">목록</button>
	<button onclick="sendData()">저장</button>
	
	<c:if test="${param.mode eq 'update'}">
		<button onclick="removeData()">삭제</button>
	</c:if>
</div>


<script src="/script/crew/enitsed.form.js"></script>
<script>

var dispAreaDtlList 		= ${dispAreaDtlListStr};


// 상세 사용여부 변경 이벤트 핸들...
function dtlUseYnChange(obj){
	if(obj.value == "Y"){ $(".dispAreaDtlArea").show(); }
	else{ $(".dispAreaDtlArea").hide(); }
}


// 전시영역상세 초기화
function initDispAreaDtl(){
	for(var i=0, d ; d = dispAreaDtlList[i] ; i++){
		createDispAreaDtl(d);
	}
}


// 전시영역 상세 추가 이벤트 핸들
function addDispAreaDtl(){
	// 데이터 구성
	var area = $(".dispAreaDtlInputArea");
	var data = {
		dispNo			: $.trim(area.find("input[name=dispNo]").val()), 
		dispAreaDtlNm	: $.trim(area.find("input[name=dispAreaDtlNm]").val()), 
		dispAreaDtlSeq	: 0, 
		useYn			: "Y"
	}
	
	// 입력값 검증
	if(data.dispNo == ""){			alert("전시순서를 입력하십시오."); area.find("input[name=dispNo]").trigger("focus"); return; }
	if(data.dispAreaDtlNm == ""){	alert("영역이름을 입력하십시오."); area.find("input[name=dispAreaDtlNm]").trigger("focus"); return; }
	if(isNaN(data.dispNo.replace(/[.]/g, ''))){	alert("전시순서는 숫자만 가능합니다."); area.find("input[name=dispNo]").trigger("focus"); return; }
	
	// 값 초기화
	area.find("input[name=dispNo]").val("");
	area.find("input[name=dispAreaDtlNm]").val("");

	// UI 구성 
	createDispAreaDtl(data);
}


// 전시영역 상세 삭제 처리
function removeDispAreaDtl(obj){
	var area = $(obj).closest(".dispAreaDtlDataArea");
	area.find("input[name=useYn]").val("N");
	area.hide();
}


// 전시영역 상세 추가 처리
function createDispAreaDtl(data){
	// UI 복제 및 클래스 변경
	var area = $(".dispAreaDtlInputArea").clone(true);
	area.removeClass("dispAreaDtlInputArea").addClass("dispAreaDtlDataArea");

	// 값채우기
	area.find("input[name=dispNo]").val(data.dispNo);
	area.find("input[name=dispAreaDtlNm]").val(data.dispAreaDtlNm);
	area.find("input[name=dispAreaDtlSeq]").val(data.dispAreaDtlSeq);
	area.find("input[name=useYn]").val(data.useYn);
	
	// 추가 버튼을 삭제 버튼으로 변경 처리
	area.find(".fnArea").html("<span onclick=\"removeDispAreaDtl(this)\" style='cursor:pointer;'>삭제</span>");
	
	// 적용
	$(".dispAreaDtlTableArea").append(area);
}

//----------------------------------------------------------------------------------------------------------
// 전송 처리
function sendData(){
	// 입력폼 기본값 체크
	var form = $("form[name=form]");
	if(!form.validateForm()){ return; }
	
	// 데이터 획득
	var data = form.serializeObject();
	

	// 상세영역 정보 구성
	var dtlDataList	= [];
	
	// 상세영역 사용인 경우에는 영역에서 데이터를 획득함.
	if(data.dtlUseYn == "Y"){
		var dtlArea = $(".dispAreaDtlDataArea");
		var useYCnt	= 0;	// 사용여부가 Y인 항목 카운트 정보
		
		for(var i=0, a ; a = dtlArea[i] ; i++){
			a = $(a);
			
			var dtlData = {
				dispNo			: $.trim(a.find("input[name=dispNo]").val()), 
				dispAreaDtlNm	: $.trim(a.find("input[name=dispAreaDtlNm]").val()), 
				dispAreaDtlSeq	: $.trim(a.find("input[name=dispAreaDtlSeq]").val()), 
				useYn			: $.trim(a.find("input[name=useYn]").val())
			}
			
			// 입력값 검증
			if(dtlData.dispNo == ""){			alert("전시순서를 입력하십시오."); a.find("input[name=dispNo]").trigger("focus"); return; }
			if(dtlData.dispAreaDtlNm == ""){	alert("영역이름을 입력하십시오."); a.find("input[name=dispAreaDtlNm]").trigger("focus"); return; }
			if(isNaN(dtlData.dispNo.replace(/[.]/g, ''))){	alert("전시순서는 숫자만 가능합니다."); a.find("input[name=dispNo]").trigger("focus"); return; }
			
			
			// 실제 사용하는 항목 카운트
			if(dtlData.useYn == "Y"){ useYCnt++; }
			
			// 데이터 저장
			dtlDataList.push(dtlData);
		}
		
		// 상세영역 사용인 경우에는 2개 이상을 등록해야 함.
		if(useYCnt < 2){
			alert("상세영역을 사용시에는 2개 이상의 상세정보를 등록해야 합니다.");
			return;
		}
	}
	

	// 상세영역 최종 정보 구성
	data.dispAreaDtlListStr = JSON.stringify(dtlDataList);
	

	// 전송
	$.ajax({
		async		: true,
		url			: '/disp/dispAreaSave.json',
		dataType	: 'json',
		type		: 'POST',
		data		: data,
		success		: function(data, textStatus, jqXHR){
			console.log(data);
			
			// 처리결과 확인
			if(data.resultCode != 1){
				if(data.resultCode == 9100){
					alert("전시영역코드는 중복될 수 없습니다.");
				}else{
					alert("잘못된 요청입니다.");
				}
				
				return;
			}
			
			// 정상 처리
			if($("input[name=mode]").val() == "update"){
				location.href = "/disp/dispAreaForm.do?<core:param />";
			}else{
				location.href = "/disp/dispAreaList.do";
			}
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
		url			: '/disp/dispAreaRemove.json',
		dataType	: 'json',
		type		: 'POST',
		data		: { dispCd : "${param.dispCd}" },
		success		: function(data, textStatus, jqXHR){
			console.log(data);
			
			// 처리결과 확인
			if(data.resultCode != 1){ alert("잘못된 요청입니다."); return; }
			
			// 정상 처리
			alert("삭제 되었습니다.");
			location.href = "/disp/dispAreaList.do";
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}



$(document).ready(function(evt){
	// 전시영역상세 초기화
	initDispAreaDtl();
});


</script>

