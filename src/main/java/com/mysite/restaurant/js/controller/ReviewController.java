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

import com.mysite.restaurant.js.model.Helpful;
import com.mysite.restaurant.js.model.Replies;
import com.mysite.restaurant.js.model.Reports;
import com.mysite.restaurant.js.model.ReviewImg;
import com.mysite.restaurant.js.model.Reviews;
import com.mysite.restaurant.js.service.ReviewService;

@RestController
@RequestMapping("/api")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;


    // 리뷰 목록
    @GetMapping("/restaurants/{restaurant_id}/reviews")
    public List<Reviews> getReview(@PathVariable("restaurant_id") Long restaurantId) {
        return reviewService.getReviewById(restaurantId);
    }
    // 리뷰 등록
    @PostMapping("/reviews")
    public int createReview(@RequestBody Reviews review) {
        return reviewService.insertReview(review);
    }
    // 리뷰 이미지 등록
    @PostMapping("/reviews/img")
    public int createReviewImg(@RequestBody ReviewImg reviewImg) {
    	return reviewService.insertReviewImage(reviewImg);
    }
    // 리뷰 수정
    @PutMapping("/reviews/{review_id}")
    public int updateReview(@PathVariable("review_id") Long reviewId, @RequestBody Reviews review) {
        review.setReviewId(reviewId);
        return reviewService.updateReview(review);
    }
    // 리뷰 이미지 수정
    @PutMapping("/reviews/{review_image_id}/img")
    public int updateReviewImage(@PathVariable("review_image_id") Long reviewImgId, @RequestBody ReviewImg reviewImg) {
        reviewImg.setReviewImageId(reviewImgId);
        return reviewService.updateReviewImage(reviewImg);
    }
    // 리뷰 삭제
    @DeleteMapping("/reviews/{review_id}")
    public int deleteReview(@PathVariable("review_id") Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
    // 리뷰 이미지 삭제
    @DeleteMapping("/reviews/{review_image_id}/img")
    public int deleteReviewImage(@PathVariable("review_image_id") Long reviewImgId) {
        return reviewService.deleteReviewImg(reviewImgId);
    }
        
	// 내 리뷰 조회
    @GetMapping("/mypage/{user_id}/reviews")
    public List<Reviews> getMyReview(@PathVariable("user_id") Long userId) {
    	return reviewService.selectMyReviews(userId);
    }
    
    // 답글 조회
    @GetMapping("/reviews/{reply_id}/replies")
    public List<Replies> getReplie(@PathVariable("reply_id") Long replyId) {
        return reviewService.selectReplies(replyId);
    }
	// 답글 작성
    @PostMapping("/reviews/{review_id}/replies")
    public int createReplies(@RequestBody Replies replies) {
        return reviewService.insertReplie(replies);
    }
    // 답글 수정
    @PutMapping("/reviews/{reply_id}/replies")
    public int updateReplies(@PathVariable("reply_id") Long replyId, @RequestBody Replies replies) {
        replies.setReplyId(replyId);
        return reviewService.updateReplies(replies);
    }
    // 답글 삭제
    @DeleteMapping("/replies/{reply_id}")
    public int deleteReply(@PathVariable("reply_id") Long replyId) {
        return reviewService.deleteReply(replyId);
    }
    
	// 리뷰 신고 목록
    @GetMapping("/admin/reports/{report_id}")
    public List<Reports> getReports(@PathVariable("report_id") Long reportId) {
        return reviewService.selectReports(reportId);
    }
	// 리뷰 신고 작성
    @PostMapping("/reviews/{report_id}")
    public int createReport(@RequestBody Reports reports) {
        return reviewService.insertReport(reports);
    }
	// 리뷰 신고 삭제
    @DeleteMapping("/admin/reports/{report_id}")
    public int deleteReport(@PathVariable("report_id") Long reportId) {
        return reviewService.deleteReport(reportId);
    }
    
	// 도움 됐어요
    @PostMapping("/reviews/{review_id}/helpful")
    public int createhelpful(@RequestBody Helpful helpful) {
        return reviewService.insertHelpful(helpful);
    }
	// 도움 삭제
    @DeleteMapping("/reviews/{review_id}/helpful/{vote_id}")
    public int deleteHelpful(@PathVariable("vote_id") Long voteId) {
        return reviewService.deleteHelpful(voteId);
    }
    
}
