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
	public String writePro(RecipeVO recipeVO, MultipartHttpServletRequest multipartRequest) 
			throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		
		int no = recipeService.processRecipeWrite(recipeVO, multipartRequest);
		
		return String.valueOf(no);
	}
	
	@RequestMapping(value = "update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(Model model, @RequestParam String no) {
		
		RecipeVO recipe = recipeService.getRecipe(no);
		
		model.addAttribute("recipe", recipe);
		
		return "/recipes/update";
	}
	
	@ResponseBody
	@RequestMapping(value = "upatePro", method = { RequestMethod.GET, RequestMethod.POST })
	public String upatePro(RecipeVO recipeVO,
			@RequestParam String originThumbnail,
			MultipartHttpServletRequest multipartRequest)
			throws Exception {
		
		int result = recipeService.processRecipeUpdate(recipeVO, originThumbnail, multipartRequest);
		
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "deletePro", method = { RequestMethod.GET, RequestMethod.POST })
	public String deletePro(@RequestParam String no) throws Exception {
		
		int result = recipeService.deleteRecipe(no);
		
		return String.valueOf(result);
	}
	
	@RequestMapping(value = "search", method = { RequestMethod.GET, RequestMethod.POST })
	public String search(Model model,
			@RequestParam String category,
			@RequestParam String key,
			@RequestParam String word) {
		
		List<RecipeVO> recipes = recipeService.getSearchedRecipeList(category, key, word);
		
		model.addAttribute("recipes", recipes);
		model.addAttribute("category", category);
		
		return "/recipes/list";
	}
}
