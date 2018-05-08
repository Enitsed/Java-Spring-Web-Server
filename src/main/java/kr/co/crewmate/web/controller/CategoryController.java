package kr.co.crewmate.web.controller;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.model.Category;
import kr.co.crewmate.api.model.Item;
import kr.co.crewmate.api.model.User;
import kr.co.crewmate.api.service.CategoryService;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.ListResult;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;
import kr.co.crewmate.web.annotation.UserAuthCheck;
/**
 * 카테고리 컨트롤러
 * @author 정슬기
 *
 */
@Controller
public class CategoryController extends ApiBaseController {
	private static Logger logger = LoggerFactory.getLogger(CategoryController.class);
	@Autowired
	private CategoryService categoryService;

	
	/**
	 * 카테고리 등록/수정 인터페이스
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/category/ctgForm")
	public ModelAndView ctgForm(ModelMap model, Category ctg){
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(ctg.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }
		
		// 수정모드인 경우의 기초 데이터 처리
		Category data = new Category();
		if(StringUtils.equals(mode, "update")) {
			data = this.categoryService.getCtgReal(ctg.getCtgSeq(), null, null);
			if(data == null) { throw new BadRequestException(); }
			if(!StringUtils.equals(data.getUseYn(), Constants.Y)) { throw new BadRequestException(); }
		}
		
		// 데이터 셋팅
		model.addAttribute("data", data);
		
		return new ModelAndView("ctg/ctgForm");
	}


	/**
	 * 카테고리 저장/수정 처리
	 * @param model
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/category/ctgSave")
	public View ctgSave(ModelMap model, Category ctg){
		// 필수값 검증
		int idx = StringUtil.isEmpty(ctg.getCtgNm(), ctg.getDispYn());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }		
		
		idx = NumberUtil.isZero(ctg.getDispNo());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }		


		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(ctg.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }
		
		
		// 사용자 정보 획득
		User currUser = super.getFoUser();
		
		// mode에 맞게 등록 수정 처리
		Result result = null;
		if(StringUtils.equals(mode, "create")) {
			result = this.categoryService.createCtg(ctg.getCtgSeqParent(), ctg.getCtgNm(), ctg.getDispNo(), ctg.getDispYn(), currUser.getUserSeq());
		}else if(StringUtils.equals(mode, "update")) {
			result = this.categoryService.updateCtg(currUser.getUserSeq(), ctg.getCtgNm(), ctg.getDispYn(), ctg.getCtgSeq(), ctg.getDispNo());
		}
		
		
		// 결과 처리
		super.addResultToModel(model, result);
		
		
		return new MappingJackson2JsonView();
	}

	
	/**
	 * 카테고리 삭제 처리
	 * @param model
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/category/ctgRemove")
	public View ctgRemove(ModelMap model, Category ctg){
		// 필수값 검증
		int idx = NumberUtil.isZero(ctg.getCtgSeq());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }		

		// 사용자 정보 획득
		User currUser = super.getFoUser();

		// 삭제 처리
		super.addResultToModel(model, this.categoryService.removeCtg(currUser.getUserSeq(), ctg.getCtgSeq()));

		return new MappingJackson2JsonView();
	}	
	
	
	/**
	 * 카테고리 목록 처리
	 * @param model
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/category/ctgList")	
	public ModelAndView ctgList(ModelMap model) {
		return new ModelAndView("ctg/ctgList");
	}

	
	
	/**
	 * 카테고리 목록 데이터 처리
	 * 	- 이기능은 BO기반의 기능으로 DISP_YN항목을 고정시키지 않고 있음
	 * 	- FO에서는 사용하면 안되는 기능임.
	 * @param model
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/category/ctgListData")	
	public View ctgListData(ModelMap model, Category ctg) {
		// 기본값 설정
		int ctgSeqParent = ctg.getCtgSeqParent() == 0 ? Constants.ctgRootSeq.DISP : ctg.getCtgSeqParent();
		
		// 데이터 조회
		model.addAttribute("list", this.categoryService.getSubCtgListReal(ctgSeqParent, ctg.getDispYn()));
		
		// 정상처리
		super.addResultToModel(model, new Result());
		
		return new MappingJackson2JsonView();
	}




	/**
	 * 프론트 상품 카테고리 처리 
	 * @param model
	 * @param ctg
	 * @return
	 */
	@RequestMapping("/category/category")
	public ModelAndView category(ModelMap model, Category ctg, Item item) {
		// 필수값 체크
		int idx = NumberUtil.isZeroMinus(item.getPage());
		if(idx != -1) { throw new BadRequestException("invalid request : " + idx); }
		
		// 정렬기준 기본값 처리
		String order = StringUtils.defaultIfEmpty(item.getOrder(), "regDtDesc");
		
		// 카테고리 유효성 체크
		Category currCtg = this.categoryService.getCtg(ctg.getCtgSeq());
		if(currCtg == null) { throw new BadRequestException(); }
		
		// 카테고리상품 조회
		ListResult<Item> result = this.categoryService.getCtgItemList(ctg.getCtgSeq(), item.getPage(), item.getListSize(), order);
		model.addAttribute("result", result);
		
		
		model.addAttribute("order", order);
		model.addAttribute("currCtg", currCtg);
		
		return new ModelAndView("ctg/category");
	}


	/**
	 * 전시카테고리 목록 정보를 반환함.
	 * 	- 프론트용 서비스임.
	 * 	- 메모리에서 획득한 데이터를 기반으로 구성함. 
	 * @param model
	 * @param ctg
	 * @return
	 */
	@RequestMapping("/category/ctgListForDisp")	
	public View ctgListForDisp(ModelMap model, Category ctg) {
		// 기본값 설정
		int ctgSeqParent = ctg.getCtgSeqParent() == 0 ? Constants.ctgRootSeq.DISP : ctg.getCtgSeqParent();
		
		// 요청받은 ctgSeq검증
		Category ctgParent = this.categoryService.getCtg(ctgSeqParent);
		if(ctgParent == null) { throw new BadRequestException(); }
		if(!StringUtils.equals(ctgParent.getCtgType(), Constants.ctgType.DISP)) { throw new BadRequestException(); }
		
		// 데이터 구성
		model.addAttribute("list", ctgParent.getSubCtgList());
		
		// 정상처리
		super.addResultToModel(model, new Result());
		
		return new MappingJackson2JsonView();
	}


	
	
}
