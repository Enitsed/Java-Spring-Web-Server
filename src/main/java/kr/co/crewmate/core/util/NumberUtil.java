package kr.co.crewmate.core.util;

/**
 * 문자열 관련 유틸 모음
 * @author crewmate
 *
 */
public class NumberUtil {

	/**
	 * 해당 값들이 모두 0인지를 체크함.
	 * 	- 0인 항목의 index값을 반환함.
	 * 	- 모두 0이 아닌경우 -1값을 리턴시킴.
	 * @param val : 체크할 값들...
	 * @return
	 */
	public static int isZero(int ... val){
		for(int i=0 ; i < val.length ; i++){
			if(val[i] == 0){ return i; }
		}
		
		return -1;
	}	
	
	/**
	 * 해당 값들이 모두 숫자인지 체크
	 * @param val
	 * @return
	 */
	public static boolean isNumeric(String ... val){
		for(int i=0; i < val.length ; i++) {
			if(!val[i].matches("[0-9]+")) { return false;}
		}
		return true;
	}

	/**
	 * 해당값들이 모두 0이거나 작은지 검증함.
	 * @param val
	 * @return
	 */
	public static int isZeroMinus(int ... val) {
		for(int i=0 ; i < val.length ; i++){
			if(val[i] <= 0){ return i; }
		}
		
		return -1;
	}

	/**
	 * 해당값들이 모두 0보다 작은지 검증함.
	 * @param val
	 * @return
	 */
	public static int isMinus(int ... val) {
		for(int i=0 ; i < val.length ; i++){
			if(val[i] < 0){ return i; }
		}
		
		return -1;
	}

}
