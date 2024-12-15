package com.foodjoa.community.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foodjoa.community.service.NoticeService;
import com.foodjoa.community.vo.NoticeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("Notice")
public class NoticeController {
	
	@Autowired
    private NoticeService noticeService;
	
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, 
    		@RequestParam(required = false, defaultValue = "0") String nowPage,
            @RequestParam(required = false, defaultValue = "0") String nowBlock) {
       
        List<NoticeVO> noticeList = noticeService.getNoticeList();

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("nowBlock", nowBlock);

        return "/notices/list";
    }
	
    @RequestMapping(value = "read", method = {RequestMethod.GET, RequestMethod.POST})
    public String read(Model model,
    		@RequestParam(required = false, defaultValue = "0") String no,
    		@RequestParam(required = false, defaultValue = "0") String nowPage,
    		@RequestParam(required = false, defaultValue = "0") String nowBlock) {
		
    	NoticeVO notice = noticeService.getNotice(no);
		
		model.addAttribute("notice", notice);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("nowBlock", nowBlock);

		return "/notices/read";
    }
   
    @RequestMapping(value = "write", method = {RequestMethod.GET, RequestMethod.POST})
    public String write() {
    	return "/notices/write";
   }

    @ResponseBody
    @RequestMapping(value = "writePro", method = {RequestMethod.GET, RequestMethod.POST})
    public String writePro(Model model, HttpSession session,
            @RequestParam(required = false, defaultValue = "0") String title,
    		@RequestParam(required = false, defaultValue = "0") String contents) {
    	
    	int result = noticeService.insertNotice(title, contents);
    	
    	return String.valueOf(result);
    }

    @RequestMapping(value = "update", method = {RequestMethod.GET, RequestMethod.POST})
    public String update(Model model,
    		@RequestParam(required = false, defaultValue = "0") String no,
    		@RequestParam(required = false, defaultValue = "0") String nowPage,
    		@RequestParam(required = false, defaultValue = "0") String nowBlock) {
    			
    	NoticeVO noticeVO = noticeService.getNotice(no);
    	
		model.addAttribute("noticeVO", noticeVO);
    	model.addAttribute("nowPage", nowPage);
		model.addAttribute("nowBlock", nowBlock);
    	
		return "/notices/update";
    }
    
    @ResponseBody
    @RequestMapping(value = "updatePro", method = {RequestMethod.GET, RequestMethod.POST})
    public String updatePro(Model model, HttpSession session,
            @RequestParam(required = false, defaultValue = "0") String title,
    		@RequestParam(required = false, defaultValue = "0") String contents) {
    	
    	int result = noticeService.updateNotice(title, contents);
    	
    	return String.valueOf(result);
    }

//    @ResponseBody
//    @RequestMapping(value = "updatePro", method = {RequestMethod.GET, RequestMethod.POST})
//    public String updatePro(CommunityVO communityVO){
//    	
//    	int result = communityService.updateCommunity(communityVO);
//    	
//    	return String.valueOf(result);
//    }
//    
//    @ResponseBody
//    @RequestMapping(value = "deletePro", method = {RequestMethod.GET, RequestMethod.POST})
//    public String deletePro(Model model,
//    		@RequestParam(required = false, defaultValue = "0") String no) {
//    	
//    	int result = communityService.deleteCommunity(no);
//
//    	return String.valueOf(result);
//    }
//
//	@RequestParam(required = false, defaultValue = "0") String no,
//	@RequestParam(required = false, defaultValue = "0") String title,
//	@RequestParam(required = false, defaultValue = "0") String contents,
//	@RequestParam(required = false, defaultValue = "0") String nowPage,
//	@RequestParam(required = false, defaultValue = "0") String nowBlock) {
//
//		NoticeVO noticeVO = noticeService.updateNotice(no, title, contents);
//		
//		model.addAttribute("noticeVO", noticeVO);
//		model.addAttribute("nowPage", nowPage);
//		model.addAttribute("nowBlock", nowBlock);

}