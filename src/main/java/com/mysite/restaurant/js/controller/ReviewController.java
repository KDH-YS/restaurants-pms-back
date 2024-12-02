package com.mysite.restaurant.js.controller;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

import com.mysite.restaurant.js.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.mysite.restaurant.js.service.ReviewService;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    // 가게 리뷰와 리뷰 이미지 조회
    @GetMapping("/restaurants/{restaurant_id}/reviews")
    public Map<String, Object> getReviewsWithImages(@PathVariable("restaurant_id") Long restaurantId) {
        // 가게 리뷰 조회
        List<Reviews> reviews = reviewService.selectRestaurantReviews(restaurantId);

        // 각 리뷰에 해당하는 이미지 조회
        Map<Long, List<ReviewImg>> reviewImagesMap = new HashMap<>();
        for (Reviews review : reviews) {
            // 리뷰에 해당하는 이미지 목록을 가져와서 Map에 저장
            List<ReviewImg> imgs = reviewService.selectReviewImg(review.getReviewId());
            reviewImagesMap.put(review.getReviewId(), imgs);
        }

        // 결과를 Map으로 묶어서 반환
        Map<String, Object> result = new HashMap<>();
        result.put("reviews", reviews);
        result.put("reviewImages", reviewImagesMap);

        return result;
    }

    // 내 리뷰와 리뷰 이미지 조회
    @GetMapping("/mypage/{user_id}/reviews")
    public Map<String, Object> getMyReviewsWithImages(@PathVariable("user_id") Long userId) {
        // 사용자 리뷰 조회
        List<Reviews> reviews = reviewService.selectMyReviews(userId);

        // 리뷰 이미지 조회
        List<ReviewImg> reviewImages = new ArrayList<>();
        for (Reviews review : reviews) {
            // 리뷰에 해당하는 이미지들을 조회하여 합침
            List<ReviewImg> imgs = reviewService.selectReviewImg(review.getReviewId());
            reviewImages.addAll(imgs);
        }

        // 결과를 Map으로 묶어서 반환
        Map<String, Object> result = new HashMap<>();
        result.put("reviews", reviews);
        result.put("reviewImages", reviewImages);

        return result;
    }

    //리뷰 작성
    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(
            @RequestParam("review_content") String reviewContent,
            @RequestParam("restaurant_id") Long restaurantId,
            @RequestParam("user_id") Long userId,
            @RequestParam("taste_rating") Double tasteRating,
            @RequestParam("service_rating") Double serviceRating,
            @RequestParam("atmosphere_rating") Double atmosphereRating,
            @RequestParam("value_rating") Double valueRating,
            @RequestParam("reservation_id") Long reservationId,
            @RequestParam(value = "images", required = false) MultipartFile[] images) throws IOException {

        // 예약 정보 가져오기
        Reservation reservation = reviewService.selectReservation(reservationId);

        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
        }

        // 예약 시간과 현재 시간 비교
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reviewPossibleTime = reservation.getReservationTime().plusMinutes(30);

        if (now.isBefore(reviewPossibleTime)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can write a review only 30 minutes after the reservation.");
        }

        // 1. 리뷰 내용과 평점 처리
        Reviews review = new Reviews();
        review.setReviewContent(reviewContent);
        review.setRestaurantId(restaurantId);
        review.setUserId(userId);
        review.setTasteRating(tasteRating);
        review.setServiceRating(serviceRating);
        review.setAtmosphereRating(atmosphereRating);
        review.setValueRating(valueRating);
        review.setReservationId(reservationId);

        int reviewId = reviewService.insertReview(review);

        // 2. 이미지가 있으면 이미지 처리
        if (images != null && images.length > 0) {
            String uploadDir = "uploads/reviews";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            for (int i = 0; i < images.length; i++) {
                MultipartFile image = images[i];
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(image.getInputStream(), filePath);

                ReviewImg reviewImg = new ReviewImg();
                reviewImg.setReviewId(Long.valueOf(reviewId));
                reviewImg.setImageUrl(filePath.toString());
                reviewImg.setImageOrder(i + 1);

                reviewService.insertReviewImage(reviewImg);
            }
        }

        System.out.println("현재 시간: " + now);
        System.out.println("예약 시간: " + reservation.getReservationTime());
        System.out.println("리뷰 가능 시간: " + reviewPossibleTime);

        // 리뷰 작성 성공 시 생성된 리뷰 ID와 함께 HTTP 상태 코드 201 반환
        return ResponseEntity.status(HttpStatus.CREATED).body("Review created successfully with ID: " + reviewId);
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

    // 가게 정보
    @GetMapping("/restaurants/{restaurant_id}")
    public ResponseEntity<Map<String, Object>> getShopDetails(@PathVariable("restaurant_id") Long restaurantId) {

        // 가게 정보*이미지 조회
        Restaurants restaurant = reviewService.selectShop(restaurantId);
        List<RestaurantImg> restaurantImg = reviewService.selectShopImg(restaurantId);
        // 결과를 Map에 담기
        Map<String, Object> response = new HashMap<>();
        response.put("restaurant", restaurant);
        response.put("restaurantImg", restaurantImg);

        return ResponseEntity.ok(response);
    }
    // 예약 조회
    @GetMapping("/js/reservation/{reservation_id}")
    public Reservation getReservation(@PathVariable("reservation_id") Long reservationId) {
        return reviewService.selectReservation(reservationId);
    }
    // 유저 조회
    @GetMapping("/js/user/{user_id}")
    public User getUser(@PathVariable("user_id") Long userId) {
        return reviewService.selectUser(userId);
    }
}
