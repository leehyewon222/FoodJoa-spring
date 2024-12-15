package com.foodjoa.community.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.community.vo.NoticeVO;

@Repository
public class NoticeDAO {

	@Autowired
	private SqlSession sqlSession;

	public List<NoticeVO> selectNotices() {
		return sqlSession.selectList("mapper.notice.selectNotices");
	}

	public int updateNoticeView(NoticeVO noticeVO) {
		return sqlSession.update("mapper.notice.updateNoticeView", noticeVO);
	}

	public NoticeVO selectNotice(NoticeVO noticeVO) {
		return sqlSession.selectOne("mapper.notice.selectNotice", noticeVO);
	}

	public int insertNotice(NoticeVO noticeVO) {

		return sqlSession.insert("mapper.notice.insertNotice", noticeVO);
	}

	public int updateNotice(NoticeVO noticeVO) {
		return sqlSession.update("mapper.notice.updateNotice", noticeVO);
	}

//	public int deleteCommunity(int no) {
//		return sqlSession.delete("mapper.community.deleteCommunity", no);
//	}
}
