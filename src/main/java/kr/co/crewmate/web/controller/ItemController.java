package kr.co.crewmate.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
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

import kr.co.crewmate.api.model.Files;
import kr.co.crewmate.api.model.Item;
import kr.co.crewmate.api.service.FileService;
import kr.co.crewmate.api.service.ItemService;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.FileInfo;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;
import kr.co.crewmate.web.annotation.UserAuthCheck;

/**
 * 상품 컨트롤러
 * @author 정슬기
 *
 */
@Controller
public class ItemController extends ApiBaseController {
	private static Logger logger = LoggerFactory.getLogger(ItemController.class);
	@Autowired
	private ItemService itemService;

	/**
	 * 상품목록 처리
	 * @param model
	 * @param item
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/item/itemList")
	public ModelAndView itemList(ModelMap model, Item item){
		model.addAttribute("result", this.itemService.getItemList(item));
		return new ModelAndView("item/itemList");
	}

	
	/**
	 * 상품 상세 인터페이스
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/item/itemView")
	public ModelAndView itemView(ModelMap model, Item item){
		// 필수값 체크
		if(item.getItemSeq() == 0){ throw new BadRequestException("invalid arguments : itemSeq"); }
		
		// 기초 데이터 처리
		Item data = this.itemService.getItem(item.getItemSeq());
		if(data == null) { throw new BadRequestException(); }
		
		// 수정모드인 경우에만 첨부파일 정보 구성
		List<Files> fileMstInfoList = new ArrayList<Files>();
		fileMstInfoList.add(data.getImgMst());
		model.addAttribute("fileMstInfoListJsonStr", FileService.getFileInfoListJsonStr(fileMstInfoList));
		model.addAttribute("fileSubInfoListJsonStr", FileService.getFileInfoListJsonStr(data.getImgSubList()));			
		

		// 데이터 셋팅
		model.addAttribute("data", data);
		
		return new ModelAndView("item/itemView");
	}	
	
	
	
	/**
	 * 상품 등록/수정 인터페이스
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/item/itemForm")
	public ModelAndView itemForm(ModelMap model, Item item){
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(item.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }
		
		// 기초 데이터 처리
		Item data = new Item();
		if(StringUtils.equals(mode, "create")) {
			model.addAttribute("fileMstInfoListJsonStr", "[]");
			model.addAttribute("fileSubInfoListJsonStr", "[]");			
		}else if(StringUtils.equals(mode, "update")) {
			data = this.itemService.getItem(item.getItemSeq());
			if(data == null) { throw new BadRequestException(); }
			
			// 수정모드인 경우에만 첨부파일 정보 구성
			List<Files> fileMstInfoList = new ArrayList<Files>();
			fileMstInfoList.add(data.getImgMst());
			model.addAttribute("fileMstInfoListJsonStr", FileService.getFileInfoListJsonStr(fileMstInfoList));
			model.addAttribute("fileSubInfoListJsonStr", FileService.getFileInfoListJsonStr(data.getImgSubList()));			
		}
		

		
		
		// 데이터 셋팅
		model.addAttribute("data", data);
		
		return new ModelAndView("item/itemForm");
	}

	/**
	 * 상품정보 등록/수정 처리
	 * @param model
	 * @param item
	 * @param fileMstInfoStr
	 * @param fileSubInfoStr
	 * @param ctgSeqDispStr
	 * @return
	 * @throws Exception
	 */
	@UserAuthCheck
	@RequestMapping("/item/itemSave")
	public View itemSave(
		ModelMap model, 
		Item item, 
		@RequestParam(name="fileMstInfoStr", required=false, defaultValue="[]") String fileMstInfoStr,
		@RequestParam(name="fileSubInfoStr", required=false, defaultValue="[]") String fileSubInfoStr, 
		@RequestParam(name="ctgSeqDispStr", required=false, defaultValue="[]") String ctgSeqDispStr
	) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(item.getItemNm(), item.getDescLong(), item.getItemStat(), item.getItemDispStat());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }
		
		idx = NumberUtil.isZero(item.getPrice(), item.getPriceDc(), item.getSellCntMin(), item.getSellCntMax(), item.getCtgSeqMst());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }		
		
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(item.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update"}, mode)) { throw new BadRequestException(); }		

		
		// 전시카테고리 정보 구성
		ObjectMapper om = new ObjectMapper();
		int[] ctgSeqDisp = om.readValue(om.readTree(ctgSeqDispStr).traverse(), new TypeReference<int[]>() {});
		item.setCtgSeqDisp(ctgSeqDisp);
		
		if(item.getCtgSeqDisp() == null || item.getCtgSeqDisp().length == 0) {
			throw new BadRequestException("item disp ctg is empty! ");
		}
		
		// 첨부이미지 정보 구성
		List<FileInfo> imgListMst = om.readValue(om.readTree(fileMstInfoStr).traverse(), new TypeReference<List<FileInfo>>() {});
		List<FileInfo> imgListSub = om.readValue(om.readTree(fileSubInfoStr).traverse(), new TypeReference<List<FileInfo>>() {});
		
		if(imgListMst == null || imgListSub == null || imgListMst.size() == 0 || imgListSub.size() == 0) {
			throw new BadRequestException("image info is empty! ");
		}

		
		// 등록 수정 처리
		Result result = new Result();
		if(StringUtils.equals(mode, "create")) {
			result = this.itemService.createItem(super.getFoUser().getUserSeq(), item, imgListMst, imgListSub);
		}else if(StringUtils.equals(mode, "update")) {
			if(item.getItemSeq() == 0) { throw new BadRequestException(); }
			result = this.itemService.updateItem(super.getFoUser().getUserSeq(), item, imgListMst, imgListSub);
		}
		
		
		super.addResultToModel(model, result);
		return new MappingJackson2JsonView();
	}
	
	
}