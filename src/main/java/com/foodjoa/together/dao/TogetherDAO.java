package com.foodjoa.together.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TogetherDAO {

	@Autowired
	private SqlSession sqlSession;
}
