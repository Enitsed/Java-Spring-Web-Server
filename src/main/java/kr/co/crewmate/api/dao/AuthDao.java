package kr.co.crewmate.api.dao;

import org.springframework.stereotype.Repository;

import kr.co.crewmate.api.model.AuthHist;
import kr.co.crewmate.api.model.User;
import kr.co.crewmate.core.dao.BaseDao;
/**
 * 
 * @author 정슬기
 *	Date : 2018.04.02
 *	기능 : 로그인 인증을 위한 DAO
 *
 */
@Repository
public class AuthDao extends BaseDao{
	
	/**
	 * 인증로그 등록 처리
	 * @param p
	 * @return
	 */
	public int insertAuthHist(AuthHist p){
		return super.insert("Auth.insertAuthHist", p);
	}

}
