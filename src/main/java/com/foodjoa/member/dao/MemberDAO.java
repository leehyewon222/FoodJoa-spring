package com.foodjoa.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.member.vo.RecentViewVO;
import com.foodjoa.mealkit.vo.MealkitOrderVO;
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
	
	public ArrayList<Integer> selectCountOrderDelivered(String id){
		
		ArrayList<Integer> countOrderDelivered = new ArrayList<Integer>();
		
		MealkitOrderVO orderVO = new MealkitOrderVO();
		orderVO.setId(id);
		
		for (int i = 0; i < 3; i++) {
			orderVO.setDelivered(i);
			
			int result = sqlSession.selectOne("mapper.mealkitOrder.selectCountOrderDelivered", orderVO);
			
			countOrderDelivered.add(result);
		}
		
		return countOrderDelivered;
	}

	public ArrayList<Integer> selectCountOrderSended(String id) {
		
		ArrayList<Integer> countOrderSended = new ArrayList<Integer>();
		
		MealkitOrderVO orderVO = new MealkitOrderVO();
		orderVO.setId(id);
		
		for (int i = 0; i < 3; i++) {
			orderVO.setDelivered(i);
			
			int result = sqlSession.selectOne("mapper.mealkitOrder.selectCountOrderSended", orderVO);
			
			countOrderSended.add(result);
		}
		
		return countOrderSended;
	}

	public static MemberVO getMemberProfile(String userId) {
		return null;
	}

	public MemberVO selectMember(String id) {
		return sqlSession.selectOne("mapper.member.selectMember", id);
	}
}
