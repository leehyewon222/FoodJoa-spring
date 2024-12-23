package com.foodjoa.recipe.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.member.dao.MemberDAO;
import com.foodjoa.member.vo.RecentViewVO;
import com.foodjoa.recipe.dao.RecipeDAO;
import com.foodjoa.recipe.vo.RecipeReviewVO;
import com.foodjoa.recipe.vo.RecipeVO;

import Common.FileIOController;
import Common.StringParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RecipeService {

	@Autowired
	private RecipeDAO recipeDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	public List<RecipeVO> getRecipes(String category) {
		
		RecipeVO recipeVO = new RecipeVO();
		recipeVO.setCategory(Integer.parseInt(category));
		
		return recipeDAO.selectRecipes(recipeVO);
	}

	public List<RecipeVO> getRecipesById(String userId) {
		return recipeDAO.selectRecipesById(userId);
	}

	public RecipeVO getRecipe(String no) {

		RecipeVO recipeVO = new RecipeVO();
		recipeVO.setNo(Integer.parseInt(no));
		
		return recipeDAO.selectRecipe(recipeVO);
	}
	
	public HashMap<String, Object> processRecipeRead(String no, String userId) {
		
		HashMap<String, Object> recipeInfo = new HashMap<String, Object>();

		RecipeVO recipeVO = new RecipeVO();
		recipeVO.setNo(Integer.parseInt(no));
		RecipeVO selectedRecipe = recipeDAO.selectRecipe(recipeVO);
		
		if (selectedRecipe == null) {
			return recipeInfo;
		}
		
		recipeInfo.put("recipe", selectedRecipe);

		recipeDAO.updateRecipeViews(Integer.parseInt(no));
		
		RecipeReviewVO reviewVO = new RecipeReviewVO();
		reviewVO.setRecipeNo(Integer.parseInt(no));
		
		List<RecipeReviewVO> reviews = recipeDAO.selectReviewsByRecipeNo(reviewVO);
		
		recipeInfo.put("reviews", reviews);
		
		if (userId != null) {		
			RecentViewVO recentViewVO = new RecentViewVO();
			recentViewVO.setId(userId);
			recentViewVO.setItemNo(Integer.parseInt(no));
			recentViewVO.setType(0);
			
			int countResult = memberDAO.selectRecentCount(recentViewVO);
			
			if (countResult <= 0)
				memberDAO.insertRecentView(recentViewVO);
			
		}
		
		return recipeInfo;
	}
	
	public int processRecipeWrite(RecipeVO recipeVO, MultipartHttpServletRequest multipartRequest)
			throws Exception {
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;

		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }

		Iterator<String> fileNames = multipartRequest.getFileNames();
		String originalFileName = "";
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			originalFileName = mFile.getOriginalFilename();
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + originalFileName));
			}			
		}
		
		recipeVO.setThumbnail(originalFileName);
		
		int result = recipeDAO.insertRecipe(recipeVO);
		
		if (result <= 0) {
			FileIOController.deleteFile(tempPath, originalFileName);
			
			return 0;
		}
		
		int no = recipeDAO.selectRecentRecipe().getNo();
		
		String destinationPath = imagesPath + "recipe" + File.separator + "thumbnails" 
				+ File.separator + no + File.separator;
		
		FileIOController.moveFile(tempPath, destinationPath, originalFileName);
		
		return no;
	}

	public int processRecipeUpdate(RecipeVO recipeVO, String originThumbnail,
			 MultipartHttpServletRequest multipartRequest)
			throws Exception {
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;

		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }

		Iterator<String> fileNames = multipartRequest.getFileNames();
		String originalFileName = "";
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			originalFileName = mFile.getOriginalFilename();
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + originalFileName));
			}			
		}
		
		boolean flag = false;
		if (originalFileName == null || originalFileName.length() <= 0 || originalFileName.equals("")) {
			flag = true;
			
			originalFileName = originThumbnail;
		}
		
		recipeVO.setThumbnail(originalFileName);
		
		int result = recipeDAO.updateRecipe(recipeVO);
		
		if (result <= 0) {
			FileIOController.deleteFile(tempPath, originalFileName);
			
			return result;
		}
		
		if (!flag) {
			String destinationPath = imagesPath + "recipe" + File.separator + "thumbnails" 
					+ File.separator + recipeVO.getNo() + File.separator;
			
			FileIOController.deleteFile(destinationPath, originThumbnail);
			FileIOController.moveFile(tempPath, destinationPath, originalFileName);
		}
		
		return result;
	}
	
	public int deleteRecipe(String no) throws Exception {
		
		int result = recipeDAO.deleteRecipe(Integer.parseInt(no));
		
		if (result > 0) {
			String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
					+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
					+ File.separator + "resources" + File.separator + "images" + File.separator;
			
			FileIOController.deleteDirectory(imagesPath + "recipe" + File.separator + "thumbnails" + File.separator + no);
		}
		
		return result;
	}

	public List<RecipeVO> getSearchedRecipeList(String category, String key, String word) {

		Map<String, String> params = new HashMap<String, String>();
		
		params.put("category", category);
		params.put("key", key);
		params.put("word", word);
		
		return recipeDAO.selectSearchedRecipes(params);
	}

	public int checkReview(String recipeNo, String id) {
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("recipeNo", recipeNo);
		params.put("id", id);
		
		return recipeDAO.selectReviewCount(params);
	}

	public int processReviewWrite(RecipeReviewVO reviewVO, MultipartHttpServletRequest multipartRequest) 
			throws Exception {
		
		reviewVO.setContents(StringParser.escapeHtml(reviewVO.getContents()));
		
		int result = recipeDAO.insertReview(reviewVO);
		
		if (result <= 0) return result;
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;
		
		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }
		
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + mFile.getOriginalFilename()));
			}
		}
		
		List<String> pictures = StringParser.splitString(reviewVO.getPictures());
		String destinationPath = imagesPath + "recipe" + File.separator + "reviews" + File.separator
				+ reviewVO.getRecipeNo() + File.separator + reviewVO.getId();
		
		for (String picture : pictures) {
			FileIOController.moveFile(tempPath, destinationPath, picture);
		}
		
		return result;
	}

	public int addWishlist(String id, String recipeNo) {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("id", id);
		params.put("recipeNo", recipeNo);
		
		if (recipeDAO.selectWishlistCount(params) > 0) return 2;
		
		return recipeDAO.insertWishlist(params);
	}

	public RecipeReviewVO getRecipeReview(String no) {
		return recipeDAO.selectRecipeReview(Integer.parseInt(no));
	}

	public int processReviewUpdate(RecipeReviewVO review, MultipartHttpServletRequest multipartRequest)
			throws Exception {
		
		String originSelectedPictures = multipartRequest.getParameter("origin_selected_pictures");
		String pictures = review.getPictures();
		
		review.setPictures(originSelectedPictures + pictures);
		review.setContents(StringParser.escapeHtml(review.getContents()));
		
		int result = recipeDAO.updateRecipeReview(review);
		
		if (result <= 0) return result;		
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		String tempPath = imagesPath + "temp" + File.separator;
		String destinationPath = imagesPath + "recipe" + File.separator +
				"reviews" + File.separator + review.getRecipeNo() + File.separator + review.getId();
		
		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }
		
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + mFile.getOriginalFilename()));
			}
		}
		
		List<String> originFileNames = StringParser.splitString(multipartRequest.getParameter("origin_pictures"));
		List<String> originSelectedFileNames = StringParser.splitString(originSelectedPictures);
		
		for (String fileName : originFileNames) {
			if (!originSelectedFileNames.contains(fileName)) {
				FileIOController.deleteFile(destinationPath, fileName);
			}
		}
		
		List<String> picturesList = StringParser.splitString(pictures);
		
        for (String picture : picturesList) {
    		FileIOController.moveFile(tempPath, destinationPath, picture);
        }
		
		return result;
	}

	public int processReviewDelete(String no, String recipeNo, String id) throws Exception {
		
		RecipeReviewVO recipeReviewVO = new RecipeReviewVO();
		recipeReviewVO.setNo(Integer.parseInt(no));
		recipeReviewVO.setId(id);
		
		int result = recipeDAO.deleteRecipeReview(recipeReviewVO);
		
		if (result > 0) {
			String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
					+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
					+ File.separator + "resources" + File.separator + "images" + File.separator;
			
			String destinationPath = imagesPath  + "recipe" + File.separator +
					"reviews" + File.separator + String.valueOf(recipeNo) + File.separator + id;
			
			FileIOController.deleteDirectory(destinationPath);
		}
		
		return result;
	}
}
