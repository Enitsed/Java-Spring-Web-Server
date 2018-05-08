package kr.co.crewmate.core.web.taglib;

import javax.servlet.jsp.JspTagException;

import org.apache.commons.lang3.StringUtils;

/**
 * 문자열 처리 관련 함수들...
 * @author crewmate
 *
 */
public class StringFn{
	/**
	 * BR 처리
	 * @param str
	 * @return
	 */
	public static String nl2br(String str){
		return StringUtils.replace(str, "\n", "<br/>");
	}
	
	
	/**
	 * 문자열 치환 처리
	 * @param str
	 * @param changeCnt
	 * @param replacestr
	 * @return
	 * @throws JspTagException
	 */
	public static String replace(String str, Integer changeCnt, String replacestr) throws JspTagException{
		try
		{
			str = StringUtils.left(str, str.length() - changeCnt);
			for(int i = 0 ; i < changeCnt; i++){
				str += replacestr;
			}
		}
		catch(Exception e)
		{
			throw new JspTagException(e);
		}
		return str;
	}

}