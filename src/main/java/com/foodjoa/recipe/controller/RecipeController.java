package com.foodjoa.recipe.controller;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.recipe.service.RecipeService;
import com.foodjoa.recipe.vo.RecipeVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("Recipe")
@Slf4j
public class RecipeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

	@Autowired
	private RecipeService recipeService;
	
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model,
			@RequestParam(required = false, defaultValue = "0") String category,
			@RequestParam(required = false, defaultValue = "0") String currentPage,
			@RequestParam(required = false, defaultValue = "0") String currentBlock) {
		
		List<RecipeVO> recipes = recipeService.getRecipes(category);
		
		model.addAttribute("recipes", recipes);
		model.addAttribute("category", category);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBlock", currentBlock);
		
		return "/recipes/list";
	}
	
	@RequestMapping(value = "read", method = { RequestMethod.GET, RequestMethod.POST })
	public String read(HttpSession session, Model model,
			@RequestParam(required = false, defaultValue = "0") String category,
			@RequestParam(required = false, defaultValue = "0") String currentPage,
			@RequestParam(required = false, defaultValue = "0") String currentBlock,
			@RequestParam String no) {
		
		//String userId = (String) session.getAttribute("userId");
		String userId = "admin";
		
		HashMap<String, Object> recipeInfo = recipeService.processRecipeRead(no, userId);
				
		model.addAttribute("recipeInfo", recipeInfo);
		model.addAttribute("category", category);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBlock", currentBlock);
		
		return "/recipes/read";
	}
	
	@RequestMapping(value = "write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write() {
		return "/recipes/write";
	}
	
	@ResponseBody
	@RequestMapping(value = "writePro", method = { RequestMethod.GET, RequestMethod.POST })
	public String writePro(RecipeVO recipeVO,
			MultipartHttpServletRequest multipartRequest, HttpServletResponse response) 
			throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		log.debug(recipeVO.toString());
		
		int no = recipeService.processRecipeWrite(recipeVO, multipartRequest);
		
		return String.valueOf(no);
	}
}
