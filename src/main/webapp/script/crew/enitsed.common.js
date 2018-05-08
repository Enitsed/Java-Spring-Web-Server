$.crewFileLib = {
	// 일반 파일 업로드 처리
	uploadCommon : function(options){
		var defaults = {
			input			: null,				// input object. jquery오브젝트가 아님! 
			success			: null, 
			uploadProgress	: null, 
			url				: "/common/file/upload.json"	
		};
		options = $.extend(true, defaults, options); 
		
		var key			= Math.ceil(Math.random() * 1000000);
		var formName	= "tempForm" + key;

		var html = [];
		html.push("<form name='tempForm" + key + "' method='post' enctype='multipart/form-data' style='display:none;'>");
		html.push("</form>");
		$(options.input).after(html.join(""));
		// 폼의 input file 구성
		var form = $("form[name=" + formName + "]");
		form.append(options.input);

		// 전송
		form.ajaxSubmit({
			url			: options.url,
			dataType	: "json", 
			complete: function(xhr) {
				// 임시폼 삭제 처리
				form.before(options.input);
				form.remove();
			},
			uploadProgress : function(event, position, total, percentComplete) {
				if(typeof(options.uploadProgress) == "function"){
					options.uploadProgress(event, position, total, percentComplete);
					
				}				
			},
			success: function(data, xhr, status) {
				console.log("uploadCommon success");
				console.log(data);
				
				if(typeof(options.success) == "function"){
					options.success(data, xhr, status);
				}
			}
		}); 
	}		
}