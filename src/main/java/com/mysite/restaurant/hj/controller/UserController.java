package com.mysite.restaurant.hj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class UserController {

	@GetMapping("/signup")
	public String signupFrom() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup() {
		userService.signup(email, password, name, phone, notification_agreed);
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
