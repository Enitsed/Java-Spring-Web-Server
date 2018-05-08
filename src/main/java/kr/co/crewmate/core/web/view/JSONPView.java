package kr.co.crewmate.core.web.view;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.view.AbstractView;

/**
 * JSONP View 처리 클래스
 * 
 * @author Administrator
 *
 */
public class JSONPView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String callback = StringUtils.defaultIfEmpty(request.getParameter("callback"), "callback");
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(model);

		Writer out = response.getWriter();
		out.append(callback).append("(").append(json).append(")");
	}

}
