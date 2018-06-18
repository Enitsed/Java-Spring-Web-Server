package kr.co.crewmate.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.model.User;
import kr.co.crewmate.api.service.AuthService;
import kr.co.crewmate.api.service.UserService;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.util.HashUtil;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 인증 관련 controller
 * @author crewmate
 *
 */
@CrossOrigin(origins="http://localhost:4200")
@Controller
public class AuthController extends ApiBaseController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 로그인폼 처리
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping("/auth/loginForm")
	public ModelAndView loginForm() {
		return new ModelAndView("auth/loginForm");
	}
	
	/**
	 * 로그인 처리
	 * @param model
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/auth/login")
	public View login(
		HttpServletResponse response, 
		ModelMap model, 
		User user,
		@RequestParam(name="saveIdYn", required=false, defaultValue="N") String saveIdYn, 
		@RequestParam(name="redirectUrl", required=false, defaultValue="") String redirectUrl
	) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(user.getUserId(), user.getUserPw());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }
		
		// 회원조회 처리. 
		User chkUser = this.userService.getUser(user.getUserId());
		if(chkUser == null) {
			this.authService.createAuthHist(super.getRequest(), null, user.getUserId(), Constants.authType.LOGIN, Constants.N);
			super.addResultToModel(model, 1000, "아이디와 패스워드가 일치하지 않습니다.");
			return new MappingJackson2JsonView();
		}
		
		// 패스워드 검증
		String chkPw = HashUtil.sha256(user.getUserPw(), user.getUserPw());
		if(!StringUtils.equals(chkUser.getUserPw(), chkPw)) {
			this.authService.createAuthHist(super.getRequest(), chkUser.getUserSeq(), user.getUserId(), Constants.authType.LOGIN, Constants.N);
			super.addResultToModel(model, 1000, "아이디와 패스워드가 일치하지 않습니다.");
			return new MappingJackson2JsonView();
		}	
		
		// 세션에 로그인 정보 저장 처리
		super.saveFoUser(chkUser);
		// 로그인 이력 정보 저장 처리
		this.authService.createAuthHist(super.getRequest(), chkUser.getUserSeq(), chkUser.getUserId(), Constants.authType.LOGIN, Constants.Y);
		
		// 아이디 저장 처리
		if(StringUtils.equals(saveIdYn, Constants.Y)) {
			super.saveCookie(response, Constants.USER_ID_COOKIE_KEY, user.getUserId(), 60 * 60 * 24 * 365);
		} else {
			super.removeCookie(response, Constants.USER_ID_COOKIE_KEY);
		}
		
		// 리다이렉트 경로 검증 처리
		if(StringUtils.startsWith(redirectUrl, "http") && !StringUtils.startsWith(redirectUrl, "http://" + Constants.SERVER_NAME)){
			redirectUrl = "/";
		}
		model.addAttribute("redirectUrl", redirectUrl);
		
		// 정상처리 표기
		super.addResultToModel(model, new Result());
		
		return new MappingJackson2JsonView();
	}
	
	/**
	 * 로그아웃 처리 확인
	 * @param model
	 * @return
	 */
	@RequestMapping("/auth/logout")
	public View logout(ModelMap model) {
		// 현재 사용자 정보 획득
		User currUser = super.getFoUser();
		if(currUser == null) { throw new BadRequestException(); }
		
		// 로그 기록
		this.authService.createAuthHist(super.getRequest(), currUser.getUserSeq(), currUser.getUserId(), Constants.authType.LOGOUT, Constants.Y);
		
		// 세션에서 사용자 정보 삭제
		super.clearFoUser();
		
		// 정상처리 표기
		super.addResultToModel(model, new Result());
		
		return new MappingJackson2JsonView();
	}
	
	/**
	 * 로그인 정보 확인
	 * @param model
	 * @return
	 */
	@RequestMapping("/auth/loginInfo")
	public ModelAndView loginInfo(ModelMap model) {
		return new ModelAndView("auth/loginInfo");
	}
}
