package kr.co.crewmate.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.util.StringUtils;

/**
 * 모델 공통 요건을 처리함.
 * @author 정슬기
 *
 */
@SuppressWarnings("serial")
public class BaseModel implements Serializable{
	
	private String lang = "ko"; // 기본언어 정보
	private String service; // 서비스 정보
	private String sysdate;
	private String mode;
	
	
	private int page		= 1;
	private int listSize	= 20;		// 목록의 개수
	private int pageSize	= 10;		// 페이지번호의 개수 - 화면상에서 필요함.
	private int totCnt		= -1;		// 오라클용 전체 개수 정보
	private int limitStart	= 0;
	private int limitEnd	= 10;

	private int startRow = -1;
	private int endRow = -1;
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	// ORACLE SYSDATE 대신 사용
	public String getSysdate() {
		if(StringUtils.isEmpty(this.sysdate)) { this.sysdate = FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date());; } 
		return this.sysdate;
	}
	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}
	
	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
	public int getLimitStart() {
		return (this.page - 1) * this.listSize;
	}
	public int getLimitEnd() {
		// MySQL 에서는 뒤의 숫자는 개수를 의미함.
		//return this.page * this.listSize;
		return listSize;
	}
	
	public int getStartRow() {
		if (startRow  == -1) {
			startRow = (page - 1) * listSize + 1;
		}
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		if (endRow  == -1) {
			endRow = page * listSize;
		}
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	/**
	 * mode값이 유효한 값이 전달되었는지 검증함.
	 * @param allows
	 * @return
	 */
	public boolean validateMode(String[] allows){
		return ArrayUtils.contains(allows, this.mode);
	}
	
	/**
	 * mode 값이 유효한 값이 전달되었는지를 검증함.
	 * @return
	 */
	public boolean validateMode(){
		return this.validateMode(new String[]{"create", "update"});
	}
	
	
	public String toString(){
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}


	
	
}
