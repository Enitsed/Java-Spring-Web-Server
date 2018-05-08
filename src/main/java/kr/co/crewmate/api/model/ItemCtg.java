package kr.co.crewmate.api.model;

import java.io.Serializable;

import kr.co.crewmate.core.model.BaseModel;

public class ItemCtg extends BaseModel implements Serializable {
	private static final long serialVersionUID = 2623592210886233007L;

	public ItemCtg() {}
	
	public ItemCtg(int itemSeq, int ctgSeq, String itemCtgType) {
		this.setItemSeq(itemSeq);
		this.setCtgSeq(ctgSeq);
		this.setItemCtgType(itemCtgType);
	}
	
	/** '상품고유번호' */
	private int itemSeq;
	/** '카테고리 고유번호' */
	private int ctgSeq;
	/** '상품 카테고리 유형 - MST, DISP' */
	private String itemCtgType;

	public int getItemSeq() {
		return itemSeq;
	}
	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
	}
	public int getCtgSeq() {
		return ctgSeq;
	}
	public void setCtgSeq(int ctgSeq) {
		this.ctgSeq = ctgSeq;
	}
	public String getItemCtgType() {
		return itemCtgType;
	}
	public void setItemCtgType(String itemCtgType) {
		this.itemCtgType = itemCtgType;
	}
	
}
