package kr.co.crewmate.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 스트링 관련 처리를 위한 클래스
 * @author crewmate
 *
 */
public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	/**
	 * 해당 값들중에 빈값이 있는지 여부를 체크함
	 * 	- 빈값이 있는 항목의 인덱스를 반환함. 0부터 시작
	 * 	- 모든값이 비어있지 않으면 -1을 리턴함. 
	 * @param val
	 * @return
	 */
	public static int isEmpty(String ... val){
		for(int i=0 ; i < val.length ; i++){
			if(StringUtils.isEmpty(val[i])){ return i; }
		}
		
		return -1;
	}

	/**
	 * 해당 파일 경로문자열에서 파일 확장자 문자열을 반환함
	 * @param path
	 * @return
	 */
	public static String getFileExt(String path){
		if(StringUtils.isEmpty(path)){ return ""; }
		
		if(StringUtils.lastIndexOf(path, ".") != -1){
			return StringUtils.substringAfterLast(path, ".");
		}
		
		
		return "";
	}

	/**
	 * 해당 오브젝트를 JSON 문자열로 반환함.
	 * @param obj
	 * @return
	 */
	public static String getJsonStr(Object obj){
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException : {}", e.getMessage());
			return null;
		}
	}

}
