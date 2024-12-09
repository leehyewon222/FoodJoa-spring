package com.foodjoa.recipe.vo;

import java.sql.Timestamp;

public class RecipeReviewVO {

	private int no;
	private String id;
	private int recipeNo;
	private String pictures;
	private String contents;
	private int rating;
	private Timestamp postDate;

	public RecipeReviewVO() {
	}
	
	public RecipeReviewVO(int no, String id, int recipeNo, String pictures, String contents, int rating) {
		
		this.no = no;
		this.id = id;
		this.recipeNo = recipeNo;
		this.pictures = pictures;
		this.contents = contents;
		this.rating = rating;
	}

	public RecipeReviewVO(int no, String id, int recipeNo, String pictures, String contents, int rating,
			Timestamp postDate) {

		this(no, id, recipeNo, pictures, contents, rating);
		this.postDate = postDate;
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

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Timestamp getPostDate() {
		return postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}
}
