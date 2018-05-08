package kr.co.crewmate.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.dao.ItemDao;
import kr.co.crewmate.api.model.Item;
import kr.co.crewmate.api.model.ItemCtg;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.FileInfo;
import kr.co.crewmate.core.model.ListResult;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.service.BaseService;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 상품 관련 업무
 * @author 정슬기
 *
 */
@Service
public class ItemService extends BaseService{
	private Logger logger = LoggerFactory.getLogger(ItemService.class);

	@Autowired
	private FileService fileService;
	
	@Autowired
	private CategoryService ctgService;
	
	@Autowired
	private ItemDao itemDao;

	
	/**
	 * 상품 상세 정보를 반환함
	 * 	- BO기능임. FO는 별도로 고민해야 함.
	 * @param itemSeq
	 * @return
	 */
	public Item getItem(int itemSeq) {
		// 필수값 체크
		if(itemSeq == 0){ throw new BadRequestException("invalid arguments : itemSeq"); }
		
		// 데이터 조회
		Item param = new Item();
		param.setItemSeq(itemSeq);
		Item item = this.itemDao.selectItem(param);

		// 데이터가 존재하는 경우 추가 데이터 획득
		if(item != null) {
			// 카테고리 정보 처리
			item.setCtgMst(this.ctgService.getCtgForItem(itemSeq, Constants.itemCtgType.MST));
			item.setCtgListDisp(this.ctgService.getCtgListForItem(itemSeq, Constants.itemCtgType.DISP));
			
			// 대표 이미지 정보 처리
			item.setImgMst(this.fileService.getFile(itemSeq, Constants.fileType.ITEM_MST));
			
			// 상세 이미지 정보 처리
			item.setImgSubList(this.fileService.getFilesList(itemSeq, Constants.fileType.ITEM_SUB));
		}
		
		return item;
	}
	
	
	/**
	 * 상품 목록 조회 처리
	 * @param keyField
	 * @param keyWord
	 * @param page
	 * @param listSize
	 * @param pageSize
	 * @return
	 */
	public ListResult<Item> getItemList(Item item){
		// 필수값 검증
		int idx = NumberUtil.isZeroMinus(item.getPage(), item.getListSize(), item.getPageSize());
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		Item param = new Item();
		param.setCtgPath(item.getCtgPath());
		param.setItemStat(item.getItemStat());
		param.setItemDispStat(item.getItemDispStat());
		param.setItemSeq(item.getItemSeq());
		param.setKeyField(item.getKeyField());
		param.setKeyWord(item.getKeyWord());
		
		param.setPage(item.getPage());
		param.setListSize(item.getListSize());
		param.setPageSize(item.getPageSize());
		
		return this.itemDao.selectItemList(param);
	}
	
	
	/**
	 * 상품 등록 처리
	 * 	- 첨부파일은 일단 필수체크에서 제외함.
	 * @param userSeq : 회원 고유번호
	 * @param item : 상품마스터 정보
	 * @param imgListMst : 상품 대표 이미지 정보
	 * @param imgListSub : 상품 상세 이미지 정보
	 * @return
	 * @throws Exception
	 */
	public Result createItem(int userSeq, Item item, List<FileInfo> imgListMst, List<FileInfo> imgListSub) throws Exception{
		// 필수값 검증
		int idx = StringUtil.isEmpty(item.getItemNm(), item.getDescLong(), item.getItemStat(), item.getItemDispStat());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(userSeq, item.getPrice(), item.getPriceDc(), item.getSellCntMin(), item.getSellCntMax(), item.getCtgSeqMst());
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		
		if(item.getCtgSeqDisp() == null || item.getCtgSeqDisp().length == 0) {
			return new Result(400, "disp ctg seq info is empty!");
		}
		
		
		// 마스터 정보 등록 처리
		item.setRegSeq(userSeq);
		item.setModSeq(userSeq);
		item.setSaleCntTot(0);
		if(this.itemDao.insertItem(item) != 1) { throw new BadRequestException(); }
		
		// 상품 카테고리 정보 등록 처리
		ItemCtg itemCtg = new ItemCtg(item.getItemSeq(), item.getCtgSeqMst(), Constants.itemCtgType.MST);
		if(this.itemDao.insertItemCtg(itemCtg) != 1) { throw new BadRequestException(); }
		
		for(int ctgSeq : item.getCtgSeqDisp()) {
			itemCtg = new ItemCtg(item.getItemSeq(), ctgSeq, Constants.itemCtgType.DISP);
			if(this.itemDao.insertItemCtg(itemCtg) != 1) { throw new BadRequestException(); }	
		}
		
		
		// 첨부이미지 처리
		Result result = this.fileService.createFileList(userSeq, item.getItemSeq(), imgListMst, Constants.fileType.ITEM_MST, Constants.filePrefix.ITEM, Constants.fileServiceType.IMAGE);
		if(result.getResultCode() != 1) { throw new BadRequestException(); }

		result = this.fileService.createFileList(userSeq, item.getItemSeq(), imgListSub, Constants.fileType.ITEM_SUB, Constants.filePrefix.ITEM, Constants.fileServiceType.IMAGE);
		if(result.getResultCode() != 1) { throw new BadRequestException(); }
		
		
		// 정상 리턴
		return new Result();
	}
	
