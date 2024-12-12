package com.foodjoa.recipe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.recipe.vo.RecipeReviewVO;
import com.foodjoa.recipe.vo.RecipeVO;

@Repository
public class RecipeDAO {

	@Autowired
	private SqlSession sqlSession;

	public List<RecipeVO> selectRecipes(RecipeVO recipeVO) {
		return sqlSession.selectList("mapper.recipe.selectRecipes", recipeVO);
	}
	
	public RecipeVO selectRecipe(RecipeVO recipeVO) {
		return sqlSession.selectOne("mapper.recipe.selectRecipe", recipeVO);
	}
	
	public RecipeVO selectRecentRecipe(RecipeVO recipeVO) {
		return sqlSession.selectOne("mapper.recipe.selectRecentRecipe", recipeVO);
	}
	
	public List<RecipeReviewVO> selectReviewsByRecipeNo(RecipeReviewVO reviewVO) {
		return sqlSession.selectList("mapper.recipeReview.selectReviewsByRecipeNo", reviewVO);
	}
	
	public int updateRecipeViews(RecipeVO recipeVO) {
		return sqlSession.update("mapper.recipe.updateRecipeViews", recipeVO);
	}
	
	public int insertRecipe(RecipeVO recipeVO) {
		return sqlSession.update("mapper.recipe.insertRecipe", recipeVO);
	}
}
