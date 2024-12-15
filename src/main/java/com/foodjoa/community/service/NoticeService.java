package com.foodjoa.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodjoa.community.dao.CommunityDAO;
import com.foodjoa.community.dao.NoticeDAO;
import com.foodjoa.community.vo.CommunityVO;
import com.foodjoa.community.vo.NoticeVO;

import lombok.Data;

@Service
@Transactional
public class NoticeService {

	@Autowired
	private NoticeDAO noticeDAO;

	public List<NoticeVO> getNoticeList() {
		return noticeDAO.selectNotices();
	}

	public NoticeVO getNotice(String no) {
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setNo(Integer.parseInt(no));
		
		noticeDAO.updateNoticeView(noticeVO);
		return noticeDAO.selectNotice(noticeVO);
	}
	
	public int insertNotice(String title, String contents) {

		NoticeVO noticeVO = new NoticeVO();

		noticeVO.setTitle(title);
		noticeVO.setContents(contents);
		
		return noticeDAO.insertNotice(noticeVO);
	}

	public int updateNotice(String nowPage, String nowBlock) {
		
		NoticeVO noticeVO = new NoticeVO();
		
		noticeVO.setContents(nowPage);
		noticeVO.setContents(nowBlock);
		
		return noticeDAO.updateNotice(noticeVO);
	}
//
//	public int deleteCommunity(String no) {
//		int _no = Integer.parseInt(no);
//		
//		int result = communityDAO.deleteCommunity(_no);
//		return result;
//	}
//
//	public List<CommunityVO> getSearchedCommunity(String key, String word) {
//		return communityDAO.selectSearchedCommunities(key, word);
//	}
}