package com.mysite.restaurant.js.service;

import com.mysite.restaurant.js.model.*;
import com.mysite.restaurant.js.mapper.ReviewMapper;

import java.util.List;

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

    public int insertReview(Reviews review) {
        return reviewMapper.insertReview(review);
    }

    public int insertReviewImage(ReviewImg reviewImg) {
        return reviewMapper.insertReviewImg(reviewImg);
    }

    public int updateReview(Reviews review) {
        return reviewMapper.updateReview(review);
    }

    public int deleteReview(Long reviewId) {
        return reviewMapper.deleteReview(reviewId);
    }

    public List<Replies> selectReplies(Long reviewId) {
        return reviewMapper.selectReplies(reviewId);
    }

    public int insertReplie(Replies replies) {
        return reviewMapper.insertReplie(replies);
    }

    public int insertReport(Reports reports) {
        return reviewMapper.insertReport(reports);
    }

    public int insertHelpful(Helpful helpful) {
        return reviewMapper.insertHelpful(helpful);
    }

    public List<Reviews> selectMyReviews(Long userId) {
        return reviewMapper.selectMyReviews(userId);
    }
    
    public Restaurants selectShop(Long restaurantId) { return reviewMapper.selectShop(restaurantId); }
    public Reservation selectReservation(Long reservationId) { return reviewMapper.selectReservation(reservationId); }
    public User selectUser(Long userId) { return reviewMapper.selectUser(userId); }
}
