package kr.co.crewmate.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kr.co.crewmate.api.constants.Constants;
import kr.co.crewmate.api.dao.CategoryDao;
import kr.co.crewmate.api.model.Category;
import kr.co.crewmate.api.model.Item;
import kr.co.crewmate.core.exception.BadRequestException;
import kr.co.crewmate.core.model.ListResult;
import kr.co.crewmate.core.model.Result;
import kr.co.crewmate.core.service.BaseService;
import kr.co.crewmate.core.util.NumberUtil;
import kr.co.crewmate.core.util.StringUtil;

/**
 * 카테고리 업무를 위한 서비스
 * @author 정슬기
 * @date 2018.04.17
 */
@Service
public class CategoryService extends BaseService {
	private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

	// 카테고리 정보를 static으로 가지고 있는 카테고리 맵 - FO에서 사용하기 위함임. 
	public static Map<Integer, Category> ctgMap = new HashMap<Integer, Category>();
	
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ItemService itemService;
	
	
	/**
	 * 전체 카테고리 정보를 static데이터에 로딩 시킨다.
	 */
//	@Scheduled(fixedDelay = 1000 * 60 * 30)
	public void loadCtgMapInfo() {
		// 셋팅 데이터 초기화
		Map<Integer, Category> map = new HashMap<Integer, Category>();
		
		// 데이터 조회
		Category param = new Category();
		param.setUseYn(Constants.Y);
		param.setDispYn(Constants.Y);
		List<Category> list = this.categoryDao.selectCategoryList(param);
		
		for(Category c : list) {
			c.setSubCtgList(this.getSubCtgList(c.getCtgSeq(), list));
			map.put(c.getCtgSeq(), c);
		}
		
		// 데이터 설정
		ctgMap = map;
		
	}
	
	/**
	 * 해당 카테고리의 하위 카테고리 목록 정보를 반환함.
	 * 	- 하위의 하위도 모두 판단함.
	 * @param ctgSeq
	 * @param list
	 * @return
	 */
	private List<Category> getSubCtgList(int ctgSeq, List<Category> list){
		List<Category> rv = new ArrayList<Category>();
		
		for(Category c : list) {
			if(c.getCtgSeqParent() == ctgSeq) {
				c.setSubCtgList(this.getSubCtgList(c.getCtgSeq(), list));
				rv.add(c);
			}
		}
		
		return rv;
	}
	

	/**
	 * 해당 카테고리 정보를 반환함.
	 * 	- FO용 서비스임. 메모리에서 데이터를 반환함.
	 * @param ctgSeq
	 * @return
	 */
	public Category getCtg(int ctgSeq) {
		return ctgMap.get(ctgSeq);
	}
	
	
	/**
	 * 카테고리 생성 처리
	 * 	- 전시기간조건이 없는 경우에 사용 -> 일반 전시카테고리
	 * 	- 마스터 카테고리는 직접 쿼리로 등록처리를 함.
	 * @param ctgSeqParent
	 * @param ctgNm
	 * @param dispNo
	 * @param ctgType
	 * @param userSeq
	 * @return 처리결과 및 생성된 카테고리 정보, getInfo("ctg")를 통해서 Ctg를 획득할 수 있음.
	 */
	public Result createCtg(int ctgSeqParent, String ctgNm, int dispNo, String dispYn, int userSeq) {
		Category ctg = new Category();
		ctg.setCtgSeqParent(ctgSeqParent);
		ctg.setCtgNm(ctgNm);
		ctg.setDispNo(dispNo);
		ctg.setDispYn(dispYn);
		
		return this.createCtg(userSeq, ctg);
	}

