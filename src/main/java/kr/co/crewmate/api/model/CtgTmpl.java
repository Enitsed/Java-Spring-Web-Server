package kr.co.crewmate.api.model;

import java.io.Serializable;

import kr.co.crewmate.core.model.BaseModel;

/**
 * 매장 템플릿 관련 모델
 * @author 정슬기
 *
 */
public class CtgTmpl extends BaseModel implements Serializable{
	private static final long serialVersionUID = 9204197462610617774L;

	/** 카테고리(매장) 고유번호 */
	private int ctgSeq;

	/** 템플릿 고유번호 */
	private int tmplSeq;

	
	public CtgTmpl() {}
	
	public CtgTmpl(int ctgSeq) {
		this.setCtgSeq(ctgSeq);
	}
	
	
	
	
	public int getCtgSeq() {
		return ctgSeq;
	}

	public void setCtgSeq(int ctgSeq) {
		this.ctgSeq = ctgSeq;
	}

	public int getTmplSeq() {
		return tmplSeq;
	}

	public void setTmplSeq(int tmplSeq) {
		this.tmplSeq = tmplSeq;
	}


}
