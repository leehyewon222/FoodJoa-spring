package com.foodjoa.mealkit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodjoa.mealkit.dao.MealkitDAO;
import com.foodjoa.mealkit.vo.MealkitReviewVO;
import com.foodjoa.mealkit.vo.MealkitVO;

@Service
public class MealkitService {
	
	@Autowired
	private MealkitDAO mealkitDAO;
	@Autowired
	private MealkitVO mealkitVO;
	
	public List<Map<String, Object>> selectMealkitsList(int category) {
		mealkitVO = new MealkitVO();
		mealkitVO.setCategory(category);
		
		return mealkitDAO.selectMealkitsList(mealkitVO);
	}

	public String getCategoryName(int category) {

		String strCategory = "";
		
		if(category == 0){ strCategory = "전체 밀키트 게시글"; }
		else if (category == 1){ strCategory = "한식 밀키트 게시글"; }
		else if (category == 2){ strCategory = "일식 밀키트 게시글"; }
		else if (category == 3){ strCategory = "중식 밀키트 게시글"; }
		else if (category == 4){ strCategory = "양식 밀키트 게시글"; }
		
		return strCategory;
	}

	public Map<String, Object> getPagingData(List<Map<String, Object>> mealkitsList, int nowPage, int nowBlock) {
		
		Map<String, Object> pageData = new HashMap<String,Object>();
		
		int numPerPage = 5;
		int pagePerBlock = 3;
		int totalRecord = 0;
		int totalPage = 0;
		int totalBlock = 0;
		int beginPerPage = 0;
		
		totalRecord = mealkitsList.size();
		
		beginPerPage = nowPage * numPerPage;
		totalPage = (int)Math.ceil((double)totalRecord / numPerPage);
		totalBlock = (int)Math.ceil((double)totalPage / pagePerBlock);
		
		pageData.put("numPerPage", numPerPage);
		pageData.put("pagePerBlock", pagePerBlock);
		pageData.put("totalRecord", totalRecord);
		pageData.put("totalPage", totalPage);
		pageData.put("totalBlock", totalBlock);
		pageData.put("beginPerPage", beginPerPage);
		
		return pageData;
	}

	public MealkitVO selectMealkitInfo(int no) {
		return mealkitDAO.selectMealkitInfo(no);
	}

	public List<Object> selectReviewInfo(int no) {
		mealkitVO = new MealkitVO();
		mealkitVO.setNo(no);
		
		return mealkitDAO.selectReviewInfo(mealkitVO);
	}

	public ArrayList<HashMap<String, Object>> getPurchaseMealkits(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
