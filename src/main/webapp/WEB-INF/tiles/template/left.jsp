<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<hr />

<%-- 전시카테고리 목록 구성 --%>
<c:if test="${not empty ctgRootDisp}">
	<%-- 대카테고리 목록 구성 --%>
	<div class="lCtgListArea">
		<c:forEach items="${ctgRootDisp.subCtgList}" var="i" varStatus="s">
			<span onmouseover="gnbShowSubCtgList(${i.ctgSeq}, 'large')"><a href="/category/category.do?ctgSeq=${i.ctgSeq}"><c:out value="${i.ctgNm}" /></a></span>
			<c:if test="${not s.last}">|</c:if>
		</c:forEach>
	</div>
	
	<%-- 중카테고리 목록 구성 --%>
	<div class="mCtgListArea">
		<c:forEach items="${ctgRootDisp.subCtgList}" var="i1" varStatus="s1">
			<div class="mCtgListAreaItem CtgListAreaItem${i1.ctgSeq}"  style="display:none;">
				<c:forEach items="${i1.subCtgList}" var="i2" varStatus="s2">
					<span onmouseover="gnbShowSubCtgList(${i2.ctgSeq}, 'middle')"><a href="/category/category.do?ctgSeq=${i2.ctgSeq}"><c:out value="${i2.ctgNm}" /></a></span>
					<c:if test="${not s2.last}">|</c:if>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	
	<%-- 소카테고리 목록 구성 --%>
	<div class="sCtgListArea">
		<c:forEach items="${ctgRootDisp.subCtgList}" var="i1" varStatus="s1">
			<c:forEach items="${i1.subCtgList}" var="i2" varStatus="s2">
				<div class="sCtgListAreaItem CtgListAreaItem${i2.ctgSeq}" style="display:none;">
					<c:forEach items="${i2.subCtgList}" var="i3" varStatus="s3">
						<span><a href="/category/category.do?ctgSeq=${i3.ctgSeq}"><c:out value="${i3.ctgNm}" /></a></span>
						<c:if test="${not s3.last}">|</c:if>
					</c:forEach>
				</div>
			</c:forEach>
		</c:forEach>
	</div>

</c:if>


<hr />


<script>
// 하위 카테고리 목록을 노출함.
function gnbShowSubCtgList(ctgSeq, type){
	// 기존 노출 항목 미노출 처리 - 대카/중카에 따라 다름.
	if(type == "large"){
		$(".mCtgListAreaItem").hide();
		$(".sCtgListAreaItem").hide();
	}
	
	if(type == "middle"){
		$(".sCtgListAreaItem").hide();
	}
	
	
	
	// 선택항목 노출 처리
	$(".CtgListAreaItem" + ctgSeq).show();
}
</script>