package com.foodjoa.community.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.coyote.ContinueResponseTiming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foodjoa.community.service.CommunityService;
import com.foodjoa.community.vo.CommunityVO;

@Controller
@RequestMapping("Community")
public class CommunityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommunityController.class);
	
	@Autowired
    private CommunityService communityService;

    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public String list(Model model, 
    		@RequestParam(required = false, defaultValue = "0") String nowPage,
            @RequestParam(required = false, defaultValue = "0") String nowBlock) {
       
        List<CommunityVO> communities = communityService.getCommunities();

        model.addAttribute("communities", communities);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("nowBlock", nowBlock);

        return "/communities/list";
    }

    @RequestMapping(value = "read", method = {RequestMethod.GET, RequestMethod.POST})
    public String read(Model model,
    		@RequestParam(required = false, defaultValue = "0") String no) {
		
		CommunityVO community = communityService.getCommunity(no);
		
		model.addAttribute("community", community);

		return "/communities/read";
		//리팩토링 다하고 페이징처리 해야함
    }
    
    @RequestMapping(value = "write", method = {RequestMethod.GET, RequestMethod.POST})
    public String write() {
    	return "/communities/write";
    }
    
    @RequestMapping(value = "writePro", method = {RequestMethod.GET, RequestMethod.POST})
    public String writePro(Model model, HttpSession session,
            @RequestParam(required = false, defaultValue = "0") String title,
    		@RequestParam(required = false, defaultValue = "0") String contents) {
    	
    	//String id = (String)session.getAttribute("userId");
    	String id = "admin";
    	
    	int community = communityService.insertCommunity(id, title, contents);
    	
    	return "redirect:/Community/list";
    }
    
    @RequestMapping(value = "update", method = {RequestMethod.GET, RequestMethod.POST})
    public String update(Model model, @ModelAttribute CommunityVO communityVO) {
    	
    	model.addAttribute("community", communityVO);    	
    	return "/communities/update";
    }

    @ResponseBody
    @RequestMapping(value = "updatePro", method = {RequestMethod.GET})
    public String updatePro(@ModelAttribute CommunityVO communityVO,
    						HttpServletResponse response) throws Exception {
    	
    	int result = communityService.getCommunityties(communityVO);
    	
    	return String.valueOf(result);
    }
}

