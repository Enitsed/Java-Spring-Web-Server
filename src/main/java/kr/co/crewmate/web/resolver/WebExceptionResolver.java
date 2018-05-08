package kr.co.crewmate.web.resolver;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.exception.UserAuthException;
import kr.co.crewmate.core.exception.BadRequestException;

/**
 * 예외 정보 처리  
 * @author crewmate
 *
 */
public class WebExceptionResolver extends SimpleMappingExceptionResolver implements HandlerExceptionResolver{
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * @param request
	 * @param response
	 * @param handler 실행되 있는데 예외 핸들러
	 * @param ex 핸들러에게 넘겨지는 예외
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView model	= super.resolveException(request, response, handler, ex);
		String requestURI	= request.getRequestURI();
		String queryString	= StringUtils.defaultIfEmpty(request.getQueryString(), "");
		String method		= StringUtils.defaultIfEmpty(request.getMethod(), "");
		String viewType		= "html";
		
		if(handler != null) {
			// request type을 판단함.
			if(StringUtils.contains(request.getHeader("Accept"), "application/json") || StringUtils.contains(requestURI, ".json")){
				viewType = "json";
				model.setView(new MappingJackson2JsonView());
			}else{
				viewType = "html";
			}
		}
		
		// 공통 Exception에 대한 처리
		model.getModelMap().remove("exception");		
		
		if(ex instanceof BadRequestException) {
			log.debug("---------------------------------------->1");
			log.debug(ex.getMessage());
			
			model.addObject("message", ex.getMessage()); // BadRequestException message 만 노출되게 처리
			
			BadRequestException e = (BadRequestException) ex;
			model.addObject("messageList", e.getMessageList());
			
			// 장애에 대한 메세지가 없는 경우의 공통 처리
			if(StringUtils.isEmpty(e.getMessage()) && (e.getMessageList() == null || e.getMessageList().size() == 0)){
				model.addObject("message", "bad request");
			}
			
			if(((BadRequestException) ex).getResultCode() != 100){
				model.addObject("resultCode", ((BadRequestException) ex).getResultCode());
			}
			
			// json 요청이 아닌경우 뷰 지정
			if(!viewType.equals("json")){model.setViewName("exception/badRequestException");}
		}else if(ex instanceof UserAuthException){		// 사용자 인증 예외의 경우 처리
			UserAuthException userException = (UserAuthException)ex;
			model.addObject("resultCode", userException.getResultCode());
			
			// 현재 경로를 리다이렉트 경로로 구성함.
			String requestURL = requestURI + (StringUtils.isNotEmpty(queryString) ? "?" + queryString : "");

			// GET 방식으로 redirectUrl이 넘어온 경우, 로그인 후 redirectUrl로 이동
			String redirectUrl	= StringUtils.defaultIfEmpty(request.getParameter("redirectUrl"), "");
			if(StringUtils.startsWithIgnoreCase(redirectUrl, "http")){	redirectUrl	= "";			}				// 타 사이트로 이동하려는 부분을 방지함. - 현재 사이트 안에서만 이동할 것!
			if(method.equals("GET") && !redirectUrl.equals("")){ requestURL = redirectUrl;	}

			try {
				requestURL = URLEncoder.encode(requestURL, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				requestURL = "";
			}			
			
			// 뷰타입에 따라서 응답형식을 지정함.
			if(StringUtils.equals(viewType, "html")){
				model.clear();
				model.setViewName("redirect:http://" + Constants.SERVER_NAME + ":" + Constants.SERVER_PORT + Constants.LOGIN_URL + "?redirectUrl=" + requestURL);
			}else if(StringUtils.equals(viewType, "json")){
				model.addObject("loginUrl", "http://" + Constants.SERVER_NAME + ":" + Constants.SERVER_PORT + Constants.LOGIN_URL);
				model.addObject("redirectUrl", StringUtils.defaultIfBlank(request.getHeader("referer"), ""));
			}
		}else {
			// 메세지 처리
			model.addObject("message", "system fail");
			
			// 에러코드 처리
			model.addObject("resultCode", "9999");
			
			// 뷰 지정
			model.setViewName("exception/baseException");
		}
		
		// Exception 로그 기록
		ex.printStackTrace();
		log.error(ex.getMessage(),ex);		
		
		return model;
	}
}
