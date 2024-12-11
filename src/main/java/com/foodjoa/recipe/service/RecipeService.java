package com.foodjoa.recipe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodjoa.member.dao.MemberDAO;
import com.foodjoa.member.vo.RecentViewVO;
import com.foodjoa.recipe.dao.RecipeDAO;
import com.foodjoa.recipe.vo.RecipeReviewVO;
import com.foodjoa.recipe.vo.RecipeVO;

@Service
@Transactional
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
	
	public HashMap<String, Object> processRecipeRead(String no, String userId) {
		
		HashMap<String, Object> recipeInfo = new HashMap<String, Object>();
		
		RecipeVO recipeVO = new RecipeVO();
		recipeVO.setNo(Integer.parseInt(no));
		
		RecipeVO selectedRecipe = recipeDAO.selectRecipe(recipeVO);
		
		if (selectedRecipe == null) {
			return recipeInfo;
		}
		
		recipeInfo.put("recipe", selectedRecipe);

		recipeDAO.updateRecipeView(recipeVO);
		
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
}
