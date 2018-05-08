<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/style/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<!-- open-iconic-bootstrap (icon set for bootstrap) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/open-iconic/1.1.1/font/css/open-iconic-bootstrap.min.css" />
<!-- jstree css -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />


<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- 구버전 explorer jquery 사용에 필요 -->
<script src="https://code.jquery.com/jquery-migrate-3.0.1.min.js"></script>
<!-- 쿠키 jquery -->
<script src="/script/lib/jquery.cookie.js"></script>
<!-- 구버전 브라우저 ajax 사용에 필요 -->
<script src="/script/lib/jquery.form.min.js"></script>
<!-- 부트스트랩 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<!-- jstree -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
<!-- 공통 라이브러리 정의 -->
<script src="/script/crew/enitsed.common.js"></script>
<script src="/script/crew/enitsed.form.js"></script>
<script src="/script/lib/jquery.ctglib.js"></script>

<title><tiles:getAsString name="title" /></title>
</head>
<body>
<div class="container">
	<header class="m-3">
		<tiles:insertAttribute name="header" />
	</header>
	<aside>
		<tiles:insertAttribute name="left" />
	</aside>
	<div>
		<tiles:insertAttribute name="body" />
	</div>
	<aside>
		<tiles:insertAttribute name="right" />
	</aside>
	<footer class="m-3">
		<tiles:insertAttribute name="footer" />
	</footer>
</div>
</body>
</html>