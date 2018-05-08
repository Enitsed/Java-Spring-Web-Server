package kr.co.crewmate.api.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.crewmate.api.model.Item;
import kr.co.crewmate.api.model.ItemCtg;
import kr.co.crewmate.core.dao.BaseDao;
import kr.co.crewmate.core.model.ListResult;


/**
 * 상품관련 DAO 
 * @author 정슬기
 *
 */
@Repository
public class ItemDao extends BaseDao {
	

	/**
	 * 상품 조회 처리
	 * @param p
	 * @return
	 */
	public Item selectItem(Item p){
		return (Item) super.selectOne("Item.selectItem", p);
	}
	
	/**
	 * 상품 카테고리 조회 처리
	 * @param p
	 * @return
	 */
	public List<ItemCtg> selectItemCtgList(ItemCtg p){
		return super.selectList("Item.selectItemCtg", p);
	}

	/**
	 * 상품 등록 처리
	 * @param p
	 * @return
	 */
	public int insertItem(Item p){
		return super.insert("Item.insertItem", p);
	}
	

	/**
	 * 상품 수정 처리
	 * @param p
	 * @return
	 */
	public int updateItem(Item p) {
		return super.update("Item.updateItem", p);
	}
	
	
	
	/**
	 * 상품 카테고리 등록 처리
	 * @param p
	 * @return
	 */
	public int insertItemCtg(ItemCtg p){
		return super.insert("Item.insertItemCtg", p);
	}

	
	/**
	 * 상품 카테고리 삭제 처리
	 * @param p
	 * @return
	 */
	public int deleteItemCtg(ItemCtg p) {
		return super.delete("Item.deleteItemCtg", p);
	}


	/**
	 * 상품 목록 조회 처리
	 * @param p
	 * @return
	 */
	public ListResult<Item> selectItemList(Item p){
		return super.selectPageList("Item.selectItem", p);
	}

	/**
	 * 상품 목록 조회 처리
	 * 	- front 전시용
	 * @param p
	 * @return
	 */
	public ListResult<Item> selectItemForDispList(Item p){
		return super.selectPageList("Item.selectItemForDisp", p);
	}
}