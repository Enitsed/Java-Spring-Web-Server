<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>

<h5>상품 등록 / 수정 폼</h5>
<hr />
<form>
<input type="hidden" name="mode" value="<c:out value='${param.mode}' />" />
<input type="hidden" name="itemSeq" value="${data.itemSeq}" />
<table class="table table-bordered">
	<tr>
		<td>
			<label for="itemNm">상품명</label>
		</td>
		<td>
			<input class="form-control" type="text" name="itemNm" value="<c:out value="${data.itemNm}" />" />
		</td>
	</tr>
	<tr>
		<td><label for="searchKeyword">검색어</label></td>
		<td>
			<input class="form-control" type="text" name="searchKeyword" value="<c:out value="${data.searchKeyword}" />" />
		</td>
	</tr>
	<tr>
		<td><label for="descShort">상품간략설명</label></td>
		<td>
			<input class="form-control" type="text" name="descShort" value="<c:out value="${data.descShort}" />" />
		</td>
	</tr>
	<tr>
		<td><label for="descLong">상품설명</label></td>
		<td>
			<textarea class="form-control" rows="4" name="descLong" >
				${data.descLong}
			</textarea>
		</td>
	</tr>
	<tr>
		<td><label for="">대표 카테고리</label></td>
		<td>
			<div class="ctgSelectAreaMst">
			
			</div>
		</td>
	</tr>
	<tr>
		<td><label for="">전시 카테고리</label></td>
		<td>
			<span class="ctgSelectAreaDisp"></span>
			<a href="javascript:addDispCtg()">[추가]</a>
			<div class="ctgSelectInfoAreaDisp">
				<%-- 기존 선택항목 인터페이스 구성 --%>
				<c:forEach items="${data.ctgListDisp}" var="i" varStatus="s">
					<div class="ctgSelectInfo${i.ctgSeq}">
						${i.ctgPathNm}
						<a href="javascript:removeDispCtg(${i.ctgSeq})">[삭제]</a>
					</div>	
				</c:forEach>			
			</div>
		</td>
	</tr>
	<tr>
		<td><label for="itemStat">상품상태</label></td>
		<td>
			<select class="form-control" name="itemStat">
				<option value="NORMAL" <c:if test="${data.itemStat eq 'NORMAL'}">selected</c:if>>정상</option>
				<option value="SOLDOUT" <c:if test="${data.itemStat eq 'SOLDOUT'}">selected</c:if>>품절</option>
				<option value="STOP" <c:if test="${data.itemStat eq 'STOP'}">selected</c:if>>판매중지</option>
			</select>
		</td>
	</tr>
	<tr>
		<td><label for="itemDispStat">전시상태</label></td>
		<td>
			<select class="form-control" name="itemDispStat">
				<option value="DISP" <c:if test="${data.itemDispStat eq 'DISP'}">selected</c:if>>전시</option>
				<option value="NODISP" <c:if test="${data.itemDispStat eq 'NODISP'}">selected</c:if>>미전시</option>
			</select>
		</td>
	</tr>
	<tr>
		<td><label for="stockCntTot">전체 재고수량</label></td>
		<td>
			<input class="form-control" type="text" name="stockCntTot" value="${data.stockCntTot }"/>
		</td>
	</tr>
	<tr>
		<td><label for="saleCntMin">최소 판매수량</label></td>
		<td>
			<input class="form-control" type="text" name="sellCntMin" value="${data.sellCntMin }"/>
		</td>
	</tr>
	<tr>
		<td><label for="sellCntMax">최대 판매수량</label></td>
		<td>
			<input class="form-control" type="text" name="sellCntMax" value="${data.sellCntMax }"/>
		</td>
	</tr>
	<tr>
		<td><label for="price">판매가</label></td>
		<td>
			<input class="form-control" type="text" name="price" value="${data.price }"/>
		</td>
	</tr>
	<tr>
		<td><label for="priceDc">할인가</label></td>
		<td>
			<input class="form-control" type="text" name="priceDc" value="${data.priceDc }"/>
		</td>
	</tr>
	<tr>
		<td><label for="">대표 이미지</label></td>
		<td>
			<div class="fileMstResultArea"></div>
			<div class="fileMstSendArea">
				<input class="form-control" type="file" name="file" accept="image/*" onchange="sendFile(this, 'Mst')" />
			</div>
		</td>
	</tr>
	<tr>
		<td><label for="">상세 이미지</label></td>
		<td>
			<div class="fileSubResultArea"></div>
			<div class="fileSubSendArea">
				<input class="form-control" type="file" name="file" accept="image/*" onchange="sendFile(this, 'Sub')" />
			</div>
		</td>
	</tr>
