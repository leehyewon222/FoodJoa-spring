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

	public int updateNotice(String title, String contents, String no) {
		
		NoticeVO noticeVO = new NoticeVO();
		
		noticeVO.setTitle(title);
		noticeVO.setContents(contents);
		noticeVO.setNo(Integer.parseInt(no));
		
		return noticeDAO.updateNotice(noticeVO);
	}

	public int processNoticeDelete(int no) {

		return noticeDAO.deleteNotice(no);
	}
}