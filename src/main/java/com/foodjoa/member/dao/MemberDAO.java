package com.foodjoa.member.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAO{
	
	@Autowired
	private SqlSession sqlSession;

	public int insertMember(MemberVO memberVO) {
		
		return sqlSession.insert("mapper.member.insertMember", memberVO);
		
	}
	
	public boolean isUserExists(String userId) {
	
		  int count = sqlSession.selectOne("mapper.member.isUserExists", userId); // 수정된 코드
		    return count > 0;
	}
	
	

}