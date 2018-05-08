package kr.co.crewmate.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.dao.TestDao;
import kr.co.crewmate.api.model.Test;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.service.BaseService;

@Service
public class TestService extends BaseService {
	@Autowired
	private TestDao testDao;

	/**
	 * @author 정슬기
	 * 작성일 2018.03.29
	 * @return Test 객체 값 리스트(값이 없을 경우도 있음)
	 */
	public List<Test> getTestList() {
		List<Test> resultList = this.testDao.selectTestList();
		return resultList;
	}
	
	/**
	 * @author 정슬기
	 * 작성일 2018.03.28
	 * @param int 테스트 값 인덱스
	 * @return Test 객체
	 */
	public Test getTest(int index) {
		Test result = this.testDao.selectTest(index);
		return result;
	}
	
	/**
	 * @author 정슬기
	 * 작성일 2018.03.28
	 * @param Test 객체
	 * @return void
	 * 테스트 객체 추가
	 */
	public void createTest(String title, String contents) {
		// 제목 또는 내용이 null 값일 때 롤백 실행
		if(title == null || contents == null){ throw new BadRequestException(); }
		
		int result = this.testDao.insertTest(title, contents);

		// DB에 추가된 튜플이 한개가 아닐 경우에 롤백
		if(result != 1) { throw new BadRequestException(); }
	}
	
	/**
	 * @author 정슬기
	 * 작성일 2018.03.28
	 * @param Test 객체
	 * @return void
	 * 테스트 객체 수정
	 */
	public void updateTest(int index, String title, String contents) {
		// 변경할 인덱스의 번호가 0보다 작거나(인덱스가 없거나) 제목 또는 내용이 null 값일 때 롤백 실행
		if(index < 0 || title == null || contents == null){ throw new BadRequestException(); }
		
		int result = this.testDao.updateTest(index, title, contents);
		
		// DB에 수정된 튜플이 한개가 아닐 경우에 롤백
		if(result != 1) { throw new BadRequestException(); }
	}

}
