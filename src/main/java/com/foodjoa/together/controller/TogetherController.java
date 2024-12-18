package com.foodjoa.together.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.together.service.TogetherService;
import com.foodjoa.together.vo.TogetherVO;

@Controller
@RequestMapping("Together")
public class TogetherController {

	@Autowired
	private TogetherService togetherService;
	
	@RequestMapping(value = "list", method = { RequestMethod.GET })
	public String list() {
		return "/togethers/list";
	}

	@RequestMapping(value = "edit", method = { RequestMethod.GET })
	public String edit(Model model,
			@RequestParam(defaultValue = "0") int no) {
		
		TogetherVO together = togetherService.getTogether(no);
		
		model.addAttribute("together", together);
		
		return "/togethers/edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "edit", method = { RequestMethod.POST })
	public String edit(TogetherVO togetherVO, MultipartHttpServletRequest multipartRequest,
			@RequestParam String originPictures,
			@RequestParam String cmd) {
		
		int togetherNo = togetherService.processTogetherEdit(cmd, togetherVO, multipartRequest, originPictures);
		
		return String.valueOf(togetherNo);
	}
}
