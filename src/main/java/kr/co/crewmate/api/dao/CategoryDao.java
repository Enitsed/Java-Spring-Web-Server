package kr.co.crewmate.api.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.crewmate.api.model.Category;
import kr.co.crewmate.core.dao.BaseDao;
import kr.co.crewmate.core.model.ListResult;

/**
 * 카테고리 DAO
 * @author 정슬기
 * @date 2018.04.17
 */
@Repository
public class CategoryDao extends BaseDao {

	
	/**
	 * 카테고리 등록 처리
	 * @param p
	 * @return
	 */
	public int insertCategory(Category p){
		return super.insert("Category.insertCategory", p);
	}
	
	
	/**
	 * 카테고리 정보 반환
	 * @param p
	 * @return
	 */
	public Category selectCategory(Category p) {
		return (Category) super.selectOne("Category.selectCategory", p);
	}


	/**
	 * 카테고리 목록 정보 반환
	 * @param p
	 * @return
	 */
	public List<Category> selectCategoryList(Category p){
		return super.selectList("Category.selectCategory", p);
	}

	
	/**
	 * 카테고리 목록 정보 반환
	 * 	- 페이징
	 * @param p
	 * @return
	 */
	public ListResult<Category> selectCategoryListPage(Category p){
		return super.selectPageList("Ctg.selectCtgList", p);
	}
	
	
	/**
	 * 카테고리 경로 정보 업데이트 처리
	 * @param p
	 * @return
	 */
	public int updateCategoryForCategoryPath(Category p) {
		return super.update("Category.updateCategoryForCategoryPath", p);
	}

	/**
	 * 카테고리 정보 수정 처리
	 * @param p
	 * @return
	 */
	public int updateCategory(Category p) {
		return super.update("Category.updateCategory", p);
	}
	

	/**
	 * 카테고리 사용여부 업데이트 처리
	 * @param p
	 * @return
	 */
	public int updateCategoryUseYn(Category p) {
		return super.update("Category.updateCategoryUseYn", p);
	}

	
}
