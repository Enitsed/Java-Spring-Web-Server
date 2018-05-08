package kr.co.crewmate.api.model;

import kr.co.crewmate.core.model.BaseModel;

/**
 * 
 * @author 정슬기
 * 작성일 : 2018.03.28
 * 내용 : Test 모델
 */
public class Test extends BaseModel{
	/**
	 * 객체직렬화***
	 */
	private static final long serialVersionUID = -2789728945209908818L;
	private int testSeq;
	private String title;
	private String contents;

	public Test() {
	}

	public int getTestSeq() {
		return testSeq;
	}

	public void setTestSeq(int testSeq) {
		this.testSeq = testSeq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
