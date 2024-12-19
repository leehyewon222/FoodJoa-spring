package com.foodjoa.together.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.together.vo.TogetherJoinVO;
import com.foodjoa.together.vo.TogetherReplyVO;
import com.foodjoa.together.vo.TogetherVO;

@Repository
public class TogetherDAO {

	@Autowired
	private SqlSession sqlSession;

	/*
	 * togetherVO
	 */
	public List<TogetherVO> selectTogethers() {
		return sqlSession.selectList("mapper.together.selectTogethers");
	}
	
	public TogetherVO selectTogether(int _no) {
		return sqlSession.selectOne("mapper.together.selectTogether", _no);
	}

	public int insertTogether(TogetherVO togetherVO) {
		return sqlSession.insert("mapper.together.insertTogether", togetherVO);
	}

	public int selectLatestNo() {
		return sqlSession.selectOne("mapper.together.selectLatestNo");
	}

	public void updateTogetherViews(int _no) {
		sqlSession.update("mapper.together.updateTogetherViews", _no);
	}

	public int updateTogether(TogetherVO togetherVO) {
		return sqlSession.update("mapper.together.updateTogether", togetherVO);
	}

	public int deleteTogether(int _no) {
		return sqlSession.delete("mapper.together.deleteTogether", _no);
	}

	/*
	 * togetherReplyVO
	 */
	public List<TogetherReplyVO> selectReplies(int _no) {
		return sqlSession.selectList("mapper.togetherReply.selectReplies", _no);
	}

	/*
	 * togetherJoinVo
	 */
	public List<TogetherJoinVO> selectJoins() {
		return sqlSession.selectList("mapper.togetherJoin.selectJoins");
	}

	public int selectJoinCount(int _no) {
		return sqlSession.selectOne("mapper.togetherJoin.selectJoinCount", _no);
	}

	public int selectJoinCountById(int no, String id) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("no", no);
		params.put("id", id);

		return sqlSession.selectOne("mapper.togetherJoin.selectJoinCountById", params);
	}

	public int insertJoin(int no, String id) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("no", no);
		params.put("id", id);

		return sqlSession.insert("mapper.togetherJoin.insertJoin", params);
	}
}
