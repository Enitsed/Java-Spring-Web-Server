package kr.co.crewmate.web.controller;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.model.Category;
import kr.co.crewmate.api.model.Tmpl;
import kr.co.crewmate.api.service.DispService;
import kr.co.crewmate.api.service.PlanService;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.web.annotation.UserAuthCheck;

/**
 * 기획전 등록/ 수정 인터페이스
 * @author 정슬기
 *
 */
@Controller
public class PlanController extends ApiBaseController {
	private final static Logger log = LoggerFactory.getLogger(PlanController.class);

	@Autowired
	private PlanService planService;

	@Autowired
	private DispService dispService;
	
	
	/**
	 * 기획전 등록/수정 인터페이스
	 * @param model
	 * @param tmpl
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/plan/planForm")
	public ModelAndView planForm(ModelMap model, Category ctg){
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(ctg.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }
		
		// 수정모드인 경우의 기초 데이터 처리
		Category data = new Category();
		if(StringUtils.equals(mode, "update")) {
			data = this.planService.getPlan(ctg.getCtgSeq(), null);
			if(data == null) { throw new BadRequestException(); }
		}

		// 데이터 셋팅
		model.addAttribute("data", data);
		
		
		// 템플릿 정보 구성
		Tmpl p = new Tmpl();
		p.setTmplTgt(Constants.tmplTgtType.PC);
		this.dispService.getTmplList(p);

		
		
		return new ModelAndView("disp/planForm");
	}
}