</table>
</form>
<div class="center">
	<button class="btn" onclick="location.href='/item/itemList.do?<core:param skip="itemSeq,mode" />'">목록</button>
	<button class="btn" onclick="sendData()">저장</button>
</div>

<script type="text/javascript">
//--------------------------------------------------------------------------------------------------
//파일처리 관련 영역
var fileMstInfoList = ${fileMstInfoListJsonStr};
var fileSubInfoList = ${fileSubInfoListJsonStr};


//파일 영역 초기화 - 수정인 경우 기존에 등록된 정보 처리용
function initFile(){
	// 대표이미지 영역
	if(fileMstInfoList.length == 1){ $(".fileMstSendArea").hide(); }
	for(var i=0, d ; d = fileMstInfoList[i] ; i++){ 
		var html = [];
		html.push("<div class='imageArea" + d.key + "'>");
		html.push("	<img src='/images/item" + d.tempFile + "' width='100' height='100' />");
		html.push("	<a href=\"javascript:removeFile('" + d.key + "', 'Mst')\">[삭제]</a>");
		html.push("</div>");
		$(".fileMstResultArea").append(html.join(""));
	}


	// 상세이미지 영역
	for(var i=0, d ; d = fileSubInfoList[i] ; i++){ 
		var html = [];
		html.push("<div class='imageArea" + d.key + "'>");
		html.push("	<img src='/images/item" + d.tempFile + "' width='100' height='100' />");
		html.push("	<a href=\"javascript:removeFile('" + d.key + "', 'Sub')\">[삭제]</a>");
		html.push("</div>");
		$(".fileSubResultArea").append(html.join(""));
	}	

}


//파일 전송 처리 - 파일을 전송하고 전송결과를 기반으로 이미지를 노출함.
function sendFile(obj, type){
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
				html.push("	<a href=\"javascript:removeFile('" + d.key + "', '" + type + "')\">[삭제]</a>");
				html.push("</div>");
				$(".file" + type + "ResultArea").append(html.join(""));
				
				if(type == "Mst"){
					fileMstInfoList.push(d); 
				}else if(type == "Sub"){
					fileSubInfoList.push(d);
				}
			}
			
			// 파일전송 영역 재구성
			$(".file" + type + "SendArea").html("<input type='file' name='file' accept='image/*' onchange=\"sendFile(this, '" + type + "')\" />");
			
			// 마스터타입인 경우에는 첨부파일을 1개만 등록 가능함. -> 파일첨부 영역 숨김 처리
			if(type == "Mst"){
				$(".file" + type + "SendArea").hide();
			}
		}
	});
}


//첨부파일 삭제 처리
function removeFile(key, type){
	// 영역 삭제
	$(".imageArea" + key).remove();
	
	// 데이터 삭제 마킹
	for(var i=0, d ; d = (type == "Mst" ? fileMstInfoList[i] : fileSubInfoList[i]) ; i++){
		if(d.key == key){
			d.useYn = "N";
			break;
		}
	}
	
	// 타입이 Mst인 경우 첨부 영역 활성화
	if(type == "Mst"){
		$(".file" + type + "SendArea").show();
	}
}


//--------------------------------------------------------------------------------------------------
//카테고리 처리 관련 영역
var ctgSeqDisp = [];
<c:forEach items="${data.ctgListDisp}" var="i" varStatus="s">
	ctgSeqDisp.push(${i.ctgSeq } );
</c:forEach>

//전시카테고리 추가 처리
function addDispCtg(){
	var info = $(".ctgSelectAreaDisp").getCtgSelectInfo();
	
	// 값이 선택되어 있는지 판단
	if(info.value.length == 0){ alert("카테고리를 선택해주십시오."); return; }

	// 기존에 등록된 항목 검증
	for(var i=0, d ; d = ctgSeqDisp[i] ; i++){
		if(d == info.lastValue){
			alert("기존에 등록되어 있는 항목입니다.");
			return;
		}
	}
	
	// 선택한 카테고리 정보 저장
	ctgSeqDisp.push(info.lastValue);
	
	// 선택 인터페이스 구성
	var html = [];
	html.push("<div class='ctgSelectInfo" + info.lastValue + "'>");
	html.push(info.text.join(" > "));
	html.push("	<a href='javascript:removeDispCtg(" + info.lastValue + ")'>[삭제]</a>");
	html.push("</div>");

	$(".ctgSelectInfoAreaDisp").append(html.join(""));
}

//전시카테고리 삭제 처리
function removeDispCtg(ctgSeq){
	// ui 삭제 처리
	$(".ctgSelectInfo" + ctgSeq).remove();
	
	// 데이터 삭제 처리
	for(var i=0, d ; d = ctgSeqDisp[i] ; i++){
		if(d == ctgSeq){
			ctgSeqDisp.splice(i, 1);
			break;
		}
	}
}




///// 상품 정보 관련

