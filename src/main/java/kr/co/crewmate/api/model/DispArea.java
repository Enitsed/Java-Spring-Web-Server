package kr.co.crewmate.api.model;

import java.io.Serializable;
import java.util.List;

import kr.co.crewmate.core.model.BaseModel;

/**
 * 전시영역 관련모델
 * @author 정슬기
 *
 */
public class DispArea extends BaseModel implements Serializable{
	private static final long serialVersionUID = 8657278044762335363L;

	/** 전시영역코드 */
	private String dispCd;

	/** 전시유형 */
	private String dispType;

	/** 전시개수 */
	private int dispCnt;

	/** 등록자 */
	private int regSeq;

	/** 등록일시 */
	private String regDt;

	/** 수정자 */
	private int modSeq;

	/** 수정일시 */
	private String modDt;

	/** 전시영역 이름 */
	private String dispAreaNm;

	/** 사용여부 */
	private String useYn;
	
	/** 상세 사용여부 */
	private String dtlUseYn;
	
	/** 전시영역상세 목록 정보 */
	private List<DispAreaDtl> dispAreaDtlList;
	
	/** 등록자 */
	private String regNm;
	
	/** 수정자 */
	private String modNm;
	
	/** 검색 필드 */
	private String keyField;
	
	/** 검색어 */
	private String keyWord;	
	
	
	public String getDispCd() {
		return dispCd;
	}

	public void setDispCd(String dispCd) {
		this.dispCd = dispCd;
	}

	public String getDispType() {
		return dispType;
	}

	public void setDispType(String dispType) {
		this.dispType = dispType;
	}

	public int getDispCnt() {
		return dispCnt;
	}

	public void setDispCnt(int dispCnt) {
		this.dispCnt = dispCnt;
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

	public String getDispAreaNm() {
		return dispAreaNm;
	}

	public void setDispAreaNm(String dispAreaNm) {
		this.dispAreaNm = dispAreaNm;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getDtlUseYn() {
		return dtlUseYn;
	}

	public void setDtlUseYn(String dtlUseYn) {
		this.dtlUseYn = dtlUseYn;
	}

	public List<DispAreaDtl> getDispAreaDtlList() {
		return dispAreaDtlList;
	}

	public void setDispAreaDtlList(List<DispAreaDtl> dispAreaDtlList) {
		this.dispAreaDtlList = dispAreaDtlList;
	}

	public String getRegNm() {
		return regNm;
	}

	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}

	public String getModNm() {
		return modNm;
	}

	public void setModNm(String modNm) {
		this.modNm = modNm;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}





}