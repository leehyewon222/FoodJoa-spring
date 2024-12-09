package com.foodjoa.member.vo;

import java.sql.Timestamp;

public class RecentViewVO {
	
	private int no;
	private String id;
	private int type;
	private int item_no;
	private Timestamp view_date;
	
	public RecentViewVO() {
	}

	public RecentViewVO(int no, String id, int type, int item_no, Timestamp view_date) {

		this(no, id, type, item_no);
		this.view_date = view_date;
	}

	public RecentViewVO(int no, String id, int type, int item_no) {
		
		this.no = no;
		this.id = id;
		this.type = type;
		this.item_no = item_no;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getItem_no() {
		return item_no;
	}

	public void setItem_no(int item_no) {
		this.item_no = item_no;
	}

	public Timestamp getView_date() {
		return view_date;
	}

	public void setView_date(Timestamp view_date) {
		this.view_date = view_date;
	}
}
