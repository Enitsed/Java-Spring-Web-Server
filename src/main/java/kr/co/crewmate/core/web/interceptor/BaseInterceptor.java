package kr.co.crewmate.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BaseInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			logger.debug("[BaseInterceptor]", "PreHandle Start!");
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (handler instanceof HandlerMethod) {
			logger.debug("[BaseInterceptor]", "PostHandle End!");
			super.postHandle(request, response, handler, modelAndView);
		}
		super.postHandle(request, response, handler, modelAndView);
	}

}
