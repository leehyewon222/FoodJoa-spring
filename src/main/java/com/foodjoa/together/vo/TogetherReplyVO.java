package com.foodjoa.together.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Data
@Component
public class TogetherReplyVO {
	
	private int no;
	private String id;
	private int togetherNo;
	private String contents;
	private Timestamp postDate;
	
	private MemberVO memberVO;
	private TogetherVO togetherVO;
}
