package com.foodjoa.recipe.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foodjoa.recipe.vo.RecipeReviewVO;
import com.foodjoa.recipe.vo.RecipeVO;
import com.foodjoa.recipe.vo.RecipeWishListVO;

@Repository
public class RecipeDAO {

	@Autowired
	private SqlSession sqlSession;

	/*
	 * recipe
	 */
	public List<RecipeVO> selectRecipes(RecipeVO recipeVO) {
		return sqlSession.selectList("mapper.recipe.selectRecipes", recipeVO);
	}

	public List<RecipeVO> selectRecipesById(String userId) {
		return sqlSession.selectList("mapper.recipe.selectRecipesById", userId);
	}
	
	public RecipeVO selectRecipe(RecipeVO recipeVO) {
		return sqlSession.selectOne("mapper.recipe.selectRecipe", recipeVO);
	}
	
	public RecipeVO selectRecentRecipe() {
		return sqlSession.selectOne("mapper.recipe.selectRecentRecipe");
	}
	
	public int updateRecipeViews(int no) {
		return sqlSession.update("mapper.recipe.updateRecipeViews", no);
	}
	
	public int insertRecipe(RecipeVO recipeVO) {
		return sqlSession.update("mapper.recipe.insertRecipe", recipeVO);
	}

	public int updateRecipe(RecipeVO recipeVO) {
		return sqlSession.update("mapper.recipe.updateRecipe", recipeVO);
	}
	
	public int deleteRecipe(int no) {
		return sqlSession.delete("mapper.recipe.deleteRecipe", no);
	}

	public List<RecipeVO> selectSearchedRecipes(Map<String, String> params) {
		return sqlSession.selectList("mapper.recipe.selectSearchedRecipes", params);
	}

	/*
	 * recipe review
	 */	
	public List<RecipeReviewVO> selectReviewsByRecipeNo(RecipeReviewVO reviewVO) {
		return sqlSession.selectList("mapper.recipeReview.selectReviewsByRecipeNo", reviewVO);
	}

	public List<RecipeReviewVO> selectReviewsById(String userId) {
		return sqlSession.selectList("mapper.recipeReview.selectReviewsById", userId);
	}

	public RecipeReviewVO selectRecipeReview(int _no) {
		return sqlSession.selectOne("mapper.recipeReview.selectRecipeReview", _no);
	}
	
	public int selectReviewCount(Map<String, String> params) {
		return sqlSession.selectOne("mapper.recipeReview.selectReviewCount", params);
	}

	public int insertReview(RecipeReviewVO reviewVO) {
		return sqlSession.insert("mapper.recipeReview.insertReview", reviewVO);
	}

	public int updateRecipeReview(RecipeReviewVO review) {
		return sqlSession.delete("mapper.recipeReview.updateRecipeReview", review);
	}

	public int deleteRecipeReview(RecipeReviewVO recipeReviewVO) {
		return sqlSession.delete("mapper.recipeReview.deleteRecipeReview", recipeReviewVO);
	}

	/*
	 * wishlist
	 */
	public int selectWishlistCount(HashMap<String, String> params) {
		return sqlSession.selectOne("mapper.recipeWishlist.selectWishlistCount", params);
	}

	public int insertWishlist(HashMap<String, String> params) {
		return sqlSession.insert("mapper.recipeWishlist.insertWishlist", params);
	}
	
	public List<RecipeWishListVO> selectWishListById(String userId) {
		return sqlSession.selectList("mapper.recipeWishlist.selectWishListById", userId);
	}

	public int deleteWishlist(int _no) {
		return sqlSession.delete("mapper.recipeWishlist.deleteWishlist", _no);
	}

	public List<RecipeWishListVO> selectRecentById(String userId) {
		return sqlSession.selectList("mapper.recentViewRecipe.selectRecentById", userId);
	}
}
