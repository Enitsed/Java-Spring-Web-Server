package kr.co.crewmate.api.model;

import java.io.Serializable;

import kr.co.crewmate.core.model.BaseModel;

/**
 * AuthHist Model
 * @author 정슬기
 *
 */
public class AuthHist extends BaseModel implements Serializable {
	private static final long serialVersionUID = 8211199949888026681L;

	/** 인증이력 고유번호 */
	private int authHistSeq;
	
	/** 회원 고유번호 */
	private Integer userSeq;
	
	/** 회원 아이디 */
	private String userId;
	
	/** 등록일시 */
	private String regDt;
	
	/** 인증유형 */
	private String authType;
	
	/** 성공여부 */
	private String successYn;
	
	/** 접속 아이피 */
	private String ip;
	
	/** 접속 환경 */
	private String userAgent;

	public int getAuthHistSeq() {
		return authHistSeq;
	}

	public void setAuthHistSeq(int authHistSeq) {
		this.authHistSeq = authHistSeq;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getSuccessYn() {
		return successYn;
	}

	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}	

}
