package kr.co.crewmate.api.model;

import java.io.Serializable;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.core.model.BaseModel;
import kr.co.crewmate.core.model.FileInfo;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 파일 모델
 * @author crewmate
 */
public class Files extends BaseModel implements Serializable {
	private static final long serialVersionUID = 2720825701712677086L;

	/** 파일 고유번호 */
	private int fileSeq;

	/** 파일유형 */
	private String fileType;

	/** 관련 고유번호 */
	private int refSeq;

	/** 파일크기 */
	private long filesize;

	/** 파일명 */
	private String filename;

	/** 파일 경로 */
	private String filepath;

	/** 등록자 */
	private int regSeq;

	/** 등록일시 */
	private String regDt;
	
	public int getFileSeq() {
		return fileSeq;
	}

	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getRefSeq() {
		return refSeq;
	}

	public void setRefSeq(int refSeq) {
		this.refSeq = refSeq;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
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

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	
	// FileInfo 모델로 변경된 정보의 JSON 문자열을 반환함.
	public String getFileInfoJsonStr() {
		return StringUtil.getJsonStr(this.getFileInfo());
	}

	// Files모델은 FileInfo 모델로 변경해서 반환함.
	public FileInfo getFileInfo() {
		FileInfo info = new FileInfo();

		info.setFileType(this.getFileType());
		info.setFilename(this.getFilename());
		info.setFilesize(this.getFilesize());
		info.setFlag("real");
		info.setKey(String.valueOf(this.getFileSeq()));
		info.setTempFile(this.getFilepath());
		info.setUseYn(Constants.Y);

		return info;
	}
}
