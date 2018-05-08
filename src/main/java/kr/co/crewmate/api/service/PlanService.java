package kr.co.crewmate.api.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.model.Category;
import kr.co.crewmate.api.model.CtgTmpl;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.ListResult;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.service.BaseService;
import kr.co.crewmate.core.util.DateUtil;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 기획전 관련 서비스
 * @author 정슬기
 *
 */
@Service
public class PlanService extends BaseService {
	
	private final static Logger log = LoggerFactory.getLogger(PlanService.class);
	
	@Autowired
	private CategoryService ctgService;
	
	@Autowired
	private DispService dispService;
	
	
	
	/**
	 * 기획전 생성 처리
	 * @param userSeq
	 * @param ctg
	 * @param ctgTmpl
	 * @return
	 */
	public Result createPlan(int userSeq, Category ctg, List<CtgTmpl> ctgTmplList) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(ctg.getCtgNm(), ctg.getDispYn(), ctg.getDispStDt(), ctg.getDispEndDt());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(userSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		
		idx = DateUtil.isDateFormat(null, ctg.getDispStDt(), ctg.getDispEndDt());
		if(idx != -1) { return new Result(400, "invalid arguments : " + idx); }
		

		// 마스터 정보 등록 처리 - 전시순서는 어떻게 처리할까? -> 그냥 1으로 통일하자.
		ctg.setCtgSeqParent(Constants.ctgRootSeq.PLAN);		// 상위카테고리를 기획매장으로 고정 처리
		ctg.setDispNo(1);
		Result result = this.ctgService.createCtg(userSeq, ctg);
		if(result.getResultCode() != 1) { return result; }
		
		// 생성된 카테고리정보 획득 - 기획매장임.
		Category plan = (Category) result.getInfo("ctg");
		
		
		// 매장 템플릿 정보 연결 처리
		Result r = this.dispService.createCtgTmpl(plan.getCtgSeq(), ctgTmplList);
		if(r.getResultCode() != 1) { throw new BadRequestException(); }
		

		return new Result();
	}
	
	
	/**
	 * 기획전 수정 처리
	 * @param userSeq
	 * @param ctg
	 * @param ctgTmplList
	 * @return
	 */
	public Result updatePlan(int userSeq, Category ctg, List<CtgTmpl> ctgTmplList) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(ctg.getCtgNm(), ctg.getDispYn(), ctg.getDispStDt(), ctg.getDispEndDt());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(userSeq, ctg.getCtgSeq());
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		
		idx = DateUtil.isDateFormat(null, ctg.getDispStDt(), ctg.getDispEndDt());
		if(idx != -1) { return new Result(400, "invalid arguments : " + idx); }
		

		// 마스터 정보 수정처리
		ctg.setCtgSeqParent(Constants.ctgRootSeq.PLAN);		// 상위카테고리를 기획매장으로 고정 처리
		ctg.setDispNo(1);
		Result result = this.ctgService.updateCtg(userSeq, ctg);
		if(result.getResultCode() != 1) { return result; }
		
		// 매장 템플릿 정보 연결 처리
		Result r = this.dispService.createCtgTmpl(ctg.getCtgSeq(), ctgTmplList);
		if(r.getResultCode() != 1) { throw new BadRequestException(); }
		

		return new Result();
	}	
	
	/**
	 * 기획전 삭제 처리
	 * @param userSeq
	 * @param ctgSeq
	 * @return
	 */
	public Result removePlan(int userSeq, int ctgSeq) {
		// 필수값 검증
		int idx = NumberUtil.isZero(userSeq, ctgSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		

		// 마스더 정보 등록 처리
		return this.ctgService.removeCtg(userSeq, ctgSeq);
	}
	
	
	/**
	 * 기획전 목록 처리
	 * @param page
	 * @param listSize
	 * @param pageSize
	 * @return
	 */
	public ListResult<Category> getPlanList(Category ctg){
		// 필수값 검증
		int idx = NumberUtil.isZeroMinus(ctg.getPage(), ctg.getListSize(), ctg.getPageSize());
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		Category param = new Category();
		param.setCtgSeqParent(Constants.ctgRootSeq.PLAN);
		param.setCtgType(Constants.ctgType.PLAN);
		param.setPage(ctg.getPage());
		param.setListSize(ctg.getListSize());
		param.setPageSize(ctg.getPageSize());
		param.setKeyField(ctg.getKeyField());
		param.setKeyWord(ctg.getKeyWord());
		param.setDispDtChkYn(ctg.getDispDtChkYn());
		
		return this.ctgService.getCtgListPage(param);
	}
	
	
	/**
	 * 기획전 정보 반환
	 * @param ctgSeq
	 * @param dispYn
	 * @return
	 */
	public Category getPlan(int ctgSeq, String dispYn) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(dispYn);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 200); }
		
		idx = NumberUtil.isZeroMinus(ctgSeq);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 마스터 정보 획득
		Category ctg = this.ctgService.getCtgReal(ctgSeq, dispYn, Constants.Y);
		
		if(ctg != null) {
			// 획득한 정보가 기획전 정보인지를 검증함.
			if(!StringUtils.equals(ctg.getCtgType(), Constants.ctgType.PLAN)) {
				throw new BadRequestException();
			}
			
			// 기획전의 적용된 템플릿 정보 획득
			ctg.setTmplList(this.dispService.getCtgTmplList(ctgSeq));
		}
		
		
		return ctg;
	}
	


}