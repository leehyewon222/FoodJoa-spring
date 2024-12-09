package com.foodjoa.member.vo;

import java.sql.Timestamp;

public class MemberVO {

	private String id;
	private String name;
	private String nickname;
	private String phone;
	private String zipcode;
	private String address1;
	private String address2;
	private String profile;
	private Timestamp join_date;
	
	public MemberVO() {
	}

	public MemberVO(String id, String name, String nickname, String phone, String zipcode, String address1,
			String address2, String profile, Timestamp join_date) {

		this(id, name, nickname, phone, zipcode, address1, address2, profile);
		this.join_date = join_date;
	}

	public MemberVO(String id, String name, String nickname, String phone, String zipcode, String address1,
			String address2, String profile) {

		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.phone = phone;
		this.zipcode = zipcode;
		this.address1 = address1;
		this.address2 = address2;
		this.profile = profile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Timestamp getJoin_date() {
		return join_date;
	}

	public void setJoin_date(Timestamp join_date) {
		this.join_date = join_date;
	}
}
