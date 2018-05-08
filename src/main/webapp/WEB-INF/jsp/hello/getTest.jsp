<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
데이터 가져오기
	<c:choose>
		<c:when test="${getTest ne null }">
			<div>
				${getTest.testSeq }
				${getTest.title }
				${getTest.contents }
			</div>
		</c:when>
		<c:otherwise>
			<div>
				요청하신 데이터가 존재하지 않습니다.
			</div>
		</c:otherwise>
	</c:choose>
</div>