package com.foodjoa.recipe.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.community.vo.NoticeVO;
import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Data
@Component
public class RecipeReviewVO {

	private int no;
	private String id;
	private int recipeNo;
	private String pictures;
	private String contents;
	private int rating;
	private Timestamp postDate;
	
	private RecipeVO recipeVO;
	private MemberVO memberVO;
}
