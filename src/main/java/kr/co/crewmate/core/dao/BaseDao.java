package kr.co.crewmate.core.dao;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.crewmate.core.model.BaseModel;
import kr.co.crewmate.core.model.ListResult;
/**
 * 
 * @author 정슬기
 * 작성일 2018.03.28
 * 기능 : 매퍼 쿼리문 실행
 *
 */
@Repository
public class BaseDao {
	@Autowired
	@Resource(name = "sqlSessionTemplate")
	protected SqlSessionTemplate sqlSession;

	protected Object selectOne(String statement, Object parameter) {
		return this.sqlSession.selectOne(statement, parameter);
	}

	protected Object selectOne(String statement) {
		return this.sqlSession.selectOne(statement);
	}

	protected <T> List<T> selectList(String statement, Object parameter) {
		return this.sqlSession.selectList(statement, parameter);
	}

	protected <T> List<T> selectList(String statement) {
		return this.sqlSession.selectList(statement);
	}

	protected <T> ListResult<T> selectPageList(String statement, Object parameter) {
		List<T> list = this.sqlSession.selectList(statement, parameter);

		// 오라클용 카운트 정보 처리 - 리스트 데이터에 totCnt값이 존재하는 경우에는 바로 획득함.
		int totalCount = 0;
		if (list != null && list.size() != 0) {
			T t = list.get(0);
			try {
				Method m = t.getClass().getMethod("getTotCnt", new Class[] {});
				totalCount = Integer.parseInt(String.valueOf(m.invoke(t, new Object[] {})));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 위에서 카운트 정보를 획드하지 못한 경우의 처리
		if (totalCount == -1 && list != null && list.size() != 0) {
			totalCount = this.sqlSession.selectOne(statement + "Count", parameter);
		}

		ListResult<T> result = new ListResult<T>(list, totalCount, (BaseModel) parameter);
		return result;
	}

	protected int update(String statement) {
		return this.sqlSession.update(statement);
	}

	protected int update(String statement, Object parameter) {
		return this.sqlSession.update(statement, parameter);
	}

	protected int insert(String statement, Object parameter) {
		return this.sqlSession.insert(statement, parameter);
	}

	protected int delete(String statement) {
		return this.sqlSession.delete(statement);
	}

	protected int delete(String statement, Object parameter) {
		return this.sqlSession.delete(statement, parameter);
	}
}
