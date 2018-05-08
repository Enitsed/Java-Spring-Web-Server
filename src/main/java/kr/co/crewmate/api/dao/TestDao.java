package kr.co.crewmate.api.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.crewmate.api.model.Test;
import kr.co.crewmate.core.dao.BaseDao;

@Repository
public class TestDao extends BaseDao {

	/**
	 * @author 정슬기
	 * 작성일 2018.03.29
	 * @return Test 객체 리스트 반환
	 */
	public List<Test> selectTestList() {
		List<Test> listTest = new ArrayList<Test>();
		listTest = super.selectList("Test.selectTestList");
		
		return listTest;
	}
	
	/**
	 * @author 정슬기
	 * 작성일 2018.03.28
	 * @param int 테스트 값 인덱스
	 * @return Test 객체
	 */
	public Test selectTest(int index) {
		Test test = (Test) super.selectOne("Test.selectTest", index);
		
		return test;
	}
	
	/**
	 * @author 정슬기
	 * 작성일 2018.03.28
	 * @param Test 객체
	 * @return int (추가된 튜플 한개)
	 * 테스트 객체 추가
	 */
	public int insertTest(String title, String contents) {
		HashMap<String, Object> testMap = new HashMap<String, Object>();
		testMap.put("title", title);
		testMap.put("contents", contents);
		
		return super.insert("Test.insertTest", testMap);
	}
	
	/**
	 * @author 정슬기
	 * 작성일 2018.03.28
	 * @param testSeq, title, contents
	 * @return int (변경된 튜플의 개수)
	 * 테스트 객체 수정
	 */
	public int updateTest(int index, String title, String contents) {
		HashMap<String, Object> testMap = new HashMap<String, Object>();
		testMap.put("index", index);
		testMap.put("title", title);
		testMap.put("contents", contents);
		
		return super.update("Test.updateTest", testMap);
	}
	

}
