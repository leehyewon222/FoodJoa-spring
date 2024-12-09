package com.foodjoa.recipe.vo;

import java.sql.Timestamp;

public class RecipeWishListVO {

	private int no;
	private String id;
	private int recipeNo;
	private Timestamp choice_date;
	
	public RecipeWishListVO() {
	}

	public RecipeWishListVO(int no, String id, int recipeNo) {
	
		this.no = no;
		this.id = id;
		this.recipeNo = recipeNo;
	}
	

	public RecipeWishListVO(int no, String id, int recipeNo, Timestamp choice_date) {
		
		this.no = no;
		this.id = id;
		this.recipeNo = recipeNo;
		this.choice_date = choice_date;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRecipeNo() {
		return recipeNo;
	}

	public void setRecipeNo(int recipeNo) {
		this.recipeNo = recipeNo;
	}
	
	public Timestamp getChoiceDate() {
		return choice_date;
	}

	public void setChoiceDate(Timestamp choice_date) {
		this.choice_date = choice_date;
	}

}
