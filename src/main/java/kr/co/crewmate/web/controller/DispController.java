package kr.co.crewmate.web.controller;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.crewmate.api.model.DispArea;
import kr.co.crewmate.api.model.DispAreaDtl;
import kr.co.crewmate.api.model.Tmpl;
import kr.co.crewmate.api.service.DispService;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;
import kr.co.crewmate.web.annotation.UserAuthCheck;

/**
 * 전시영역 컨트롤러
 * @author 정슬기
 *
 */
@Controller
public class DispController extends ApiBaseController {
	private final static Logger log = LoggerFactory.getLogger(DispController.class);

	@Autowired
	private DispService dispService;

	
	/**
	 * 전시영역 목록 처리
	 * @param model
	 * @param dispArea
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/disp/dispAreaList")
	public ModelAndView dispAreaList(ModelMap model, DispArea dispArea){
		model.addAttribute("result", this.dispService.getDispAreaPageList(dispArea));
		return new ModelAndView("disp/dispAreaList");
	}	
	
	

	/**
	 * 전시영역 삭제 처리
	 * @param model
	 * @param dispArea
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/disp/dispAreaRemove")
	public View dispAreaRemove(ModelMap model, DispArea dispArea){
		// 필수값 검증
		int idx = StringUtil.isEmpty(dispArea.getDispCd());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }		


		// 삭제 처리
		super.addResultToModel(model, this.dispService.removeDispArea(super.getUserSeq(), dispArea.getDispCd()));
		
		
		return new MappingJackson2JsonView();
	}		
	
	
	/**
	 * 전시영역 등록/수정 인터페이스
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/disp/dispAreaForm")
	public ModelAndView dispAreaForm(ModelMap model, DispArea dispArea){
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(dispArea.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }
		
		// 수정모드인 경우의 기초 데이터 처리
		DispArea data = new DispArea();
		if(StringUtils.equals(mode, "update")) {
			data = this.dispService.getDispArea(dispArea.getDispCd());
			if(data == null) { throw new BadRequestException(); }
			
			model.addAttribute("dispAreaDtlListStr", StringUtil.getJsonStr(data.getDispAreaDtlList()));
		}else if(StringUtils.equals(mode, "create")) {
			model.addAttribute("dispAreaDtlListStr", "[]");
		}

		// 데이터 셋팅
		model.addAttribute("data", data);
		
		return new ModelAndView("disp/dispAreaForm");
	}


	/**
	 * 전시영역 저장/수정 처리
	 * @param model
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/disp/dispAreaSave")
	public View dispAreaSave(ModelMap model, DispArea dispArea, @RequestParam(name="dispAreaDtlListStr", required=true, defaultValue="[]") String dispAreaDtlListStr) throws Exception{
		// 필수값 검증
		int idx = StringUtil.isEmpty(dispArea.getDispCd(), dispArea.getDispAreaNm());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }		
		
		idx = NumberUtil.isZero(dispArea.getDispCnt());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }		


		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(dispArea.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }
		
		// 상세영역 정보 구성
		ObjectMapper om = new ObjectMapper();
		List<DispAreaDtl> dispAreaDtlList = om.readValue(om.readTree(dispAreaDtlListStr).traverse(), new TypeReference<List<DispAreaDtl>>() {});
		
		// mode에 맞게 등록 수정 처리
		Result result = null;
		if(StringUtils.equals(mode, "create")) {
			result = this.dispService.createDispArea(super.getUserSeq(), dispArea, dispAreaDtlList);
		}else if(StringUtils.equals(mode, "update")) {
			result = this.dispService.updateDispArea(super.getUserSeq(), dispArea, dispAreaDtlList);
		}
		
		
		// 결과 처리
		super.addResultToModel(model, result);
		
		
		return new MappingJackson2JsonView();
	}



	/**
	 * 템플릿 등록/수정 인터페이스
	 * @param model
	 * @param tmpl
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/disp/tmplForm")
	public ModelAndView tmplForm(ModelMap model, Tmpl tmpl){
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(tmpl.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }
		
		// 수정모드인 경우의 기초 데이터 처리
		Tmpl data = new Tmpl();
		if(StringUtils.equals(mode, "update")) {
			data = this.dispService.getTmpl(tmpl.getTmplSeq());
			if(data == null) { throw new BadRequestException(); }
			
			model.addAttribute("dispAreaListStr", StringUtil.getJsonStr(data.getDispAreaList()));
		}else if(StringUtils.equals(mode, "create")) {
			model.addAttribute("dispAreaListStr", "[]");
		}

		// 데이터 셋팅
		model.addAttribute("data", data);
		
		
		// 전시영역 정보 구성
		model.addAttribute("dispAreaList", this.dispService.getDispAreaList(new DispArea()));
		
		
		return new ModelAndView("disp/tmplForm");
	}

	/**
	 * 템플릿 등록 수정 처리
	 * @param model
	 * @param tmpl
	 * @param dispAreaListStr
	 * @return
	 * @throws Exception
	 */
	@UserAuthCheck
	@RequestMapping("/disp/tmplSave")
	public View tmplSave(ModelMap model, Tmpl tmpl, @RequestParam(name="dispAreaListStr", required=true, defaultValue="[]") String dispAreaListStr) throws Exception{
		// 필수값 검증
		int idx = StringUtil.isEmpty(tmpl.getTmplNm(), tmpl.getTmplPath());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }		
		
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(tmpl.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }
		
		// 상세영역 정보 구성
		ObjectMapper om = new ObjectMapper();
		List<DispArea> dispAreaDtlList = om.readValue(om.readTree(dispAreaListStr).traverse(), new TypeReference<List<DispArea>>() {});
		
		// mode에 맞게 등록 수정 처리
		Result result = null;
		if(StringUtils.equals(mode, "create")) {
			result = this.dispService.createTmpl(super.getUserSeq(), tmpl, dispAreaDtlList);
		}else if(StringUtils.equals(mode, "update")) {
			result = this.dispService.updateTmpl(super.getUserSeq(), tmpl, dispAreaDtlList);
		}
		
		
		// 결과 처리
		super.addResultToModel(model, result);
		
		
		return new MappingJackson2JsonView();
	}	


	/**
	 * 템플릿 목록 처리
	 * @param model
	 * @param tmpl
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/disp/tmplList")
	public ModelAndView tmplList(ModelMap model, Tmpl tmpl){
		model.addAttribute("result", this.dispService.getTmplPageList(tmpl));
		return new ModelAndView("disp/tmplList");
	}

}