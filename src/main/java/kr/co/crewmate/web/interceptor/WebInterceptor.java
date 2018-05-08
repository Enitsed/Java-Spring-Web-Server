package kr.co.crewmate.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.exception.UserAuthException;
import kr.co.crewmate.api.model.User;
import kr.co.crewmate.api.service.CategoryService;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.web.annotation.UserAuthCheck;
/**
 * 
 * @author 정슬기
 * date : 2018.03.28
 * 설명 : 인터셉터 로그
 *
 */
public class WebInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(WebInterceptor.class);

	@Autowired
	private CategoryService ctgService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			logger.debug("[WebInterceptor] : {}", "PreHandle Start!");
			
			// 요청 uri, controller, method 획득
			String requestURI = request.getRequestURI();
			Method method = ((HandlerMethod) handler).getMethod();
			ApiBaseController controller = (ApiBaseController) ((HandlerMethod) handler).getBean();
			// 현재 사용자 정보 셋팅
			User currUser = controller.getFoUser();
			request.setAttribute("currUser", currUser);
			// UserAuthCheck Annotation 체크 - 사용자 로그인이 되어 있지 않으면 UserAuthException
			UserAuthCheck userAuthCheck = method.getAnnotation(UserAuthCheck.class);
			if(userAuthCheck != null && currUser == null) { throw new UserAuthException(); }

			// 화면에 대한 요청인 경우에 대한 처리
			if(StringUtils.contains(requestURI, ".do")){
				// 전시 카테고리 정보를 구성함.
				request.setAttribute("ctgRootDisp", this.ctgService.getCtg(Constants.ctgRootSeq.DISP));
			}
			
		}
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						ModelAndView modelAndView) throws Exception {
		if (handler instanceof HandlerMethod) {
			logger.debug("[WebInterceptor] : {}", "PostHandle End!");
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
}
