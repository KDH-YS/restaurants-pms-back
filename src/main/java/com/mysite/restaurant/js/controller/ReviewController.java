package com.mysite.restaurant.js.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.restaurant.js.mapper.ReviewMapper;
import com.mysite.restaurant.js.model.Reviews;

@Controller
@RequestMapping("/api")
public class ReviewController {
	
	// 리뷰 목록
	@GetMapping("/restaurant/{restaurant_id}/reviews")
	public String reviewList() {
		
	}
	
	// 내 리뷰
	@GetMapping("/reviews/{user_id}")
	public String myReivewList() {
		
	}
	
	// 리뷰 작성
	@PostMapping("/reviews")
	public Stirng writeReview() {
		
	}
	
	// 리뷰 수정
	@PutMapping("/reviews/{review_id}")
	public String updateReview() {
		
	}
	
	// 리뷰 삭제
	// 도움 됐어요
	// 리뷰 답글 작성
	// 리뷰 신고
	// 리뷰 신고 목록
	// 리뷰 신고 삭제
}
