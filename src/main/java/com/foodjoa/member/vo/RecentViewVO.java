package com.foodjoa.member.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.community.vo.NoticeVO;

import lombok.Data;

@Data
@Component
public class RecentViewVO {
	
	private int no;
	private String id;
	private int type;
	private int itemNo;
	private Timestamp viewDate;
}
