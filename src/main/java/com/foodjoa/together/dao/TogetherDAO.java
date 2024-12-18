package com.foodjoa.together.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.together.vo.TogetherVO;

@Repository
public class TogetherDAO {

	@Autowired
	private SqlSession sqlSession;

	public TogetherVO selectTogether(int _no) {
		return sqlSession.selectOne("mapper.together.selectTogether", _no);
	}
}
