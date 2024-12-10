package com.foodjoa.recipe.controller;

import org.slf4j.Logger;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodjoa.recipe.service.RecipeService;

@Controller
@RequestMapping("Recipe")
public class RecipeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

	@Autowired
	private RecipeService recipeService;
	
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model,
			@RequestParam(required = false, defaultValue = "0") String category,
			@RequestParam(required = false, defaultValue = "0") String currentPage,
			@RequestParam(required = false, defaultValue = "0") String currentBlock) {
		
		List<Map<String, Object>> recipes = recipeService.getRecipesWithAvgRating(category);
		
		System.out.println("size : " + recipes.size());
		
		model.addAttribute("recipes", recipes);
		model.addAttribute("category", category);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBlock", currentBlock);
		
		return "/recipes/list";
	}
}