	/**
	 * 카테고리 생성 처리 
	 * 	- 공통임.
	 * 	- 기획매장생성하면서 변경처리
	 * @param userSeq
	 * @param ctg
	 * @return
	 */
	public Result createCtg(int userSeq, Category ctg) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(ctg.getCtgNm(), ctg.getDispYn());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(ctg.getCtgSeqParent(), userSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		

		// 상위 카테고리 검증
		Category p = new Category();
		p.setCtgSeq(ctg.getCtgSeqParent());
		Category ctgParent = this.categoryDao.selectCategory(p);
		if(ctgParent == null) { return new Result(500, "parent category is not exists!"); }
		
		// 리턴값 초기화
		Result result = new Result();
		
		
		// 마스터 정보 등록 처리 - ctgPath는 시퀀스가 발생한 후 업데이트해야 함.
		Category createCtg = new Category();
		createCtg.setCtgSeqParent(ctg.getCtgSeqParent());
		createCtg.setCtgNm(ctg.getCtgNm());
		createCtg.setCtgLv(ctgParent.getCtgLv()+1);
		createCtg.setDispNo(ctg.getDispNo());
		createCtg.setCtgType(ctgParent.getCtgType());
		createCtg.setUseYn(Constants.Y);
		createCtg.setDispYn(ctg.getDispYn());
		createCtg.setDispStDt(ctg.getDispStDt());
		createCtg.setDispEndDt(ctg.getDispEndDt());
		createCtg.setRegSeq(userSeq);
		createCtg.setModSeq(userSeq);
		if(this.categoryDao.insertCategory(createCtg) != 1) { throw new BadRequestException(); }
		

		// ctgPath정보 갱신 처리
		createCtg.setCtgPath(ctgParent.getCtgPath() + "," + createCtg.getCtgSeq());
		if(this.categoryDao.updateCategoryForCategoryPath(createCtg) != 1) { throw new BadRequestException(); }

		// 리턴값 설정 및 정상 처리
		result.putInfo("ctg", createCtg);
		return result;
	}	
	
	
	
	
	/**
	 * 해당 카테고리 정보 반환
	 * @param ctgSeq
	 * @return
	 */
	public Category getCtgReal(int ctgSeq, String dispYn, String useYn) {
		// 필수값 검증
		int idx = NumberUtil.isZero(ctgSeq);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		Category param = new Category();
		param.setCtgSeq(ctgSeq);
		param.setDispYn(dispYn);
		param.setUseYn(useYn);
		return this.categoryDao.selectCategory(param);	
	}
	
	
	/**
	 * 해당 카테고리의 하위 카테고리 목록정보를 리턴함.
	 * @param ctgSeqParent
	 * @return
	 */
	public List<Category> getSubCtgListReal(int ctgSeqParent, String dispYn){
		// 필수값 체크
		int idx = NumberUtil.isZero(ctgSeqParent);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		Category param = new Category();
		param.setCtgSeqParent(ctgSeqParent);
		param.setUseYn(Constants.Y);
		param.setDispYn(dispYn);
		return this.categoryDao.selectCategoryList(param);
	}
	
	
	/**
	 * 해당 상품에 연결된 카테고리 정보를 반환함.
	 * @param itemSeq
	 * @param itemCtgType
	 * @return
	 */
	public List<Category> getCtgListForItem(int itemSeq, String itemCtgType){
		// 필수값 체크
		int idx = StringUtil.isEmpty(itemCtgType);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 200); }

		idx = NumberUtil.isZero(itemSeq);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		Category param = new Category();
		param.setItemSeq(itemSeq);
		param.setItemCtgType(itemCtgType);
		return this.categoryDao.selectCategoryList(param);
	}

	
	/**
	 * 해당 상품에 연결된 카테고리 정보를 반환함.
	 * @param itemSeq
	 * @param itemCtgType
	 * @return
	 */
	public Category getCtgForItem(int itemSeq, String itemCtgType){
		// 필수값 체크
		int idx = StringUtil.isEmpty(itemCtgType);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 200); }

		idx = NumberUtil.isZero(itemSeq);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		List<Category> list = this.getCtgListForItem(itemSeq, itemCtgType);
		return list == null ? null : list.get(0);
	}	


	/**
	 * 카테고리 정보 수정 처리
	 * @param userSeq
	 * @param ctg
	 * @return
	 */
	public Result updateCtg(int userSeq, String ctgNm, String dispYn, int ctgSeq, int dispNo) {
		// 마스터 정보 등록 처리
		Category ctg = new Category();
		ctg.setCtgNm(ctgNm);
		ctg.setDispYn(dispYn);
		ctg.setCtgSeq(ctgSeq);
		ctg.setDispNo(dispNo);
		ctg.setModSeq(userSeq);
		return this.updateCtg(userSeq, ctg);
	}

	
	/**
	 * 카테고리 생성 
	 * 	- 공통임.
	 * @param userSeq
	 * @param ctg
	 * @return
	 */
	public Result updateCtg(int userSeq, Category ctg) {
		// 필수값 검증
		int idx = StringUtil.isEmpty(ctg.getCtgNm(), ctg.getDispYn());
		if(idx != -1) { return new Result(200, "invalid arguments : " + idx); }

		idx = NumberUtil.isZero(userSeq, ctg.getCtgSeq(), ctg.getDispNo());
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		

		// 마스터 정보 등록 처리
		Category createCtg = new Category();
		createCtg.setCtgNm(ctg.getCtgNm());
		createCtg.setDispYn(ctg.getDispYn());
		createCtg.setCtgSeq(ctg.getCtgSeq());
		createCtg.setDispNo(ctg.getDispNo());
		createCtg.setModSeq(userSeq);
		createCtg.setDispStDt(ctg.getDispStDt());
		createCtg.setDispEndDt(ctg.getDispEndDt());
		if(this.categoryDao.updateCategory(createCtg) != 1) { throw new BadRequestException(); }
		
		// 정상 처리
		return new Result();
	}	
	
	

	/**
	 * 카테고리 삭제 처리
	 * @param userSeq
	 * @param ctgSeq
	 * @return
	 */
	public Result removeCtg(int userSeq, int ctgSeq) {
		// 필수값 검증
		int idx = NumberUtil.isZero(userSeq, ctgSeq);
		if(idx != -1) { return new Result(300, "invalid arguments : " + idx); }
		

		// 마스터 정보 등록 처리
		Category ctg = new Category();
		ctg.setCtgSeq(ctgSeq);
		ctg.setUseYn(Constants.N);
		ctg.setModSeq(userSeq);
		if(this.categoryDao.updateCategoryUseYn(ctg) != 1) { throw new BadRequestException(); }
		
		// 정상 처리
		return new Result();
	}


	/**
	 * 카테고리 상품 목록 조회
	 * @param ctgSeq : 카테고리 번호
	 * @param order : 정렬기준
	 * @return
	 */
	public ListResult<Item> getCtgItemList(int ctgSeq, int page, int listSize, String order){
		// 필수값 검증
		int idx = StringUtil.isEmpty(order);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx); }
		
		idx = NumberUtil.isZeroMinus(page, listSize);
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx); }		
		
		
		// 카테고리 정보 획득 + 카테고리 유형 판단
		Category ctg = this.getCtg(ctgSeq);
		if(ctg == null) { throw new BadRequestException("ctgSeq invalid"); }
		if(!StringUtils.equals(ctg.getCtgType(), Constants.ctgType.DISP)) {
			throw new BadRequestException("ctgType invalid");
		}
		
		
		// 데이터 조회
		Item param = new Item();
		param.setCtgPath(ctg.getCtgPath());
		param.setPage(page);
		param.setListSize(listSize);
		param.setOrder(order);
		return this.itemService.getItemListForDisp(param);
	}

	/**
	 * 카테고리 목록 정보 반환 - 페이징
	 * @param ctg
	 * @return
	 */
	public ListResult<Category> getCtgListPage(Category ctg){
		// 필수값 검증
		int idx = NumberUtil.isZeroMinus(ctg.getCtgSeqParent(), ctg.getPage(), ctg.getListSize(), ctg.getPageSize());
		if(idx != -1) { throw new BadRequestException("invalid arguments : " + idx, 300); }
		
		// 데이터 조회
		Category param = new Category();
		param.setPage(ctg.getPage());
		param.setListSize(ctg.getListSize());
		param.setPageSize(ctg.getPageSize());
		
		param.setCtgSeqParent(ctg.getCtgSeqParent());
		param.setUseYn(Constants.Y);
		param.setDispYn(ctg.getDispYn());
		param.setCtgType(ctg.getCtgType());

		return this.categoryDao.selectCategoryListPage(param);		
	}

}
