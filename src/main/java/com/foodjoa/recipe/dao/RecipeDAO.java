package com.foodjoa.recipe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.recipe.vo.RecipeVO;

@Repository
public class RecipeDAO {

	@Autowired
	private SqlSession sqlSession;

	public List<Map<String, Object>> selectRecipesWithAvgRating(RecipeVO recipeVO) {
		
		List<Map<String, Object>> recipe = sqlSession.selectList("mapper.recipe.selectRecipesWithAvgRating", recipeVO);
		
		System.out.println("?? : " + recipe.size());
		
		return recipe;
	}
}
