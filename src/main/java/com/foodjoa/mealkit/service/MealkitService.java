package com.foodjoa.mealkit.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.foodjoa.mealkit.dao.MealkitDAO;
import com.foodjoa.mealkit.vo.MealkitReviewVO;
import com.foodjoa.mealkit.vo.MealkitVO;

import Common.FileIOController;

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

	public MealkitVO selectMealkitInfo(int no) {
		return mealkitDAO.selectMealkitInfo(no);
	}

	public List<Object> selectReviewsInfo(int no) {
		mealkitVO = new MealkitVO();
		mealkitVO.setNo(no);
		
		return mealkitDAO.selectReviewsInfo(mealkitVO);
	}

	public List<Map<String, Object>> selectMyMealkitsList(String id) {
		mealkitVO = new MealkitVO();
		mealkitVO.setId(id);
		
		return mealkitDAO.selectMyMealkitsList(mealkitVO);
	}

	public MealkitReviewVO selectMyReviewInfo(int no) {
		return mealkitDAO.selectMyReviewInfo(no);
	}

	public int deleteMealkit(int no) throws Exception {
		int result = mealkitDAO.deleteMealkit(no);
		
		if (result > 0) {
			String path = new ClassPathResource("").getFile().getParentFile().getParent()
					+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
					+ File.separator + "resources" + File.separator + "images" + File.separator + "mealkit" 
					+ File.separator + "thumbnails" + File.separator + no;
			
			String reviewPath = new ClassPathResource("").getFile().getParentFile().getParent()
					+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
					+ File.separator + "resources" + File.separator + "images" + File.separator + "mealkit" 
					+ File.separator + "reviews" + File.separator + no;
			
			FileIOController.deleteDirectory(path);
			FileIOController.deleteDirectory(reviewPath);
		}
		
		return result;
	}

	public List<Map<String, Object>> selectSearchList(String key, String word) {
		return mealkitDAO.selectSearchList(key, word);
	}

}
