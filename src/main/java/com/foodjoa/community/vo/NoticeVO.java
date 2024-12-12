package com.foodjoa.community.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class NoticeVO {
	
	private int no;
	private String title;
	private String contents;
	private int views;
	private Timestamp postDate;
}
