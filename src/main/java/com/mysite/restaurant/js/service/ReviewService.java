package com.mysite.restaurant.js.service;

import com.mysite.restaurant.js.model.*;
import com.mysite.restaurant.js.mapper.ReviewMapper;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    // 리뷰 목록 조회
    public List<Reviews> selectRestaurantReviews(Long restaurantId) {
        return reviewMapper.selectRestaurantReviews(restaurantId);
    }
    // 내 리뷰 조회
    public List<Reviews> selectMyReviews(Long userId) {
        return reviewMapper.selectMyReviews(userId);
    }
    public List<Restaurants> selectMyRestaurants(Long userId) {
        return reviewMapper.selectMyRestaurants(userId);
    }

    // 리뷰 이미지 조회
    public List<ReviewImg> selectReviewImg(Long reviewId) {
        return reviewMapper.selectReviewImg(reviewId);
    }
    // 리뷰 작성
    public int insertReview(Reviews review) {
        return reviewMapper.insertReview(review);
    }
    // 리뷰 이미지 작성
    public int insertReviewImage(ReviewImg reviewImg) {
        return reviewMapper.insertReviewImg(reviewImg);
    }

    // 리뷰 수정
    public int updateReview(Reviews review) {
        return reviewMapper.updateReview(review);
    }
    // 리뷰 삭제
    public int deleteReview(Long reviewId) {
        return reviewMapper.deleteReview(reviewId);
    }

    // 답글 조회
    public List<Replies> selectReplies(Long reviewId) {
        return reviewMapper.selectReplies(reviewId);
    }
    // 답글 작성
    public int insertReplie(Replies replies) {
        return reviewMapper.insertReplie(replies);
    }

    // 신고 조회
    public List<Map<String, Object>> getReports(Long restaurantId) {
        return reviewMapper.selectReportDetails(restaurantId);
    }
    // 신고 작성
    public int insertReport(Reports reports) {
        return reviewMapper.insertReport(reports);
    }
    // 신고 삭제
    public void deleteReport(Long reportId) { reviewMapper.deleteReport(reportId);}

    // 좋아요 작성
    public void addHelpful(Helpful helpful) {
        // 이미 투표한 내역이 있는지 확인
        Boolean isHelpful = reviewMapper.isHelpfulExist(helpful.getReviewId(), helpful.getUserId());

        if (Boolean.TRUE.equals(isHelpful)) {
            throw new IllegalStateException("이미 도움이 등록된 리뷰입니다.");
        } else {
            reviewMapper.insertHelpful(helpful);
        }
    }
    public void removeHelpful(Long reviewId, Long userId) {
        // 투표한 내역이 있는지 확인 후 삭제
        Boolean isHelpful = reviewMapper.isHelpfulExist(reviewId, userId);

        if (Boolean.TRUE.equals(isHelpful)) {
            reviewMapper.deleteHelpful(reviewId, userId);
        } else {
            throw new IllegalStateException("도움이 등록되지 않은 리뷰입니다.");
        }
    }
    public boolean isHelpfulExist(Long reviewId, Long userId) {
        // 기본값을 false로 설정하여 null 반환을 방지
        Boolean isHelpful = reviewMapper.isHelpfulExist(reviewId, userId);
        return isHelpful != null && isHelpful;
    }
    public Restaurants selectShop(Long restaurantId) { return reviewMapper.selectShop(restaurantId); }
    public List<RestaurantImg> selectShopImg(Long restaurantId) { return reviewMapper.selectShopImg(restaurantId); }
    public Reservation selectReservation(Long reservationId) { return reviewMapper.selectReservation(reservationId); }
    public User selectUser(Long userId) { return reviewMapper.selectUser(userId); }
    public List<User> getAllUsers(Long restaurantId) { return reviewMapper.selectAllUser(restaurantId); }
}
