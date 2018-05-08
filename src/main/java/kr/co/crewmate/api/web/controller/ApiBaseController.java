package kr.co.crewmate.api.web.controller;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.model.User;
import kr.co.crewmate.core.web.controller.BaseController;

/**
 * API 공통 기능을 통합한 컨트롤러
 * @author 정슬기
 * Date : 2018.04.04
 * 
 */
public class ApiBaseController extends BaseController {
	
	/**
	 * 현재 사용자 정보를 세션에 저장함.
	 * @param user
	 */
	public void saveFoUser(User user) {
		super.saveSession(Constants.USER_AUTH_SESSION_KEY, user);
	}
	/**
	 * 현재 사용자 정보를 반환함.
	 * @return
	 */
	public User getFoUser() {
		return (User) super.getSession(Constants.USER_AUTH_SESSION_KEY);
	}
	/**
	 * 현재 사용자 초기화 처리
	 */
	public void clearFoUser() {
		super.removeSession(Constants.USER_AUTH_SESSION_KEY);
	}
	

	/**
	 * 현재 사용자 고유번호를 리턴함.
	 * @return
	 */
	public int getUserSeq() {
		User u = this.getFoUser();
		return u == null ? 0 : u.getUserSeq();
	}
	

}
