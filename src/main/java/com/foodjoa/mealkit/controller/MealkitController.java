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
		Map<String, Object> pagingData = mealkitService.getPagingData(mealkitsList, nowPage, nowBlock);
		
		model.addAttribute("mealkitsList", mealkitsList);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("pageData", pagingData); 
		model.addAttribute("nowBlock", nowBlock);
	    model.addAttribute("nowPage", nowPage);
		
		return "/mealkits/list";
	}
	
	@RequestMapping(value="info", method = { RequestMethod.GET, RequestMethod.POST })
	public String infoMealkit(@RequestParam int no, 
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		MealkitVO mealkitInfo = mealkitService.selectMealkitInfo(no);
		List<Object> reviewInfo = mealkitService.selectReviewInfo(no);
		
		model.addAttribute("mealkitInfo", mealkitInfo);
		model.addAttribute("reviewInfo", reviewInfo);
		
		return "/mealkits/info";
	}
}
