package kr.co.crewmate.api.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.dao.FileDao;
import kr.co.crewmate.api.model.Files;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.FileInfo;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.service.BaseService;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 파일 처리를 위한 서비스
 * @author 정슬기
 *
 */
@Service
public class FileService extends BaseService {
	@Autowired
	private FileDao fileDao;

	/**
	 * 파일을 신규생성/수정 처리를 수행함.
	 * @param regSeq : 등록자 고유번호
	 * @param refSeq : 관련 고유번호
	 * @param fileInfoList : 처리할 파일 정보
	 * @param fileType : 파일유형
	 * @param prefix : 저장시 경로에 사용할 사용할 prefix정보
	 * @param fileServiceType : 파일 서비스 유형
	 * @return
	 * @throws Exception
	 */
	public Result createFileList(int regSeq, int refSeq, List<FileInfo> fileInfoList, String fileType, String prefix, String fileServiceType) throws Exception {
		// 첨부파일 정보 처리
		if(fileInfoList != null && fileInfoList.size() != 0) {
			for(FileInfo info : fileInfoList) {
				// 필수값 검증
				int idx = StringUtil.isEmpty(prefix, fileServiceType, info.getFilename(), info.getTempFile());
				if(idx != -1) { throw new BadRequestException("invalid arguments. idx : " + idx); }
				if(info.getFilesize() == 0) { throw new BadRequestException("invalid arguments. filesize "); }
				
				
				// 기존등록 파일이고 변경사항이 없으면 스킵
				if(StringUtils.equals(info.getFlag(), "real") && StringUtils.equals(info.getUseYn(), Constants.Y)) {
					continue;
				}
				
				// 신규 등록 파일인 경우 등록 처리
				if(!StringUtils.equals(info.getFlag(), "real") && StringUtils.equals(info.getUseYn(), Constants.Y)) {
					Result r = this.createFile(regSeq, fileType, refSeq, info.getFilesize(), info.getFilename(), info.getTempFile(), prefix, fileServiceType);
					if(r.getResultCode() != 1) { throw new BadRequestException("file exception"); }					
				}
				
				// 기존 파일이고 삭제된 경우 삭제 처리
				if(StringUtils.equals(info.getFlag(), "real") && StringUtils.equals(info.getUseYn(), Constants.N)) {
					Result r = this.removeFile(fileType, NumberUtils.toInt(info.getKey()), refSeq, prefix, fileServiceType, regSeq, Constants.Y);
					if(r.getResultCode() != 1) { throw new BadRequestException("file exception"); }
				}
			}
		}
		
		return new Result();
	}
	
	
	
