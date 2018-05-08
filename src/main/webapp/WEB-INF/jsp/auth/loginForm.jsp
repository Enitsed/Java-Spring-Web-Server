<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<h2>로그인 폼 화면</h2>
	<form name="form" onsubmit="return false">
		<input type="hidden" name="redirectUrl" value="<c:out value="${param.redirectUrl }" />" />
		<table class="table table-bordered">
			<tr>
				<th><label for="userId">아이디</label></th>
				<td><input class="form-control" type="text" name="userId" value="" />
			</tr>
			<tr>
				<th colspan="2">
					<label for="saveIdYn">아이디 저장</label>
					<input type="checkbox" name="saveIdYn" />
				</th>
			</tr>
			<tr>
				<th><label for="userPw">비밀번호</label></th>
				<td><input class="form-control" type="password" name="userPw" />
			</tr>
			<tr>
				<td colspan="2">
					<input class="btn btn-outline-success" type="button" onclick="sendData()" value="로그인" />
				</td>
			</tr>
		</table>
	</form>
	<a class="btn btn-outline-primary" href="/">메인 화면으로 이동</a>
</div>

<script>
// 전송 처리
function sendData(){
	// 데이터 구성
	var data = { userId : '', userPw : '', redirectUrl : '' }
	for(var key in data){ data[key] = $.trim($("input[name=" + key + "]").val()); }
	
	// 필수값 체크
	if(data.userId == ""){ alert("아이디를 입력하십시오."); $("input[name=userId]").trigger("focus"); return; }
	if(data.userPw == ""){ alert("비밀번호를 입력하십시오."); $("input[name=userPw]").trigger("focus"); return; }

	// 아이디 저장 여부
	data.saveIdYn = $("input[name=saveIdYn]").prop("checked") ? "Y" : "N";
	
	// 전송
	$.ajax({
		async		: true,
		url			: '/auth/login.json',
		dataType	: 'json',
		type		: 'POST',
		data		: data,
		success		: function(data, textStatus, jqXHR){
			console.log(data);
			
			// 처리결과 확인
			if(data.resultCode != 1){
				alert("아이디와 비밀번호가 일치하지 않습니다.");
				return;
			}
			
			// 정상 처리
			alert("로그인되었습니다.");
			location.href = data.redirectUrl;
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}

// 사용자 아이디 저장을 한 경우 값 채우기
function setUserId(){
	if($.cookie("uid") != ""){
		$("input[name=userId]").val($.cookie("uid"));
		$("input[name=saveIdYn]").prop("checked", true);
	}
}

$(document).ready(function(evt){
	setUserId();
});
</script>