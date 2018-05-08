package kr.co.crewmate.api.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kr.co.crewmate.core.model.BaseModel;
/**
 * 카테고리 모델
 * @author 정슬기
 *
 */
public class Category extends BaseModel implements Serializable {
	private static final long serialVersionUID = -7625164692075731185L;

	/** 카테고리 고유번호 */
	private int ctgSeq;

	/** 상위 카테고리 고유번호 */
	private int ctgSeqParent;

	/** 카테고리 이름 */
	private String ctgNm;

	/** 카테고리 레벨 */
	private int ctgLv;
	
	/** 전시순서 */
	private int dispNo;
	
	/** 카테고리 경로 */
	private String ctgPath;

	/** 카테고리 유형 */
	private String ctgType;

	/** 사용여부 */
	private String useYn;

	/** 전시여부 */
	private String dispYn;

	/** 등록자 */
	private int regSeq;

	/** 등록일시 */
	private String regDt;

	/** 수정자 */
	private int modSeq;

	/** 수정일시 */
	private String modDt;
	
	/** 하위카테고리 존재유부 */
	private String subCtgYn;

	/** 카테고리 경로명 */
	private String ctgPathNm;

	/** 상위 카테고리 경로명 */
	private String ctgPathNmParent;
	

	/** 상품고유번호 */
	private int itemSeq;
	
	/** 상품 카테고리 매핑 유형 */
	private String itemCtgType;

	/** 하위 카테고리 목록 정보 */
	private List<Category> subCtgList;
	

	/** 전시 시작 일시 */
	private String dispStDt;
	
	/** 전시 종료 일시 */
	private String dispEndDt;

	/** 작성자 */
	private String regNm;
	
	/** 수정자 */
	private String modNm;
	
	/** 전시기간 체크 여부 */
	private String dispDtChkYn;
	
	/** 검색 필드 */
	private String keyField;
	
	/** 검색어 */
	private String keyWord;
	
	/** 매장에서 사용하는 템플릿 정보 */
	private List<Tmpl> tmplList;
	
	public int getCtgSeq() {
		return ctgSeq;
	}

	public void setCtgSeq(int ctgSeq) {
		this.ctgSeq = ctgSeq;
	}

	public int getCtgSeqParent() {
		return ctgSeqParent;
	}

	public void setCtgSeqParent(int ctgSeqParent) {
		this.ctgSeqParent = ctgSeqParent;
	}

	public String getCtgNm() {
		return ctgNm;
	}

	public void setCtgNm(String ctgNm) {
		this.ctgNm = ctgNm;
	}

	public int getCtgLv() {
		return ctgLv;
	}

	public void setCtgLv(int ctgLv) {
		this.ctgLv = ctgLv;
	}

	public String getCtgPath() {
		return ctgPath;
	}

	public void setCtgPath(String ctgPath) {
		this.ctgPath = ctgPath;
	}

	public String getCtgType() {
		return ctgType;
	}

	public void setCtgType(String ctgType) {
		this.ctgType = ctgType;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getDispYn() {
		return dispYn;
	}

	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
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

	public int getDispNo() {
		return dispNo;
	}

	public void setDispNo(int dispNo) {
		this.dispNo = dispNo;
	}

	public String getSubCtgYn() {
		return subCtgYn;
	}

	public void setSubCtgYn(String subCtgYn) {
		this.subCtgYn = subCtgYn;
	}

	public String getCtgPathNm() {
		return ctgPathNm;
	}

	public void setCtgPathNm(String ctgPathNm) {
		this.ctgPathNm = ctgPathNm;
		
		// 상위카테고리 경로정보도 같이 저장함.
		if(StringUtils.isNotEmpty(ctgPathNm)) {
			this.setCtgPathNmParent(StringUtils.trim(StringUtils.substringBeforeLast(ctgPathNm, ">")));
		}
	}

	public String getCtgPathNmParent() {
		return ctgPathNmParent;
	}

	public void setCtgPathNmParent(String ctgPathNmParent) {
		this.ctgPathNmParent = ctgPathNmParent;
	}

	public int getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
	}

	public String getItemCtgType() {
		return itemCtgType;
	}

	public void setItemCtgType(String itemCtgType) {
		this.itemCtgType = itemCtgType;
	}

	public List<Category> getSubCtgList() {
		return subCtgList;
	}

	public void setSubCtgList(List<Category> subCtgList) {
		this.subCtgList = subCtgList;
	}

	public String getDispStDt() {
		return dispStDt;
	}

	public void setDispStDt(String dispStDt) {
		this.dispStDt = dispStDt;
	}

	public String getDispEndDt() {
		return dispEndDt;
	}

	public void setDispEndDt(String dispEndDt) {
		this.dispEndDt = dispEndDt;
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

	public String getDispDtChkYn() {
		return dispDtChkYn;
	}

	public void setDispDtChkYn(String dispDtChkYn) {
		this.dispDtChkYn = dispDtChkYn;
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

	public List<Tmpl> getTmplList() {
		return tmplList;
	}

	public void setTmplList(List<Tmpl> tmplList) {
		this.tmplList = tmplList;
	}
	
	

}
