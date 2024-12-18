package com.foodjoa.main.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodjoa.community.vo.CommunityVO;
import com.foodjoa.community.vo.NoticeVO;
import com.foodjoa.mealkit.vo.MealkitVO;
import com.foodjoa.recipe.vo.RecipeVO;
import com.foodjoa.share.vo.ShareVO;

@Controller
@RequestMapping(value = "/Main")
public class MainController {

	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
	public String home(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		
		List<RecipeVO> recipeRank = sqlSession.selectList("mapper.mainHome.selectRecipeRank");
		List<MealkitVO> mealkitRank = sqlSession.selectList("mapper.mainHome.selectMealkitRank");
		List<NoticeVO> notices = sqlSession.selectList("mapper.mainHome.selectNoticeRank");
		List<CommunityVO> communityRank = sqlSession.selectList("mapper.mainHome.selectCommunityRank");
		List<ShareVO> shareRank = sqlSession.selectList("mapper.mainHome.selectshareRank");
		
		model.addAttribute("recipes", recipeRank);
		model.addAttribute("mealkits", mealkitRank);
		model.addAttribute("notices", notices);
		model.addAttribute("communities", communityRank);
		model.addAttribute("shares", shareRank);
		
		return "/includes/center";
	}
}