// 전송 처리
function sendData(){
	// 데이터 구성
	var data = { mode : '', itemSeq : 0, itemNm : '', searchKeyword : '', descShort : '', descLong : '', itemStat : '', itemDispStat : '', stockCntTot : 0, sellCntMin : 0, sellCntMax : 0, price : 0, priceDc : 0 }
	
	for(var key in data){
		if($("input[name=" + key + "]").length == 1){
			data[key] = $.trim($("input[name=" + key + "]").val());
		}else if($("select[name=" + key + "]").length == 1){
			data[key] = $("select[name=" + key + "]").val();
		}else{
			data[key] = $("textarea[name=" + key + "]").val();
		}
	}
	
	// 카테고리 정보 구성
	data.ctgSeqMst		= $(".ctgSelectAreaMst").getCtgSelectValue();
	data.ctgSeqDispStr	= JSON.stringify(ctgSeqDisp);
	

	// 첨부파일 정보 구성
	data.fileMstInfoStr = JSON.stringify(fileMstInfoList);
	data.fileSubInfoStr = JSON.stringify(fileSubInfoList);
	
	
	// 필수값 체크
	if(data.itemNm == ""){ alert("상품명을 입력하십시오."); $("input[name=itemNm]").trigger("focus"); return; }
	if(data.descLong == ""){ alert("상품설명을 입력하십시오."); $("textarea[name=descLong]").trigger("focus"); return; }

	if(data.stockCntTot == "" || isNaN(data.stockCntTot)){ alert("재고 수량은 숫자만 입력이 가능합니다."); $("input[name=stockCntTot]").trigger("focus"); return; }
	
	if(data.sellCntMin == "" || data.sellCntMin == 0){ alert("최소판매수량을 입력하십시오."); $("input[name=sellCntMin]").trigger("focus"); return; }
	if(isNaN(data.sellCntMin)){ alert("최소판매수량은 숫자만 입력이 가능합니다."); $("input[name=sellCntMin]").trigger("focus"); return; }

	if(data.sellCntMax == "" || data.sellCntMax == 0){ alert("최대판매수량을 입력하십시오."); $("input[name=sellCntMax]").trigger("focus"); return; }
	if(isNaN(data.sellCntMax)){ alert("최대판매수량은 숫자만 입력이 가능합니다."); $("input[name=sellCntMax]").trigger("focus"); return; }
	
	if(data.price == "" || data.price == 0){ alert("판매가를 입력하십시오."); $("input[name=price]").trigger("focus"); return; }
	if(isNaN(data.price)){ alert("판매가는 숫자만 입력이 가능합니다."); $("input[name=price]").trigger("focus"); return; }

	if(data.priceDc == "" || data.priceDc == 0){ alert("할인가를 입력하십시오."); $("input[name=priceDc]").trigger("focus"); return; }
	if(isNaN(data.priceDc)){ alert("할인가는 숫자만 입력이 가능합니다."); $("input[name=priceDc]").trigger("focus"); return; }

	if(data.ctgSeqMst == 10){ alert("대표카테고리를 선택하십시오."); $(".ctgSelectAreaMst").find("select:first").trigger("focus"); return; }
	if(ctgSeqDisp.length == 0){ alert("전시카테고리를 선택하십시오."); $(".ctgSelectAreaDisp").find("select:first").trigger("focus"); return; }

	// 대표이미지 점검
	var imgYn = "N";
	for(var i=0, d ; d = fileMstInfoList[i] ; i++){
		if(d.useYn == "Y"){ imgYn = "Y"; break; }
	}
	if(imgYn == "N"){ alert("대표이미지를 등록해주십시오."); return; }

	// 상세이미지 점검
	imgYn = "N";
	for(var i=0, d ; d = fileSubInfoList[i] ; i++){
		if(d.useYn == "Y"){ imgYn = "Y"; break; }
	}
	if(imgYn == "N"){ alert("상세이미지를 등록해주십시오."); return; }
	
	// 전송
	$.ajax({
		async		: true,
		url			: '/item/itemSave.json',
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
			
			if($("input[name=mode]").val() == "update"){
				location.href = "/item/itemView.do?<core:param skip="mode" />";
			}else{
				location.href = "/item/itemList.do";
			}
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
	
	
}


$(document).ready(function(){
	// 파일영역 초기화
	initFile();

	// 카테고리 선택 인터페이스 구성 - 마스터
	$(".ctgSelectAreaMst").ctgSelect({
		ctgPath : "${data.ctgMst.ctgPath}", 
		data : { dispYn : "Y" }
	});
	
	// 카테고리 선택 인터페이스 구성 - 전시
	$(".ctgSelectAreaDisp").ctgSelect({
		data : { dispYn : "Y" }
	});
	
	
});


</script>
