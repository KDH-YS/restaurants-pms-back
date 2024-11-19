package com.mysite.restaurant.js.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.restaurant.js.model.Reviews;
import com.mysite.restaurant.js.service.ReviewService;

@RestController
@RequestMapping("/api")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	

    // 리뷰 등록
    @PostMapping("/reviews")
    public int createReview(@RequestBody Reviews review) {
        return reviewService.insertReview(review);
    }

    @GetMapping("/restaurants/{restaurant_id}/reviews")
    public List<Reviews> getReview(@PathVariable("restaurant_id") Long restaurantId) {
        return reviewService.getReviewById(restaurantId);
    }
    
    // 리뷰 수정
    @PutMapping("/reviews/{review_id}")
    public int updateReview(@PathVariable("review_id") Long reviewId, @RequestBody Reviews review) {
        review.setReviewId(reviewId);
        return reviewService.updateReview(review);
    }

    // 리뷰 이미지 수정
    @PutMapping("/reviews/{review_id}/image")
    public int updateReviewImage(@PathVariable Long reviewId, @RequestBody Reviews review) {
        review.setReviewId(reviewId);
        return reviewService.updateReviewImage(review);
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{review_id}")
    public int deleteReview(@PathVariable("review_id") Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
    
	// 내 리뷰
    @GetMapping("/mypage/{user_id}/reviews")
    public List<Reviews> getMyReview(@PathVariable("user_id") Long userId) {
    	return reviewService.selectMyReviews(userId);
    }
	// 리뷰 수정
	// 도움 됐어요
	// 리뷰 답글 작성
	// 리뷰 신고
	// 리뷰 신고 목록
	// 리뷰 신고 삭제
}
