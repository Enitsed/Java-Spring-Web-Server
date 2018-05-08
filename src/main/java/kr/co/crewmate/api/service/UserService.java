package kr.co.crewmate.api.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.dao.UserDao;
import kr.co.crewmate.api.model.User;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.service.BaseService;
import kr.co.crewmate.core.util.HashUtil;
import kr.co.crewmate.core.util.StringUtil;
/**
 * 
 * @author 정슬기
 * date : 2018.04.02
 * 회원가입 서비스
 */
@Service
public class UserService extends BaseService{

	@Autowired
	private UserDao userDao;
	
	/**
	 * 회원 신규 생성 처리
	 * @param user
	 * @return
	 */
	public Result createUser(User user) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(user.getUserId(), user.getUserPw(), user.getName());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }
		
		// 아이디 중복 체크
		User u = this.getUser(user.getUserId());
		if(u != null) { return new Result(300, "user id duplicate : " + user.getUserId()); }
		
		// 암호화 필요 항목들 암호화 처리 : 비밀번호, 메일주소
		user.setUserPw(HashUtil.sha256(user.getUserPw(), user.getUserPw()));
		
		if(StringUtils.isNotEmpty(user.getEmail())) {
			user.setEmail(HashUtil.desEncrypt(user.getUserId(), user.getEmail(), HashUtil.KEY_SPEC_TripleDES));
		}
		
		// 사용자 등록 처리
		if(this.userDao.insertUser(user) != 1) {
			throw new BadRequestException();
		}
		
		return new Result();
	}
	
	
	/**
	 * 사용자 조회 처리
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public User getUser(String userId) throws Exception {
		User p = new User();
		p.setUserId(userId);
		User user = this.userDao.selectUser(p);
		
		// 사용자 정보가 존재하면 메일주소는 다시 복호화 한다.
		if(user != null) {
			if(StringUtils.isNotEmpty(user.getEmail())) {
				user.setEmail(HashUtil.desDecrypt(user.getUserId(), user.getEmail(), HashUtil.KEY_SPEC_TripleDES));
			}
		}
		
		return user;
	}
}
