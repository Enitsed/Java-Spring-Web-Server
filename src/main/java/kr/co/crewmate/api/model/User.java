package kr.co.crewmate.api.model;

import kr.co.crewmate.core.model.BaseModel;

public class User extends BaseModel {
	private static final long serialVersionUID = 6781127815557944326L;

	/** 사용자 고유번호 */
	private int userSeq;

	/** 사용자 아이디 */
	private String userId;

	/** 사용자 비밀번호 */
	private String userPw;

	/** 사용자 이름 */
	private String name;

	/** 성별 */
	private String gender;

	/** 사용자 메일주소 */
	private String email;

	/** 생년월일 */
	private String birth;

	/** 주소 */
	private String address;

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
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
