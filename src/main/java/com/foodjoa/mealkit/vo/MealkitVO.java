package com.foodjoa.mealkit.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Data
@Component
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
    
	private float averageRating;
	private int reviewCount;
	
	private MemberVO memberVO;
}
