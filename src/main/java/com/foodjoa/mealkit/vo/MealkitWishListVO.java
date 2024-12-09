package com.foodjoa.mealkit.vo;

import java.sql.Timestamp;

public class MealkitWishListVO {
	
    private int no;
	private String id;
	private int mealkitNo;
    private Timestamp choice_date;
    
    public MealkitWishListVO() {
	}
	
	public MealkitWishListVO(int no, String id, int mealkitNo) {
		this.no = no;
		this.id = id;
		this.mealkitNo = mealkitNo;
	}

	public MealkitWishListVO(int no, String id, int mealkitNo, Timestamp choice_date) {
		this.no = no;
		this.id = id;
		this.mealkitNo = mealkitNo;
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

	public int getMealkitNo() {
		return mealkitNo;
	}

	public void setMealkitNo(int mealkitNo) {
		this.mealkitNo = mealkitNo;
	}

	public Timestamp getChoiceDate() {
		return choice_date;
	}

	public void setChoiceDate(Timestamp choice_date) {
		this.choice_date = choice_date;
	}

}
