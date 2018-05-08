package kr.co.crewmate.api.service;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.dao.DispDao;
import kr.co.crewmate.api.model.CtgTmpl;
import kr.co.crewmate.api.model.DispArea;
import kr.co.crewmate.api.model.DispAreaDtl;
import kr.co.crewmate.api.model.Tmpl;
import kr.co.crewmate.api.model.TmplDispArea;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.ListResult;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.service.BaseService;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 전시 관련 서비스
 * @author 정슬기
 *
 */
@Service
public class DispService extends BaseService {
	
	private final static Logger log = LoggerFactory.getLogger(DispService.class);
	
	@Autowired
	private DispDao dispDao;
	

	/**
	 * 전시 영역 생성 처리
	 * @param userSeq
	 * @param dispArea
	 * @param dispAreaDtlList
	 * @return
	 */
	public Result createDispArea(int userSeq, DispArea dispArea, List<DispAreaDtl> dispAreaDtlList) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(dispArea.getDispCd(), dispArea.getDispAreaNm(), dispArea.getDispType(), dispArea.getDtlUseYn());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(dispArea.getDispCnt(), userSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }

		
		// 마스터 정보 등록 처리 - dispCd의 중복 체크 로직 점검 필요함.
		dispArea.setUseYn(Constants.Y);
		dispArea.setRegSeq(userSeq);
		dispArea.setModSeq(userSeq);
		
		try {
			if(this.dispDao.insertDispArea(dispArea) != 1) { throw new BadRequestException(); }
		}catch(DuplicateKeyException e) {
			throw new BadRequestException("Duplicate Exception", 9100);
		}
		
