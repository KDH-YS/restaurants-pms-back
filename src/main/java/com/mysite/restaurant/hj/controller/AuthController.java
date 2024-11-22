package com.mysite.restaurant.hj.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.restaurant.hj.domain.User;
import com.mysite.restaurant.hj.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
//	private final PasswordEncoder passwordEncoder;
	
//	회원가입 처리
	@PostMapping("/signup")
	public int signup (@RequestBody User user) {
		return customUserDetailsService.save(user);
	}

//  로그인
    @PostMapping("/login")
    public User login(@RequestBody String email) {
    	return customUserDetailsService.findByUserEmail(email);
    }
    
//  로그아웃
    
//	사용자 정보 조회
    @GetMapping("/user/{user_id}")
    public ResponseEntity<User> getUser(@PathVariable("user_id") Long userId) {
        User user = customUserDetailsService.selectUserProfile(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }
    
//  사용자 정보 수정
    @PutMapping("/user")
    public int updateUserProfile(@RequestBody User user) {
    	return customUserDetailsService.updateUserProfile(user);
    }
    
//  회원 탈퇴
    @DeleteMapping("/deleteUser")
    public int deleteUser(@RequestParam("email") String email) {
    	return customUserDetailsService.deleteUser(email);
    }
    
}
