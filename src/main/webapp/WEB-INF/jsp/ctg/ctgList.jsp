<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/crewmate/core" prefix="core" %>

<h3>카테고리 목록 </h3> <button class="btn btn-primary" onclick="location.href='/category/ctgForm.do'"> 카테고리 작성</button>

<hr />

<div id="tree">
</div>



<script>
//카테고리를 트리형식으로 구성함.
function createTree(){
	$("#tree").jstree({
		core : {
			data : {
				url : function(node){
					return '/category/ctgListData.json';
				}, 
				data : function(node){
					return { ctgSeqParent : (node.id === "#") ? 0 : node.original.ctgSeq };
				},
				dataFilter(resStr){
					var data = JSON.parse(resStr);
					// 비정상인 경우
					if(data.resultCode != 1){ return "[]"; }
					
					// 정상 처리
					var list = [];
					
					for(var i=0, d ; d = data.list[i] ; i++){
						list.push(
							$.extend(true, d, {
								text : d.ctgNm + " [" + d.dispYn + ", " + d.dispNo + "]", 
								id : d.ctgSeq, 
								children : d.subCtgYn == "Y" ? true : false 
							})
						);
					}
					
					// 정상결과 리턴
					return JSON.stringify(list);
				}/*, 
				success : function(data, status, xhr){
					console.log(data);
				}*/
			}
		}
	})
	.on("select_node.jstree", function(evt, data){
		location.href = "/category/ctgForm.do?ctgSeq=" + data.node.id + "&mode=update";
	});
}



$(document).ready(function(evt){
	// 카테고리를 트리형식으로 구성함.
	createTree();
});


</script>