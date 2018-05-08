package kr.co.crewmate.core.model;

import java.util.List;

/**
 * 일반 처리 결과에 대한 정보 모델
 * @author 정슬기
 *
 */
public class ListResult <T> {
	private List<T> list;
	private int totalCount;
	private int listSize	= 10;
	private int pageSize	= 10;
	private int page		= 1;
	
	
	public ListResult(){}
	public ListResult(List<T> list, int totalCount){
		this.setList(list);
		this.setTotalCount(totalCount);
	}
	public ListResult(List<T> list, int totalCount, int listSize){
		this.setList(list);
		this.setTotalCount(totalCount);
		this.setListSize(listSize);
	}
	public ListResult(List<T> list, int totalCount, int listSize, int pageSize){
		this.setList(list);
		this.setTotalCount(totalCount);
		this.setListSize(listSize);
		this.setPageSize(pageSize);
	}
	
	public ListResult(List<T> list, int totalCount, int page, int listSize, int pageSize){
		this.setList(list);
		this.setTotalCount(totalCount);
		this.setPage(page);
		this.setListSize(listSize);
		this.setPageSize(pageSize);
	}
	
	public ListResult(List<T> list, int totalCount, BaseModel model){
		this.setList(list);
		this.setTotalCount(totalCount);
		this.setPage(model.getPage());
		this.setListSize(model.getListSize());
		this.setPageSize(model.getPageSize());
	}
	
	
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	// 전체 페이지 정보
	public int getTotalPage() {
		return (int) Math.ceil((double)this.totalCount / (double)this.listSize);
	}
	
	// 시작페이지 번호
	public int getStartPage(){
		return (int) ((Math.ceil((double)this.page / (double)this.pageSize) - 1) * (double)this.pageSize + 1);
	}
	
	// 종료 페이지 번호
	public int getEndPage(){
		int startPage	= this.getStartPage();
		int totalPage	= this.getTotalPage();
		return (startPage + this.pageSize - 1) > totalPage ? totalPage : (startPage + this.pageSize - 1);
	}

}
