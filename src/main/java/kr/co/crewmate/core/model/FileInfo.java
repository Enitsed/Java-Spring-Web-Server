package kr.co.crewmate.core.model;

/**
 * 파일 관리를 위한 모델
 * - 업로드된 파일의 정보를 처리하기 위해서 생성함.
 * @author 정슬기
 *
 */

public class FileInfo {
	private String key;
	private String filename;
	private String tempFile;
	private long filesize;
	private String useYn;
	private String flag;
	private String fileType;
	private String iconType;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public String getTempFile() {
		return tempFile;
	}
	public void setTempFile(String tempFile) {
		this.tempFile = tempFile;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getIconType() {
		return iconType;
	}
	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

}
