package kr.co.crewmate.api.model;

import java.util.List;

import kr.co.crewmate.core.model.BaseModel;

/**
 * 게시글 관리를 위한 모델
 * @author 정슬기
 *
 */
public class Contents extends BaseModel{
	private static final long serialVersionUID = -6984935338492778771L;

	/** 컨텐츠 고유번호 */
	private int contentsSeq;
	
	/** 컨텐츠 타입 */
	private String contentsType;
	
	/** 상위 컨텐츠 고유번호 */
	private int contentsSeqParent;
	
	/** 답변 단계 */
	private int replyStep;
	
	/** 전시 순서 */
	private float dispNo;
	
	/** 사용여부 */
	private String useYn;
	
	/** 전시여부 */
	private String dispYn;
	
	/** 제목 */
	private String title;
	
	/** 컨텐츠 */
	private String contents;
	
	/** 조회수 */
	private int viewCnt;
	
	/** 등록자 */
	private int regSeq;
	
	/** 등록일시 */
	private String regDt;
	
	/** 수정자 */
	private int modSeq;
	
	/** 수정일시 */
	private String modDt;

	/** 작성자 */
	private String regName;
	
	/** 검색어 */
	private String keyWord;
	
	/** 검색필드 */
	private String keyField;
	
	/** 첨부파일 정보 */
	private List<Files> filesList;
	
	public int getContentsSeq() {
		return contentsSeq;
	}

	public void setContentsSeq(int contentsSeq) {
		this.contentsSeq = contentsSeq;
	}

	public String getContentsType() {
		return contentsType;
	}

	public void setContentsType(String contentsType) {
		this.contentsType = contentsType;
	}

	public int getContentsSeqParent() {
		return contentsSeqParent;
	}

	public void setContentsSeqParent(int contentsSeqParent) {
		this.contentsSeqParent = contentsSeqParent;
	}

	public int getReplyStep() {
		return replyStep;
	}

	public void setReplyStep(int replyStep) {
		this.replyStep = replyStep;
	}

	public float getDispNo() {
		return dispNo;
	}

	public void setDispNo(float dispNo) {
		this.dispNo = dispNo;
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

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
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

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public List<Files> getFilesList() {
		return filesList;
	}

	public void setFilesList(List<Files> filesList) {
		this.filesList = filesList;
	}

}
