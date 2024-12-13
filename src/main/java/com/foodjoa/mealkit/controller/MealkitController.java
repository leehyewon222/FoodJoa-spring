package com.foodjoa.mealkit.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodjoa.mealkit.service.MealkitService;
import com.foodjoa.mealkit.vo.MealkitReviewVO;
import com.foodjoa.mealkit.vo.MealkitVO;

@Controller
@RequestMapping("Mealkit")
public class MealkitController {
	
	@Autowired
	private MealkitService mealkitService;
	
	@RequestMapping(value="list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listMealkits(
			@RequestParam(defaultValue = "0") int category, 
			@RequestParam(defaultValue = "0") int nowPage,
            @RequestParam(defaultValue = "0") int nowBlock, 
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		List<Map<String, Object>> mealkitsList = mealkitService.selectMealkitsList(category);
		String categoryName = mealkitService.getCategoryName(category);
		
		model.addAttribute("mealkitsList", mealkitsList);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("nowBlock", nowBlock);
	    model.addAttribute("nowPage", nowPage);
		
		return "/mealkits/list";
	}
	
	@RequestMapping(value="info", method = { RequestMethod.GET, RequestMethod.POST })
	public String infoMealkit(@RequestParam int no, 
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		MealkitVO mealkitInfo = mealkitService.selectMealkitInfo(no);
		List<Object> reviewInfo = mealkitService.selectReviewsInfo(no);
		
		model.addAttribute("mealkitInfo", mealkitInfo);
		model.addAttribute("reviewInfo", reviewInfo);
		
		return "/mealkits/info";
	}
	
	@RequestMapping(value="write", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeMealkit( HttpServletRequest request, HttpServletResponse response, 
			Model model) throws Exception {
		
		return "/mealkits/write";
	}
	
	@RequestMapping(value="review", method = { RequestMethod.GET, RequestMethod.POST })
	public String reviewMealkit(@RequestParam int no, 
			HttpServletRequest request, HttpServletResponse response, 
			Model model) throws Exception {
		
		MealkitVO mealkitInfo = mealkitService.selectMealkitInfo(no);
		
		model.addAttribute("mealkitInfo", mealkitInfo);
		
		return "/mealkits/review";
	}
	
	@RequestMapping(value="updateMealkit", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateMealkit(@RequestParam int no, 
			HttpServletRequest request, HttpServletResponse response, 
			Model model) throws Exception {
		
		MealkitVO mealkitInfo = mealkitService.selectMealkitInfo(no);
		
		model.addAttribute("mealkitInfo", mealkitInfo);
		
		return "/mealkits/updateMealkit";
	}
	
	@RequestMapping(value="updateReview", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateReview( @RequestParam int no, 
			HttpServletRequest request, HttpServletResponse response, 
			Model model) throws Exception {
		
		MealkitReviewVO reviewInfo = mealkitService.selectMyReviewInfo(no);
		
		model.addAttribute("reviewInfo", reviewInfo);
		
		return "/mealkits/updateReview";
	}
	
	@RequestMapping(value="mymealkit", method = { RequestMethod.GET, RequestMethod.POST })
	public String myMealkit(HttpServletRequest request, HttpServletResponse response, 
			Model model) throws Exception {
		
		String id = "aronId";
		
		List<Map<String, Object>> mymealkits = mealkitService.selectMyMealkitsList(id);
		
		model.addAttribute("mymealkits", mymealkits);
		
		return "/mealkits/mymealkit";
	}
	
	@RequestMapping(value="searchlistPro", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchlist(Model model,
			@RequestParam("key") String key,
	        @RequestParam("word") String word, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Map<String, Object>> searchResults = mealkitService.selectSearchList(key, word);
		
	    model.addAttribute("mealkitsList", searchResults);
		model.addAttribute("categoryName", word);
	    
	    return "/mealkits/list";
	}
}
