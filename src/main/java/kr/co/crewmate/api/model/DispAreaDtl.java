package kr.co.crewmate.api.model;

import java.io.Serializable;

import kr.co.crewmate.core.model.BaseModel;

/**
 * 전시영역 상세 관련 모델
 * @author 정슬기
 *
 */
public class DispAreaDtl extends BaseModel implements Serializable{
	private static final long serialVersionUID = -1538868683430126575L;

	/** 전시영역코드 */
	private String dispCd;

	/** 전시영역 상세 고유번호 */
	private int dispAreaDtlSeq;

	/** 전시영역 상세 이름 */
	private String dispAreaDtlNm;

	/** 전시순서 */
	private int dispNo;

	/** 사용여부 */
	private String useYn;
	

	
	
	public String getDispCd() {
		return dispCd;
	}

	public void setDispCd(String dispCd) {
		this.dispCd = dispCd;
	}

	public int getDispAreaDtlSeq() {
		return dispAreaDtlSeq;
	}

	public void setDispAreaDtlSeq(int dispAreaDtlSeq) {
		this.dispAreaDtlSeq = dispAreaDtlSeq;
	}

	public String getDispAreaDtlNm() {
		return dispAreaDtlNm;
	}

	public void setDispAreaDtlNm(String dispAreaDtlNm) {
		this.dispAreaDtlNm = dispAreaDtlNm;
	}

	public int getDispNo() {
		return dispNo;
	}

	public void setDispNo(int dispNo) {
		this.dispNo = dispNo;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	
	
}