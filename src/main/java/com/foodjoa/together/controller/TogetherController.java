package com.foodjoa.together.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodjoa.together.service.TogetherService;

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
	public String edit(Model model) {
		
		return "";
	}
	
	@RequestMapping(value = "edit", method = { RequestMethod.POST })
	public String edit() {
		
		return "";
	}
}
