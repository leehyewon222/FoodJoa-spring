package com.foodjoa.mealkit.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class MealkitReviewVO {
    
    private int no;
	private String id;
	private int mealkitNo;
	private String pictures;
	private String contents;
	private int rating;
	private Timestamp postDate;
	
	public MealkitReviewVO() {}
	
	public MealkitReviewVO(int no, String id, int mealkitNo, String pictures, 
			String contents, int rating) {
		
		this.no = no;
		this.id = id;
		this.mealkitNo = mealkitNo;
		this.pictures = pictures;
		this.contents = contents;
		this.rating = rating;
	}

	public MealkitReviewVO(int no, String id, int mealkitNo, String pictures, 
			String contents, int rating, Timestamp postDate) {
		
		this(no, id, mealkitNo, pictures, contents, rating);
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

	public int getMealkitNo() {
		return mealkitNo;
	}

	public void setMealkitNo(int mealkitNo) {
		this.mealkitNo = mealkitNo;
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
