package com.mysite.restaurant.js.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.restaurant.js.model.Helpful;
import com.mysite.restaurant.js.model.Replies;
import com.mysite.restaurant.js.model.Reports;
import com.mysite.restaurant.js.model.Restaurants;
import com.mysite.restaurant.js.model.ReviewImg;
import com.mysite.restaurant.js.model.Reviews;
import com.mysite.restaurant.js.service.ReviewService;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    // 리뷰 목록 조회
    @GetMapping("/restaurants/{restaurant_id}/reviews")
    public List<Reviews> getReview(@PathVariable("restaurant_id") Long restaurantId) {
        return reviewService.getReviewById(restaurantId);
    }
    
    @PostMapping("/reviews")
    public int createReviewAndImage(
            @RequestParam("review_content") String reviewContent,  // 리뷰 내용
            @RequestParam(value = "restaurant_id") Long restaurantId, // 리뷰를 작성한 음식점 ID
            @RequestParam(value = "user_id", required = true) Long userId, // 사용자 ID
            @RequestParam(value = "taste_rating", required = true) Double tasteRating,  // 맛 평점
            @RequestParam(value = "service_rating", required = true) Double serviceRating,  // 서비스 평점
            @RequestParam(value = "atmosphere_rating", required = true) Double atmosphereRating,  // 분위기 평점
            @RequestParam(value = "value_rating", required = true) Double valueRating,  // 가성비 평점
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        // 1. 리뷰 내용과 평점 처리 (Review 객체 생성 후 저장)
        Reviews review = new Reviews();
        review.setReviewContent(reviewContent);  // 리뷰 내용 설정
        review.setRestaurantId(restaurantId);  // 음식점 ID 설정
        review.setUserId(userId);  // userId 설정
        review.setTasteRating(tasteRating);  // 맛 평점 설정
        review.setServiceRating(serviceRating);  // 서비스 평점 설정
        review.setAtmosphereRating(atmosphereRating);  // 분위기 평점 설정
        review.setValueRating(valueRating);  // 가성비 평점 설정
        
        int reviewId = reviewService.insertReview(review);  // 리뷰 등록

        // 2. 이미지가 있으면 이미지 처리 (ReviewImg 객체 생성 후 저장)
        if (image != null && !image.isEmpty()) {
            // 서버의 디렉토리 경로 설정
            String uploadDir = "uploads/reviews";  // 업로드 디렉토리
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();  // 디렉토리가 없으면 생성
            }

            // 파일 이름 생성 (중복을 피하기 위해 UUID 사용 가능)
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            // 파일 경로 설정
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(image.getInputStream(), filePath);  // 파일 저장

            // ReviewImg 객체 생성
            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setReviewId(Long.valueOf(reviewId));  // 리뷰와 연결
            reviewImg.setImageUrl(filePath.toString());  // 이미지 경로 저장
            reviewImg.setImageOrder(1);  // 이미지 순서 (필요한 경우 설정)

            reviewService.insertReviewImage(reviewImg);  // 이미지 등록
        }

        return reviewId;  // 리뷰 등록 후 리뷰 ID 반환
    }


    // 리뷰 수정
    @PutMapping("/reviews/{review_id}")
    public int updateReview(@PathVariable("review_id") Long reviewId, @RequestBody Reviews review) {
        review.setReviewId(reviewId);
        return reviewService.updateReview(review);
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{review_id}")
    public int deleteReview(@PathVariable("review_id") Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
    
    // 내 리뷰 조회
    @GetMapping("/mypage/{user_id}/reviews")
    public List<Reviews> getMyReview(@PathVariable("user_id") Long userId) {
        return reviewService.selectMyReviews(userId);
    }

    // 답글 조회
    @GetMapping("/reviews/{review_id}/replies")
    public List<Replies> getReplies(@PathVariable("review_id") Long reviewId) {
        return reviewService.selectReplies(reviewId);
    }

    // 답글 작성
    @PostMapping("/reviews/{review_id}/replies")
    public int createReplies(@PathVariable("review_id") Long reviewId, @RequestBody Replies replies) {
        replies.setReviewId(reviewId);
        return reviewService.insertReplie(replies);
    }

    // 리뷰 신고 작성
    @PostMapping("/reviews/{review_id}/report")
    public int createReport(@PathVariable("review_id") Long reviewId, @RequestBody Reports reports) {
        reports.setReviewId(reviewId);
        return reviewService.insertReport(reports);
    }

    // 도움 됐어요
    @PostMapping("/reviews/{review_id}/helpful")
    public int createHelpful(@PathVariable("review_id") Long reviewId, @RequestBody Helpful helpful) {
        helpful.setReviewId(reviewId);
        return reviewService.insertHelpful(helpful);
    }
    
    // 특정 가게 조회
    @GetMapping("/restaurants/{restaurant_id}")
    public Restaurants getShop(@PathVariable("restaurant_id") Long restaurantId) {
        return reviewService.selectShop(restaurantId);
    }
}
