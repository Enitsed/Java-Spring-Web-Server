package kr.co.crewmate.api.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.crewmate.api.model.Files;
import kr.co.crewmate.core.dao.BaseDao;

/**
 * 파일 데이터베이스 처리를 위한 DAO
 * @author 정슬기
 *
 */
@Repository
public class FileDao extends BaseDao {
	
	/**
	 * 파일 등록 처리
	 * @param p
	 * @return
	 */
	public int insertFiles(Files p){
		return super.insert("File.insertFiles", p);
	}
	
	/**
	 * 파일삭제 처리
	 * @param p
	 * @return
	 */
	public int deleteFiles(Files p) {
		return super.delete("File.deleteFiles", p);
	}

	/**
	 * 파일목록 정보 반환 처리
	 * @param p
	 * @return
	 */
	public List<Files> selectFilesList(Files p){
		return super.selectList("File.selectFiles", p);
	}

	/**
	 * 파일 정보 반환 처리
	 * @param p
	 * @return
	 */
	public Files selectFiles(Files p){
		return (Files) super.selectOne("File.selectFiles", p);
	}
	
}
