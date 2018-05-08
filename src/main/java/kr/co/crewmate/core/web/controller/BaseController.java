package kr.co.crewmate.core.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.crewmate.core.model.Result;

/**
 * 모든 컨트롤러의 기본 기능을 수행하는 컨트롤러
 * @author 정슬기
 * 작성일 2018.03.28
 *
 */
@Controller
public class BaseController{


	/**
	 * request 객체를 반환함.
	 */
	public HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}
	
	/**
	 * request uri 정보를 반환함.
	 * @return
	 */
	public String getRequestURI(){
		return this.getRequest().getRequestURI();
	}
	
	
	/**
	 * session 객체를 반환함.
	 * @return
	 */
	public HttpSession getSession() {
		return this.getRequest().getSession(true);
	}
	
	
	/**
	 * 세션에 필요한 정보를 저장함.
	 * @param key
	 * @param o
	 */
	public void saveSession(String key, Object o){
		this.getSession().setAttribute(key, o);
	}
	
	
	/**
	 * 세션에서 해당 정보를 반환함.
	 * @param key
	 * @return
	 */
	public Object getSession(String key){
		return this.getSession().getAttribute(key);
	}
	
	
	/**
	 * 세션에서 해당 정보를 삭제 처리함.
	 * @param key
	 */
	public void removeSession(String key){
		this.getSession().removeAttribute(key);
	}

	/**
	 * session id 반환
	 * @return
	 */
	public String getSessionId() {
		String sessionId = this.getSession().getId();
		return sessionId;
	}	
	
	/**
	 * 해당 쿠키의 값을 반환함.
	 * @param name
	 */
	public String getCookie(String name){
		Cookie[] cookies = this.getRequest().getCookies();

		if(cookies != null){
			for(Cookie c : this.getRequest().getCookies()){
				if(StringUtils.equals(c.getName(), name)){
					return c.getValue();
				}
			}			
		}
		
		return null;
	}
	
	
	/**
	 * 해당 정보를 쿠키에 저장함.
	 * @param response
	 * @param name
	 * @param value
	 */
	public void saveCookie(HttpServletResponse response, String name, String value){
		Cookie cookie = new Cookie(name, value.replaceAll("(\r\n|\n|\r)", ""));
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 해당 정보를 쿠키에 저장함.
	 * @param response
	 * @param name
	 * @param value
	 */
	public void saveCookie(HttpServletResponse response, String name, String value, int expiry){
		Cookie cookie = new Cookie(name, value.replaceAll("(\r\n|\n|\r)", ""));
		cookie.setPath("/");
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}

	
	/**
	 * 해당 쿠키값을 삭제함.
	 * @param response
	 * @param name
	 */
	public void removeCookie(HttpServletResponse response, String name){
		Cookie cookie = new Cookie(name, "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	
	/**
	 * result 정보를 model에 적용시킨다.
	 * @param model
	 * @param result
	 */
	public void addResultToModel(ModelMap model, Result result){
		model.addAttribute("resultCode", result.getResultCode());
		model.addAttribute("message", result.getMessage());
		if(result.getInfoMap() != null){
    		model.addAttribute("info", result.getInfoMap());
    	}
	}

	/**
	 * result 정보를 model에 적용시킨다.
	 * @param model
	 * @param resultCode
	 * @param message
	 */
	public void addResultToModel(ModelMap model, int resultCode, String message){
		model.addAttribute("resultCode", resultCode);
		model.addAttribute("message", message);
	}
}
