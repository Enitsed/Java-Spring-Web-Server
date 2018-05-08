package kr.co.crewmate.web.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.web.controller.ApiBaseController;
import kr.co.crewmate.core.model.FileInfo;
import kr.co.crewmate.core.util.DateUtil;
import kr.co.crewmate.core.util.HttpUtil;
import kr.co.crewmate.core.web.view.ImageView;
import kr.co.crewmate.core.web.view.JSONView;
import kr.co.crewmate.web.annotation.UserAuthCheck;

/**
 * 파일업로드를 위한 컨트롤러
 * @author 정슬기
 *
 */
@Controller
public class CommonController extends ApiBaseController {
	private final static Logger logger = LoggerFactory.getLogger(CommonController.class);
	

	/**
	 * 파일업로드 처리
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@UserAuthCheck
	@RequestMapping("/common/file/upload")
	public View upload(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		
		String tempPath		= Constants.UPLOAD_TEMP_PATH;
		String tempDirname	= DateUtil.getDateStr("yyyyMMddHH");
		String tempFilename = UUID.randomUUID() + "_" + DateUtil.getDateStr("yyyyMMddHHmmss");
		String tempFile		= tempPath + "/" + tempDirname + "/" + tempFilename;
		model.addAttribute("result", HttpUtil.uploadFiles(mptRequest, tempPath, tempDirname, tempFilename, tempFile));
		
		return new JSONView();
	}


	/**
	 * 이미지 뷰 temp
	 * @return
	 */
	@RequestMapping("/common/file/imageView")
	public ModelAndView imageView(ModelMap model, FileInfo fileInfo){
		String tempPath	= Constants.UPLOAD_TEMP_PATH;
		if(StringUtils.equals(fileInfo.getFlag(), "real")){ tempPath = Constants.UPLOAD_REAL_PATH; }
		String url = tempPath + fileInfo.getTempFile();
		return new ModelAndView(new ImageView(url, fileInfo.getFilename()));
	}
}
