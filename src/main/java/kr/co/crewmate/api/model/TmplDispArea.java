package kr.co.crewmate.api.model;

import java.io.Serializable;

import kr.co.crewmate.core.model.BaseModel;

/**
 * 템플릿 전시 영역 관련 모델
 * @author 정슬기
 *
 */
public class TmplDispArea extends BaseModel implements Serializable{
	private static final long serialVersionUID = -375964893034415532L;

	/** 템플릿 고유번호 */
	private int tmplSeq;

	/** 전시영역코드 */
	private String dispCd;
	
	
	public TmplDispArea() {}
	
	public TmplDispArea(int tmplSeq, String dispCd) {
		this.setTmplSeq(tmplSeq);
		this.setDispCd(dispCd);
	}
	
	

	public int getTmplSeq() {
		return tmplSeq;
	}

	public void setTmplSeq(int tmplSeq) {
		this.tmplSeq = tmplSeq;
	}

	public String getDispCd() {
		return dispCd;
	}

	public void setDispCd(String dispCd) {
		this.dispCd = dispCd;
	}



}