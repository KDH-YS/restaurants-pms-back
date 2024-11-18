package com.mysite.restaurant.js.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class TestController {

	@PostMapping("/test")
	public String posttest(@RequestBody String entity) {
		
		
		return entity;
	}
	
}
