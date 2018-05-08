<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	리스트
	<c:choose>
		<c:when test="${!empty getTestList}">
			<c:forEach items="${getTestList }" var="testObject">
				<div>
					<p>${testObject.testSeq }</p>
					<p>${testObject.title }</p>
					<p>${testObject.contents }</p>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div>
				요청하신 데이터가 존재하지 않습니다.
			</div>
		</c:otherwise>
	</c:choose>
</div>