	/**
	 * 파일 신규 생성 처리
	 * @param user
	 * @return
	 */
	public Result createFile(int regSeq, String fileType, int refSeq, long filesize, String filename, String filepath, String prefix, String fileServiceType) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(fileType, filename, filepath, prefix);
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }
		
		idx = NumberUtil.isZero(refSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }

		if(filesize == 0){ return new Result(350, "invalid arguments : filesize"); }
		
		if(!ArrayUtils.contains(Constants.FILE_SERVICE_TYPE_ALLOW, fileServiceType)) { return new Result(400, "invalid fileServiceType"); }
		
		
		// 임시파일 체크
		File fileTmp = new File(Constants.UPLOAD_TEMP_PATH + filepath);
		if(!fileTmp.exists()) { return new Result(500, "file not exists"); }

		// 복사 위치 지정
		String basePath = Constants.UPLOAD_REAL_PATH;
		if(StringUtils.equals(fileServiceType, Constants.fileServiceType.IMAGE)) {
			basePath = Constants.UPLOAD_IMG_PATH;
		}
		
		// 이미지인 경우에는 바로 서비스를 하기 위해서 확장자를 포함시킴.
		String fileExt = StringUtils.equals(fileServiceType, Constants.fileServiceType.IMAGE) ? "." + StringUtil.getFileExt(filename) : "";

		// 복사 처리
		File file = new File(basePath + "/" + prefix + filepath + fileExt);
		FileUtils.copyFile(fileTmp, file);
		

		// 데이터 처리
		Files files = new Files();
		files.setFileType(fileType);
		files.setRefSeq(refSeq);
		files.setFilesize(filesize);
		files.setFilename(filename);
		files.setFilepath(filepath + fileExt);
		files.setRegSeq(regSeq);
		if(this.fileDao.insertFiles(files) != 1) { throw new BadRequestException(); }
		
		return new Result();
	}
	
	
	/**
	 * 첨부파일 삭제 처리
	 * @param fileType
	 * @param fileSeq
	 * @param refSeq
	 * @param prefix
	 * @param fileServiceType
	 * @param regSeq
	 * @param regCheckYn
	 * @return
	 * @throws Exception
	 */
	public Result removeFile(String fileType, int fileSeq, int refSeq, String prefix, String fileServiceType, int regSeq, String regCheckYn) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(fileType, prefix, fileServiceType);
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }
		
		idx = NumberUtil.isZero(fileSeq, refSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		
		
		// 파일정보 획득
		Files files = this.getFile(fileSeq, refSeq, fileType);
		if(files == null) { throw new BadRequestException(); }
		
		// 작성자 검증
		if(StringUtils.equals(regCheckYn, Constants.Y) && regSeq != files.getRegSeq()) {
			throw new BadRequestException();
		}
		
		// 삭제할 파일 정보 구성
		String basePath = Constants.UPLOAD_REAL_PATH;
		if(StringUtils.equals(fileServiceType, Constants.fileServiceType.IMAGE)) {
			basePath = Constants.UPLOAD_IMG_PATH;
		}
		File file = new File(basePath + "/" + prefix + files.getFilepath());
		
		// 파일삭제처리 - 파일존재유무는 판단하지 않음.
		FileUtils.forceDelete(file);
		
		// 파일정보 삭제 처리
		Files param = new Files();
		param.setFileSeq(fileSeq);
		param.setFileType(fileType);
		param.setRefSeq(refSeq);
		param.setRegSeq(StringUtils.equals(regCheckYn, Constants.Y) ? regSeq : 0);
		if(this.fileDao.deleteFiles(param) != 1) { throw new BadRequestException(); }
		
		
		return new Result();
	}
	
	

	/**
	 * 파일목록 정보 반환
	 * @param refSeq
	 * @param fileType
	 * @return
	 */
	public List<Files> getFilesList(int refSeq, String fileType){
		// 필수값 검증
		int idx = StringUtil.isEmpty(fileType);
		if(idx != -1) {		throw new BadRequestException("invalid arguments : " + idx); }
		if(refSeq == 0){	throw new BadRequestException("invalid arguments : refSeq"); }
		
		// 데이터 조회
		Files param = new Files();
		param.setRefSeq(refSeq);
		param.setFileType(fileType);
		return this.fileDao.selectFilesList(param);
	}

	/**
	 * 파일정보 반환
	 * @param fileSeq
	 * @param refSeq
	 * @param fileType
	 * @return
	 */
	public Files getFile(int fileSeq, int refSeq, String fileType) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(fileType);
		if(idx != -1) {		throw new BadRequestException("invalid arguments : " + idx); }

		idx = NumberUtil.isZero(fileSeq, refSeq);
		if(idx != -1) {		throw new BadRequestException("invalid arguments : " + idx); }
		
		
		// 데이터 조회
		Files param = new Files();
		param.setRefSeq(refSeq);
		param.setFileType(fileType);
		param.setFileSeq(fileSeq);
		return this.fileDao.selectFiles(param);
		
	}

	/**
	 * 파일정보 반환
	 * @param fileSeq
	 * @param refSeq
	 * @param fileType
	 * @return
	 */
	public Files getFile(int refSeq, String fileType) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(fileType);
		if(idx != -1) {		throw new BadRequestException("invalid arguments : " + idx); }

		idx = NumberUtil.isZero(refSeq);
		if(idx != -1) {		throw new BadRequestException("invalid arguments : " + idx); }
		
		
		// 데이터 조회
		Files param = new Files();
		param.setRefSeq(refSeq);
		param.setFileType(fileType);
		return this.fileDao.selectFiles(param);
		
	}
	
	

	/**
	 * Files 모델을 FileInfo 모델로 변경한 목록 정보의 JSON문자열을 반환함.
	 * @param filesList
	 * @return
	 */
	public static String getFileInfoListJsonStr(List<Files> filesList) {
		return StringUtil.getJsonStr(getFileInfoList(filesList));
	} 


	/**
	 * Files 모델을 FileInfo 모델로 변경한 목록 정보를 반환함.
	 * @param filesList
	 * @return
	 */
	public static List<FileInfo> getFileInfoList(List<Files> filesList){
		// 원본이 없으면 null 처리
		if(filesList == null) { return new ArrayList<FileInfo>(); }
		
		// 데이터 재구성
		List<FileInfo> list = new ArrayList<FileInfo>();
		for(Files f : filesList) {
			list.add(f.getFileInfo());
		}
		
		// 리턴
		return list;
	}
}
