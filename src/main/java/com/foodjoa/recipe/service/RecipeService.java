package com.foodjoa.recipe.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodjoa.recipe.dao.RecipeDAO;
import com.foodjoa.recipe.vo.RecipeVO;

@Service
public class RecipeService {

	@Autowired
	private RecipeDAO recipeDAO;
	
	public List<Map<String, Object>> getRecipesWithAvgRating(String category) {
		
		RecipeVO recipeVO = new RecipeVO();
		recipeVO.setCategory(Integer.parseInt(category));
		
		return recipeDAO.selectRecipesWithAvgRating(recipeVO);
	}
}
