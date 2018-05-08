package kr.co.crewmate.core.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 날짜 관련 메서드를 위한 클래스
 * @author 정슬기
 * date : 2018.04.03
 *
 */
public class DateUtil {
	
	/**
	 * 현재 시작을 해당 패턴에 맞게 리턴함. - 패턴은 FastDateFormat의 정보를 확인할 것
	 * 
	 * @param pattern
	 *            : 리턴시킬패턴. ex) yyyyMMddHHmmss
	 * @return
	 */
	public static String getDateStr(String pattern) {
		return FastDateFormat.getInstance(pattern).format(new Date());
	}
	

	/**
	 * 해당 날짜 포맷이 유효한지 검증함.
	 * @param pattern : 체크할 패턴. null로 전달하면 yyyyMMddHHmmss 패턴을 기본값으로 사용함.
	 * @param source
	 * @return
	 */
	public static int isDateFormat(String pattern, String ... source) {
		String chkPattern = StringUtils.defaultIfEmpty(pattern, "yyyyMMddHHmmss");
		FastDateFormat format = FastDateFormat.getInstance(chkPattern);
		
		for(int i=0 ; i < source.length ; i++){
			try {
				format.parse(source[i]);
			}catch(Exception e) {
				return i;
			}
		}

		return -1;
	}
	
}
