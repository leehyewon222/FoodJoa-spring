package com.foodjoa.community.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.community.vo.CommunityShareVO;
import com.foodjoa.community.vo.CommunityVO;
import com.foodjoa.member.vo.MemberVO;

@Repository
public class CommunityDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List<Map<String, Object>> communityListAll() {

		List<Map<String, Object>> communities = sqlSession.selectList("mapper.community.selectCommunities");
		
		return communities;
	}


}
