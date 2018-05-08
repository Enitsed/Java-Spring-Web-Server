package kr.co.crewmate.api.exception;

/**
 * 사용자 인증 Exception
 * resultCode는 10000대 사용
 * @author crewmate
 * 
 */

@SuppressWarnings("serial")
public class UserAuthException extends RuntimeException {
	private String message = "";
	private int resultCode = 10000; // 기본 10000

	public UserAuthException() {
	}

	public UserAuthException(String message) {
		this.setMessage(message);
	}

	public UserAuthException(String message, int resultCode) {
		this.setMessage(message);
		this.setResultCode(resultCode);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
