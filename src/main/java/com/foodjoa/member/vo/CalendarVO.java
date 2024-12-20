package com.foodjoa.member.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CalendarVO {
	private int no;
	private String id;
	private int cn;              // 일정넘버 calendar number
	private String summary;      // 제목	 
	private String description;  // 내용
	private String location;     // 장소
	private String startTime;    // 시작 시간
	private String endTime;      // 종료 시간
}
