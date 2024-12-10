package com.foodjoa.community.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodjoa.community.dao.CommunityDAO;

@Service
public class CommunityService {

	@Autowired
	private CommunityDAO communityDAO;
	
	public List<Map<String, Object>> getCommunityList() {

		return communityDAO.communityListAll();
	}

}