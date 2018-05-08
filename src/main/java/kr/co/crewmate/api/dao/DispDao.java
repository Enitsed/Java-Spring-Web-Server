package kr.co.crewmate.api.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.crewmate.api.model.CtgTmpl;
import kr.co.crewmate.api.model.DispArea;
import kr.co.crewmate.api.model.DispAreaDtl;
import kr.co.crewmate.api.model.Tmpl;
import kr.co.crewmate.api.model.TmplDispArea;
import kr.co.crewmate.core.dao.BaseDao;
import kr.co.crewmate.core.model.ListResult;


/**
 * 전시 관련 DAO 
 * @author 정슬기
 *
 */
@Repository
public class DispDao extends BaseDao {
	
	/**
	 * 전시 영역 정보 조회 처리
	 * @param p
	 * @return
	 */
	public DispArea selectDispArea(DispArea p) {
		return (DispArea) super.selectOne("Disp.selectDispArea", p);
	}
	
	
	/**
	 * 전시영역상세 목록 정보 조회 처리
	 * @param p
	 * @return
	 */
	public List<DispAreaDtl> selectDispAreaDtlList(DispAreaDtl p){
		return super.selectList("Disp.selectDispAreaDtl", p);
	}
	
	
	
	/**
	 * 전시영역 등록 처리
	 * @param p
	 * @return
	 */
	public int insertDispArea(DispArea p){
		return super.insert("Disp.insertDispArea", p);
	}
	
	/**
	 * 전시영역상세 등록 처리
	 * @param p
	 * @return
	 */
	public int insertDispAreaDtl(DispAreaDtl p){
		return super.insert("Disp.insertDispAreaDtl", p);
	}
	

	/**
	 * 전시영역 수정 처리
	 * @param p
	 * @return
	 */
	public int updateDispArea(DispArea p) {
		return super.update("Disp.updateDispArea", p);
	}

	/**
	 * 전시영역 사용여부 수정 처리
	 * @param p
	 * @return
	 */
	public int updateDispAreaUseYn(DispArea p) {
		return super.update("Disp.updateDispAreaUseYn", p);
	}
	
	/**
	 * 전시영역상세 수정 처리
	 * @param p
	 * @return
	 */
	public int updateDispAreaDtl(DispAreaDtl p) {
		return super.update("Disp.updateDispAreaDtl", p);
	}

	/**
	 * 전시영역상세 사용여부 수정 처리
	 * @param p
	 * @return
	 */
	public int updateDispAreaDtlUseYn(DispAreaDtl p) {
		return super.update("Disp.updateDispAreaDtlUseYn", p);
	}

	
	/**
	 * 전시영역상세 사용여부 수정 처리 - 첫번째만 남기로 나머지는 모두 미사용으로 변경 처리
	 * @param p
	 * @return
	 */
	public int updateDispAreaDtlUseYnFirst(DispAreaDtl p) {
		return super.update("Disp.updateDispAreaDtlUseYnFirst", p);
	}


	/**
	 * 전시영역 목록 조회 처리
	 * @param p
	 * @return
	 */
	public ListResult<DispArea> selectDispAreaPageList(DispArea p){
		return super.selectPageList("Disp.selectDispArea", p);
	}

	
	/**
	 * 전시영역 목록 조회 처리
	 * @param p
	 * @return
	 */
	public List<DispArea> selectDispAreaList(DispArea p){
		return super.selectList("Disp.selectDispArea", p);
	}	


	/**
	 * 템플릿 등록 처리
	 * @param p
	 * @return
	 */
	public int insertTmpl(Tmpl p){
		return super.insert("Disp.insertTmpl", p);
	}


	/**
	 * 템플릿 전시영역 등록 처리
	 * @param p
	 * @return
	 */
	public int insertTmplDispArea(TmplDispArea p){
		return super.insert("Disp.insertTmplDispArea", p);
	}


	/**
	 * 템플릿 수정처리
	 * @param p
	 * @return
	 */
	public int updateTmpl(Tmpl p) {
		return super.update("Disp.updateTmpl", p);
	}


	/**
	 * 템플릿 전시영역 삭제 처리
	 * @param p
	 * @return
	 */
	public int deleteTmplDispArea(TmplDispArea p) {
		return super.delete("Disp.deleteTmplDispArea", p);
	}



	/**
	 * 템플릿 목록 조회 처리
	 * @param p
	 * @return
	 */
	public ListResult<Tmpl> selectTmplPageList(Tmpl p){
		return super.selectPageList("Disp.selectTmpl", p);
	}


	/**
	 * 템플릿 목록 조회 처리
	 * @param p
	 * @return
	 */
	public List<Tmpl> selectTmplList(Tmpl p){
		return super.selectList("Disp.selectTmpl", p);
	}	
	
	
	/**
	 * 템플릿 조회 처리
	 * @param p
	 * @return
	 */
	public Tmpl selectTmpl(Tmpl p){
		return (Tmpl) super.selectOne("Disp.selectTmpl", p);
	}
	
	
	/**
	 * 템플릿 전시영역정보 조회 처리
	 * @param p
	 * @return
	 */
	public List<DispArea> selectTmplDispAreaList(TmplDispArea p){
		return super.selectList("Disp.selectTmplDispArea", p);
	}


	/**
	 * 매장 템플릿 정보 등록 처리
	 * @param p
	 * @return
	 */
	public int insertCtgTmpl(CtgTmpl p) {
		return super.insert("Disp.insertCtgTmpl", p);
	}


	/**
	 * 매장 템플릿 정보 삭제 처리
	 * @param p
	 * @return
	 */
	public int deleteCtgTmpl(CtgTmpl p) {
		return super.delete("Disp.deleteCtgTmpl", p);
	}

	
	/**
	 * 매장 템플릿 정보를 반환함.
	 * @param p
	 * @return
	 */
	public List<Tmpl> selectCtgTmplList(CtgTmpl p){
		return super.selectList("Disp.selectCtgTmpl", p);
	}

}