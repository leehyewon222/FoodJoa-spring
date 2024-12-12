package com.foodjoa.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.member.vo.MemberVO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public ArrayList<Integer> selectCountOrderDelivered(String id){
		return ((MemberDAO) sqlSession).selectCountOrderDelivered("mapper.member.memberResultMap,memberVO");
		
	}

	public static MemberVO getMemberProfile(String userId) {
		return null;
	}

	public MemberVO selectMember(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
