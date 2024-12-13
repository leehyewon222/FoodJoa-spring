package com.foodjoa.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodjoa.community.dao.CommunityDAO;
import com.foodjoa.community.vo.CommunityVO;

import lombok.Data;

@Service
@Transactional
public class CommunityService {

	@Autowired
	private CommunityDAO communityDAO;
	
	public List<CommunityVO> getCommunities() {

		return communityDAO.selectCommunities();
	}

	public CommunityVO getCommunity(String no) {
		CommunityVO communityVO = new CommunityVO();
		communityVO.setNo(Integer.parseInt(no));
		
		communityDAO.updateCommunityView(communityVO);
		return communityDAO.selectCommunity(communityVO);
	}
	
	public int insertCommunity(String id, String title, String contents) {

		CommunityVO communityVO = new CommunityVO();
		communityVO.setId(id);
		communityVO.setTitle(title);
		communityVO.setContents(contents);
		
		return communityDAO.insertCommunity(communityVO);
	}

	public int updateCommunity(CommunityVO communityVO) {
		
		return communityDAO.updateCommunity(communityVO);
	}

	public int deleteCommunity(String no) {
		int _no = Integer.parseInt(no);
		
		int result = communityDAO.deleteCommunity(_no);
		return result;
	}

	public List<CommunityVO> getSearchedCommunity(String key, String word) {
		return communityDAO.selectSearchedCommunities(key, word);
	}

}