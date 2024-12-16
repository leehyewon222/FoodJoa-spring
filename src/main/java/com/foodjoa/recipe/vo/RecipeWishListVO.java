package com.foodjoa.recipe.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Data
@Component
public class RecipeWishListVO {

	private int no;
	private String id;
	private int recipeNo;
	private Timestamp choiceDate;
	
	private RecipeVO recipeVO;
	private MemberVO memberVO;
}
