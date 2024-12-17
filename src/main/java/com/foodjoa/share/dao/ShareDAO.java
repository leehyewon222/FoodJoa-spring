package com.foodjoa.share.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.share.vo.ShareVO;

@Repository
public class ShareDAO {

	@Autowired
	private SqlSession sqlSession;

	public List<ShareVO> selectShareList() {
		
		List<ShareVO> shareVO = sqlSession.selectList("mapper.share.selectShareList");
		
		return shareVO;
	}

	public int insertShare(ShareVO shareVO) {
		return sqlSession.insert("mapper.share.insertShare", shareVO);
	}

	public int selectLatestShareNo() {
		return sqlSession.selectOne("mapper.share.selectLatestShareNo");
	}

	public ShareVO selectShare(int _no) {
		return sqlSession.selectOne("mapper.share.selectShare", _no);
	}

	public int updateShare(ShareVO shareVO) {
		return sqlSession.update("mapper.share.updateShare", shareVO);
	}

	public int deleteShare(ShareVO shareVO) {
		return sqlSession.delete("mapper.share.deleteShare", shareVO);
	}

	public List<ShareVO> selectSearchedShares(Map<String, String> params) {
		return sqlSession.selectList("mapper.share.selectSearchedShares", params);
	}
}
