package com.foodjoa.mealkit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Mealkit")
public class MealkitController {
	
	 @RequestMapping("/list")
	    public String listMealkits(@RequestParam(defaultValue = "0") int category) {
	        
	        return "mealkit/list";
	    }
}
