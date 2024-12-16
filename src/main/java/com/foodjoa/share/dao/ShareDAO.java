package com.foodjoa.share.dao;

import java.util.List;

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
}
