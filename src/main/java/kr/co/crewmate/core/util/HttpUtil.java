package kr.co.crewmate.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.crewmate.core.model.FileInfo;
import kr.co.crewmate.core.web.wrapper.ImportResponseWrapper;

/**
 * 공통 HTTP 메서드를 위한 클래스
 * @author 정슬기
 * date : 2018.04.03
 *
 */
public class HttpUtil {
	/**
	 * 클라이언트 ip 반환
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String[] headerKeys = {"X-Forwareded-For", "Proxy-Client-IP","WL-Proxy-Client-IP","HTTP_CLIENT_IP","HTTP_X_FORWARDED_FOR"};
		String ip = null;
		for(String key : headerKeys){
			ip = request.getHeader(key);
			
			if(StringUtils.isNotEmpty(ip)){
				return ip;
			}
		}
		return request.getRemoteAddr();		
	}
	
	
	/**
	 * 클라이언트 에이전트 정보 반환
	 * @param request
	 * @return
	 */
	public static String getUserAgent(HttpServletRequest request){
		return request.getHeader("user-agent");
	}


	/**
	 * request에서 해당 오브젝트를 반환함.
	 * 	- 태그라이브러리 제작에서 활용
	 * @param pageContext
	 * @param name
	 * @param scope
	 * @return
	 * @throws JspTagException
	 */
	public static Object lookup(PageContext pageContext, String name, String scope) throws JspTagException{
		Object bean = null;
		if(scope == null)
		{
			bean = pageContext.findAttribute(name);
		}
		else if(scope.equalsIgnoreCase("page"))
		{
			bean = pageContext.getAttribute(name, PageContext.PAGE_SCOPE);
		}
		else if(scope.equalsIgnoreCase("request"))
		{
			bean = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
		}
		else if(scope.equalsIgnoreCase("session"))
		{
			bean = pageContext.getAttribute(name, PageContext.SESSION_SCOPE);
		}
		else if(scope.equalsIgnoreCase("application"))
		{
			bean = pageContext.getAttribute(name, PageContext.APPLICATION_SCOPE);
		}
		else
		{
			JspTagException e = new JspTagException("Invalid scope " + scope);
			throw e;
		}
		return (bean);
	}


	/**
	 * request를 받아서 파라미터 문자열을 구성한다. - 파라미터를 그대로 전달하는 경우에 사용할 목적임.
	 * @param request
	 * @param skip
	 * @return
	 */
	public static String getParamStr(ServletRequest request, String skip){
		return getParamStr(request, skip, null);
	}



	/**
	 * request를 받아서 파라미터 문자열을 구성한다. - 파라미터를 그대로 전달하는 경우에 사용할 목적임.
	 * @param request
	 * @param skip
	 * @return
	 * @throws Exception
	 */
	public static String getParamStr(ServletRequest request, String skip, String addParamStr){
		try{
			StringBuffer sb = new StringBuffer();

			Enumeration<?> paramNames = request.getParameterNames();
			String[] skips = StringUtils.isNotEmpty(skip) ? skip.split(",") : null;
	
			while(paramNames.hasMoreElements()){
				String name = (String)paramNames.nextElement();
				if(ArrayUtils.contains(skips, name)){ continue; }

				if(request.getParameterValues(name) != null){
					String[] values = request.getParameterValues(name);
					
					for(String value : values){
						sb.append("&" + name + "=" + URLEncoder.encode(value, "UTF-8"));
					}
				}else{
					String value = request.getParameter(name);
					sb.append("&" + name + "=" + URLEncoder.encode(value, "UTF-8"));					
				}
				
			}
			
			if(StringUtils.isNotEmpty(addParamStr)){ sb.append("&").append(addParamStr); }
			
			if (sb.length() > 0) {
				
				String paramStr = sb.toString();
				paramStr = StringUtils.replace(paramStr, "<", "&lt;");
				paramStr = StringUtils.replace(paramStr, ">", "&gt;");
				
				return paramStr.substring(1);
			} 
			
			return "";
			//return sb.length() > 0 ? sb.toString().substring(1) : "";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 해당 경로의 JSP를 dispatch 시킨 결과를 반환함.
	 * @param request
	 * @param response
	 * @param path
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws Exception
	 */
	public static String dispatch(HttpServletRequest request, HttpServletResponse response, Map<String, Object> dataMap, String path) throws ServletException, IOException{
		// request에 해당 정보를 할당함.
		for(Map.Entry<String, Object> e : dataMap.entrySet()){
			request.setAttribute(e.getKey(), e.getValue());
		}
		
		// dispatch 시키고 결과를 반환함.
		ImportResponseWrapper wrapper = new ImportResponseWrapper(response);
		request.getRequestDispatcher(path).include(request, wrapper);
		return wrapper.getString();
	}


	/**
	 * 임시 파일 업로드.
	 * @param file
	 * @param tempPath
	 * @param tempDirname
	 * @param tempFilename
	 * @param tempFile
	 * @return
	 * @throws Exception
	 */
	public static FileInfo uploadFile(MultipartFile file, String tempPath, String tempDirname, String tempFilename, String tempFile) throws Exception {
		// 임시폴더 생성
		File tempDir = new File(tempPath + "/" + tempDirname);
		if(!tempDir.exists()){ tempDir.mkdirs(); }
		
		// 파일 복사
		file.transferTo(new File(tempFile));
		
		// 결과 정보 반환
		FileInfo fileInfo = new FileInfo();
		fileInfo.setKey(tempFilename);
		fileInfo.setFilename(file.getOriginalFilename());
		fileInfo.setFilesize(file.getSize());
		fileInfo.setTempFile("/" + tempDirname + "/" + tempFilename);
		fileInfo.setUseYn("Y");
		
		return fileInfo;
	}
	
	/**
	 * 임시 파일 업로드.
	 * @param mptRequest
	 * @param tempPath
	 * @param tempDirname
	 * @param tempFilename
	 * @param tempFile
	 * @return
	 * @throws Exception
	 */
	public static List<FileInfo> uploadFiles(MultipartHttpServletRequest mptRequest, String tempPath, String tempDirname, String tempFilename, String tempFile) throws Exception {
		Iterator<String> it = mptRequest.getFileNames();
		List<FileInfo> result = new ArrayList<FileInfo>();
		
		if(it != null){
			while(it.hasNext()){
				List<MultipartFile> mFiles = mptRequest.getFiles(it.next());
				
				for(MultipartFile file : mFiles){
					result.add(HttpUtil.uploadFile(file, tempPath, tempDirname, tempFilename, tempFile));
				}
			}
		}
		
		return result;
	}


	/**
	 * 멀티파트에서 첫번째 업로드 파일정보를 반환함.
	 * @param mptRequest
	 * @return
	 */
	public static MultipartFile getMultipartFileFirst(MultipartHttpServletRequest mptRequest){
		Iterator<String> it = mptRequest.getFileNames();
		
		if(it != null){
			while(it.hasNext()){
				List<MultipartFile> mFiles = mptRequest.getFiles(it.next());
				
				for(MultipartFile file : mFiles){
					return file;
				}
			}
		}
		
		return null;
	}
	
}
