package com.foodjoa.recipe.service;

import java.io.File;
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

		recipeDAO.updateRecipeViews(recipeVO);
		
		RecipeReviewVO reviewVO = new RecipeReviewVO();
		reviewVO.setRecipeNo(Integer.parseInt(no));
		
		List<RecipeReviewVO> reviews = recipeDAO.selectReviewsByRecipeNo(reviewVO);
		
		recipeInfo.put("reviews", reviews);
		
		RecentViewVO recentViewVO = new RecentViewVO();
		recentViewVO.setNo(Integer.parseInt(no));
		recentViewVO.setId(userId);
		recentViewVO.setType(0);
		
		int countResult = memberDAO.selectRecentCount(recentViewVO);
		
		if (countResult <= 0)
			memberDAO.insertRecentRecipe(recentViewVO);
		
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
		
		int no = recipeDAO.selectRecentRecipe(recipeVO).getNo();
		
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
		
		recipeVO.setThumbnail(originalFileName);
		
		int result = recipeDAO.updateRecipe(recipeVO);
		
		if (result <= 0) {
			FileIOController.deleteFile(tempPath, originalFileName);
			
			return result;
		}
		
		int no = recipeVO.getNo();
		
		String destinationPath = imagesPath + "recipe" + File.separator + "thumbnails" 
				+ File.separator + no + File.separator;
		
		FileIOController.deleteFile(destinationPath, originThumbnail);
		FileIOController.moveFile(tempPath, destinationPath, originalFileName);
		
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
}
