package kr.co.crewmate.core.exception;

import java.util.List;

/**
 * 
 * @author : 정슬기
 * @date : 2018.03.28
 * @function : 일반적인 잘못된 요청에 대한 예외
 * @description : resultCode는 100번대를 사용할 것! -> BadRequestException이 기본적으로 100으로 처리됨.(ajax에서)
 *
 */

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {
	public final static int DISP_MODE_DEFAULT	= 100;	// 바닥 페이지
	public final static int DISP_MODE_ALERT		= 200;	// Alert 출력후 메인페이지 이동
	
	private String message ="";
	private List<String> messageList;
	private int resultCode = 100; // 기본 100
	private int dispMode = 200;
	
	public BadRequestException() {} // 생성자
	
	public BadRequestException(int dispMode, String message) {
		this.dispMode = dispMode;
		this.message = message;
	}
	
	public BadRequestException(String message){
		this.setMessage(message);
	}
	
	public BadRequestException(String message, int resultCode){
		this.setMessage(message);
		this.setResultCode(resultCode);
	}

	public BadRequestException(List<String> messageList){
		this.setMessageList(messageList);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public int getDispMode() {
		return dispMode;
	}

	public void setDispMode(int dispMode) {
		this.dispMode = dispMode;
	}
	

}
