package com.foodjoa.community.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Data
@Component
public class CommunityVO {
	
	private int no;
	private String id;
	private String title;
	private String contents;
	private int views;
	private Timestamp postDate;
	
	private MemberVO memberVO;
}


