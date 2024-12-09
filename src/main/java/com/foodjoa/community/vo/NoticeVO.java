package com.foodjoa.community.vo;

import java.sql.Timestamp;

public class NoticeVO {
	
	private int no;
	private String title;
	private String contents;
	private int views;
	private Timestamp postDate;
	
	public NoticeVO() {
	}

	public NoticeVO(int no, String title, String contents, int views, Timestamp postDate) {

		this(no, title, contents, views);
		this.postDate = postDate;
	}

	public NoticeVO(int no, String title, String contents, int views) {

		this.no = no;
		this.title = title;
		this.contents = contents;
		this.views = views;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Timestamp getPostDate() {
		return postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}
}
