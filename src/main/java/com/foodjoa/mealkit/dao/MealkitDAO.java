package com.foodjoa.mealkit.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.mealkit.vo.MealkitReviewVO;
import com.foodjoa.mealkit.vo.MealkitVO;

@Repository
public class MealkitDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<Map<String, Object>> selectMealkitsList(MealkitVO mealkitVO){
		return sqlSession.selectList("mapper.mealkit.selectMealkitsList", mealkitVO);
	}

	public MealkitVO selectMealkitInfo(int no) {
		return sqlSession.selectOne("mapper.mealkit.selectMealkitInfo", no);
	}

	public List<Object> selectReviewsInfo(MealkitVO mealkitVO) {
		return sqlSession.selectList("mapper.mealkitReview.selectReviewsInfo", mealkitVO);
	}

	public List<Map<String, Object>> selectMyMealkitsList(MealkitVO mealkitVO) {
		return sqlSession.selectList("mapper.mealkit.selectMyMealkitsList", mealkitVO);
	}

	public MealkitReviewVO selectMyReviewInfo(int no) {
		return sqlSession.selectOne("mapper.mealkit.selectMyReviewInfo", no);
	}

	public int deleteMealkit(int no) {
		return sqlSession.delete("mapper.mealkit.deleteMealkit", no);
	}

	public List<Map<String, Object>> selectSearchList(String key, String word) {
		Map<String, Object> params = new HashMap<>();
	    params.put("key", key);
	    params.put("word", word);
	    
		return sqlSession.selectList("mapper.mealkit.selectSearchList", params);
	}

}
