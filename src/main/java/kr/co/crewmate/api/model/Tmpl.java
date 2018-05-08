package kr.co.crewmate.api.model;

import java.io.Serializable;
import java.util.List;

import kr.co.crewmate.core.model.BaseModel;

/**
 * 템플릿 관련 모델
 * @author 정슬기
 *
 */
public class Tmpl extends BaseModel implements Serializable{
	private static final long serialVersionUID = -253659951879317981L;

	/** 템플릿 고유번호 */
	private int tmplSeq;

	/** 템플릿 이름 */
	private String tmplNm;

	/** 템플릿 타겟 */
	private String tmplTgt;

	/** 템플릿 경로 */
	private String tmplPath;

	/** 등록자 */
	private int regSeq;

	/** 등록일시 */
	private String regDt;

	/** 수정자 */
	private int modSeq;

	/** 수정일시 */
	private String modDt;

	/** 등록자 */
	private String regNm;
	
	/** 수정자 */
	private String modNm;
	
	/** 검색 필드 */
	private String keyField;
	
	/** 검색어 */
	private String keyWord;	
	
	/** 전시영역 리스트 */
	private List<DispArea> dispAreaList;
	
	
	public int getTmplSeq() {
		return tmplSeq;
	}

	public void setTmplSeq(int tmplSeq) {
		this.tmplSeq = tmplSeq;
	}

	public String getTmplNm() {
		return tmplNm;
	}

	public void setTmplNm(String tmplNm) {
		this.tmplNm = tmplNm;
	}

	public String getTmplTgt() {
		return tmplTgt;
	}

	public void setTmplTgt(String tmplTgt) {
		this.tmplTgt = tmplTgt;
	}

	public String getTmplPath() {
		return tmplPath;
	}

	public void setTmplPath(String tmplPath) {
		this.tmplPath = tmplPath;
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

	public List<DispArea> getDispAreaList() {
		return dispAreaList;
	}

	public void setDispAreaList(List<DispArea> dispAreaList) {
		this.dispAreaList = dispAreaList;
	}	
	
	
	
	
}
