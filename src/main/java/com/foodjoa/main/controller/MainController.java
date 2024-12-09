package com.foodjoa.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/Main")
public class MainController {

	@RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
	public String home(HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		
		// ModelAndView modelAndView = new ModelAndView("/includes/center");
		
		model.addAttribute("test", "테스트입니다.");
		
		return "/includes/center";
	}
}
