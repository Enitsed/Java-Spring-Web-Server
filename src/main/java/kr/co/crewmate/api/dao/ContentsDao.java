package kr.co.crewmate.api.dao;

import org.springframework.stereotype.Repository;

import kr.co.crewmate.api.model.Contents;
import kr.co.crewmate.core.dao.BaseDao;
import kr.co.crewmate.core.model.ListResult;

/**
 * 게시판 관련 데이터 접근을 위한 DAO
 * @author 정슬기
 *
 */
@Repository
public class ContentsDao extends BaseDao {

	/**
	 * 컨텐츠 등록 처리
	 * @param p
	 * @return
	 */
	public int insertContents(Contents p){
		return super.insert("Contents.insertContents", p);
	}

	/**
	 * 컨텐츠 조회 처리
	 * @param p
	 * @return
	 */
	public Contents selectContents(Contents p) {
		return (Contents) super.selectOne("Contents.selectContents", p);
	}
	
	/**
	 * 컨텐츠 목록 조회 처리
	 * @param p
	 * @return
	 */
	public ListResult<Contents> selectContentsList(Contents p){
		return super.selectPageList("Contents.selectContents", p);
	}

	/**
	 * 다음 컨텐츠 전시순서를 반환함.
	 * @param p
	 * @return
	 */
	public float selectContentsNextDispNo(Contents p) {
		return (float) super.selectOne("Contents.selectContentsNextDisp", p);
	}

	/**
	 * 컨텐츠 조회수 증가 처리
	 * @param contentsSeq
	 * @return
	 */
	public int updateContentsViewCnt(int contentsSeq) {
		return super.update("Contents.updateContentsViewCnt", contentsSeq);
	}

	/**
	 * 컨텐츠 사용여부 수정 처리
	 * @param contentsSeq
	 * @return
	 */
	public int updateContentsUseYn(int contentsSeq) {
		return super.update("Contents.updateContentsUseYn", contentsSeq);
	}	
	
	/**
	 * 컨텐츠 업데이트 처리
	 * @param contentsSeq
	 * @return
	 */
	public int updateContents(Contents p) {
		return super.update("Contents.updateContents", p);
	}

	/**
	 * 컨텐츠 삭제 가능여부를 반환함.
	 * @param p
	 * @return
	 */
	public String selectContentsRemoveYn(Contents p) {
		return (String) super.selectOne("Contents.selectContentsRemoveYn", p);
	}
	
}
