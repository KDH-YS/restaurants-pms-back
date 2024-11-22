package com.mysite.restaurant.js;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class test {

	@GetMapping("/home")
	@ResponseBody
	public String home() {
		return "hello";
	}
	
}
