package com.foodjoa.mealkit.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.community.vo.NoticeVO;
import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Data
@Component
public class MealkitOrderVO {
	
	private int no;
    private String id;
    private int mealkitNo;
    private String address;
    private int quantity;
    private int delivered;
    private int refund;
    private Timestamp postDate;
    
    private MealkitVO mealkitVO;
    private MemberVO memberVO;
}
