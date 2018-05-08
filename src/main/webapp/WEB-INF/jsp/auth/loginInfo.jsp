<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<h2>로그인 Info 화면</h2>
	<table class="table table-bordered">
		<c:choose>
			<c:when test="${not empty currUser }">
				<tr>
					<th>${currUser.userId}님 로그인 하셨습니다.</th>
				</tr>
				<tr>
					<th colspan="2">
						<button class="btn btn-outline-success" onclick="logout()">로그아웃</button>
					</th>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<th colspan="2">잘못된 회원정보입니다.</th>
				</tr>
				<tr>
					<th colspan="2">
						<a class="btn btn-outline-success" href="/">메인페이지로 이동</a>
					</th>
				</tr>
			</c:otherwise>
		</c:choose>
		
	</table>
</div>

<script>
// 로그아웃 처리
function logout(){
	// 전송
	$.ajax({
		async		: true,
		url			: '/auth/logout.json',
		dataType	: 'json',
		type		: 'POST',
		data		: {},
		success		: function(data, textStatus, jqXHR){
			console.log(data);
			
			// 처리결과 확인
			if(data.resultCode != 1){
				alert("잘못된 요청입니다.");
				return;
			}
			
			// 정상 처리
			alert("로그아웃 되었습니다.");
			location.href = "/";
		},
		error	   : function(jqXHR, textStatus, errorThrown){
		  //console.log(jqXHR.status);
		}
	});
}

</script>
