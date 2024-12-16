package com.foodjoa.member.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.community.vo.NoticeVO;
import com.foodjoa.mealkit.vo.MealkitVO;

import lombok.Data;

@Data
@Component
public class MemberVO {

	private String id;
	private String name;
	private String nickname;
	private String phone;
	private String zipcode;
	private String address1;
	private String address2;
	private String profile;
	private Timestamp joinDate;
	
	private MealkitVO mealkitVO;
}
