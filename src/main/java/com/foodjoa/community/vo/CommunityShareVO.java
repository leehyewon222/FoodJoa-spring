package com.foodjoa.community.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Data
@Component
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
	
	private MemberVO memberVO;
}

