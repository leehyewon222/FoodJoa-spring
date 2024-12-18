package com.foodjoa.community.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.community.vo.CommunityVO;

@Repository
public class CommunityDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List<CommunityVO> selectCommunities() {

		List<CommunityVO> communities = sqlSession.selectList("mapper.community.selectCommunities");
		
		return communities;
	}

	public int updateCommunityView(CommunityVO communityVO) {
		return sqlSession.update("mapper.community.updateCommunityView", communityVO);
	}
	
	public CommunityVO selectCommunity(CommunityVO communityVO) {
		return sqlSession.selectOne("mapper.community.selectCommunity", communityVO);
	}

	public int insertCommunity(CommunityVO communityVO) {
		return sqlSession.insert("mapper.community.insertCommunity", communityVO);
	}

	public int updateCommunity(CommunityVO communityVO) {
		return sqlSession.update("mapper.community.updateCommunity", communityVO);
	}

	public int deleteCommunity(int no) {
		return sqlSession.delete("mapper.community.deleteCommunity", no);
	}

	public List<CommunityVO> selectSearchedCommunities(String key, String word) {
		
		Map<String, String> params = new HashMap<String, String>();

		params.put("key", key);
		params.put("word", word);
		
		return sqlSession.selectList("mapper.community.selectSearchedCommunities", params);
	}
}
