package com.foodjoa.mealkit.vo;

import java.sql.Timestamp;

public class MealkitVO {
	
	private int no;
    private String id;
    private String title;
    private String contents;
    private int category;
    private String price;
    private int stock;
    private String pictures;
    private String orders;
    private String origin;
    private int views;
    private int soldout;
    private Timestamp postDate;
    
    public MealkitVO() {}
    
    // postDate 없는 생성자 
	public MealkitVO(int no, String id, String title, String contents, int category, String price, int stock,
			String pictures, String orders, String origin, int views, int soldout) {
		
		this.no = no;
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.category = category;
		this.price = price;
		this.stock = stock;
		this.pictures = pictures;
		this.orders = orders;
		this.origin = origin;
		this.views = views;
		this.soldout = soldout;
	}

	public MealkitVO(int no, String id, String title, String contents, int category, String price, int stock, 
			String pictures, String orders, String origin, int views, int soldout, Timestamp postDate) {

		this(no, id, title, contents, category, price, stock, pictures, orders, origin, views, soldout);
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getSoldout() {
		return soldout;
	}

	public void setSoldout(int soldout) {
		this.soldout = soldout;
	}

	public Timestamp getPostDate() {
		return postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}
}
