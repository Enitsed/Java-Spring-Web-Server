package kr.co.crewmate.api.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.dao.ContentsDao;
import kr.co.crewmate.api.model.Contents;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.FileInfo;
import kr.co.crewmate.core.model.ListResult;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.service.BaseService;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 게시판 관련 서비스
 * @author 정슬기
 */
@Service
public class ContentsService extends BaseService{

	@Autowired
	private ContentsDao contentsDao;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 컨텐츠 신규 생성 처리
	 * @param userSeq
	 * @param contents
	 * @return
	 */
	public Result createContents(int userSeq, Contents contents, List<FileInfo> fileInfoList, String fileServiceType) throws Exception{
		// 필수값 검증
		int idx = StringUtil.isEmpty(contents.getContentsType(), contents.getTitle(), contents.getContents());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		// 회원번호 필수값 검증.
		if(userSeq == 0) { return new Result(200, "invalid arguments : userSeq"); }

		// 상위게시글 번호가 존재하는 경우 해당 게시글 정보 조회 - 컨텐츠 타입 검증, 답변 스텝 계산
		if(contents.getContentsSeqParent() != 0) {
			Contents contentsParent = this.getContents(contents.getContentsSeqParent(), contents.getContentsType(), Constants.Y, Constants.Y);
			if(contentsParent == null) { throw new BadRequestException(); }
			
			// 답변단계 계산
			contents.setReplyStep(contentsParent.getReplyStep() + 1);
		}
		
		// 데이터 값 처리
		contents.setDispNo(this.contentsDao.selectContentsNextDispNo(contents));
		contents.setUseYn(Constants.Y);
		contents.setDispYn(Constants.Y);
		contents.setViewCnt(0);
		contents.setRegSeq(userSeq);
		contents.setModSeq(userSeq);
		
		// 데이터 등록 처리
		if(this.contentsDao.insertContents(contents) != 1) { throw new BadRequestException(); }
		
		// 첨부파일 정보 처리
		Result result = this.fileService.createFileList(userSeq, contents.getContentsSeq(), fileInfoList, Constants.fileType.CONTENTS, Constants.filePrefix.CONTENTS, fileServiceType);
		if(result.getResultCode() != 1) { throw new BadRequestException(); }

		return new Result();
	}
	
	/**
	 * 컨텐츠 정보를 반환함.
	 * @param contentsSeq
	 * @param contentsType
	 * @param useYn
	 * @param dispYn
	 * @return
	 */
	public Contents getContents(int contentsSeq, String contentsType, String useYn, String dispYn) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(contentsType);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 200); }
		
		if(contentsSeq == 0){ throw new BadRequestException("invalid arguments : contentsSeq"); }
		
		// 데이터 조회
		Contents param = new Contents();
		param.setContentsSeq(contentsSeq);
		param.setContentsType(contentsType);
		param.setUseYn(useYn);
		param.setDispYn(dispYn);
		Contents contents = this.contentsDao.selectContents(param);
		
		// 컨텐츠의 파일 정보 조회
		if(contents != null) {
			contents.setFilesList(this.fileService.getFilesList(contentsSeq, Constants.fileType.CONTENTS));
		}
		
		return contents;
	}

	/**
	 * 컨텐츠 목록 정보를 반환함.
	 * @param contentsType
	 * @param page
	 * @param listSize
	 * @param pageSize
	 * @return
	 */
	public ListResult<Contents> getContentsList(String contentsType, String keyField, String keyWord, int page, int listSize, int pageSize){
		// 필수값 검증
		int idx = StringUtil.isEmpty(contentsType);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 200); }
		
		idx = NumberUtil.isZeroMinus(page, listSize, pageSize);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		Contents param = new Contents();
		param.setContentsType(contentsType);
		param.setKeyField(keyField);
		param.setKeyWord(keyWord);
		param.setPage(page);
		param.setListSize(listSize);
		param.setPageSize(pageSize);
		param.setUseYn(Constants.Y);
		param.setDispYn(Constants.Y);
		
		return this.contentsDao.selectContentsList(param);
	}
	
	/**
	 * 컨텐츠 조회수 증가 처리
	 * @param contentsSeq
	 * @return
	 */
	public Result updateContentsViewCnt(int contentsSeq) {
		// 필수값 체크
		if(contentsSeq == 0) { return new Result(100, "invalid arguments"); }
		
		// 데이터 처리
		int result = this.contentsDao.updateContentsViewCnt(contentsSeq);
		if(result != 1) { throw new BadRequestException(); }
		
		return new Result();
	}
	
	/**
	 * 컨텐츠 업데이트 생성 처리
	 * 	- 컨텐츠 수정시 작성자 검증은 controller레벨에서 처리하도록 구성함.
	 * 	- BO에서도 동일기능을 사용할 수 있음.
	 * @param userSeq
	 * @param contents
	 * @return
	 */
	public Result updateContents(int userSeq, Contents contents, List<FileInfo> fileInfoList, String fileServiceType) throws Exception {
		// 필수값 검증
		int idx = StringUtil.isEmpty(contents.getContentsType(), contents.getTitle(), contents.getContents());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(userSeq, contents.getContentsSeq());
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }

		// 데이터 값 처리
		contents.setModSeq(userSeq);
		
		// 데이터 등록 처리
		if(this.contentsDao.updateContents(contents) != 1) { throw new BadRequestException(); }

		// 첨부파일 정보 처리
		Result result = this.fileService.createFileList(userSeq, contents.getContentsSeq(), fileInfoList, Constants.fileType.CONTENTS, Constants.filePrefix.CONTENTS, fileServiceType);
		if(result.getResultCode() != 1) { throw new BadRequestException(); }

		return new Result();
	}

	/**
	 * 컨텐츠 삭제 처리
	 * @param contentsSeq
	 * @return
	 */
	public Result removeContents(int contentsSeq) {
		// 필수값 체크
		int idx = NumberUtil.isZero(contentsSeq);
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }
		
		// 삭제 가능여부 판단.
		Contents param = new Contents();
		param.setContentsSeq(contentsSeq);
		param.setDispYn(Constants.Y);
		param.setUseYn(Constants.Y);
		String removeYn = this.contentsDao.selectContentsRemoveYn(param);
		if(StringUtils.equals(removeYn, Constants.N)) {
			return new Result(500, "exists sub contents");
		}
		
		// 삭제 처리
		if(this.contentsDao.updateContentsUseYn(contentsSeq) != 1) { throw new BadRequestException(); }
		
		return new Result();
	}
}
