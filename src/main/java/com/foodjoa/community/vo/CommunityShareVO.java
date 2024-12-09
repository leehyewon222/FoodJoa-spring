package com.foodjoa.community.vo;

import java.sql.Timestamp;

public class CommunityShareVO {
	private int no;
	private String id;
	private String thumbnail;
	private String title;
	private String contents;
	private double lat;
	private double lng;
	private int type;
	private int views;
	private Timestamp postDate;
	
	public CommunityShareVO() {

	}

	public CommunityShareVO(int no, String id, String thumbnail, String title, String contents, double lat, double lng,
			int type, int views, Timestamp postDate) {

		this(no, id, thumbnail, title, contents, lat, lng, type, views);
		this.postDate = postDate;
	}

	public CommunityShareVO(int no, String id, String thumbnail, String title, String contents, double lat, double lng,
			int type, int views) {

		this(id, thumbnail, title, contents, lat, lng, type, views);
		this.no = no;
	}

	public CommunityShareVO(String id, String thumbnail, String title, String contents, double lat, double lng,
			int type, int views) {

		this.id = id;
		this.thumbnail = thumbnail;
		this.title = title;
		this.contents = contents;
		this.lat = lat;
		this.lng = lng;
		this.type = type;
		this.views = views;
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

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

