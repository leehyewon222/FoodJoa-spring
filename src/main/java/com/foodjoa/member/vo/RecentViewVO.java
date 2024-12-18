package com.foodjoa.member.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.foodjoa.mealkit.vo.MealkitVO;
import com.foodjoa.recipe.vo.RecipeVO;

import lombok.Data;

@Data
@Component
public class RecentViewVO {
	
	private int no;
	private String id;
	private int type;
	private int itemNo;
	private Timestamp viewDate;
	
	private RecipeVO recipeVO;
	private MealkitVO mealkitVO;
	private MemberVO memberVO;
}
