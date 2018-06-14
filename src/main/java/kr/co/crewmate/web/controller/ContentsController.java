package kr.co.crewmate.web.controller;

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

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.model.Contents;
import kr.co.crewmate.api.model.Files;
import kr.co.crewmate.api.model.User;
import kr.co.crewmate.api.service.ContentsService;
import kr.co.crewmate.api.service.FileService;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.FileInfo;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;
import kr.co.crewmate.core.web.view.DownloadView;
import kr.co.crewmate.web.annotation.UserAuthCheck;

/**
 * 게시판 관련 컨트롤러
 * @author 정슬기
 */
@Controller
public class ContentsController extends ApiBaseController{
	@Autowired
	private ContentsService contentsService;
	@Autowired
	private FileService fileService;
	
	private static Logger logger = LoggerFactory.getLogger(ContentsController.class);
	
	/**
	 * 컨텐츠 목록 처리
	 * @param model
	 * @param contents
	 * @return
	 */
	@RequestMapping("/contents/bbsList")
	public ModelAndView bbsList(ModelMap model, Contents contents) {
		model.addAttribute("result", this.contentsService.getContentsList(Constants.contentsType.BBS, contents.getKeyField(), contents.getKeyWord(), contents.getPage(), contents.getListSize(), contents.getPageSize()));
		return new ModelAndView("contents/bbsList");
	}	

	/**
	 * 컨텐츠 상세
	 * @param model
	 * @param contents
	 * @return
	 */
	@RequestMapping("/contents/bbsView")
	public ModelAndView bbsView(ModelMap model, Contents contents) {
		// 필수값 체크
		if(contents.getContentsSeq() == 0) { throw new BadRequestException(); }
		
		// 데이터 조회
		Contents data = this.contentsService.getContents(contents.getContentsSeq(), Constants.contentsType.BBS, Constants.Y, Constants.Y);
		if(data == null) { throw new BadRequestException(); }
		model.addAttribute("data", data);
		
		// 조회수 업데이트 처리
		this.contentsService.updateContentsViewCnt(data.getContentsSeq());
		
		// 이미지 출력을 위한 기본 경로 정보 구성
		model.addAttribute("imgBase", "/images/" + Constants.filePrefix.CONTENTS);
		
		return new ModelAndView("contents/bbsView");
	}	
	
