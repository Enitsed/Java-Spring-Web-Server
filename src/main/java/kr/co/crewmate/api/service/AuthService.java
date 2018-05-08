package kr.co.crewmate.api.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.dao.AuthDao;
import kr.co.crewmate.api.model.AuthHist;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.service.BaseService;
import kr.co.crewmate.core.util.HttpUtil;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 유저 인증 및 로그인 기록 관련 서비스
 * @author 정슬기
 * date: 2018.04.02
 */
@Service
public class AuthService extends BaseService {
	@Autowired
	private AuthDao authDao;

	/**
	 * 사용자 인증 이력 등록 처리
	 * @param request : HttpServletRequest
	 * @param authHist : 인증 이력
	 * @return
	 */
	public Result createAuthHist(HttpServletRequest request, Integer userSeq, String userId, String authType, String successYn) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(userId, authType, successYn);
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		// 데이터 구성 및 클라이언트 정보 셋팅
		AuthHist authHist = new AuthHist();
		authHist.setUserSeq(userSeq);
		authHist.setUserId(userId);
		authHist.setAuthType(authType);
		authHist.setSuccessYn(successYn);
		authHist.setIp(HttpUtil.getRemoteAddr(request));
		authHist.setUserAgent(HttpUtil.getUserAgent(request));
		
		// 데이터 등록 처리
		if(this.authDao.insertAuthHist(authHist) != 1) {
			throw new BadRequestException();
		}
		
		return new Result();
	}
	
}
