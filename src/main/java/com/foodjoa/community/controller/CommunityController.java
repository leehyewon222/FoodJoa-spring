package com.foodjoa.community.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodjoa.community.service.CommunityService;

@Controller
@RequestMapping("Community")
public class CommunityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommunityController.class);
	
	@Autowired
    private CommunityService communityService;

    @RequestMapping("list")
    public String list(Model model, 
    		@RequestParam(required = false, defaultValue = "0") String nowPage,
            @RequestParam(required = false, defaultValue = "0") String nowBlock) {
       
        List<Map<String, Object>> communities = communityService.getCommunityList();

        model.addAttribute("communities", communities);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("nowBlock", nowBlock);

        return "/communities/list";
    }
}
