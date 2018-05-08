package kr.co.crewmate.core.web.view;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author crewmate
 * JSON View 처리 클래스
 * 	일반적으로 MappingJackson2JsonView를 사용하나 헤더를 application/json이 아니라
 *  다른형식(text/plain)으로 보내야 하는 경우에 사용함. 
 * 	-> Content-Type -< application/json;charset=UTF-8
 */
public class JSONView extends AbstractView {
	private String contentType	= "text/plain;charset=UTF-8";
	
	public JSONView(){}
	
	public JSONView(String contentType){
		this.contentType = contentType;
	}
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(model);

		response.setHeader("Content-Type", contentType);
		Writer out = response.getWriter();
		out.append(json);
	}
	

}