package com.foodjoa.mealkit.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.community.vo.NoticeVO;

import lombok.Data;

@Data
@Component
public class MealkitCartVO {
		
	private int no;
	private String id;
	private int mealkit_no;
	private int quantity;
	private Timestamp choice_date;
}
