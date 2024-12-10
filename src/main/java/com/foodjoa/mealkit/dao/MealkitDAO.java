package com.foodjoa.mealkit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.mealkit.vo.MealkitVO;

@Repository
public class MealkitDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<Map<String, Object>> selectMealkitsList(MealkitVO mealkitVO){
		return sqlSession.selectList("mapper.mealkit.selectMealkitsList", mealkitVO);
	}
}
