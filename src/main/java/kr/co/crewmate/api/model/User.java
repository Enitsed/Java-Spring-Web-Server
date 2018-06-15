package kr.co.crewmate.api.model;

import kr.co.crewmate.core.model.BaseModel;

public class User extends BaseModel {
	private static final long serialVersionUID = 6781127815557944326L;

	/** 사용자 고유번호 */
	private int userSeq;
	
	/** 사용자 아이디 */
	private String _userId;
	
	/** 사용자 비밀번호 */
	private String _userPw;
	
	/** 사용자 이름 */
	private String name;
	
	/** 사용자 메일주소 */
	private String email;
	
	/** 등록자 */
	private int regSeq;
	
	/** 등록일시 */
	private String regDt;
	
	/** 수정자 */
	private int modSeq;
	
	/** 수정일시 */
	private String modDt;

	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserId() {
		return _userId;
	}

	public void setUserId(String userId) {
		this._userId = userId;
	}

	public String getUserPw() {
		return _userPw;
	}

	public void setUserPw(String userPw) {
		this._userPw = userPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRegSeq() {
		return regSeq;
	}

	public void setRegSeq(int regSeq) {
		this.regSeq = regSeq;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public int getModSeq() {
		return modSeq;
	}

	public void setModSeq(int modSeq) {
		this.modSeq = modSeq;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

}