		// 상세 정보 등록 처리 - 상세정보 사용인 경우
		if(StringUtils.equals(dispArea.getDtlUseYn(), Constants.Y)) {
			int useYCnt = 0;
			
			for(DispAreaDtl d : dispAreaDtlList) {
				if(StringUtils.equals(d.getUseYn(), Constants.Y)) {
					// 필수값 검증
					idx = StringUtil.isEmpty(d.getDispAreaDtlNm());
					if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx); }
					
					// 기본값 설정 및 데이터 처리 - 전시순서는 그냥 0도 허용하자.
					d.setDispCd(dispArea.getDispCd());
					if(this.dispDao.insertDispAreaDtl(d) != 1) { throw new BadRequestException(); }
					
					useYCnt++;
				}
			}
			
			// 실제 등록된 개수 정보 검증 - 2개 이상이어야 함.
			if(useYCnt < 2) { throw new BadRequestException("dtl info size low!"); }
		}
		
		// 상세 정보 등록 처리 - 상세정보 미사용인 경우 기본값 1개 등록 처리
		if(StringUtils.equals(dispArea.getDtlUseYn(), Constants.N)) {
			DispAreaDtl d = new DispAreaDtl();
			d.setDispNo(1);
			d.setDispAreaDtlNm("기본영역");
			d.setUseYn(Constants.Y);
			d.setDispCd(dispArea.getDispCd());
			if(this.dispDao.insertDispAreaDtl(d) != 1) { throw new BadRequestException(); }
		}
		
		return new Result();
	}
	
	
	/**
	 * 전시영역 수정처리
	 * @param userSeq
	 * @param dispArea
	 * @param dispAreaDtlList
	 * @return
	 */
	public Result updateDispArea(int userSeq, DispArea dispArea, List<DispAreaDtl> dispAreaDtlList) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(dispArea.getDispCd(), dispArea.getDispAreaNm(), dispArea.getDtlUseYn());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(dispArea.getDispCnt(), userSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }

		
		// 마스터 정보 수정 처리
		dispArea.setModSeq(userSeq);
		dispArea.setUseYn(Constants.Y);
		if(this.dispDao.updateDispArea(dispArea) != 1) { throw new BadRequestException(); }
		
		// 상세 정보 등록 처리 - 상세정보를 사용하는 경우
		if(StringUtils.equals(dispArea.getDtlUseYn(), Constants.Y)) {
			int useYCnt = 0;
			
			for(DispAreaDtl d : dispAreaDtlList) {
				// 필수값 검증
				idx = StringUtil.isEmpty(d.getDispAreaDtlNm());
				if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx); }
				
				// 기본값 설정 및 데이터 처리 - 전시순서는 그냥 0도 허용하자.
				d.setDispCd(dispArea.getDispCd());
				
				// 고유번호가 존재하지 않으면 신규 등록항목이고, 존재하면 기존 데이터를 수정한 경우임. - 실제 데이터가 수정되었는지까지는 판단하지 않음.
				if(d.getDispAreaDtlSeq() == 0 && StringUtils.equals(d.getUseYn(), Constants.Y)) {
					if(this.dispDao.insertDispAreaDtl(d) != 1) { throw new BadRequestException(); }
					useYCnt++;
				}else {
					// 사용여부가 Y이면 수정처리, N이면 삭제 처리
					if(StringUtils.equals(d.getUseYn(), Constants.Y)) {
						if(this.dispDao.updateDispAreaDtl(d) != 1) { throw new BadRequestException(); }
						useYCnt++;
					}else {
						d.setUseYn(Constants.N);
						if(this.dispDao.updateDispAreaDtlUseYn(d) != 1) { throw new BadRequestException(); }
					}
				}
			}
			
			// 실제 등록된 개수 정보 검증 - 2개 이상이어야 함.
			if(useYCnt < 2) { throw new BadRequestException("dtl info size low!"); }			
		}

		// 상세 정보 등록 처리 - 상세정보를 사용하지 않는 경우 경우 - 첫번째만 사용여부를 Y로 유지하고 나머지는 미사용으로 변경 처리
		if(StringUtils.equals(dispArea.getDtlUseYn(), Constants.N)) {
			DispAreaDtl p = new DispAreaDtl();
			p.setDispCd(dispArea.getDispCd());
			this.dispDao.updateDispAreaDtlUseYnFirst(p);
		}
		
		
		
		return new Result();
	}	

	
	/**
	 * 전시영역 삭제 처리
	 * @param userSeq
	 * @param dispCd
	 * @return
	 */
	public Result removeDispArea(int userSeq, String dispCd) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(dispCd);
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }
		
		idx = NumberUtil.isZero(userSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		
		
		// 마스더 정보 등록 처리
		DispArea p = new DispArea();
		p.setDispCd(dispCd);
		p.setUseYn(Constants.N);
		p.setModSeq(userSeq);
		if(this.dispDao.updateDispAreaUseYn(p) != 1) { throw new BadRequestException(); }
		
		// 정상 처리
		return new Result();
	}	
	
	
	


	/**
	 * 전시영역 상세 정보를 반환함.
	 * @param dispCd
	 * @return
	 */
	public DispArea getDispArea(String dispCd) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(dispCd);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 200); }
		
		// 데이터 조회
		DispArea param = new DispArea();
		param.setDispCd(dispCd);
		param.setUseYn(Constants.Y);
		DispArea dispArea = this.dispDao.selectDispArea(param);
		
		if(dispArea != null) {
			DispAreaDtl p = new DispAreaDtl();
			p.setDispCd(dispCd);
			p.setUseYn(Constants.Y);
			dispArea.setDispAreaDtlList(this.dispDao.selectDispAreaDtlList(p));
		}

		return dispArea;
	}
	
	
	/**
	 * 전시영역 목록 정보 반환
	 * @param dispArea
	 * @return
	 */
	public ListResult<DispArea> getDispAreaPageList(DispArea dispArea){
		// 필수값 검증
		int idx = NumberUtil.isZeroMinus(dispArea.getPage(), dispArea.getListSize(), dispArea.getPageSize());
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		DispArea param = new DispArea();
		param.setPage(dispArea.getPage());
		param.setListSize(dispArea.getListSize());
		param.setPageSize(dispArea.getPageSize());
		
		param.setUseYn(Constants.Y);
		param.setDispType(dispArea.getDispType());
		param.setKeyField(dispArea.getKeyField());
		param.setKeyWord(dispArea.getKeyWord());
		
		return this.dispDao.selectDispAreaPageList(param);
	}
	


	/**
	 * 전시영역 목록 정보 반환
	 * @param dispArea
	 * @return
	 */
	public List<DispArea> getDispAreaList(DispArea dispArea){
		// 데이터 조회
		DispArea param = new DispArea();
		param.setPage(1);
		param.setListSize(999999);
		
		param.setUseYn(Constants.Y);
		param.setDispType(dispArea.getDispType());
		param.setKeyField(dispArea.getKeyField());
		param.setKeyWord(dispArea.getKeyWord());
		
		return this.dispDao.selectDispAreaList(param);
	}
	
	
	



	/**
	 * 템플릿 생성 처리
	 * @param userSeq
	 * @param tmpl
	 * @param tmplDispAreaList
	 * @return
	 */
	public Result createTmpl(int userSeq, Tmpl tmpl, List<DispArea> tmplDispAreaList) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(tmpl.getTmplNm(), tmpl.getTmplTgt(), tmpl.getTmplPath());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(userSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }

		
		// 마스터 정보 등록 처리
		tmpl.setRegSeq(userSeq);
		tmpl.setModSeq(userSeq);
		if(this.dispDao.insertTmpl(tmpl) != 1) { throw new BadRequestException(); }
		

		// 전시영역 정보 등록 처리
		for(DispArea d : tmplDispAreaList) {
			// 필수값 검증
			idx = StringUtil.isEmpty(d.getDispCd());
			if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx); }
			
			// 기본값 설정 및 데이터 처리
			if(this.dispDao.insertTmplDispArea(new TmplDispArea(tmpl.getTmplSeq(), d.getDispCd())) != 1) { throw new BadRequestException(); }
		}
		
		return new Result();
	}


	/**
	 * 템플릿 삭제 처리
	 * @param userSeq
	 * @param tmpl
	 * @param tmplDispAreaList
	 * @return
	 */
	public Result updateTmpl(int userSeq, Tmpl tmpl, List<DispArea> tmplDispAreaList) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(tmpl.getTmplNm(), tmpl.getTmplPath());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(tmpl.getTmplSeq(), userSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }

		
		// 마스터 정보 수정 처리
		tmpl.setModSeq(userSeq);
		if(this.dispDao.updateTmpl(tmpl) != 1) { throw new BadRequestException(); }
		
		
		// 전시영역 정보 삭제 처리 - 삭제후 다시 등록 처리를 함.
		TmplDispArea p = new TmplDispArea();
		p.setTmplSeq(tmpl.getTmplSeq());
		this.dispDao.deleteTmplDispArea(p);
		

		// 전시영역 정보 등록 처리
		for(DispArea d : tmplDispAreaList) {
			// 필수값 검증
			idx = StringUtil.isEmpty(d.getDispCd());
			if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx); }
			
			// 기본값 설정 및 데이터 처리
			if(this.dispDao.insertTmplDispArea(new TmplDispArea(tmpl.getTmplSeq(), d.getDispCd())) != 1) { throw new BadRequestException(); }
		}
		
		return new Result();
	}



	/**
	 * 템플릿 목록 정보 반환 처리
	 * @param tmpl
	 * @return
	 */
	public ListResult<Tmpl> getTmplPageList(Tmpl tmpl){
		// 필수값 검증
		int idx = NumberUtil.isZeroMinus(tmpl.getPage(), tmpl.getListSize(), tmpl.getPageSize());
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		Tmpl param = new Tmpl();
		param.setPage(tmpl.getPage());
		param.setListSize(tmpl.getListSize());
		param.setPageSize(tmpl.getPageSize());
		
		param.setTmplTgt(tmpl.getTmplTgt());
		param.setKeyField(tmpl.getKeyField());
		param.setKeyWord(tmpl.getKeyWord());
		
		return this.dispDao.selectTmplPageList(param);
	}

	
	/**
	 * 템플릿 목록 정보 반환 처리
	 * @param tmpl
	 * @return
	 */
	public List<Tmpl> getTmplList(Tmpl tmpl){
		// 데이터 조회
		Tmpl param = new Tmpl();
		param.setPage(1);
		param.setListSize(999999);
		
		param.setTmplTgt(tmpl.getTmplTgt());
		param.setKeyField(tmpl.getKeyField());
		param.setKeyWord(tmpl.getKeyWord());
		
		return this.dispDao.selectTmplList(param);
	}	
	
	

	/**
	 * 템플릿 정보 반환 처리
	 * @param tmplSeq
	 * @return
	 */
	public Tmpl getTmpl(int tmplSeq) {
		// 필수값 검증
		int idx = NumberUtil.isZero(tmplSeq);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		Tmpl param = new Tmpl();
		param.setTmplSeq(tmplSeq);
		Tmpl tmpl = this.dispDao.selectTmpl(param);
		
		if(tmpl != null) {
			TmplDispArea p = new TmplDispArea();
			p.setTmplSeq(tmplSeq);
			tmpl.setDispAreaList(this.dispDao.selectTmplDispAreaList(p));
		}

		return tmpl;
	}

	/**
	 * 매장 템플릿 생성 처리
	 * 	- 매장템플릿을 모두 삭제하고 재등록 처리임.
	 * @param ctgTmplList
	 * @return
	 */
	public Result createCtgTmpl(int ctgSeq, List<CtgTmpl> ctgTmplList) {
		// 필수값 검증
		int idx = NumberUtil.isZero(ctgSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		
		// 기존 데이터 삭제 처리
		this.dispDao.deleteCtgTmpl(new CtgTmpl(ctgSeq));
		
		// 데이터 등록 처리
		if(ctgTmplList != null) {
			for(CtgTmpl d : ctgTmplList) {
				d.setCtgSeq(ctgSeq);
				if(this.dispDao.insertCtgTmpl(d) != 1) { throw new BadRequestException(); }
			}
		}
		
		
		return new Result();
	}

	
	/**
	 * 매장 템플릿 정보를 반환함.
	 * @param ctgSeq
	 * @return
	 */
	public List<Tmpl> getCtgTmplList(int ctgSeq){
		// 필수값 검증
		int idx = NumberUtil.isZero(ctgSeq);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx); }

		
		// 데이터 조회
		return this.dispDao.selectCtgTmplList(new CtgTmpl(ctgSeq));
	}
	
}