package com.mysite.restaurant.js.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Reviews {
	
    private Long reviewId;         // 리뷰 ID
    private Long restaurantId;     // 식당 ID
    private Long userId;           // 사용자 ID
    private Long reservationId;    // 예약 ID
    private Integer rating;    // 별점
    private String reviewContent;  // 리뷰 내용
    private Boolean isVerifiedVisit; // 방문 확인 여부
    private Integer helpfulCount;  // 유용한 리뷰 수
    private Timestamp createdAt;   // 생성일
    private Timestamp updatedAt;   // 수정일
    private Double averageRating;    // 평균 평점
}