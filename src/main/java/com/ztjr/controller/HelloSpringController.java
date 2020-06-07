package com.ztjr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloSpringController {

	String message = "Hello world";
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(@RequestParam(value="name", required=false, defaultValue="spring") String name) {
		ModelAndView mv = new ModelAndView("hellospring");
		mv.addObject("message", message);
		mv.addObject("name", name);
		return mv; 
	}
}