	/**
	 * 컨텐츠 등록폼 처리
	 * @param model
	 * @param contents
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/contents/bbsForm")
	public ModelAndView bbsForm(ModelMap model, Contents contents) {
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(contents.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update", "reply"}, mode)) { throw new BadRequestException(); }		
		
		// 수정, 답변모드인 경우에는 데이터 획득 처리
		Contents data = new Contents();

		if(ArrayUtils.contains(new String[] {"update", "reply"}, mode)) {
			data = this.contentsService.getContents(contents.getContentsSeq(), Constants.contentsType.BBS, Constants.Y, Constants.Y);
			
			// 데이터 존재여부 판단
			if(data == null) { throw new BadRequestException(); }
			
			// 작성자 검증 - 수정모드인 경우에만 검증함.
			if(StringUtils.equals("update", mode) && data.getRegSeq() != super.getFoUser().getUserSeq()) { throw new BadRequestException(); }
		}
		model.addAttribute("data", data);
		
		// 수정모드인 경우에만 첨부파일 정보 구성
		model.addAttribute("fileInfoListJsonStr", StringUtils.equals(mode, "update") ? FileService.getFileInfoListJsonStr(data.getFilesList()) : "[]");
		
		// 이미지 출력을 위한 기본 경로 정보 구성
		model.addAttribute("imgBase", "/images/" + Constants.filePrefix.CONTENTS);		
		
		return new ModelAndView("contents/bbsForm");
	}	
	
	/**
	 * 컨텐츠 등록 처리
	 * @param model
	 * @param user
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/contents/bbsSave")
	public View bbsSave(ModelMap model, Contents contents, @RequestParam(name="fileInfoStr", required=false, defaultValue="[]") String fileInfoStr) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(contents.getTitle(), contents.getContents());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }
		
		// mode에 대한 검증
		String mode = StringUtils.defaultIfEmpty(contents.getMode(), "create");
		if(!ArrayUtils.contains(new String[] {"create", "update", "reply"}, mode)) { throw new BadRequestException(); }
		
		// 사용자 정보 획득
		User currUser = super.getFoUser();

		// 첨부파일 정보 획득
		ObjectMapper om = new ObjectMapper();
		List<FileInfo> fileInfoList = StringUtils.isNotBlank(fileInfoStr) ? om.readValue(om.readTree(fileInfoStr).traverse(), new TypeReference<List<FileInfo>>() {}) : null;

		// 데이터 처리
		contents.setContentsType(Constants.contentsType.BBS);
		Result result = new Result();
		if(ArrayUtils.contains(new String[] {"create", "reply"}, mode)) {
			// 등록 처리 - 답변인 경우에는 시퀀스를 상위 시퀀스로 변경 처리
			if(StringUtils.equals("reply", mode)) {
				if(contents.getContentsSeq() == 0) { throw new BadRequestException(); }
				contents.setContentsSeqParent(contents.getContentsSeq());
				contents.setContentsSeq(0);
			}

			result = this.contentsService.createContents(currUser.getUserSeq(), contents, fileInfoList, Constants.fileServiceType.IMAGE);
		}else if(StringUtils.equals("update", mode)) {
			// 수정 처리 - 작성자 검증후 수정처리
			Contents data = this.contentsService.getContents(contents.getContentsSeq(), Constants.contentsType.BBS, Constants.Y, Constants.Y);
			if(data == null) { throw new BadRequestException(); }
			if(StringUtils.equals("update", mode) && data.getRegSeq() != super.getFoUser().getUserSeq()) { throw new BadRequestException(); }

			result = this.contentsService.updateContents(currUser.getUserSeq(), contents, fileInfoList, Constants.fileServiceType.IMAGE);
		}
		// 처리결과 등록
		super.addResultToModel(model, result);
		
		return new MappingJackson2JsonView();
	}
	
	/**
	 * 컨텐츠 삭제 처리
	 * @param model
	 * @param contents
	 * @return
	 */
	@UserAuthCheck
	@RequestMapping("/contents/bbsRemove")	
	public View bbsRemove(ModelMap model, Contents contents) {
		// 필수값 검증
		int idx = NumberUtil.isZero(contents.getContentsSeq());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }
		
		// 작성자 검증
		Contents data = this.contentsService.getContents(contents.getContentsSeq(), Constants.contentsType.BBS, Constants.Y, Constants.Y);
		if(data == null) { throw new BadRequestException(); }
		if(data.getRegSeq() != super.getFoUser().getUserSeq()) { throw new BadRequestException(); }
		
		// 삭제처리
		Result result = this.contentsService.removeContents(contents.getContentsSeq());

		// 처리결과 등록
		super.addResultToModel(model, result);
		
		return new MappingJackson2JsonView();
	}

	/**
	 * 첨부파일 다운로드 처리
	 * @param model
	 * @param contents
	 * @param files
	 * @return
	 */
	@RequestMapping("/contents/download")
	public View download(ModelMap model, Contents contents, Files files) {
		// 필수값 검증
		int idx = NumberUtil.isZero(contents.getContentsSeq(), files.getFileSeq());
		if(idx != -1) {  throw new BadRequestException("invalid arguments : " + idx); }
		
		// 다운로드 데이터 정보 획득
		Files f = this.fileService.getFile(files.getFileSeq(), contents.getContentsSeq(), Constants.fileType.CONTENTS);
		if(f == null) { throw new BadRequestException(); }
		
		// 다운로드 처리	
		return new DownloadView(Constants.UPLOAD_IMG_PATH + "/" + Constants.filePrefix.CONTENTS + f.getFilepath(), f.getFilename());
	}

}
