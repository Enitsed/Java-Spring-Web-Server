package kr.co.crewmate.core.web.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

public class ImageView extends AbstractView {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	private File file;
	private String filename;


	public ImageView(File file){
		this.file = file;
	}
	
	
	public ImageView(String pathname){
		if(this.file == null){
			file = new File(pathname);
		}
	}
	
	
	public ImageView(String pathname, String name){
		if(this.file == null){
			file = new File(pathname);
		}
		filename = name;
	}
	
	
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(this.file != null && this.file.exists() && this.file.isFile()){
			// 파일 정보 획득
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.file));
			byte[] bytes = new byte[bis.available()];
			bis.read(bytes);
			bis.close();
			
			// 헤더 구성
			response.setContentType(this.getContentType(this.file));
			response.setContentLength(bytes.length);
			

			// 파일 쓰기
			OutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
		}else{
			log.error("ImageView file load fail! : {}", this.file);
		}
	}


	/**
	 * 파일 확장자를 기준으로 파일의 contentType 정보를 반환함.
	 * @param file
	 * @return
	 */
	private String getContentType(File file){
		//String contentType = new MimetypesFileTypeMap().getContentType(this.file);		=> 요거는 Resource를 활용하기 때문에 application/octet-stream값이 나올수 있음.
		String contentType = "";
		String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		
		if (file.getName().indexOf(".") == -1 && StringUtils.isNotEmpty(filename)) {
			ext = filename.substring(filename.lastIndexOf(".") + 1);
			
		}
		
		if(StringUtils.equalsIgnoreCase(ext, "jpg") || StringUtils.equalsIgnoreCase(ext, "jpeg")){
			contentType = "image/jpeg";
		}else if(StringUtils.equalsIgnoreCase(ext, "gif")){
			contentType = "image/gif";
		}else if(StringUtils.equalsIgnoreCase(ext, "png")){
			contentType = "image/png";
		}
		
		return contentType;
	}



}
