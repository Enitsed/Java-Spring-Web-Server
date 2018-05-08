/**
 * 2018.04.20
 * 제이쿼리 카테고리 플러그인
 */
$.ctgLib = {
	// 카테고리 선택 인터페이스 구성
	ctgSelect : function(options){

		// 기본값 구성 및 옵션 정보 구성
		var defaults = {
			url			: "/category/ctgListData.json",		// 데이터 요청 경로
			ctgSeqRoot	: 10, 							// 최상위 카테고리 정보
			ctgPath		: "",							// 선택되어진 카테고리의 경로 정보
			initFn		: null,							// 초기화 완료후 콜백 처리 함수
			changeFn	: null,							// onchange 처리 함수
			initYn		: "N",							// 초기화 완료여부
			data	: {
				ctgSeqParent	: 10					// 시작 카테고리 번호
			}
		}
		options = $.extend(true, defaults, options);

		// 영역에 별도 클래스 추가
		$(this).addClass("_ctgSelectArea");
		
		// 옵션정보 저장
		$(this).data("options", options);
		
		// select인터페이스 구성 시작
		$.ctgLib.createSelect(this);
	}, 
	
	// select 인터페이스 구성
	createSelect : function(obj){
		var options = $(obj).data("options");

		// 데이터 요청
		$.ajax({
			async		: true,
			url			: options.url,
			dataType	: 'json',
			type		: 'POST',
			data		: options.data,
			success		: function(data, textStatus, jqXHR){
				console.log(data);
				// 처리결과 확인
				if(data.resultCode != 1){ alert("잘못된 요청입니다."); return; }
				if(data.list.length == 0){
					options.initYn = "Y";
					$(obj).data("options", options);
					
					if(typeof(options.initFn) == "function"){
						options.initFn(obj);
					}
					
					return;
				}
				
				// 정상 처리
				var html = [];
				html.push("<select onchange='$.ctgLib.changeSelect(this)'>");
				html.push("<option value=''>선택</option>");
				for(var i=0, d ; d = data.list[i] ; i++){
					html.push("<option value='" + d.ctgSeq + "'>" + d.ctgNm + "</option>");
				}
				html.push("</select>");
				$(obj).append(html.join(""));
				
				
				// 하위 항목이 지정되어 있지 않은 경우 추가 실행 - 초기화 완료여부 처리 및 추가 함수 실행
				if(options.ctgPath == ""){
					options.initYn = "Y";
					$(obj).data("options", options);
					
					if(typeof(options.initFn) == "function"){
						options.initFn(obj);
					}
				}
				
				
				// 하위 선택 처리
				if(options.ctgPath != ""){
					var ctgList = options.ctgPath.split(/,/g);
					
					for(var i=0, d ; d = ctgList[i] ; i++){
						if(d == options.data.ctgSeqParent){
							// 다음 선택항목이 존재하는 경우 change이벤트를 강제로 발생시킴.
							if(ctgList.length > i+1){
								var selValue = ctgList[i+1];
								$(obj).find("select:last").find("option[value=" + selValue + "]").attr("selected", true);
								$(obj).find("select:last").trigger("change");
								break;
							}
							
							// 마지막까지 선택처리가 완료된 경우의 처리 - 초기화 완료여부 처리 및 추가 함수 실행
							options.initYn = "Y";
							$(obj).data("options", options);
							
							if(typeof(options.initFn) == "function"){
								options.initFn(obj);
							}							
						}
					}
				}
				
				
			},
			error	   : function(jqXHR, textStatus, errorThrown){
			  //console.log(jqXHR.status);
			}
		});		
	}, 
	
	// select 변경 이벤트 처리
	changeSelect : function(sel){
		var area = $(sel).closest("._ctgSelectArea");
		var options	= area.data("options");
		
		// 하위 select 항목들 삭제
		var selList 	= area.find("select");
		var removeYn	= "N";
		for(var i=0, s ; s = selList[i] ; i++){
			if(removeYn == "Y"){ $(s).remove(); }
			if(s == sel){ removeYn = "Y"; }
		}
		
		// change 콜백이 존재하는 경우에는 콜백 호출후 종료
		if(typeof(options.changeFn) == "function" && options.initYn == "Y"){
			options.changeFn(area);
			return;
		}
		
		
		// 선택된 값이 없는 경우 리턴
		if(sel.value == ""){ return; }
		
		// 요청 카테고리 번호 정보 변경
		options.data.ctgSeqParent = sel.value;
		area.data("options", options);
		
		// select영역 생성 처리
		$.ctgLib.createSelect(area);
	}, 
	
	// 최종 선택값 반환 처리
	getCtgSelectValue : function(){
		var options	= $(this).data("options");
		var selList = $(this).find("select");
		
		for(var i = selList.length-1, d ; d = selList[i] ; i--){
			if(d.value != ""){ return d.value; }
		}
		
		return options.ctgSeqRoot;
	}, 
	
	// 최종 선택 정보를 반환함.
	getCtgSelectInfo : function(){
		var options	= $(this).data("options");
		var selList = $(this).find("select");
		
		// 리턴값 구성
		var rv = { value : [], text : [], lastValue : "" }
		for(var i = 0, d ; d = selList[i] ; i++){
			if(d.value != ""){
				rv.value.push(d.value);
				rv.text.push($(d).find("option:selected").text());
				rv.lastValue = d.value;
			}
		}
		if(rv.lastValue == ""){ rv.lastValue = options.ctgSeqRoot; }
		
		
		// 경로정보 구성
		rv.ctgPath = options.ctgSeqRoot + (rv.value.length != 0 ? "," + rv.value.join(",") : "");
		
		// 리턴
		return rv;
		
	}
};

(function($) {
	$.fn.ctgSelect			= $.ctgLib.ctgSelect;
	$.fn.getCtgSelectValue	= $.ctgLib.getCtgSelectValue;
	$.fn.getCtgSelectInfo	= $.ctgLib.getCtgSelectInfo;
})(jQuery);

