<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<hr>
		param1 : ${param.param1}
	<hr>
	
	<a href="getTestList">데이터베이스 리스트 조회</a>
	<hr>
	<form action="getTest.do">
		<label for="index">index</label><input type="text" name="index">
		<input type="submit" value="전송">
	</form>
	<hr>
	<h3>업데이트</h3>
	<form action="updateTest.do" method="post">
		<label for="index">index</label><input type="text" name="index">
		<label for="title">title</label><input type="text" name="title">
		<label for="contents">content</label><input type="text" name="contents">
		<input type="submit" value="전송">
	</form>
	<hr>
	
	<h3>추가</h3>
	<form action="createTest.do" method="post">
		<label for="title">title</label><input type="text" name="title">
		<label for="contents">content</label><input type="text" name="contents">
		<input type="submit" value="전송">
	</form>
	<hr>
	<div>
		<c:forEach items="${authCheck }" var="value">
			authCheck : ${value }
		</c:forEach>
	</div>
</div>