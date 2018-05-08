package kr.co.crewmate.api.model;

import java.io.Serializable;
import java.util.List;

import kr.co.crewmate.core.model.BaseModel;

public class Item extends BaseModel implements Serializable {
	private static final long serialVersionUID = -889858018905362547L;

	/** 상품고유번호 */
	private int itemSeq;

	/** 상품명 */
	private String itemNm;

	/** 검색어 */
	private String searchKeyword;

	/** 상품간략설명 */
	private String descShort;

	/** 상품설명 */
	private String descLong;

	/** 상품상태 */
	private String itemStat;

	/** 상품전시상태 */
	private String itemDispStat;

	/** 최소판매수량 */
	private int sellCntMin;

	/** 최대판매수량 */
	private int sellCntMax;

	/** 판매가 */
	private int price;

	/** 할인가 */
	private int priceDc;

	/** 등록자 */
	private int regSeq;

	/** 등록일시 */
	private String regDt;

	/** 수정자 */
	private int modSeq;

	/** 수정일시 */
	private String modDt;
	
	/** 전체 재고 수량 */
	private int stockCntTot;
	
	/** 전체 판매 수량 */
	private int saleCntTot;
	
	
	/** 마스터 카테고리 번호 */
	private int ctgSeqMst;
	
	/** 전시카테고리 번호 */
	private int[] ctgSeqDisp;
	
	
	/** 마스터 카테고리 정보 */
	private Category ctgMst;
	
	/** 전시카테고리 목록 정보 */
	private List<Category> ctgListDisp;
	
	
	/** 마스터 이미지 정보 */
	private Files imgMst;
	
	/** 상세 이미지 정보 */
	private List<Files> imgSubList;
	
	/** 검색 필드 */
	private String keyField;
	
	/** 검색어 */
	private String keyWord;
	
	
	/** 등록자 */
	private String regNm;
	
	/** 수정자 */
	private String modNm;
	
	/** 카테고리 검색용 경로 정보 */
	private String ctgPath;
	
	/** 대표이미지 경로 정보 */
	private String mstImgPath;
	
	/** 상품목록 정렬 기준 */
	private String order;
	
	
	public int getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getDescShort() {
		return descShort;
	}

	public void setDescShort(String descShort) {
		this.descShort = descShort;
	}

	public String getDescLong() {
		return descLong;
	}

	public void setDescLong(String descLong) {
		this.descLong = descLong;
	}

	public String getItemStat() {
		return itemStat;
	}

	public void setItemStat(String itemStat) {
		this.itemStat = itemStat;
	}

	public String getItemDispStat() {
		return itemDispStat;
	}

	public void setItemDispStat(String itemDispStat) {
		this.itemDispStat = itemDispStat;
	}

	public int getSellCntMin() {
		return sellCntMin;
	}

	public void setSellCntMin(int sellCntMin) {
		this.sellCntMin = sellCntMin;
	}

	public int getSellCntMax() {
		return sellCntMax;
	}

	public void setSellCntMax(int sellCntMax) {
		this.sellCntMax = sellCntMax;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPriceDc() {
		return priceDc;
	}

	public void setPriceDc(int priceDc) {
		this.priceDc = priceDc;
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

	public int getCtgSeqMst() {
		return ctgSeqMst;
	}

	public void setCtgSeqMst(int ctgSeqMst) {
		this.ctgSeqMst = ctgSeqMst;
	}

	public int[] getCtgSeqDisp() {
		return ctgSeqDisp;
	}

	public void setCtgSeqDisp(int[] ctgSeqDisp) {
		this.ctgSeqDisp = ctgSeqDisp;
	}

	public int getStockCntTot() {
		return stockCntTot;
	}

	public void setStockCntTot(int stockCntTot) {
		this.stockCntTot = stockCntTot;
	}

	public int getSaleCntTot() {
		return saleCntTot;
	}

	public void setSaleCntTot(int saleCntTot) {
		this.saleCntTot = saleCntTot;
	}

	public Files getImgMst() {
		return imgMst;
	}

	public void setImgMst(Files imgMst) {
		this.imgMst = imgMst;
	}

	public List<Files> getImgSubList() {
		return imgSubList;
	}

	public void setImgSubList(List<Files> imgSubList) {
		this.imgSubList = imgSubList;
	}

	public List<Category> getCtgListDisp() {
		return ctgListDisp;
	}

	public void setCtgListDisp(List<Category> ctgListDisp) {
		this.ctgListDisp = ctgListDisp;
	}

	public Category getCtgMst() {
		return ctgMst;
	}

	public void setCtgMst(Category ctgMst) {
		this.ctgMst = ctgMst;
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

	public String getCtgPath() {
		return ctgPath;
	}

	public void setCtgPath(String ctgPath) {
		this.ctgPath = ctgPath;
	}

	public String getMstImgPath() {
		return mstImgPath;
	}

	public void setMstImgPath(String mstImgPath) {
		this.mstImgPath = mstImgPath;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * 할인율 정보를 반환함.
	 * @return
	 */
	public double getDcRate() {
		return Math.floor(100 - (100 * this.priceDc / this.price));
	}

}
