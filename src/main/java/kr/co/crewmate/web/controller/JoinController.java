package kr.co.crewmate.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import kr.co.crewmate.api.model.User;
import kr.co.crewmate.api.service.UserService;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.util.StringUtil;

/**
 * @author 정슬기
 * Date : 2018.04.02
 * 회원가입을 위한 컨트롤러
 *
 */
@Controller
public class JoinController extends ApiBaseController {
	@Autowired
	private UserService userService;

	/**
	 * 회원가입폼 처리
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping("/join/userJoinForm")
	public ModelAndView userJoinForm() {
		return new ModelAndView("join/userJoinForm");
	}
	
	/**
	 * 회원가입 처리
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping("/join/userJoin")
	public View userJoin(ModelMap model, User user) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(user.getUserId(), user.getUserPw(), user.getName());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }
		
		// 회원등록 처리
		Result result = this.userService.createUser(user);
		super.addResultToModel(model, result);
		
		return new MappingJackson2JsonView();
	}
	
	/**
	 * 아이디 체크 처리
	 * @param model
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/join/userIdCheck")
	public View userIdCheck(ModelMap model, User user) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(user.getUserId());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }
		
		// 회원조회 처리. 회원정보가 없어야 사용가능한 아이디임.
		User u = this.userService.getUser(user.getUserId());
		model.addAttribute("useYn", u == null ? "Y" : "N");
		
		// 정상처리 표기
		super.addResultToModel(model, new Result());
		
		return new MappingJackson2JsonView();
	}	
	
}
