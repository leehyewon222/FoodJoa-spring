package com.foodjoa.share.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.share.service.ShareService;
import com.foodjoa.share.vo.ShareVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("Share")
public class ShareContoller {

	@Autowired
	private ShareService shareService;
	
	@RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, 
    		@RequestParam(required = false, defaultValue = "0") String nowPage,
            @RequestParam(required = false, defaultValue = "0") String nowBlock) {
       
        List<ShareVO> shareList = shareService.getShareList();

        model.addAttribute("shareList", shareList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("nowBlock", nowBlock);

        return "/shares/list";
    }
	
	@RequestMapping(value = "write", method = {RequestMethod.GET, RequestMethod.POST})
    public String write() {
		return "/shares/write";
	}
	
	@ResponseBody
	@RequestMapping(value = "writePro", method = {RequestMethod.GET, RequestMethod.POST})
	public String writePro(ShareVO shareVO, MultipartHttpServletRequest multiRequest) throws Exception {
		return String.valueOf(shareService.processWrite(shareVO, multiRequest));
	}
	
	@RequestMapping(value = "read", method = {RequestMethod.GET, RequestMethod.POST})
	public String read(Model model,
    		@RequestParam(required = false, defaultValue = "0") int no,
    		@RequestParam(required = false, defaultValue = "0") String nowPage,
            @RequestParam(required = false, defaultValue = "0") String nowBlock) {
		
		ShareVO share = shareService.getShare(no);
		
		model.addAttribute("share", share);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("nowBlock", nowBlock);
		
		return "/shares/read";
	}
	
	@RequestMapping(value = "update", method = {RequestMethod.GET, RequestMethod.POST})
	public String update(Model model,
			@RequestParam(required = false, defaultValue = "0") int no,
			@RequestParam(required = false, defaultValue = "0") String nowPage,
            @RequestParam(required = false, defaultValue = "0") String nowBlock) {
		
		ShareVO share = shareService.getShare(no);
		
		model.addAttribute("share", share);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("nowBlock", nowBlock);
		
		return "/shares/update";
	}
	
	@ResponseBody
	@RequestMapping(value = "updatePro", method = {RequestMethod.GET, RequestMethod.POST})
	public String updatePro(ShareVO shareVO, MultipartHttpServletRequest multipartRequest,
			@RequestParam String originThumbnail) throws Exception {
		
		int result = shareService.processShareUpdate(shareVO, multipartRequest, originThumbnail);
		return String.valueOf(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "deletePro", method = {RequestMethod.GET, RequestMethod.POST})
	public String deletePro(@RequestParam String no) throws Exception {
		return String.valueOf(shareService.processShareDelete(no));
	}
	
	@RequestMapping(value = "searchList", method = {RequestMethod.GET, RequestMethod.POST})
	public String searchList(Model model,
			@RequestParam String key,
			@RequestParam String word) {
		
		List<ShareVO> shareList = shareService.getSearchedShares(key, word);
		
		model.addAttribute("shareList", shareList);

        return "/shares/list";
	}
	
}