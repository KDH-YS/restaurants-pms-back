package com.mysite.restaurant.hj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
public class UserController {

	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	
//	회원가입 폼
	@GetMapping("/signup")
	public String signupFrom() {
		return "signup";
	}
	
//	회원가입 처리
	@PostMapping("/signup")
	public String signup(@RequestParam String email, 
						 @RequestParam String password, 
						 @RequestParam String name, 
						 @RequestParam String phone, 
						 @RequestParam int notification_agreed) {
//		사용자 생성
		User user = new User();
		
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
