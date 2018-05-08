package kr.co.crewmate.core.web.resolver;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * tiles를 적용하지 않고 그냥 다음 resolver를 사용하기 위해서 재정의함.
 * @author 정슬기
 *
 */
public class TilesViewResolver extends org.springframework.web.servlet.view.tiles3.TilesViewResolver {
	//private Renderer renderer;

	/**
	 * 여기가 재정의한 내용임.
	 * 	- 여기에서 null을 리턴하면 다음 resolver를 찾아감.
	 */
	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		if(StringUtils.startsWith(viewName, "no-tiles")){ return null; }
		String realViewName = StringUtils.contains(viewName, ":") ? StringUtils.substringAfter(viewName, ":") : viewName; 
		
		// 여기는 원래 loadView의 기능 내용임.
		AbstractUrlBasedView view = buildView(realViewName);
		View result = (View) getApplicationContext().getAutowireCapableBeanFactory().initializeBean(view, realViewName);
		return (view.checkResource(locale) ? result : null);
	}
}
