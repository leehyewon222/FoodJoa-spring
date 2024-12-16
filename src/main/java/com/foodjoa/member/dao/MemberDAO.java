package com.foodjoa.member.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.member.vo.RecentViewVO;
import com.foodjoa.mealkit.vo.MealkitOrderVO;
import com.foodjoa.mealkit.vo.MealkitVO;

import com.foodjoa.member.vo.MemberVO;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSession sqlSession;		
	
	public List<HashMap<String, Object>> selectSendedMealkit(MealkitVO mealkitvo){
		
		return sqlSession.selectList("mapper.member.selectSendedMealkit", mealkitvo);
	}

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
	
	
	public int deleteMemberById(String readonlyId) {
		
		int count = sqlSession.delete("mapper.member.deleteMemberById", readonlyId);
		return count;
		
	}
	
	public String getProfileFileName(String readonlyId) {
	    return sqlSession.selectOne("mapper.member.getProfileFileName", readonlyId);
	}
	
	
	//----------------------------------------------------
	
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

	public MemberVO selectMember(String id) {
		return sqlSession.selectOne("mapper.member.selectMember", id);
	}

	public int deleteWishRecipe(String userId, int recipeNo) {
	    // 파라미터를 map에 담아 전달
	    Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("recipeNo", recipeNo);

	    return sqlSession.delete("mapper.recipeWishlist.deleteWishRecipe", params);
	}

	public int deleteWishMealkit(String userId, int mealkitNo) {
	    // 파라미터를 map에 담아 전달
	    Map<String, Object> params = new HashMap<>();
	    params.put("userId", userId);
	    params.put("mealkitNo", mealkitNo);

	    return sqlSession.delete("mapper.mealkitWishlist.deleteWishMealkit", params);
	}


	public int updateMember(MemberVO memberVO) {
	    // MyBatis의 update 메서드를 사용하여 업데이트 작업을 수행합니다.
	    int result = sqlSession.update("mapper.member.updateMember", memberVO);
	    return result;
	}
}
