package com.foodjoa.mealkit.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.community.vo.NoticeVO;
import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Data
@Component
public class MealkitReviewVO {
    
    private int no;
	private String id;
	private int mealkitNo;
	private String pictures;
	private String contents;
	private int rating;
	private Timestamp postDate;
	
	private MealkitVO mealkitVO;
	private MemberVO memberVO;
}
