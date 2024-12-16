package com.foodjoa.mealkit.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.mealkit.service.MealkitService;
import com.foodjoa.mealkit.vo.MealkitReviewVO;
import com.foodjoa.mealkit.vo.MealkitVO;

@Controller
@RequestMapping("Mealkit")
public class MealkitController {
	
	@Autowired
	private MealkitService mealkitService;
	
	@RequestMapping(value="list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(
			@RequestParam(defaultValue = "0") int category, 
			@RequestParam(defaultValue = "0") int nowPage,
            @RequestParam(defaultValue = "0") int nowBlock, 
			Model model) throws Exception {
		
		List<Map<String, Object>> mealkitsList = mealkitService.selectMealkitsList(category);
		String categoryName = mealkitService.getCategoryName(category);
		
		model.addAttribute("mealkitsList", mealkitsList);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("nowBlock", nowBlock);
	    model.addAttribute("nowPage", nowPage);
		
		return "/mealkits/list";
	}
	
	@RequestMapping(value="info", method = { RequestMethod.GET, RequestMethod.POST })
	public String info(@RequestParam int no, Model model) throws Exception {
		
		MealkitVO mealkitInfo = mealkitService.selectMealkitInfo(no);
		List<Object> reviewInfo = mealkitService.selectReviewsInfo(no);
		
		model.addAttribute("mealkitInfo", mealkitInfo);
		model.addAttribute("reviewInfo", reviewInfo);
		
		return "/mealkits/info";
	}
	
	@RequestMapping(value="write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write() throws Exception {
		
		return "/mealkits/write";
	}
	
	@RequestMapping(value="review", method = { RequestMethod.GET, RequestMethod.POST })
	public String review(@RequestParam int no, Model model) throws Exception {
		
		MealkitVO mealkitInfo = mealkitService.selectMealkitInfo(no);
		
		model.addAttribute("mealkitInfo", mealkitInfo);
		
		return "/mealkits/review";
	}
	
	@RequestMapping(value="updateMealkit", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateMealkit(@RequestParam int no, Model model) throws Exception {
		
		MealkitVO mealkitInfo = mealkitService.selectMealkitInfo(no);
		
		model.addAttribute("mealkitInfo", mealkitInfo);
		
		return "/mealkits/updateMealkit";
	}
	
	@RequestMapping(value="updateReview", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateReview( @RequestParam int no, Model model) throws Exception {
		
		MealkitReviewVO reviewInfo = mealkitService.selectMyReviewInfo(no);
		
		model.addAttribute("reviewInfo", reviewInfo);
		
		return "/mealkits/updateReview";
	}
	
	@RequestMapping(value="mymealkit", method = { RequestMethod.GET, RequestMethod.POST })
	public String myMealkit(Model model, HttpSession session) throws Exception {
		
		String id = (String) session.getAttribute("userId");
		
		List<Map<String, Object>> mymealkits = mealkitService.selectMyMealkitsList(id);
		
		model.addAttribute("mymealkits", mymealkits);
		
		return "/mealkits/mymealkit";
	}
	
	@ResponseBody
	@RequestMapping(value = "writePro", method = { RequestMethod.GET, RequestMethod.POST })
	public String writePro(MealkitVO mealkitVO, MultipartHttpServletRequest multipartRequest) 
			throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		
		int no = mealkitService.processMealkitWrite(mealkitVO, multipartRequest);
		
		return String.valueOf(no);
	}
	
	@ResponseBody
	@RequestMapping(value="updatePro", method = {RequestMethod.GET, RequestMethod.POST})
	public String updatePro(MealkitVO mealkitVO, MultipartHttpServletRequest multipartRequest)
		throws Exception{

		multipartRequest.setCharacterEncoding("utf-8");
		
		int no = mealkitService.processMealkitUpdate(mealkitVO, multipartRequest);
		
		return String.valueOf(no);
	}
	
	@ResponseBody
	@RequestMapping(value="deletePro", method = { RequestMethod.GET, RequestMethod.POST })
	public String deletePro(@RequestParam int no) throws Exception {
		
		int result = mealkitService.deleteMealkit(no);		

		return String.valueOf(result);
	}

	@ResponseBody
	@RequestMapping(value = "reviewPro", method = { RequestMethod.GET, RequestMethod.POST })
	public String reviewPro(MealkitReviewVO reviewVO, MultipartHttpServletRequest multipartRequest) 
			throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		
		int no = mealkitService.processReviewWrite(reviewVO, multipartRequest);
		
		return String.valueOf(no);
	}
	
	@ResponseBody
	@RequestMapping(value="reviewDeletePro", method = { RequestMethod.GET, RequestMethod.POST })
	public String reviewDeletePro(@RequestParam int no, @RequestParam int mealkitNo, 
			HttpSession session) throws Exception {
		
		int result = mealkitService.deleteReview(no, mealkitNo, (String) session.getAttribute("userId"));		

		return String.valueOf(result);
	}
	
	@RequestMapping(value="searchlistPro", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchlistPro(Model model,
			@RequestParam("key") String key,
	        @RequestParam("word") String word, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Map<String, Object>> searchResults = mealkitService.selectSearchList(key, word);
		
	    model.addAttribute("mealkitsList", searchResults);
		model.addAttribute("categoryName", word);
	    
	    return "/mealkits/list";
	}
	
	@ResponseBody
	@RequestMapping(value="wishPro", method = { RequestMethod.GET, RequestMethod.POST })
	public String wishPro(HttpSession session, @RequestParam int no) throws Exception {
		
		int result = mealkitService.processWishlist(no, (String) session.getAttribute("userId"));
		
		return String.valueOf(result);
	}
	
	@RequestMapping(value="cartPro", method = { RequestMethod.GET, RequestMethod.POST })
	public String cartPro(@RequestParam int no, @RequestParam int quantity, 
			HttpSession session) throws Exception {
		
		int result = mealkitService.processCart(no, quantity, (String) session.getAttribute("userId"));
		
		return String.valueOf(result);
	}
}
