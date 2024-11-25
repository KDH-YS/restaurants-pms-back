package com.mysite.restaurant.js.service;

import com.mysite.restaurant.js.model.Helpful;
import com.mysite.restaurant.js.model.Replies;
import com.mysite.restaurant.js.model.Reports;
import com.mysite.restaurant.js.model.Restaurants;
import com.mysite.restaurant.js.model.ReviewImg;
import com.mysite.restaurant.js.model.Reviews;
import com.mysite.restaurant.js.mapper.ReviewMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    // 리뷰 목록 조회
    public List<Reviews> getReviewById(Long restaurantId) {
        return reviewMapper.getReviewById(restaurantId);
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
    
    public Restaurants selectShop(Long restaurantId) {
    	return reviewMapper.selectShop(restaurantId);
    }
}
