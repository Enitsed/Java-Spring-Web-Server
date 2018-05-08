package kr.co.crewmate.core.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import kr.co.crewmate.core.exception.BadRequestException;

public class DownloadView extends AbstractView {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	private File file;
	private String downloadFilename;
	private String pathname;
	
	
	
	public DownloadView(){}
	public DownloadView(File file){
		this.file = file;
	}
	public DownloadView(File file, String downloadFilename){
		this.file				= file;
		this.downloadFilename	= downloadFilename;
	}
	public DownloadView(String pathname){
		this.pathname	= pathname;
	}
	public DownloadView(String pathname, String downloadFilename){
		this.pathname			= pathname;
		this.downloadFilename	= downloadFilename;
	}	
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 다운로드 파일 정보를 획득함.
		this.setSourceFile(model);
		
		if(file != null && file.exists()){
			// response header 설정
			response.setContentType("application/octet-stream");
			response.setContentLength((int)file.length());
			response.setHeader("Content-Transfer-Encoding", "binary");
			String userAgent = request.getHeader("user-agent");
			String filename = null;
			
			if(StringUtils.isNotEmpty(this.downloadFilename)){				
				filename = this.downloadFilename;
			}else{
				filename =  file.getName();
			}
			if(userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1 || userAgent.indexOf("Chrome") > -1){	//IE, Chrome 브라우저에 경우
				filename = URLEncoder.encode(filename, "UTF-8").replace("\\+", "%20");
			}else{
				filename = new String(filename.getBytes("UTF-8"), "8859_1");
			}
			response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
			// 다운로드 처리
			OutputStream out	= response.getOutputStream();
			FileInputStream fis	= new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
			fis.close();
		}else{
			log.info("file info not exists!");
			throw new BadRequestException();
		}
	}


	/**
	 * 다운로드 시킬 원본 파일정보를 반환함.
	 * @param modelMap
	 * @return
	 */
	private void setSourceFile(Map<String, Object> model){
		// 기본 정보 설정
		File file				= this.file;
		String downloadFilename	= this.downloadFilename;

		// 파일 경로에서 파일정보를 찾음.
		if(StringUtils.isNotEmpty(this.pathname)){
			file = new File(pathname);
		}
		
		// 모델에서 파일정보를 찾음.
		if(model.containsKey("file")){				file = (File) model.get("file"); }
		if(model.containsKey("downloadFilename")){	downloadFilename = (String) model.get("downloadFilename"); }
		
		
		// 정보 설정
		this.file 				= file;
		this.downloadFilename	= downloadFilename;
	}


}
