package com.foodjoa.recipe.vo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;

import com.foodjoa.member.vo.MemberVO;

import lombok.Data;

@Component
@Data
public class RecipeVO {

	private int no;
	private String id;
	private String title;
	private String thumbnail;
	private String description;
	private String contents;
	private int category;
	private int views;
	private String ingredient;
	private String ingredientAmount;
	private String orders;
	private Timestamp postDate;
	
	private float averageRating;
	private int reviewCount;
	
	private MemberVO memberVO;
}
