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

import com.foodjoa.community.vo.NoticeVO;

@Controller
@RequestMapping(value = "/Main")
public class MainController {

	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
	public String home(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		
		List<Map<String, Object>> recipeRank = sqlSession.selectList("selectRecipeRank");
		List<Map<String, Object>> mealkitRank = sqlSession.selectList("selectMealkitRank");
		List<NoticeVO> notices = sqlSession.selectList("selectNoticeRank");
		List<Map<String, Object>> communityRank = sqlSession.selectList("selectCommunityRank");
		List<Map<String, Object>> shareRank = sqlSession.selectList("selectshareRank");
		
		model.addAttribute("recipes", recipeRank);
		model.addAttribute("mealkits", mealkitRank);
		model.addAttribute("notices", notices);
		model.addAttribute("communities", communityRank);
		model.addAttribute("shares", shareRank);
		
		return "/includes/center";
	}
}
