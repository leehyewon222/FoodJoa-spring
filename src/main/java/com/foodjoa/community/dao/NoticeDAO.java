package com.foodjoa.community.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.community.vo.CommunityShareVO;
import com.foodjoa.community.vo.CommunityVO;
import com.foodjoa.community.vo.NoticeVO;
import com.foodjoa.member.vo.MemberVO;

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

	//	public int updateCommunity(CommunityVO communityVO) {
//		return sqlSession.update("mapper.community.updateCommunity", communityVO);
//	}
//
//	public int deleteCommunity(int no) {
//		return sqlSession.delete("mapper.community.deleteCommunity", no);
//	}
//
//	public List<CommunityVO> selectSearchedCommunities(String key, String word) {
//		
//		Map<String, String> params = new HashMap<String, String>();
//
//		params.put("key", key);
//		params.put("word", word);
//		
//		return sqlSession.selectList("mapper.community.selectSearchedCommunities", params);
//	}

	

}