	/**
	 * 상품정보 수정 처리
	 * @param userSeq : 회원고유번호
	 * @param item : 상품마스터 정보
	 * @param imgListMst : 대표이미지 정보
	 * @param imgListSub : 상세 이미지 정보
	 * @return
	 * @throws Exception
	 */
	public Result updateItem(int userSeq, Item item, List<FileInfo> imgListMst, List<FileInfo> imgListSub) throws Exception{
		// 필수값 검증
		int idx = StringUtil.isEmpty(item.getItemNm(), item.getDescLong(), item.getItemStat(), item.getItemDispStat());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(userSeq, item.getItemSeq(), item.getPrice(), item.getPriceDc(), item.getSellCntMin(), item.getSellCntMax(), item.getCtgSeqMst());
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		
		if(item.getCtgSeqDisp() == null || item.getCtgSeqDisp().length == 0) {
			return new Result(400, "disp ctg seq info is empty!");
		}


		// 마스터 정보 수정 처리
		item.setModSeq(userSeq);
		if(this.itemDao.updateItem(item) != 1) { throw new BadRequestException(); }
		
		
		// 상품 카테고리 정보 등록 처리 - 기존정보 삭제후 신규 등록임.
		this.itemDao.deleteItemCtg(new ItemCtg(item.getItemSeq(), 0, Constants.itemCtgType.MST));		// 대표카테고리 삭제
		this.itemDao.deleteItemCtg(new ItemCtg(item.getItemSeq(), 0, Constants.itemCtgType.DISP));		// 전시카테고리 삭제
		
		ItemCtg itemCtg = new ItemCtg(item.getItemSeq(), item.getCtgSeqMst(), Constants.itemCtgType.MST);
		if(this.itemDao.insertItemCtg(itemCtg) != 1) { throw new BadRequestException(); }
		
		for(int ctgSeq : item.getCtgSeqDisp()) {
			itemCtg = new ItemCtg(item.getItemSeq(), ctgSeq, Constants.itemCtgType.DISP);
			if(this.itemDao.insertItemCtg(itemCtg) != 1) { throw new BadRequestException(); }	
		}		
		
		// 첨부이미지 처리
		Result result = this.fileService.createFileList(userSeq, item.getItemSeq(), imgListMst, Constants.fileType.ITEM_MST, Constants.filePrefix.ITEM, Constants.fileServiceType.IMAGE);
		if(result.getResultCode() != 1) { throw new BadRequestException(); }

		result = this.fileService.createFileList(userSeq, item.getItemSeq(), imgListSub, Constants.fileType.ITEM_SUB, Constants.filePrefix.ITEM, Constants.fileServiceType.IMAGE);
		if(result.getResultCode() != 1) { throw new BadRequestException(); }
		
		// 정상 리턴
		return new Result();
	}

	
	
	/**
	 * 상품목록 조회 처리
	 * 	- front service를 위한 기능임.
	 * 	- 여기에서는 최소한의 기능을 정의하고 다른 service에서 이 method를 활용해서 실제 업무 기능을 구현할 것!
	 * @param item
	 * @return
	 */
	public ListResult<Item> getItemListForDisp(Item item){
		// 필수값 검증
		int idx = NumberUtil.isZeroMinus(item.getPage(), item.getListSize(), item.getPageSize());
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		

		return this.itemDao.selectItemForDispList(item);
	}
	

}
