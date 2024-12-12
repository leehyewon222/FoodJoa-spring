package com.foodjoa.member.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.member.vo.RecentViewVO;
import com.foodjoa.member.vo.MemberVO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSession sqlSession;

	public int selectRecentCount(RecentViewVO recentViewVO) {

		return sqlSession.selectOne("mapper.recentView.selectRecentCount", recentViewVO);
	}

	public int insertRecentRecipe(RecentViewVO recentViewVO) {

		return sqlSession.insert("mapper.recentView.insertRecentView", recentViewVO);
	}

	public int insertMember(MemberVO memberVO) {

		return sqlSession.insert("mapper.member.insertMember", memberVO);

	}

	public boolean isUserExists(String userId) {

		int count = sqlSession.selectOne("mapper.member.isUserExists", userId); // 수정된 코드
		return count > 0;
	}
}
