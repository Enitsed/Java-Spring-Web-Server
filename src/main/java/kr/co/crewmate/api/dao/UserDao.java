package kr.co.crewmate.api.dao;

import org.springframework.stereotype.Repository;

import kr.co.crewmate.api.model.User;
import kr.co.crewmate.core.dao.BaseDao;

/**
 * 
 * @author 정슬기
 * date 2018.04.02
 * 데이터베이스 접근을 위한 객체 생성
 */
@Repository
public class UserDao extends BaseDao {
	
	/**
	 * 회원 등록 처리
	 * @param p
	 * @return
	 */
	public int insertUser(User p){
		return super.insert("User.insertUser", p);
	}
	
	/**
	 * 회원 조회 처리
	 * @param p
	 * @return
	 */
	public User selectUser(User p) {
		return (User) super.selectOne("User.selectUser", p);
	}

}
