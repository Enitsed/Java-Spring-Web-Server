<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div>
	<h2>회원가입 화면</h2>
	
	<form name="form" onsubmit="return false">
		<table class="table table-bordered">
		<tr>
			<th><label for="userId">아이디</label></th>
			<td class="form-check-inline" width="100%">
			<input class="form-control" type="text" name="userId" />
			<button class="btn btn-outline-warning mx-sm-1" type="button" onclick="checkDuplicate()">중복체크</button>
			</td>
		</tr>
		<tr>
			<th><label for="userPw">비밀번호</label></th>
			<td><input class="form-control" type="password" name="userPw" ></td>
		</tr>
		<tr>
			<th><label for="Name">이름</label></th>
			<td><input class="form-control" type="text" name="name" ></td>
		</tr>
		<tr>
			<th><label for="Email">이메일</label></th>
			<td><input class="form-control" type="text" name="email" ></td>
		</tr>
		<tr>
			<td colspan="2">
				<button class="btn btn-outline-success" onclick="sendData()">회원가입</button>
			</td>
		</tr>
		</table>
	</form>
	<a class="btn btn-outline-primary" href="/auth/loginForm.do">로그인 창으로 이동</a>
</div>

<script>
//중복체크 결과를 저장하는 변수들...
var dupCheckId		= "";

// 아이디 중복 체크 처리
function checkDuplicate(){
	var id = $.trim($("input[name=userId]").val());
	
	// 필수값 체크
	if(id == ''){ alert("아이디를 입력해 주십시오."); return; }
	
	// 전송
	$.ajax({
		async		: true,
		url			: '/join/userIdCheck.json',
		dataType	: 'json',
		type		: 'POST',
		data		: { userId : id },
		success		: function(data, textStatus, jqXHR){
			// 처리결과 확인
			if(data.resultCode != 1){
				alert("잘못된 요청입니다.");
				return;
			}
			
			// 정상인경우에 검증된 아이디 정보 저장
			dupCheckId = data.useYn == "Y" ? id : "";
			
			// 메세지 처리
			alert(data.useYn == "Y" ? "사용 가능한 아이디입니다." : "사용할 수 없는 아이디입니다.");
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}

// 전송 처리
function sendData(){
	// 데이터 구성
	var data = { userId : '', userPw : '', name : '', email : '' }
	for(var key in data){ data[key] = $.trim($("input[name=" + key + "]").val()); }
	
	// 필수값 체크
	if(data.userId == ""){ alert("아이디를 입력하십시오."); $("input[name=userId]").trigger("focus"); return; }
	if(data.userPw == ""){ alert("비밀번호를 입력하십시오."); $("input[name=userPw]").trigger("focus"); return; }
	if(data.name == ""){ alert("이름을 입력하십시오."); $("input[name=name]").trigger("focus"); return; }
	
	// 아이디 중복 체크 여부 검증
	if(dupCheckId != data.userId){
		alert("아이디 중복체크를 해야 합니다.");
		dupCheckId = "";
		return;
	}
	
	// 전송
	$.ajax({
		async		: true,
		url			: '/join/userJoin.json',
		dataType	: 'json',
		type		: 'POST',
		data		: data,
		success		: function(data, textStatus, jqXHR){
			// 처리결과 확인
			if(data.resultCode != 1){
				alert("잘못된 요청입니다.");
				return;
			}
			
			// 정상 메세지 처리
			alert("등록되었습니다.");
			location.href = "/";
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}
</script>